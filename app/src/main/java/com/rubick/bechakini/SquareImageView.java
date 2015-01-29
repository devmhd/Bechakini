package com.rubick.bechakini;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Mehedee Zaman on 1/29/2015.
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);

    //    setDefaultImageResId(R.drawable.loading);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    //    setDefaultImageResId(R.drawable.loading);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    //    setDefaultImageResId(R.drawable.loading);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }

}
