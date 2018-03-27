package toong.vn.androidmixboundandunboundservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class MixBoundAndUnboundService extends Service {
    public static String TAG = "BoundService";
    private final IBinder binder = new LocalBinder();
    List<BoundServiceClient> mBoundServiceClients = new ArrayList<>();
    private long value;
    private CountDownTimer countDownTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
        countDownTimer = new CountDownTimer(3000000, 1000) {

            public void onTick(long millisUntilFinished) {
                value = millisUntilFinished;
                notifyClient((int) value);
            }

            public void onFinish() {
            }

        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return START_NOT_STICKY;
    }


    class LocalBinder extends Binder {
        MixBoundAndUnboundService getService() {
            return MixBoundAndUnboundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.i(TAG, "onRebind");
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        countDownTimer.cancel();
    }

    public void addBoundServiceClient(BoundServiceClient client) {
        mBoundServiceClients.add(client);
    }

    public void removeBoundServiceClient(BoundServiceClient client) {
        mBoundServiceClients.remove(client);
    }

    public void notifyClient(int value) {
        for (BoundServiceClient serviceClient : mBoundServiceClients) {
            serviceClient.doSomething(value);
        }
    }
}