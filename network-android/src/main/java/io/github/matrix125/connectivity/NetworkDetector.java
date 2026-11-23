
package io.github.matrix125.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

/**
 * Provides utility methods for detecting the active
 * network transport on an Android device.
 *
 * <p>This class uses Android's ConnectivityManager
 * and NetworkCapabilities APIs.</p>
 *
 * <p>Network transport detection does not guarantee
 * that an internet endpoint is reachable.</p>
 *
 * @since 1.0.0
 */
public final class NetworkDetector {

    /**
     * Prevents instantiation of this utility class.
     */
    private NetworkDetector() {
        // Utility class.
    }

    /**
     * Determines the transport type of the current
     * default network.
     *
     * @param context the Android context used to
     *                access system connectivity services
     * @return the detected network transport type,
     *         or NONE if no default network or
     *         capabilities are available
     * @since 1.0.0
     */
    public static NetworkType getNetworkType(Context context) {

        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);

        if (manager == null) {
            return NetworkType.NONE;
        }

        Network network = manager.getActiveNetwork();

        if (network == null) {
            return NetworkType.NONE;
        }

        NetworkCapabilities capabilities =
                manager.getNetworkCapabilities(network);

        return classifyNetwork(capabilities);
    }

    /**
     * Classifies Android network capabilities into
     * a supported network transport type.
     *
     * <p>Returns NONE when capabilities are unavailable.
     * Otherwise, delegates transport priority decisions
     * to {@link #classifyTransport(boolean, boolean, boolean)}.</p>
     *
     * @param capabilities Android network capabilities,
     *                     or null if unavailable
     * @return the classified network transport type,
     *         or NONE when capabilities are null
     * @since 1.0.0
     */
    static NetworkType classifyNetwork(
            NetworkCapabilities capabilities) {

        if (capabilities == null) {
            return NetworkType.NONE;
        }

        return classifyTransport(
                capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_WIFI),
                capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR),
                capabilities.hasTransport(
                        NetworkCapabilities.TRANSPORT_ETHERNET)
        );
    }


    /**
     * Classifies a network using its transport indicators.
     *
     * <p>Transport priority is Wi-Fi, followed by
     * cellular and Ethernet. If no supported transport
     * is present, OTHER is returned.</p>
     *
     * <p>This method contains no Android framework
     * dependencies, allowing its decision logic
     * to be tested using local JVM unit tests.</p>
     *
     * @param wifi true if Wi-Fi transport is present
     * @param cellular true if cellular transport is present
     * @param ethernet true if Ethernet transport is present
     * @return the classified network transport type
     * @since 1.0.0
     */
    static NetworkType classifyTransport(
            boolean wifi,
            boolean cellular,
            boolean ethernet) {

        if (wifi) {
            return NetworkType.WIFI;
        }

        if (cellular) {
            return NetworkType.CELLULAR;
        }

        if (ethernet) {
            return NetworkType.ETHERNET;
        }

        return NetworkType.OTHER;
    }

}
