package club.playerfox.litepal_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button create = findViewById(R.id.create);
        create.setOnClickListener(v -> {
            Connector.getDatabase();
        });

        Button add = findViewById(R.id.add);
        add.setOnClickListener(v -> {
            Book book = new Book();
            book.setName("傻逼如何养成");
            book.setAuthor("李沈阳");
            book.setPrice(1.1);
            book.setPages(999);
            book.save();
        });

        Button update = findViewById(R.id.update);
        update.setOnClickListener(v -> {
            Book book = new Book();
            book.setPrice(0.99);
            book.updateAll("name=? and author=?","傻逼如何养成","李沈阳");
        });

        Button delete = findViewById(R.id.delete);
        delete.setOnClickListener(v -> {
            Book book = new Book();
            DataSupport.deleteAll(Book.class,"price > ?","1");
        });
        TextView queryRes = findViewById(R.id.query_res);
        Button query =findViewById(R.id.query);
        query.setOnClickListener(v -> {
            queryRes.setText(null);
            List<Book> books = DataSupport.findAll(Book.class);
            for (Book book: books){
                queryRes.append("name:" + book.getName());
                queryRes.append(",author:" + book.getAuthor());
                queryRes.append(",price:" + book.getPrice());
                queryRes.append(",pages:" + book.getPages() + "\n");
            }
        });

    }
}
