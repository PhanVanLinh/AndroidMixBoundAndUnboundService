package toong.vn.androidmixboundandunboundservice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements BoundServiceClient {
    private String TAG = getClass().getSimpleName();
    private ServiceHandler serviceHandler;
    private Button btnStartService;
    private Button btnStopService;
    private Button btnBindService;
    private Button btnUnbindService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serviceHandler = new ServiceHandler(this);

        btnStartService = findViewById(R.id.buttonStartService);
        btnStopService = findViewById(R.id.buttonStopService);
        btnBindService = findViewById(R.id.buttonBindService);
        btnUnbindService = findViewById(R.id.buttonUnbindService);

        btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService();
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService();
            }
        });

        btnBindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService();
            }
        });

        btnUnbindService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
            }
        });
    }

    private void startService() {
        serviceHandler.startService();
    }

    private void stopService() {
        serviceHandler.stopService();
    }

    private void bindService() {
        serviceHandler.bindService(this);
    }

    private void unbindService() {
        serviceHandler.unbind();
    }

    @Override
    public void doSomething(int value) {
        Log.i(TAG, "value = " + value);
    }
}
