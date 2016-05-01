package com.teampet.petsitter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {

    // instantiate variables
    Context context;
    String url;
    Bitmap bm;
    Thread thread;
    ArrayList<String> petImages;
    boolean done = false;

    // imageAdapter constructor, i know it always says petImages, but also includes profile pictures
    ImageAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        petImages = urls;
    }

    // override the various pager adapter methods, this is what lets us slide through the pictures
    @Override
    public int getCount() {
        return petImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        imageView.setImageBitmap(getImageBitmap("http://" + petImages.get(position)));
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }

    private Bitmap getImageBitmap(String petUrl) {
        bm = null;
        // reset just in case

        done = false;
        url = petUrl;



        // start thread to get data. this has to be in a background thread or else Android will complain
        thread = new Thread(background);
        thread.start();

        // wait for thread to finish
        while(!done){}

        return bm;
    }

    private Runnable background = new Runnable() {
        public void run(){
            try {
                // parse the url into a URL object
                URL aURL = new URL(url);
                //open a connection using the url
                URLConnection conn = aURL.openConnection();
                conn.connect();
                // simply download the body of the request and parse it as a bitmap, closing afterwards
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();

                done = true;
            } catch (Exception e) {
                Log.e("BitmapError", "Error getting bitmap", e);
                done = true;
            }
        } //run
    }; //background
}
