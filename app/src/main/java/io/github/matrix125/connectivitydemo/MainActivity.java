
package io.github.matrix125.connectivitydemo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.github.matrix125.connectivity.InternetConnectivity;
import io.github.matrix125.connectivity.NetworkDetector;
import io.github.matrix125.connectivity.NetworkMonitor;
import io.github.matrix125.connectivity.NetworkType;

/**
 * Demonstrates network transport detection and
 * internet validation using the Connectivity SDK.
 *
 * <p>The activity supports both manual network
 * checks and automatic monitoring of network
 * transport changes.</p>
 *
 * <p>Monitoring starts when the activity becomes
 * visible and stops when it is no longer visible.</p>
 *
 * @since 1.0.0
 */
public class MainActivity extends AppCompatActivity {

    private TextView networkStatus;
    private NetworkMonitor networkMonitor;

    /**
     * Initializes the activity, its UI components,
     * and the network monitor.
     *
     * @param savedInstanceState previously saved
     *                           activity state, if any
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (view, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    view.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        networkStatus = findViewById(R.id.networkStatus);

        Button checkButton =
                findViewById(R.id.checkNetworkButton);

        checkButton.setOnClickListener(
                view -> checkNetwork()
        );

        networkMonitor = new NetworkMonitor(
                this,
                this::updateNetworkStatus
        );
    }

    /**
     * Starts automatic network monitoring when
     * the activity becomes visible.
     */
    @Override
    protected void onStart() {

        super.onStart();

        networkMonitor.start();
    }

    /**
     * Stops automatic network monitoring when
     * the activity is no longer visible.
     */
    @Override
    protected void onStop() {

        networkMonitor.stop();

        super.onStop();
    }

    /**
     * Performs a manual network transport and
     * internet validation check.
     *
     * <p>The result is displayed using the
     * existing network status TextView.</p>
     */
    private void checkNetwork() {

        NetworkType type =
                NetworkDetector.getNetworkType(this);

        updateNetworkStatus(type);
    }

    /**
     * Updates the displayed network transport
     * and Android internet validation status.
     *
     * <p>This method is invoked on the main
     * thread by the network monitor or by
     * a manual button click.</p>
     *
     * @param type the detected network transport
     */
    private void updateNetworkStatus(NetworkType type) {

        boolean internetAvailable =
                InternetConnectivity.isInternetAvailable(this);

        String internetStatus =
                internetAvailable ? "YES" : "NO";

        String status =
                "Active network: " + type.name()
                        + "\nInternet validated: "
                        + internetStatus;

        networkStatus.setText(status);
    }
}
