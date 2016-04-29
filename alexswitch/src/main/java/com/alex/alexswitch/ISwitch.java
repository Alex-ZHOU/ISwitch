/*
 * Copyright 2016 AlexZHOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alex.alexswitch;

import android.animation.TimeInterpolator;
import android.graphics.RectF;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.res.TypedArray;


/**
 * Created by Alex on 28/4/16.
 *
 * ISwitch
 * Make mButtonRadius switch like this ios switch.
 * 做一个模仿苹果的开关
 */
public class ISwitch extends View {

    private final static String TAG = "ISwitch";

    private final static int DEFAULT_OPEN_COLOR = 0x14e715;

    private final static int DEFAULT_CLOSE_COLOR = Color.GRAY;

    private final static int DEFAULT_BUTTON_COLOR = Color.WHITE;

    private final static int DEFAULT_BUTTON_ANIMATION_TIME = 100;

    private Boolean mIsOpen = false;

    private int mOpenColor;

    private int mCloseColor;

    private int mButtonColor;

    private int mAnimationTime;

    // 按钮圆心到左边的距离
    private int mButtonX;

    private int mWidth;

    private int mHeight;

    private Paint mPaint;

    private int mAlpha = 0;

    private int mButtonRadius;

    private RectF backCircleRect;

    private ISwitchOnClickListeners mISwitchOnClickListeners;

    public interface ISwitchOnClickListeners {

        void open();

        void close();
    }


    public ISwitch(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ISwitch(Context context) {
        this(context, null);
    }

    public ISwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        mISwitchOnClickListeners = null;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.ISwitch);

        mIsOpen = typedArray.getBoolean(R.styleable.ISwitch_isOpen, mIsOpen);

        mOpenColor = typedArray.getInt(R.styleable.ISwitch_openColor, DEFAULT_OPEN_COLOR);

        mCloseColor = typedArray.getInt(R.styleable.ISwitch_closeColor, DEFAULT_CLOSE_COLOR);

        mButtonColor = typedArray.getInt(R.styleable.ISwitch_buttonColor, DEFAULT_BUTTON_COLOR);

        mAnimationTime = typedArray.getInt(R.styleable.ISwitch_animationTime, DEFAULT_BUTTON_ANIMATION_TIME);

        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = measureDimension(280, widthMeasureSpec);
        mHeight = measureDimension(140, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

        initDrawingVal();
    }

    private void initDrawingVal() {
        //mWidth  = (int) (mHeight * 1.5);
        backCircleRect = new RectF(0, 0, mWidth, mHeight);
        mButtonRadius = mHeight / 2;
        if (mIsOpen) {
            mAlpha = 255;
            mButtonX = ((mWidth - (mButtonRadius + mButtonRadius)) + mButtonRadius);
        } else {
            mAlpha = 0;
            mButtonX = mButtonRadius;
        }
    }

    public int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize; // UNSPECIFIED
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制关闭显示颜色
        mPaint.setColor(mCloseColor);
        canvas.drawRoundRect(backCircleRect, mButtonRadius, mButtonRadius, mPaint);

        // 绘制打开显示的颜色并设置透明度
        mPaint.setColor(mOpenColor);
        mPaint.setAlpha(mAlpha);
        canvas.drawRoundRect(backCircleRect, mButtonRadius, mButtonRadius, mPaint);

        // 设置了按钮颜色
        mPaint.setColor(mButtonColor);
        canvas.drawCircle(mButtonX, mButtonRadius, mButtonRadius - mButtonRadius / 14, mPaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // 如果动画处于执行状态,就直接返回
        if (!(mAlpha == 255 || mAlpha == 0)) {
            return super.onTouchEvent(event);
        }

        // 设置时间差值器
        TimeInterpolator timeInterpolator = new TimeInterpolator() {
            @Override
            public float getInterpolation(float v) {
                debug(v + " " + mButtonX);
                if (mIsOpen) {
                    mAlpha = (int) ((1 - v) * 255);
                    // mButtonX = (int) ((1-v) * mButtonRadius + mButtonRadius);
                    mButtonX = (int) ((1 - v) * (mWidth - (mButtonRadius + mButtonRadius)) + mButtonRadius);
                    invalidate();
                } else {
                    mAlpha = (int) (v * 255);
                    // mButtonX = (int) (v * mButtonRadius + mButtonRadius);
                    mButtonX = (int) (v * (mWidth - (mButtonRadius + mButtonRadius)) + mButtonRadius);
                    invalidate();
                }
                if (v == 1f) {
                    if (mIsOpen) {
                        mIsOpen = false;
                        if (mISwitchOnClickListeners != null) {
                            mISwitchOnClickListeners.close();
                        }
                    } else {
                        mIsOpen = true;
                        if (mISwitchOnClickListeners != null) {
                            mISwitchOnClickListeners.open();
                        }

                    }
                }

                return v;

            }

        };

        // 运行动画设置时间和时间差值器
        this.animate().setDuration(mAnimationTime).setInterpolator(timeInterpolator).start();

        return super.onTouchEvent(event);
    }


    public Boolean getIsOpen() {
        return mIsOpen;
    }

    public void setIsOpen(Boolean mIsOpen) {
        this.mIsOpen = mIsOpen;
        if (mIsOpen) {
            mAlpha = 255;
            mButtonX = ((mWidth - (mButtonRadius + mButtonRadius)) + mButtonRadius);
        } else {
            mAlpha = 0;
            mButtonX = mButtonRadius;
        }
        invalidate();
    }

    public int getOpenColor() {
        return mOpenColor;
    }

    public void setOpenColor(int mOpenColor) {
        this.mOpenColor = mOpenColor;
    }

    public int getCloseColor() {
        return mCloseColor;
    }

    public void setCloseColor(int mCloseColor) {
        this.mCloseColor = mCloseColor;
    }

    public int getButtonColor() {
        return mButtonColor;
    }

    public void setButtonColor(int mButtonColor) {
        this.mButtonColor = mButtonColor;
    }

    public int getViewWidth() {
        return mWidth;
    }

    public void setViewWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getViewHeight() {
        return mHeight;
    }

    public void setViewHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    // 设置监听
    public void setOnISwitchOnClickListener(ISwitchOnClickListeners listener) {
        this.mISwitchOnClickListeners = listener;
    }

    private void debug(final String string) {
        Log.d(TAG, "debug: " + string);
    }

}
