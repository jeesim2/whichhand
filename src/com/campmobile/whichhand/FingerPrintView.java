/*
 * @(#)FingerPrintView.java $version 2013. 5. 22.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.campmobile.whichhand;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @author Jihun No (KR14624)
 */
public class FingerPrintView extends View {

	List<Float> listX = new ArrayList<Float>();
	List<Float> listY = new ArrayList<Float>();

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FingerPrintView(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FingerPrintView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public FingerPrintView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionevent) {

		if (motionevent.getAction() == MotionEvent.ACTION_DOWN) {
			listX.clear();
			listY.clear();
		}

		if (motionevent.getAction() == MotionEvent.ACTION_UP) {

			float xWhenYMin = -1;
			float xWhenYMax = -1;
			float yMin = Float.MAX_VALUE;
			float yMax = Float.MIN_VALUE;

			for (int i = 0; i < listY.size(); i++) {
				if (listY.get(i) < yMin) {
					yMin = listY.get(i);
					xWhenYMin = listX.get(i);
				}
				if (listY.get(i) > yMax) {
					yMax = listY.get(i);
					xWhenYMax = listX.get(i);
				}
			}

			float yDiff = yMax - yMin;

			if (yDiff < 100) {
				if (xWhenYMin > xWhenYMax) {
					Toast.makeText(getContext(), "왼손-터치" + yDiff, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "오른손-터치" + yDiff, Toast.LENGTH_SHORT).show();
				}
			} else {
				if (xWhenYMin > xWhenYMax) {
					Toast.makeText(getContext(), "오른손-플링" + yDiff, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "왼손-플링" + yDiff, Toast.LENGTH_SHORT).show();
				}
			}

			listX.clear();
			listY.clear();
		}

		listX.add(motionevent.getX());
		listY.add(motionevent.getY());
		invalidate();
		return true;
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {

		Paint paint = new Paint();
		paint.setColor(0x55000000);
		paint.setStyle(Style.STROKE);
		for (int i = 1; i < listX.size(); i++) {
			canvas.drawLine(listX.get(i - 1), listY.get(i - 1), listX.get(i), listY.get(i), paint);
		}

		super.dispatchDraw(canvas);
	}

}
