package forfun.good.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    NotificationChannel channel01;
    String idlalaland = "lalaland";
    NotificationManager nm;
    int NOTIFICATION_ID = 888; //設定通知的宣告

    public MyService() {


    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Myservice","this is onCreate");
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //啟動就開啟通知服務
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService","this is onStartCommand");
        new Thread(){ //用新執行緒 防止當掉
            @Override
            public void run() {
                super.run();
                int i;
                for(i = 0; i>=0; i++)
                {
                    try {
                        Thread.sleep(800);
                        Log.d("MyService","Delay, i="+i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                sentNotification(); //結束之後做動作
            }
        }.start();


        return super.onStartCommand(intent, flags, startId);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @SuppressWarnings("deprecation")
    private void sentNotification() {
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= 26) {
            builder = new Notification.Builder(MyService.this, idlalaland);
        } else {
            builder = new Notification.Builder(MyService.this);
        }
        builder.setContentTitle("測試123");
        builder.setContentText("這是內容");
        if (Build.VERSION.SDK_INT >= 26)
        {
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        }
        else
        {
            builder.setSmallIcon(R.mipmap.ic_launcher);
        }
        builder.setAutoCancel(true);

        Notification notify = builder.build();
        nm.notify(NOTIFICATION_ID, notify);

    }

    @TargetApi(Build.VERSION_CODES.O)
    // 8.0的設定
    public void regChannel()
    {
        channel01 = new NotificationChannel(
                idlalaland,
                "Channel LALA",
                NotificationManager.IMPORTANCE_HIGH);
        channel01.setDescription("最重的人");
        channel01.enableLights(true);
        channel01.enableVibration(true);

        nm.createNotificationChannel(channel01);
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
