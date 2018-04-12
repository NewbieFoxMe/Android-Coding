package club.playerfox.downloadertest;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = -1;
    public static final int TYPE_PAUSED = 1;
    public static final int TYPE_CANCELED = -2;

    private DownloadListener listener;
    private File file;

    private boolean isCanceled = false;

    public void cancelDownload(){
        isCanceled = true;
    }

    private boolean isPaused = false;

    public void pauseDownload(){
        isPaused = true;
    }

    private int lastProgress = 0;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        long downloadLength = 0;
        String downloadUrl = strings[0];
        String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        String directory = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS).getPath();

        file = new File(directory,fileName);
        if (file.exists()){
            downloadLength = file.length();
        }
        long contentLength = getContentLength(downloadUrl);
        if (contentLength == 0){
            return TYPE_FAILED;
        }else if (contentLength == downloadLength){
            return TYPE_SUCCESS;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .addHeader("RANGE","bytes=" + downloadLength + "-")
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return TYPE_FAILED;
        }
        if (response != null){

            try (
                    InputStream in = response.body().byteStream();
                    RandomAccessFile  savedFile = new RandomAccessFile(file,"rw")
            ){
                savedFile.seek(downloadLength);
                byte[] bytes = new byte[1024];
                int len;
                int total = 0;
                while ((len = in.read(bytes)) != -1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(bytes,0,len);
                        int progress = (int) ((total + downloadLength) / 100 / contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }catch (IOException e){
                e.printStackTrace();
                return TYPE_FAILED;
            }
        }

        return null;
    }

    private long getContentLength(String downloadUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                //deleteFile();
                listener.onFailed();
                break;
            case TYPE_CANCELED:
                deleteFile();
                listener.onCanceled();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
        }
    }

    private void deleteFile(){
        if (file != null){
            file.delete();
        }
    }
}
