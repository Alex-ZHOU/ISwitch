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
import android.view.ViewConfiguration;


/**
 * Created by Alex on 28/4/16.
 *
 * ISwitch
 * Make mButtonRadius switch like this ios switch.
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

    private float mStartX = 0;

    private float mStartY = 0;

    private float mLastX = 0;

    private int mButtonRadius;

    private RectF backCircleRect;

    private int mTouchSlop;

    private int mClickTimeout;

    private int mMoveDsitance;

    private int mMinDistance;

    private int mMaxDistance;

    private boolean mIsAnimationRun = false;

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
        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mClickTimeout = ViewConfiguration.getPressedStateDuration() + ViewConfiguration.getTapTimeout();

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

        mMinDistance = mButtonRadius;

        mMaxDistance = mWidth - mButtonRadius;

        mMoveDsitance = mMaxDistance - mMinDistance;

        if (mIsOpen) {
            mAlpha = 255;
            mButtonX = mMaxDistance;
        } else {
            mAlpha = 0;
            mButtonX = mMinDistance;
        }

        setClickable(true);
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
        if (mIsAnimationRun) {
            return false;
        }

        if (!isEnabled() || !isClickable()) {
            debug("onTouchEvent " + "return false");
            return false;
        }

        int action = event.getAction();
        debug("onTouchEvent " + "action :" + action);

        float deltaX = event.getX() - mStartX;
        float deltaY = event.getY() - mStartY;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                debug("onTouchEvent " + "ACTION_DOWN :" + action);
                mStartX = event.getX();
                mStartY = event.getY();
                mLastX = mStartX;
                setPressed(true);
                break;
            case MotionEvent.ACTION_MOVE:

                debug("onTouchEvent " + "ACTION_MOVE :" + action);
                float x = event.getX();
                int temp = (int) (mButtonX + (x - mLastX));
                mLastX = x;
                if (temp < mMinDistance || temp > mMaxDistance) {
                    debug("onTouchEvent " + "Invalid");
                } else {
                    debug("onTouchEvent " + "Effective");
                    mAlpha = 255 * (temp - mMinDistance) / mMoveDsitance;
                    mButtonX = temp;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                debug("onTouchEvent " + "ACTION_UP" + action);
                int tempClose = mButtonX - mMinDistance;
                int tempOpen = mMaxDistance - mButtonX;

                float time = event.getEventTime() - event.getDownTime();
                if (deltaX < mTouchSlop && deltaY < mTouchSlop && time < mClickTimeout) {
                    debug("onTouchEvent " + "Click");
                    performClick();
                    if (mIsOpen) {
                        int tempTime2 = mAnimationTime * (mButtonX - mMinDistance) / mMoveDsitance;
                        viewAnimation(tempTime2, false, mAlpha);
                    } else {
                        int tempTime = mAnimationTime * (mMaxDistance - mButtonX) / mMoveDsitance;
                        viewAnimation(tempTime, true, mAlpha);
                    }

                } else {
                    debug("onTouchEvent " + "Move");

                    if (tempClose > tempOpen) {
                        // TO OPEN
                        int tempTime = mAnimationTime * tempOpen / mMoveDsitance;
                        viewAnimation(tempTime, true, mAlpha);
                        debug("onTouchEvent " + "ToOpen" + tempTime);
                    } else {
                        // TO CLOSE
                        int tempTime2 = mAnimationTime * tempClose / mMoveDsitance;
                        viewAnimation(tempTime2, false, mAlpha);
                        debug("onTouchEvent " + "ToClose" + tempTime2);
                    }

                }

                break;
        }

        return true;
    }


    private void viewAnimation(int time, final boolean toType, final int currentAlpha) {

        final int distanceToBegin = mButtonX - mMinDistance;
        final int distanceToEnd = mMaxDistance - mButtonX;
        // 设置时间差值器
        TimeInterpolator timeInterpolator = new TimeInterpolator() {
            @Override
            public float getInterpolation(float v) {
                //debug(v + " " + mButtonX);
                if (!toType) {
                    // TO CLOSE
                    mAlpha = (int) ((1 - v) * currentAlpha);
                    mButtonX = (int) ((1 - v) * (distanceToBegin)) + mButtonRadius;
                    invalidate();
                } else {
                    // TO OPEN
                    mAlpha = (int) (v * (255 - currentAlpha)) + currentAlpha;
                    mButtonX = ((mWidth - (mButtonRadius + mButtonRadius)) + mButtonRadius) - (int) ((1 - v) * distanceToEnd);
                    invalidate();
                }
                if (v == 1f) {
                    setPressed(false);
                    mIsAnimationRun = false;
                    if (!toType) {
                        if (mISwitchOnClickListeners != null && mIsOpen) {
                            mISwitchOnClickListeners.close();
                        }
                        mIsOpen = false;
                    } else {
                        if (mISwitchOnClickListeners != null && !mIsOpen) {
                            mISwitchOnClickListeners.open();
                        }
                        mIsOpen = true;
                    }
                }

                return v;

            }

        };

        // 运行动画设置时间和时间差值器
        this.animate().setDuration(time).setInterpolator(timeInterpolator).start();
        mIsAnimationRun = true;

    }


    public Boolean getIsOpen() {
        return mIsOpen;
    }

    public void setIsOpen(Boolean mIsOpen) {
        this.mIsOpen = mIsOpen;
        if (mIsOpen) {
            mAlpha = 255;
            mButtonX = mMaxDistance;
        } else {
            mAlpha = 0;
            mButtonX = mMinDistance;
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
