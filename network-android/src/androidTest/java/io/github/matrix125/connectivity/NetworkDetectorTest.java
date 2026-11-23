
package io.github.matrix125.connectivity;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented tests for {@link NetworkDetector}.
 *
 * <p>These tests run on an Android device or emulator
 * and use the real Android connectivity service.</p>
 *
 * @since 1.0.0
 */
@RunWith(AndroidJUnit4.class)
public class NetworkDetectorTest {

    /**
     * Verifies that the detector returns a valid
     * network type on an Android device.
     *
     * <p>The test does not assume Wi-Fi is active,
     * because the device's network may vary.</p>
     */
    @Test
    public void getNetworkType_returnsNonNullType() {

        Context context =
                InstrumentationRegistry
                        .getInstrumentation()
                        .getTargetContext();

        NetworkType type =
                NetworkDetector.getNetworkType(context);

        assertNotNull(type);
    }


    /**
     * Verifies that the detector identifies Wi-Fi
     * when Wi-Fi is the active default network.
     *
     * <p>This test requires the emulator's default
     * network transport to be Wi-Fi. It will fail
     * if cellular or another transport is active.</p>
     */
    @Test
    public void getNetworkType_detectsWifi() {

        Context context =
                InstrumentationRegistry
                        .getInstrumentation()
                        .getTargetContext();

        NetworkType actual =
                NetworkDetector.getNetworkType(context);

        assertEquals(NetworkType.WIFI, actual);
    }

}
