
package io.github.matrix125.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

/**
 * Provides utility methods for checking whether
 * Android has validated internet connectivity.
 *
 * <p>Network validation is performed by Android.
 * This class does not send its own network requests.</p>
 *
 * <p>A validated network does not guarantee that
 * every remote server or endpoint is reachable.</p>
 *
 * @since 1.0.0
 */
public final class InternetConnectivity {

    /**
     * Prevents instantiation of this utility class.
     */
    private InternetConnectivity() {
        // Utility class.
    }

    /**
     * Determines whether the device's current default
     * network has Android-validated internet connectivity.
     *
     * @param context the Android context used to
     *                access connectivity services
     * @return true if Android reports that the active
     *         network has validated internet connectivity;
     *         false otherwise
     * @throws IllegalArgumentException if context is null
     * @since 1.0.0
     */
    public static boolean isInternetAvailable(Context context) {

        if (context == null) {
            throw new IllegalArgumentException(
                    "Context cannot be null");
        }

        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return false;
        }

        Network network = manager.getActiveNetwork();

        if (network == null) {
            return false;
        }

        NetworkCapabilities capabilities =
                manager.getNetworkCapabilities(network);

        if (capabilities == null) {
            return false;
        }

        return capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }
}
