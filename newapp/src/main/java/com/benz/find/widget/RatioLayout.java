package com.benz.find.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.benz.find.R;


public class RatioLayout extends RelativeLayout {
    private static final String SCALE = "rectScale";

    private double scale = 1.0f;//width/height
    private float padding = 0f;
    private int lastpad = -1;


    public void setRatio(double ratio){
        varm.setAspectRatio(ratio);
        if(scale == ratio){
            return;
        }else{
            scale = ratio;
            requestLayout();
        }
    }

    public double getRatio() {
        return scale;
    }

    public RatioLayout(Context context) {
        super(context);
        initScale(context, null);

    }
    public RatioLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initScale(context,attrs);
    }

    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initScale(context, attrs);
    }
    @TargetApi(21)
    public RatioLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initScale(context,attrs);
    }

    private void initScale(Context context , AttributeSet attrs){
        if(attrs!=null){
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);
            scale = a.getFloat(R.styleable.RatioLayout_rectScale,1f);
            if(scale<=0){
                throw new RuntimeException("it is not valid scale");
            }
            a.recycle();
        }
        setDrawingCacheEnabled(true);
        varm = new ViewAspectRatioMeasurer(scale);

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                updatePadding();
            }
        });
    }

    private ViewAspectRatioMeasurer varm;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(scale<=0){
            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
            return;
        }
        if (varm != null) {
            varm.measure(widthMeasureSpec, heightMeasureSpec);
            int w = varm.getMeasuredWidth();
            int h = varm.getMeasuredHeight();
            widthMeasureSpec =  MeasureSpec.makeMeasureSpec(w, MeasureSpec.getMode(MeasureSpec.EXACTLY));
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.getMode(MeasureSpec.EXACTLY));

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
        updatePadding();
    }

    public void setPadding(float p){
        padding = p;
        updatePadding();
    }

    private void updatePadding(){
        int pad =(int)Math.ceil(Math.min(getWidth(),getHeight())*padding/2);
        if(lastpad == pad){
            return;
        }
        lastpad = pad;
        setPadding(pad,pad,pad,pad);
        invalidate();
    }

    public float getPadding(){
        return padding;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
