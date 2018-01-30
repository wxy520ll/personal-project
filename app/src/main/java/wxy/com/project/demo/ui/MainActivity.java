package wxy.com.project.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import wxy.com.project.R;

/**
 * Created by jiajun.wang on 2017/12/31.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button takenInforButton;
    private Button takenPhoto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takenInforButton= (Button) findViewById(R.id.taken_infor_button);
        takenPhoto= (Button) findViewById(R.id.taken_photo);
        takenInforButton.setOnClickListener(this);
        takenPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.taken_infor_button:
                intent.setClass(MainActivity.this,TakenInputWidgetActivity.class);
                break;
            case R.id.taken_photo:
                intent.setClass(MainActivity.this,TakenPhotoAty.class);
                break;
        }
        startActivity(intent);
    }

}
