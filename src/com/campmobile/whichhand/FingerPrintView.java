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

	/**
	 * ACTION_MOVE 발생시 각 이벤트의 x좌표를 저장한다.
	 */
	List<Float> listX = new ArrayList<Float>();

	/**
	 * ACTION_MOVE 발생시 각 이벤트의 y좌표를 저장한다.
	 */
	List<Float> listY = new ArrayList<Float>();

	public FingerPrintView(Context context) {
		super(context);
	}

	public FingerPrintView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

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

			// 터치 이벤트의 세로축 이동 거리를 구한다.
			float yDiff = yMax - yMin;

			if (yDiff < 5) {

				// 이동거리가 너무 짧아 판단하기 힘들다.

			} else if (yDiff < 100) {

				// 100 픽셀 이하면 일반적인 '누름'을 가능성이 크다.
				if (xWhenYMin > xWhenYMax) {
					// 터치에서 좌상단을 향하면 왼손 터치다.
					Toast.makeText(getContext(), "왼손-터치" + yDiff, Toast.LENGTH_SHORT).show();
				} else {
					// 터치에서 우상단을 향하면 오른손 터치다.
					Toast.makeText(getContext(), "오른손-터치" + yDiff, Toast.LENGTH_SHORT).show();
				}

			} else {

				// 100 픽셀 이상이면 스와이핑일 가능성이 크다
				if (xWhenYMin > xWhenYMax) {
					// 스와이핑에서 우상단을 향하면 오른손 터치다.
					Toast.makeText(getContext(), "오른손-플링" + yDiff, Toast.LENGTH_SHORT).show();
				} else {
					// 스와이핑에서 좌상단을 향하면 왼손 터치다.
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
