package club.playerfox.www.activitylifecycletest;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity a : activities){
            if (!a.isFinishing()){
                Log.d("App PID:" + android.os.Process.myPid(), a.getClass().getSimpleName());
                a.finish();
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
