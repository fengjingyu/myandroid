package com.jingyu.app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingyu.app.R;

public class TitleLayout extends FrameLayout {

    private RelativeLayout titlebarLayout;
    private LinearLayout leftLayout;
    private ImageView leftImageView;
    private TextView leftTextView;
    private TextView centerTextView;
    private LinearLayout rightLayout;
    private TextView rightTextView;
    private LinearLayout rightImageViewLayout;
    private ImageView rightImageView;

    public TitleLayout(Context context) {
        super(context);
    }

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_titlebar, this, true);
        titlebarLayout = (RelativeLayout) findViewById(R.id.titlebarLayout);
        leftLayout = (LinearLayout) findViewById(R.id.leftLayout);
        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
        leftImageView = (ImageView) findViewById(R.id.leftImageView);
        leftTextView = (TextView) findViewById(R.id.leftTextView);
        centerTextView = (TextView) findViewById(R.id.centerTextView);
        rightLayout = (LinearLayout) findViewById(R.id.rightLayout);
        rightTextView = (TextView) findViewById(R.id.rightTextView);
        rightImageViewLayout = (LinearLayout) findViewById(R.id.rightImageViewLayout);
        rightImageView = (ImageView) findViewById(R.id.rightImageView);

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.Title);

        int color = typeArray.getColor(R.styleable.Title_titleBgColor, 0xffffffff);
        titlebarLayout.setBackgroundColor(color);

        Drawable leftImg = typeArray.getDrawable(R.styleable.Title_leftImg);
        if (leftImg != null) {
            leftImageView.setImageDrawable(leftImg);
            leftImageView.setVisibility(View.VISIBLE);
        } else {
            leftImageView.setVisibility(View.GONE);
        }

        String leftText = typeArray.getString(R.styleable.Title_leftText);
        if (leftText != null) {
            leftTextView.setText(leftText);
            leftTextView.setVisibility(View.VISIBLE);
        } else {
            leftTextView.setVisibility(View.GONE);
        }

        float leftTextSize = typeArray.getDimension(R.styleable.Title_leftTextSize, 14);
        leftTextView.setTextSize(leftTextSize);

        int leftTextColor = typeArray.getColor(R.styleable.Title_leftTextColor, 0xff000000);
        leftTextView.setTextColor(leftTextColor);

        Drawable rightImg = typeArray.getDrawable(R.styleable.Title_rightImg);
        if (rightImg != null) {
            rightImageView.setImageDrawable(leftImg);
            rightImageView.setVisibility(View.VISIBLE);
            rightImageViewLayout.setVisibility(View.VISIBLE);
        } else {
            rightImageView.setVisibility(View.GONE);
            rightImageViewLayout.setVisibility(View.GONE);
        }

        String rightText = typeArray.getString(R.styleable.Title_rightText);
        if (rightText != null) {
            rightTextView.setText(rightText);
            rightTextView.setVisibility(View.VISIBLE);
        } else {
            rightTextView.setVisibility(View.GONE);
        }

        float rightTextSize = typeArray.getDimension(R.styleable.Title_rightTextSize, 14);
        rightTextView.setTextSize(rightTextSize);

        int rightTextColor = typeArray.getColor(R.styleable.Title_rightTextColor, 0xff000000);
        rightTextView.setTextColor(rightTextColor);

        String centerText = typeArray.getString(R.styleable.Title_centerText);
        if (centerText != null) {
            centerTextView.setText(centerText);
            centerTextView.setVisibility(View.VISIBLE);
        } else {
            centerTextView.setVisibility(View.GONE);
        }

        float centerTextSize = typeArray.getDimension(R.styleable.Title_centerTextSize, 16);
        centerTextView.setTextSize(centerTextSize);

        int centerTextColor = typeArray.getColor(R.styleable.Title_centerTextColor, 0xff000000);
        centerTextView.setTextColor(centerTextColor);

        typeArray.recycle();
    }

    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置title的中心的标题
     */
    public void setCenter(String title) {
        if (title != null) {
            centerTextView.setText(title);
            centerTextView.setVisibility(View.VISIBLE);
        } else {
            centerTextView.setVisibility(View.GONE);
        }
    }

    public void setTitleRightDrawable(int drawableId) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        centerTextView.setCompoundDrawables(null, null, drawable, null);
    }

    public void setLeft(int drawableId, String text) {
        if (text == null) {
            leftTextView.setVisibility(View.GONE);
        } else {
            leftTextView.setText(text);
            leftTextView.setVisibility(View.VISIBLE);
        }

        if (drawableId > 0) {
            leftImageView.setImageResource(drawableId);
            leftImageView.setVisibility(View.VISIBLE);
        } else {
            leftImageView.setVisibility(View.GONE);
        }
    }

    public void setRight(int drawableId, String text) {
        if (drawableId > 0) {
            rightImageView.setBackgroundResource(drawableId);
            rightImageView.setVisibility(View.VISIBLE);
        } else {
            rightImageView.setVisibility(View.GONE);
        }

        if (text == null) {
            rightTextView.setVisibility(View.GONE);
        } else {
            rightTextView.setText(text);
            rightTextView.setVisibility(View.VISIBLE);
        }
    }

    public RelativeLayout getTitlebarLayout() {
        return titlebarLayout;
    }

    public LinearLayout getLeftLayout() {
        return leftLayout;
    }

    public ImageView getLeftImageView() {
        return leftImageView;
    }

    public TextView getLeftTextView() {
        return leftTextView;
    }

    public TextView getCenterTextView() {
        return centerTextView;
    }

    public LinearLayout getRightLayout() {
        return rightLayout;
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public LinearLayout getRightImageViewLayout() {
        return rightImageViewLayout;
    }

    public ImageView getRightImageView() {
        return rightImageView;
    }
}
