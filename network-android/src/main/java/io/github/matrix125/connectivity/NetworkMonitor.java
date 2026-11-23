
package io.github.matrix125.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Looper;

/**
 * Monitors changes to the device's default network
 * and notifies a registered listener.
 *
 * <p>All listener notifications are delivered on
 * the Android main thread.</p>
 *
 * <p>Call {@link #stop()} when monitoring is no
 * longer required to release the registered
 * network callback.</p>
 *
 * <p>Both {@link #start()} and {@link #stop()}
 * must be called on the main thread.</p>
 *
 * @since 1.0.0
 */
public final class NetworkMonitor {

    private final Context context;
    private final ConnectivityManager manager;
    private final NetworkChangeListener listener;
    private final Handler mainHandler;

    private ConnectivityManager.NetworkCallback callback;
    private boolean monitoring;
    private NetworkType lastType;

    private int generation;

    /**
     * Creates a network monitor.
     *
     * @param context the Android context used to
     *                access connectivity services
     * @param listener the listener that receives
     *                 network transport changes
     * @throws IllegalArgumentException if either
     *                                  argument is null
     * @since 1.0.0
     */
    public NetworkMonitor(
            Context context,
            NetworkChangeListener listener) {

        if (context == null || listener == null) {
            throw new IllegalArgumentException(
                    "Context and listener cannot be null");
        }

        this.context = context.getApplicationContext();
        this.listener = listener;
        this.mainHandler =
                new Handler(Looper.getMainLooper());

        this.manager = (ConnectivityManager)
                this.context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Starts monitoring the default network.
     *
     * <p>Repeated calls while monitoring is active
     * have no effect.</p>
     *
     * <p>An initial network type is delivered after
     * successful registration. Subsequent notifications
     * are delivered only when the type changes.</p>
     *
     * @throws IllegalStateException if called from
     *                               outside the main thread
     * @throws RuntimeException if Android fails to
     *                          register the callback
     * @since 1.0.0
     */
    public void start() {

        requireMainThread();

        if (monitoring) {
            return;
        }

        if (manager == null) {
            listener.onNetworkChanged(NetworkType.NONE);
            return;
        }

        final int session = ++generation;

        ConnectivityManager.NetworkCallback newCallback =
                new ConnectivityManager.NetworkCallback() {

                    @Override
                    public void onAvailable(Network network) {
                        refreshNetworkType(session);
                    }

                    @Override
                    public void onLost(Network network) {
                        refreshNetworkType(session);
                    }

                    @Override
                    public void onCapabilitiesChanged(
                            Network network,
                            NetworkCapabilities capabilities) {

                        refreshNetworkType(session);
                    }
                };

        manager.registerDefaultNetworkCallback(newCallback);

        callback = newCallback;
        monitoring = true;
        lastType = null;

        refreshNetworkType(session);
    }

    /**
     * Stops monitoring network changes.
     *
     * <p>Repeated calls when monitoring is already
     * stopped have no effect.</p>
     *
     * <p>Pending notifications from the stopped
     * monitoring session are invalidated.</p>
     *
     * @throws IllegalStateException if called from
     *                               outside the main thread
     * @since 1.0.0
     */
    public void stop() {

        requireMainThread();

        if (!monitoring) {
            return;
        }

        monitoring = false;
        generation++;

        ConnectivityManager.NetworkCallback oldCallback =
                callback;

        callback = null;
        lastType = null;

        if (oldCallback != null) {
            manager.unregisterNetworkCallback(oldCallback);
        }
    }

    /**
     * Schedules a network transport refresh on
     * the Android main thread.
     *
     * <p>The session identifier prevents callbacks
     * from a previous monitoring session from
     * delivering stale notifications.</p>
     *
     * @param session the monitoring session that
     *                requested the refresh
     */
    private void refreshNetworkType(int session) {

        mainHandler.post(() -> {

            if (!monitoring || session != generation) {
                return;
            }

            NetworkType currentType =
                    NetworkDetector.getNetworkType(context);

            if (currentType != lastType) {
                lastType = currentType;
                listener.onNetworkChanged(currentType);
            }
        });
    }

    /**
     * Verifies that the current method is executing
     * on the Android main thread.
     *
     * @throws IllegalStateException if the current
     *                               thread is not the
     *                               Android main thread
     */
    private void requireMainThread() {

        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException(
                    "This operation must be called "
                            + "on the main thread");
        }
    }
}
