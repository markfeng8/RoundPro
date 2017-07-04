package com.my.roundpro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * fct
 * Created by Administrator on 2017/7/4.
 */

public class RoundProView extends View {

    //进度条背景画笔
    private Paint rbPaint;
    //进度条画笔
    private Paint rpPaint;
    //进度文字画笔
    private Paint txtPaint;
    //进度条背景颜色
    private int rbColor;
    //进度条颜色
    private int rpColor;
    //文字颜色
    private int txtColor = Color.RED;
    //文字大小
    private float txtSize = 25;
    private String txt = "0%";
    //环形的宽度
    private float roundWidth;
    private int mWidth;
    private int mHeight;

    private int raduis;
    private float angle = 0;

    public RoundProView(Context context) {
        super(context);
    }

    public RoundProView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        initPaint();
    }

    public RoundProView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initPaint();
    }


    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProView);
        txtSize = typedArray.getDimensionPixelSize(R.styleable.RoundProView_txtsize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
                        getResources().getDisplayMetrics()));
        txtColor = typedArray.getColor(R.styleable.RoundProView_txtcolor, Color.RED);
        rbColor = typedArray.getColor(R.styleable.RoundProView_rbColor, Color.GRAY);
        rpColor = typedArray.getColor(R.styleable.RoundProView_rpColor, Color.GREEN);
        roundWidth = typedArray.getFloat(R.styleable.RoundProView_roundWidth, 25f);
        typedArray.recycle();
    }

    private void initPaint() {
        rbPaint = new Paint();
        rbPaint.setStrokeWidth(roundWidth);
        rbPaint.setStyle(Paint.Style.STROKE);
        rbPaint.setColor(rbColor);

        rpPaint = new Paint();
        rpPaint.setColor(rpColor);
        rpPaint.setStyle(Paint.Style.STROKE);
        rpPaint.setStrokeWidth(roundWidth);
        rpPaint.setStrokeCap(Paint.Cap.ROUND);

        txtPaint = new Paint();
        txtPaint.setColor(txtColor);
        txtPaint.setTextSize(txtSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWH(widthMeasureSpec), measureWH(heightMeasureSpec));
    }

    private int measureWH(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 200;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, measureSpec);
            }
        }
        return result;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mWidth == 0) {
            mWidth = getMeasuredWidth();
            mHeight = getMeasuredHeight();
            raduis = mWidth / 2 <= mHeight / 2
                    ? mWidth / 2 : mHeight / 2;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制圆环背景
        RectF rectF = new RectF(0 + rpPaint.getStrokeWidth() / 2,
                0 + rpPaint.getStrokeWidth() / 2,
                raduis * 2 - rpPaint.getStrokeWidth() / 2,
                raduis * 2 - rpPaint.getStrokeWidth() / 2);
        canvas.drawArc(rectF, 0, 360, false, rbPaint);

        //绘制圆环进度条
        canvas.drawArc(rectF, -90, angle, false, rpPaint);

        //绘制进度文字
        float x = raduis - txtPaint.measureText(txt) / 2;
        Paint.FontMetrics fontMetrics = txtPaint.getFontMetrics();
        float dy = -(fontMetrics.descent + fontMetrics.ascent) / 2;
        float y = raduis + dy;
        canvas.drawText(txt, x, y, txtPaint);
    }

    public void setProgress(int progress) {
        if (progress <= 100) {
            angle = (float) (3.6 * progress);
            txt = progress + "%";
            postInvalidate();

            Log.d("progress==", progress + "");
            Log.d("angle==", angle + "");
        }
    }
}
