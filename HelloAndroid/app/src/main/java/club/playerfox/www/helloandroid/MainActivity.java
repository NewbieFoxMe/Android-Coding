package club.playerfox.www.helloandroid;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//设置布局文件

        Button button = findViewById(R.id.btn_click_me);
        button.setOnClickListener(v -> {
            Toast.makeText(context,"Jump to page2",Toast.LENGTH_SHORT).show();//Toast显示提示
            Intent intent = new Intent(context,Page2Activity.class);
            //Intent intent = new Intent("club.playerfox.www.ACTION_PAGE2");
            startActivity(intent);//显式Intent
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_baidu:
                Intent baidu = new Intent(Intent.ACTION_VIEW);
                baidu.setData(Uri.parse("https://www.baidu.com"));
                startActivity(baidu);
                break;
            case R.id.item_tel:
                Intent tel = new Intent(Intent.ACTION_DIAL);
                tel.setData(Uri.parse("tel:10086"));
                startActivity(tel);
                break;
            case R.id.item_exit:
                finish();
        }
        return true;
    }
}
