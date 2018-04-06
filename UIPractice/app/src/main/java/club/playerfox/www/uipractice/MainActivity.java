package club.playerfox.www.uipractice;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context contextn = this;
    private Button send;
    private EditText msg;
    private RecyclerView recyclerView;
    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        binds();

    }

    private void initData() {
        Message msg1 = new Message("hello",Message.TYPE_RECEVIE);
        messageList.add(msg1);
        Message msg2 = new Message("hello? who are you?",Message.TYPE_SEND);
        messageList.add(msg2);
        Message msg3 = new Message("I'm jack,?",Message.TYPE_RECEVIE);
        messageList.add(msg3);
    }

    private void binds() {
        send = findViewById(R.id.msg_send);
        msg = findViewById(R.id.msg);
        recyclerView = findViewById(R.id.msg_recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(contextn);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(adapter);
        send.setOnClickListener(v -> {
            String content = msg.getText().toString();
            if (!"".equals(content)){
                Message message = new Message(content,Message.TYPE_SEND);
                messageList.add(message);
                adapter.notifyItemInserted(messageList.size() - 1);
                recyclerView.scrollToPosition(messageList.size() - 1);
                msg.setText(null);
            }
        });
    }
}
