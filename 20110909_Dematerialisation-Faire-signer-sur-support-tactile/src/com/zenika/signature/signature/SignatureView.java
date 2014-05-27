package com.zenika.signature.signature;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SignatureView extends View 
{
		
	private static final int SIZE = 4;
	private static final int BACKGROUND = Color.WHITE;
	private static final int FOREGROUND = Color.BLACK;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private float mCurX;
    private float mCurY;
    
    public SignatureView(Context c, AttributeSet set) 
    {
        super(c,set);
    }

    public void clear() 
    {
        if (mCanvas != null) 
        {
            reset();
            invalidate();
        }
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
    	mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);
    	reset();
    }
    
    private void reset()
    {
        Paint p = new Paint();
        p.setColor(BACKGROUND);
        
        mCanvas.drawPaint(p);
    }
    
    @Override 
    protected void onDraw(Canvas canvas) 
    {
        if (mBitmap != null) 
        {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }
    
    @Override 
    public boolean onTouchEvent(MotionEvent event) 
    {
        int action = event.getAction();
        boolean line = action == MotionEvent.ACTION_MOVE;
        if(line || action == MotionEvent.ACTION_DOWN)
        {
        	final Paint p = new Paint();
        	p.setColor(FOREGROUND);
            p.setAntiAlias(true);
        	p.setStrokeWidth(SIZE);
        	int n = event.getHistorySize();
        	for (int i=0; i<n; i++) 
        	{
        		drawPoint(event.getHistoricalX(i), event.getHistoricalY(i), line, p);
        	}
        	drawPoint(event.getX(), event.getY(), line, p);
        }
        return true;
    }
    
    private void drawPoint(float x, float y, boolean line, Paint p) 
    {
        if (mBitmap != null) 
        {
            if(line)
            {
            	mCanvas.drawLine(mCurX, mCurY, x, y, p);
            }
            else
            {
            	mCanvas.drawCircle(x, y, SIZE/2, p);
            }
            invalidate();
        }
        mCurX = x;
        mCurY = y;
    }
    
    public Bitmap save()
    {
    	return mBitmap;
    }
}
