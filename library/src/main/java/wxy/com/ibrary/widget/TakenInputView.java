package wxy.com.ibrary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import wxy.com.ibrary.R;
import wxy.com.ibrary.utils.DensityUtils;

/**
 * Created by jiajun.wang on 2017/12/31.
 */

public class TakenInputView extends LinearLayout {


    private TextView leftTextView;
    private EditText rightEditText;
    private float tvTextSize;
    private int tvTextCorlr;
    private String tvText;
    private float edTextSize;
    private int  edTextCorlr;
    private String edHintText;
    private int editViewBg;
    private float tvWidth;
    private View mView;

    public Context context;
    public TakenInputView(Context context) {
        super(context);
    }

    public TakenInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initParams(context,attrs);
    }

    public TakenInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context,attrs);
    }
    private void initParams(Context context, AttributeSet attrs) {
        this.context=context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TakenInputView);
        if (typedArray != null) {
            editViewBg = typedArray.getColor(R.styleable.TakenInputView_ed_bg, Color.WHITE);
            edTextSize=typedArray.getDimension(R.styleable.TakenInputView_ed_text_size,16f);
            edTextCorlr=typedArray.getColor(R.styleable.TakenInputView_ed_text_corlr,Color.BLACK);
            edHintText=typedArray.getString(R.styleable.TakenInputView_ed_hint_text);
            tvTextSize=typedArray.getDimension(R.styleable.TakenInputView_tv_text_size,16f);
            tvTextCorlr=typedArray.getColor(R.styleable.TakenInputView_tv_text_corlr,Color.BLACK);
            tvText=typedArray.getString(R.styleable.TakenInputView_tv_text);
            tvWidth=typedArray.getDimension(R.styleable.TakenInputView_tv_width,80f);
            typedArray.recycle();
        }
        mView=View.inflate(context,R.layout.layout_taken_input,this);
        setParams();
    }

    private void setParams() {
        leftTextView=mView.findViewById(R.id.left_tex_view);
        rightEditText=mView.findViewById(R.id.right_edit_view);
        leftTextView.setTextColor(tvTextCorlr);
        leftTextView.setText(tvText);
        leftTextView.setTextSize(DensityUtils.px2dip(context,tvTextSize));
        LinearLayout.LayoutParams layoutParams= (LayoutParams) leftTextView.getLayoutParams();
        layoutParams.width= (int) tvWidth;
        layoutParams.height=LayoutParams.MATCH_PARENT;
        leftTextView.setLayoutParams(layoutParams);
        ColorDrawable drawable = new ColorDrawable(editViewBg);
        rightEditText.setBackground(drawable);
        rightEditText.setHint(edHintText);
        rightEditText.setTextSize(DensityUtils.px2dip(context,edTextSize));
        rightEditText.setTextColor(edTextCorlr);
    }

    public TakenInputView setTvTextSize(float tvTextSize) {
        this.tvTextSize = tvTextSize;
        return this;
    }

    public TakenInputView setTvTextCorlr(int tvTextCorlr) {
        this.tvTextCorlr = tvTextCorlr;
        return this;
    }

    public TakenInputView setTvText(String tvText) {
        this.tvText = tvText;
        return this;
    }

    public TakenInputView setEdTextSize(float edTextSize) {
        this.edTextSize = edTextSize;
        return this;
    }

    public TakenInputView setEdTextCorlr(int edTextCorlr) {
        this.edTextCorlr = edTextCorlr;
        return this;
    }

    public TakenInputView setEdHintText(String edHintText) {
        this.edHintText = edHintText;
        return this;
    }

    public TakenInputView setEditViewBg(int editViewBg) {
        this.editViewBg = editViewBg;
        return this;
    }

    public TakenInputView setTvWidth(float tvWidth) {
        this.tvWidth = tvWidth;
        return this;
    }

    public TakenInputView setmView(View mView) {
        this.mView = mView;
        return this;
    }

    public void build(){
        if (leftTextView==null||rightEditText==null){
            Log.e("com.wxy.library","view没有初始化");
            return;
        }
        setParams();
    }

    public TextView getLeftTextView() {
        return leftTextView;
    }

    public void setLeftTextView(TextView leftTextView) {
        this.leftTextView = leftTextView;
    }

    public EditText getRightEditText() {
        return rightEditText;
    }

    public void setRightEditText(EditText rightEditText) {
        this.rightEditText = rightEditText;
    }
}
