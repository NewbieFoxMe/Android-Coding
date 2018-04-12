package club.playerfox.downloadertest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class DownloadService extends Service {

    private DownloadTask task;
    private String downloadUrl;
    private final DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification("Downloading ...", progress));
        }

        @Override
        public void onSuccess() {
            task = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Success", -1));
            Toast.makeText(DownloadService.this, "Download Success", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            task = null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("Download Failed", -1));
            Toast.makeText(DownloadService.this, "Download Failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            task = null;
            Toast.makeText(DownloadService.this, "Paused", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            task = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private NotificationManager getNotificationManager(){
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title,int progress){
        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"default");
        builder.setSmallIcon(R.drawable.download);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(title);
        if (progress > 0){
            builder.setContentText(progress + "%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }

    public DownloadService() {
    }

    class MyBinder extends Binder{
        public void startDownload(String url){
            if (task == null){
                downloadUrl = url;
                task = new DownloadTask(listener);
                task.execute(url);
                startForeground(1,getNotification("Downloading ...",0));
                Toast.makeText(DownloadService.this,"start download",Toast.LENGTH_SHORT).show();
            }
        }

        public void pausedDownload(){
            if (task != null){
                task.pauseDownload();
            }
        }

        public void canceledDownload(){
            if (task != null){
                task.cancelDownload();
            }else {
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this,"Canceled",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
}
