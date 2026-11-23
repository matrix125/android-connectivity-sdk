
package io.github.matrix125.connectivity;

import java.util.Objects;

/**
 * Represents an immutable snapshot of the device's
 * default network connectivity state.
 *
 * <p>A network may be connected without having
 * Android-validated internet access.</p>
 *
 * <p>Instances can be compared by value using
 * {@link #equals(Object)}.</p>
 *
 * @since 1.0.0
 */
public final class NetworkStatus {

    private final NetworkType networkType;
    private final boolean internetValidated;
    private final boolean metered;

    /**
     * Creates a network status snapshot.
     *
     * @param networkType the detected network transport
     * @param internetValidated true when Android has
     *                          validated internet access
     * @param metered true when Android considers the
     *                connection metered
     * @throws NullPointerException if networkType is null
     * @since 1.0.0
     */
    public NetworkStatus(
            NetworkType networkType,
            boolean internetValidated,
            boolean metered) {

        this.networkType =
                Objects.requireNonNull(
                        networkType,
                        "networkType cannot be null");

        this.internetValidated = internetValidated;
        this.metered = metered;
    }

    /**
     * Returns the network transport type.
     *
     * @return the network transport type
     */
    public NetworkType getNetworkType() {
        return networkType;
    }

    /**
     * Indicates whether Android has validated
     * internet connectivity.
     *
     * @return true when internet is validated
     */
    public boolean isInternetValidated() {
        return internetValidated;
    }

    /**
     * Indicates whether the network is metered.
     *
     * <p>Metered connections may have usage
     * limits or additional data charges.</p>
     *
     * @return true when the network is metered
     */
    public boolean isMetered() {
        return metered;
    }

    /**
     * Compares this status with another object.
     *
     * @param object the object to compare
     * @return true when all status fields match
     */
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof NetworkStatus)) {
            return false;
        }

        NetworkStatus other = (NetworkStatus) object;

        return networkType == other.networkType
                && internetValidated == other.internetValidated
                && metered == other.metered;
    }

    /**
     * Returns a hash code based on all status fields.
     *
     * @return the status hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                networkType,
                internetValidated,
                metered);
    }

    /**
     * Returns a readable representation of the
     * network status.
     *
     * @return a string containing the status fields
     */
    @Override
    public String toString() {

        return "NetworkStatus{"
                + "networkType=" + networkType
                + ", internetValidated=" + internetValidated
                + ", metered=" + metered
                + '}';
    }
}
