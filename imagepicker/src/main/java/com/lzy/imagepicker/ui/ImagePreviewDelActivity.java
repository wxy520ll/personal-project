package com.lzy.imagepicker.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.R;
import com.lzy.imagepicker.bean.ImageItem;

import java.util.ArrayList;

import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧），ikkong （ikkong@163.com）
 * 版    本：1.0
 * 创建日期：2016/5/19
 * 描    述：
 * 修订历史：预览已经选择的图片，并可以删除, 感谢 ikkong 的提交
 * ================================================
 */
public class ImagePreviewDelActivity extends ImagePreviewBaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView mBtnDel = (ImageView) findViewById(R.id.btn_del);
        mBtnDel.setOnClickListener(this);
        mBtnDel.setVisibility(View.VISIBLE);
        topBar.findViewById(R.id.btn_back).setOnClickListener(this);
        mBtnDel.setImageResource(R.mipmap.grid_camera);
        //mTitleCount.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
        //滑动ViewPager的时候，根据外界的数据改变当前的选中状态和当前的图片的位置描述文本
       /* mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                mTitleCount.setText(getString(R.string.preview_image_count, mCurrentPosition + 1, mImageItems.size()));
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_del) {
            Intent intent = new Intent(ImagePreviewDelActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, imagePickerCode);
        } else if (id == R.id.btn_back) {
            onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_ITEMS) {
            if (data != null) {
                if (requestCode == imagePickerCode) {
                    showImage(data);
                }
            } else {
                Toast.makeText(ImagePreviewDelActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showImage(Intent data) {
        ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        if (images != null && images.size() > 0) {
            mImageItems.clear();
            mImageItems.addAll(images);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(ImagePreviewDelActivity.this, "拍照失败", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        //带回最新数据
        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, mImageItems);
        intent.putExtra("REQUESTCODE",imagePickerCode);
        setResult(ImagePicker.RESULT_CODE_BACK, intent);
        finish();
        super.onBackPressed();
    }

    /** 单击时，隐藏头和尾 */
    @Override
    public void onImageSingleTap() {
        if (topBar.getVisibility() == View.VISIBLE) {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_out));
            topBar.setVisibility(View.GONE);
            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.transparent);//通知栏所需颜色
            //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_in));
            topBar.setVisibility(View.VISIBLE);
            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.status_bar);//通知栏所需颜色
            //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}