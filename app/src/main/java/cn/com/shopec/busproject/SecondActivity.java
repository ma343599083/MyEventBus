package cn.com.shopec.busproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        MyEventBus.getInstance().register(this);
        findViewById(R.id.tv_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer i = 0;
                if(i instanceof Object){

                }
                MyEventBus.getInstance().post("abab",100);
            }
        });
    }


    @Subscribe("abab")
    public void play222() {
        Log.d("myq", "SecondActivity---play222: ");
        Toast.makeText(this,"play222",Toast.LENGTH_SHORT).show();
    }
}
