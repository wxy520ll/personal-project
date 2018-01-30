package wxy.com.ibrary.widget;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.util.StringUtil;

import java.util.ArrayList;

import wxy.com.ibrary.R;
import wxy.com.ibrary.utils.DensityUtils;
import wxy.com.ibrary.utils.GlideUtils;

import static com.lzy.imagepicker.ImagePicker.REQUEST_CODE_PREVIEW;
import static com.lzy.imagepicker.ImagePicker.RESULT_CODE_ITEMS;

/**
 * Created by jiajun.wang on 2018/1/2.
 */

public class TakenPhotoView extends LinearLayout {
    private int IMAGE_PICKER = 0;
    private Context context;
    private int drawErrorImage;
    private int drawHolderImage;
    private int drawSrcImage;
    private boolean isAlbum;//是进入相册还是直接拍照，默认进入相册
    private float tipsTextSize;
    private int tipsTextColor;
    private String tipsText = "";

    private ImageView ivPhoto;
    private String filePath;//拍完照片的路径
    private TextView tvTips;
    private View mView;
    private boolean isShow = false;

    public TakenPhotoView(Context context) {
        super(context);
    }

    public TakenPhotoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context, attrs);
    }

    public TakenPhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
    }

    private void initParams(Context context, AttributeSet attrs) {
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TakenPhotoView);
        if (typedArray != null) {
            drawErrorImage = typedArray.getResourceId(R.styleable.TakenPhotoView_error_icon, R.mipmap.ic_launcher);
            drawHolderImage = typedArray.getResourceId(R.styleable.TakenPhotoView_holder, R.mipmap.ic_launcher);
            drawSrcImage = typedArray.getResourceId(R.styleable.TakenPhotoView_src, R.mipmap.ic_launcher);
            isAlbum = typedArray.getBoolean(R.styleable.TakenPhotoView_album, true);
            IMAGE_PICKER = typedArray.getInt(R.styleable.TakenPhotoView_request_code, 0);
            tipsTextSize = typedArray.getDimension(R.styleable.TakenPhotoView_tips_size, 15f);
            tipsTextColor = typedArray.getColor(R.styleable.TakenPhotoView_tips_color, Color.BLACK);
            isShow = typedArray.getBoolean(R.styleable.TakenPhotoView_tip_show, false);
            tipsText = typedArray.getString(R.styleable.TakenPhotoView_tip_text);
            typedArray.recycle();
        }
        mView = View.inflate(context, R.layout.layout_taken_photo, this);
        setParams();
    }


    private void setParams() {
        ivPhoto = mView.findViewById(R.id.iv_photo);
        tvTips = mView.findViewById(R.id.tv_tips);
        tvTips.setTextColor(tipsTextColor);
        tvTips.setTextSize(DensityUtils.px2dip(context, tipsTextSize));
        tvTips.setText(tipsText);
        tvTips.setVisibility(isShow ? View.VISIBLE : View.GONE);
        ivPhoto.setImageResource(drawSrcImage);
        ivPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmpty(filePath)) {
                    takenPhoto();
                } else {
                    ImageItem imageItem = new ImageItem();
                    imageItem.path = filePath;
                    ArrayList<ImageItem> imageItemList = new ArrayList<ImageItem>();
                    imageItemList.add(imageItem);
                    Intent intentPreview = new Intent(context, ImagePreviewDelActivity.class);
                    intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, imageItemList);
                    intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, 0);
                    intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                    intentPreview.putExtra("REQUESTCODE", IMAGE_PICKER);
                    ((AppCompatActivity) context).startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                }
            }
        });
    }

    public void takenPhoto() {
        if (IMAGE_PICKER == 0) {
            Toast.makeText(context, "requestCode 需要手动指定", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isAlbum) {
            Intent intent = new Intent(context, ImageGridActivity.class);
            ((AppCompatActivity) context).startActivityForResult(intent, IMAGE_PICKER);
        } else {
            Intent intent = new Intent(context, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            ((AppCompatActivity) context).startActivityForResult(intent, IMAGE_PICKER);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CODE_ITEMS) {
            if (data != null) {
                if (requestCode == IMAGE_PICKER) {
                    showImage(data, ImagePicker.EXTRA_RESULT_ITEMS);
                }
            } else {
                Toast.makeText(context, "拍照失败", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            int code = data.getIntExtra("REQUESTCODE", -1);
            if (data != null && code == IMAGE_PICKER) {
                showImage(data, ImagePicker.EXTRA_IMAGE_ITEMS);
            } else if (code == IMAGE_PICKER){
                Toast.makeText(context, "拍照失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showImage(Intent data, String name) {
        ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(name);
        if (images != null && images.size() > 0) {
            filePath = images.get(0).path;
            GlideUtils.loadImageViewLoding(context, images.get(0).path, ivPhoto, drawHolderImage, drawErrorImage);
        } else {
            Toast.makeText(context, "拍照失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void setDrawHolderImage(int drawHolderImage) {
        this.drawHolderImage = drawHolderImage;
    }

    public void setDrawSrcImage(int drawSrcImage) {
        this.drawSrcImage = drawSrcImage;
        if (ivPhoto != null) {
            ivPhoto.setImageResource(drawSrcImage);
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setDrawErrorImage(int drawErrorImage) {
        this.drawErrorImage = drawErrorImage;
    }

    public void setRequestCode(int code) {
        this.IMAGE_PICKER = code;
    }

    /***
     * 加载网络图片
     * @param url
     */
    public void showHttpImage(String url) {
        this.filePath = url;
        GlideUtils.loadImageViewLoding(context, url, ivPhoto, drawHolderImage, drawErrorImage);
    }

    public void showHttpImage(String url, int drawHolderImage, int drawErrorImage) {
        GlideUtils.loadImageViewLoding(context, url, ivPhoto, drawHolderImage, drawErrorImage);
    }

    public int getIMAGE_PICKER() {
        return IMAGE_PICKER;
    }

    public void setIMAGE_PICKER(int IMAGE_PICKER) {
        this.IMAGE_PICKER = IMAGE_PICKER;
    }
}
