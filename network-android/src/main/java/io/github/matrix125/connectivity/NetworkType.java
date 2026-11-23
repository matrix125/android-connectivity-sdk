
package io.github.matrix125.connectivity;

/**
 * Represents the transport type of an Android device's
 * current default network.
 *
 * <p>This enum is returned by
 * {@link NetworkDetector#getNetworkType(android.content.Context)}
 * to identify the detected network transport.</p>
 *
 * <p>A detected transport does not necessarily mean
 * that the internet or a remote server is reachable.</p>
 *
 * @since 1.0.0
 */
public enum NetworkType {

    /**
     * The active network uses Wi-Fi transport.
     */
    WIFI,

    /**
     * The active network uses cellular transport,
     * such as LTE or 5G.
     */
    CELLULAR,

    /**
     * The active network uses Ethernet transport.
     */
    ETHERNET,

    /**
     * The active network uses another transport type
     * not explicitly identified by this SDK.
     */
    OTHER,

    /**
     * No active default network was detected,
     * or its information was unavailable.
     */
    NONE
}
