package io.github.matrix125.connectivity;

/**
 * Receives notifications when the device's
 * default network transport changes.
 *
 * @since 1.0.0
 */
public interface NetworkChangeListener {

    /**
     * Called when the detected network type changes.
     *
     * @param networkType the new network type
     */
    void onNetworkChanged(NetworkType networkType);
}
