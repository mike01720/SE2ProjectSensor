package com.example.android.batchstepsensor;

/**
 * File:   BufferToCloud.java
 * Author: James Kuczynski
 * Email; jkuczyns@cs.uml.edu
 * File Description: This class runs on an asynchronous thread.  It reads sensor data from the
 *                   buffer, does some preliminary computations, and updates the UI.  It (will)
 *                   also send data to the cloud.
 *
 * Last Modified 11/09/15 by James Kuczynski
 */


import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.widget.ImageView;


/**
 * Created by root on 10/10/15.
 */
public class DataAgg implements Runnable {

    private Handler m_handler;
    //private ImageView m_imageView;
    //private Canvas m_canvas;
    //private Paint m_paint;
    //private float fakePosY;
    //private float fakeTimeCounter = 0.0f;
    private Integer value = 0;

    @Override
    public void run() {
        System.out.println("^^^spinning in thread...");
        //System.out.println("BufferToCloud thread is spinning...");
        if (!MainActivity.getBuffer().isEmpty() ) {
            //System.out.println("Extracted: " + MainActivity.getBuffer().remove(MainActivity.getBuffer().size()-1));
            //fakePosY = (MainActivity.getBuffer().remove(MainActivity.getBuffer().size()-1) * 100);
            value = (MainActivity.getBuffer().remove(MainActivity.getBuffer().size()-1) * 100);
            //fakeTimeCounter += 100;
        } else {
            System.out.println("^^^The buffer is empty");
        }

        //Use handler to tell UI thread to update itself
        //m_canvas.drawLine(fakeTimeCounter, fakePosY, fakeTimeCounter+100, fakePosY, m_paint);
        //m_imageView.invalidate(); // This ensures that the UI actually updates the graphics
        //System.out.println("Draw at: (" + fakeTimeCounter + ", " + fakePosY + ")");
        System.out.println("^^^Extracted: " + value);
        m_handler.postDelayed(this, 1000);
    }


    // Accessor and Mutator methods

    public void setM_handler(Handler m_handler)
    {
        this.m_handler = m_handler;
    }


    public void setM_imageView(ImageView m_imageView)
    {
        //this.m_imageView = m_imageView;
    }


    public void setM_canvas(Canvas m_canvas)
    {
        //this.m_canvas = m_canvas;
    }


    public void setM_paint(Paint m_paint)
    {
        //this.m_paint = m_paint;
    }



} // End of class BufferToCloud
