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

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Alex on 29/4/16.
 *
 * 多种样式的Switch
 */
public class MultiSwitch extends View {


    private int mShape;

    private int mWidth;

    private int mHeight;


    private final static String TAG = "MultiSwitch";

    private Paint mPaint;

    public MultiSwitch(Context context) {
        this(context,null);
    }

    public MultiSwitch(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultiSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MultiSwitch);

        mShape = typedArray.getInt(R.styleable.MultiSwitch_shape, 1);

        typedArray.recycle();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = measureDimension(280,widthMeasureSpec);
        mHeight = measureDimension(140,heightMeasureSpec);

        initDrawingVal();

    }

    private Rect mRest;

    private void initDrawingVal() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mRest = new Rect(0,0,mWidth,mHeight);


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

        if (mShape==1){
            mPaint.setColor(Color.BLACK);

            canvas.drawRect(mRest,mPaint);
        }
        else if (mShape==2){

            mPaint.setColor(Color.RED);

            canvas.drawRect(mRest,mPaint);
        }




        super.onDraw(canvas);
    }
}
