package club.playerfox.www.fragmenttest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RightFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("RightFragment:", "onCreateView");
        return inflater.inflate(R.layout.right_fragment,container,false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RightFragment:", "onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("RightFragment:", "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("RightFragment:", "onActivityStart");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("RightFragment:", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("RightFragment:", "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("RightFragment:", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("RightFragment:", "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("RightFragment:", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("RightFragment:", "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("RightFragment:", "onDetach");
    }
}
