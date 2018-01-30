package wxy.com.project.demo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import wxy.com.ibrary.widget.TakenPhotoView;
import wxy.com.project.R;

/**
 * Created by jiajun.wang on 2018/1/2.
 */

public class TakenPhotoAty extends AppCompatActivity {

    private Button btPhoto;
    private TextView textView1;
    private TextView textView2;
    private  wxy.com.ibrary.widget.TakenPhotoView ivPhoto;
    private  wxy.com.ibrary.widget.TakenPhotoView ivPhoto2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taken_photo);
        btPhoto= (Button) findViewById(R.id.btPhoto);
        ivPhoto= (TakenPhotoView) findViewById(R.id.ivPhoto);
        ivPhoto2= (TakenPhotoView) findViewById(R.id.ivPhoto2);
        textView1= (TextView) findViewById(R.id.text1);
        textView2= (TextView) findViewById(R.id.text2);
        ivPhoto.showHttpImage("http://pic4.nipic.com/20091217/3885730_124701000519_2.jpg");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ivPhoto.onActivityResult(requestCode,resultCode,data);
        ivPhoto2.onActivityResult(requestCode,resultCode,data);
        textView1.setText(ivPhoto.getFilePath());
        textView2.setText(ivPhoto2.getFilePath());
    }
}
