///*
// * Copyright 2016 AlexZHOU
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.alex.alexswitch;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.View;
//
///**
// * Created by Alex on 29/4/16.
// *
// * 多种样式的Switch
// */
//public class MultiSwitch extends View {
//
//    private final static String TAG = "MultiSwitch";
//
//    private final static int DEFAULT_OPEN_COLOR = 0x14e715;
//
//    private final static int DEFAULT_CLOSE_COLOR = Color.GRAY;
//
//    private final static int DEFAULT_BUTTON_COLOR = Color.WHITE;
//
//    private final static int DEFAULT_BUTTON_ANIMATION_TIME = 100;
//
//    private int mType;
//
//    private Boolean mIsOpen = false;
//
//    private int mAnimationTime;
//
//    private int mWidth;
//
//    private int mHeight;
//
//    private Paint mPaint;
//
//    private Rect mBackgroundRest;
//
//    private Rect mButtomRest;
//
//    private int mOpenColor;
//
//    private int mCloseColor;
//
//    private int mButtonColor;
//
//    private int mAlpha = 0;
//
//    public MultiSwitch(Context context) {
//        this(context,null);
//    }
//
//    public MultiSwitch(Context context, AttributeSet attrs) {
//        this(context, attrs,0);
//    }
//
//    public MultiSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs,
//                R.styleable.ISwitch);
//
//        mType = typedArray.getInt(R.styleable.ISwitch_type, 1);
//
//        mIsOpen = typedArray.getBoolean(R.styleable.ISwitch_isOpen, mIsOpen);
//
//        mOpenColor = typedArray.getInt(R.styleable.ISwitch_openColor, DEFAULT_OPEN_COLOR);
//
//        mCloseColor = typedArray.getInt(R.styleable.ISwitch_closeColor, DEFAULT_CLOSE_COLOR);
//
//        mButtonColor = typedArray.getInt(R.styleable.ISwitch_buttonColor, DEFAULT_BUTTON_COLOR);
//
//        mAnimationTime = typedArray.getInt(R.styleable.ISwitch_animationTime, DEFAULT_BUTTON_ANIMATION_TIME);
//
//        typedArray.recycle();
//
//    }
//
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        mWidth = measureDimension(280,widthMeasureSpec);
//        mHeight = measureDimension(140,heightMeasureSpec);
//
//        initDrawingVal();
//
//    }
//
//
//
//    private void initDrawingVal() {
//
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//
//        mBackgroundRest = new Rect(0,0,mWidth,mHeight);
//
//        mButtomRest = new Rect(mHeight/14,mHeight/14,mHeight-mHeight/14,mHeight-mHeight/14);
//    }
//
//    public int measureDimension(int defaultSize, int measureSpec) {
//        int result;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        } else {
//            result = defaultSize; // UNSPECIFIED
//            if (specMode == MeasureSpec.AT_MOST) {
//                result = Math.min(result, specSize);
//            }
//        }
//        return result;
//    }
//
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//
//        // 绘制关闭显示颜色
//        mPaint.setColor(mCloseColor);
//        canvas.drawRect(mBackgroundRest, mPaint);
//
//        // 绘制打开显示的颜色并设置透明度
//        mPaint.setColor(mOpenColor);
//        mPaint.setAlpha(mAlpha);
//        canvas.drawRect(mBackgroundRest, mPaint);
//
//        // 设置了按钮颜色
//        mPaint.setColor(mButtonColor);
//        canvas.drawRect(mButtomRest, mPaint);
//
//    }
//
//    private void debug(final String string) {
//        Log.d(TAG, "debug: " + string);
//    }
//
//}
