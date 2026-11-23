
package io.github.matrix125.connectivity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Local JVM unit tests for network transport classification.
 *
 * <p>These tests verify the classification decisions
 * without requiring an Android device, emulator,
 * or mocking framework.</p>
 *
 * @since 1.0.0
 */
public class NetworkTransportTest {

    /**
     * Verifies that Wi-Fi is correctly identified.
     */
    @Test
    public void classifyTransport_detectsWifi() {

        NetworkType actual =
                NetworkDetector.classifyTransport(
                        true, false, false);

        assertEquals(NetworkType.WIFI, actual);
    }

    /**
     * Verifies that cellular is correctly identified.
     */
    @Test
    public void classifyTransport_detectsCellular() {

        NetworkType actual =
                NetworkDetector.classifyTransport(
                        false, true, false);

        assertEquals(NetworkType.CELLULAR, actual);
    }

    /**
     * Verifies that Ethernet is correctly identified.
     */
    @Test
    public void classifyTransport_detectsEthernet() {

        NetworkType actual =
                NetworkDetector.classifyTransport(
                        false, false, true);

        assertEquals(NetworkType.ETHERNET, actual);
    }

    /**
     * Verifies that an unrecognized transport
     * combination is classified as OTHER.
     */
    @Test
    public void classifyTransport_detectsOther() {

        NetworkType actual =
                NetworkDetector.classifyTransport(
                        false, false, false);

        assertEquals(NetworkType.OTHER, actual);
    }

    /**
     * Verifies that Wi-Fi takes priority when
     * multiple transports are present.
     */
    @Test
    public void classifyTransport_prioritizesWifi() {

        NetworkType actual =
                NetworkDetector.classifyTransport(
                        true, true, true);

        assertEquals(NetworkType.WIFI, actual);
    }

    /**
     * Verifies that cellular takes priority over
     * Ethernet when Wi-Fi is absent.
     */
    @Test
    public void classifyTransport_prioritizesCellular() {

        NetworkType actual =
                NetworkDetector.classifyTransport(
                        false, true, true);

        assertEquals(NetworkType.CELLULAR, actual);
    }
}
