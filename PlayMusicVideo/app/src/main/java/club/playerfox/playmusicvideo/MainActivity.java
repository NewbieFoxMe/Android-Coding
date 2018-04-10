package club.playerfox.playmusicvideo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        videoView = findViewById(R.id.video_view);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },1);
        }else {
            initPlayer();
            initVideo();
        }

        Button play = findViewById(R.id.play);
        Button pause = findViewById(R.id.pause);
        Button stop = findViewById(R.id.stop);
        Button vPlay = findViewById(R.id.v_play);
        Button vPause = findViewById(R.id.v_pause);
        Button vStop = findViewById(R.id.v_stop);


        play.setOnClickListener(v -> {
            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();

        });

        pause.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying())
                mediaPlayer.pause();
        });

        stop.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()){
                mediaPlayer.reset();
                initPlayer();
            }
        });

        vPlay.setOnClickListener(v -> {
            if (!videoView.isPlaying()){
                videoView.start();
            }
        });

        vPause.setOnClickListener(v -> {
            if (videoView.isPlaying()){
                videoView.pause();
            }
        });

        vStop.setOnClickListener(v -> {
            if (videoView.isPlaying()){
                videoView.resume();
            }
        });

    }

    private void initVideo() {
        File file = new File(Environment.getExternalStorageDirectory(),"m.mp4");
        videoView.setVideoPath(file.getPath());
    }

    private void initPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"m.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        }catch (Exception e){
            Log.e(TAG, "initPlayer: " + e.toString());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initPlayer();
                    initVideo();
                }else {
                    Toast.makeText(this,"denied",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        if (videoView != null){
            videoView.suspend();
        }
    }
}
