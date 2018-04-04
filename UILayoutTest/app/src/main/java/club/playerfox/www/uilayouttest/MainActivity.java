package club.playerfox.www.uilayouttest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private List<Fruit> fruitList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        initFruits();
        FruitAdapter adapter = new FruitAdapter(context,R.layout.fruit_item,fruitList);
        ListView listView = findViewById(R.id.lv_show);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Fruit fruit = fruitList.get(position);
            Toast.makeText(context,fruit.getName(),Toast.LENGTH_SHORT).show();
        });
    }

    private void initFruits() {
        Fruit fruit = new Fruit("Apple",R.drawable.apple);
        fruitList.add(fruit);
    }
}
