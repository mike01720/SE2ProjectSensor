// http://developer.android.com/intl/ko/reference/java/net/HttpURLConnection.html

package com.example.james.se2project;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by root on 11/9/15.
 */
public class DbCom {

    private URL url;
    HttpURLConnection urlConnection;
    InputStream in;


    public void getData() {
        String response = "";
        try {
            url = new URL("http://www.android.com/");
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream());
            //readStream(in);



        } catch (Exception e) {
            //handle error
        } finally {

            urlConnection.disconnect();
        }
    }

}