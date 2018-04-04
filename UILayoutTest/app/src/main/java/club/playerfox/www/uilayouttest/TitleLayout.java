package club.playerfox.www.uilayouttest;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.jar.Attributes;

public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.titlebar,this);
        Button back = findViewById(R.id.btn_back);
        Button edit = findViewById(R.id.btn_edit);
        back.setOnClickListener(v -> ((Activity)getContext()).finish());
        edit.setOnClickListener(v ->
                Toast.makeText(getContext(),"Edit Button Clicked",Toast.LENGTH_SHORT).show());
    }
}
