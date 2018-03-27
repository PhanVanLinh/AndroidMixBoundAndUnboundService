package toong.vn.androidmixboundandunboundservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

public class ServiceHandler {
    private MixBoundAndUnboundService mService;
    private Context mContext;
    private BoundServiceClient mClient;

    public ServiceHandler(Context context) {
        //        mContext = context.getApplicationContext();
        mContext = context;
    }

    ServiceConnection weatherServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(MixBoundAndUnboundService.TAG, "onServiceConnected");
            MixBoundAndUnboundService.LocalBinder binder =
                    (MixBoundAndUnboundService.LocalBinder) service;
            mService = binder.getService();
            mService.addBoundServiceClient(mClient);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(MixBoundAndUnboundService.TAG, "onServiceDisconnected");
        }
    };

    public void startService() {
        mContext.startService(new Intent(mContext, MixBoundAndUnboundService.class));
    }

    public void stopService() {
        mContext.stopService(new Intent(mContext, MixBoundAndUnboundService.class));
    }

    public void bindService(BoundServiceClient client) {
        Intent intent = new Intent(mContext, MixBoundAndUnboundService.class);
        mContext.bindService(intent, weatherServiceConnection, Context.BIND_AUTO_CREATE);
        mClient = client;
    }

    public void unbind() {
        if (mService != null) {
            mContext.unbindService(weatherServiceConnection);
        }
    }
}