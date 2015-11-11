/**
 * File:   MainActivity.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class stores data into a buffer (similar to how a sensor could), for
 *                   the data aggregation thread to use.
 *
 * Reference(s): (Timer) http://stackoverflow.com/questions/4776514/updating-textview-every-n-seconds
 *
 *
 *
 * Last Modified 11/09/15 by James Kuczynski
 */


package com.example.james.se2project;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    private ImageView m_imageView;
    private Bitmap m_bitmap;
    private Canvas m_canvas;
    private Paint m_paint;
    private Button m_button;

    private Timer m_timer;
    private TimerTask m_timerTask;
    private static ArrayList<Float> buffer = new ArrayList<Float>();
    private Random m_randomNumGenerator = new Random();
    private Handler m_handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initialize variables
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap tmpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.graph_paper2);
        m_bitmap = tmpBitmap.copy(Bitmap.Config.ARGB_8888, true);
        m_canvas = new Canvas(m_bitmap);
        m_paint = new Paint();
        m_paint.setColor(Color.RED);
        m_paint.setStrokeWidth(20);
        m_imageView = (ImageView) findViewById(R.id.graph_view);
        m_imageView.setImageDrawable(new BitmapDrawable(getResources(), m_bitmap));

        m_button = (Button) findViewById(R.id.graph_button);


        try {
            /*
              Set up asynchronous thread.  This thread will extract data from the buffer, and
              use it to update the UI graph, as well as send it to the cloud.
             */
            DataAgg bufferToCloud = new DataAgg();
            bufferToCloud.setM_handler(m_handler);
            bufferToCloud.setM_imageView(m_imageView);
            bufferToCloud.setM_canvas(m_canvas);
            bufferToCloud.setM_paint(m_paint);
            m_handler.post(bufferToCloud);

            /*
              This timer is simulating a sensor, i.e. it is generating random floats every
              1000 millisecond.
             */
            m_timer = new Timer();
            m_timerTask = new TimerTask() {
                @Override
                public void run() {
                    //System.out.println("running...");
                    /*
                      Important!!! Make sure to check the buffers length before adding another
                      value.  Since I am using an ArrayList (instead of an array), if this check
                      was not in place the buffer could grow infinatly long, which would be
                      really, really bad.
                    */
                    if(buffer.size() < 100) {
                        System.out.println("adding to buffer");
                        buffer.add(m_randomNumGenerator.nextFloat() );
                    }else {
                        System.out.println("WARNING: buffer full");
                    }
                }
            };
            m_timer.schedule(m_timerTask, 1000, 1000); // Start the timer/sensor simulator
        } catch (IllegalStateException e) {
            System.err.println("ERROR trying to push_back to buffer");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public static ArrayList<Float> getBuffer()
    {
        return buffer;
    }



} // End of class MainActivity