
package io.github.matrix125.connectivity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the NetworkType enum.
 *
 * <p>These tests run on the local JVM and
 * do not require an Android emulator.</p>
 */
public class NetworkTypeTest {

    /**
     * Verifies that all expected network
     * transport types are defined.
     */
    @Test
    public void networkType_containsExpectedValues() {

        NetworkType[] types = NetworkType.values();

        assertEquals(5, types.length);

        assertNotNull(NetworkType.WIFI);
        assertNotNull(NetworkType.CELLULAR);
        assertNotNull(NetworkType.ETHERNET);
        assertNotNull(NetworkType.OTHER);
        assertNotNull(NetworkType.NONE);
    }

    /**
     * Verifies that a network type can be
     * resolved from its enum name.
     */
    @Test
    public void networkType_valueOfReturnsCorrectType() {

        NetworkType type = NetworkType.valueOf("WIFI");

        assertEquals(NetworkType.WIFI, type);
    }

    /**
     * Verifies that enum names remain stable.
     *
     * <p>Applications may use these names for
     * logging, diagnostics, or serialization.</p>
     */
    @Test
    public void networkType_namesAreCorrect() {

        assertEquals("WIFI", NetworkType.WIFI.name());
        assertEquals("CELLULAR", NetworkType.CELLULAR.name());
        assertEquals("ETHERNET", NetworkType.ETHERNET.name());
        assertEquals("OTHER", NetworkType.OTHER.name());
        assertEquals("NONE", NetworkType.NONE.name());
    }
}
