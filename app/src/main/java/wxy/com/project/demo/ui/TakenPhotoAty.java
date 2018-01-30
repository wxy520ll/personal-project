package wxy.com.project.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import wxy.com.project.R;

/**
 * Created by jiajun.wang on 2018/1/2.
 */

public class TakenPhotoAty extends AppCompatActivity {

    private Button btPhoto;
    private TextView textView1;
    private TextView textView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taken_photo);
        btPhoto= (Button) findViewById(R.id.btPhoto);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
