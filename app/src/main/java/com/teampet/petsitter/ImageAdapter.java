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

    Context context;
    String url;
    Bitmap bm;
    Thread thread;
    ArrayList<String> petImages;
    boolean done = false;


    ImageAdapter(Context context, ArrayList<String> urls) {
        this.context = context;
        petImages = urls;
    }

    @Override
    public int getCount() {
        return petImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);

        imageView.setImageBitmap(getImageBitmap("http://" + petImages.get(position)));
        ((ViewPager) container).addView(imageView, 0);
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



        // start thread to get data
        thread = new Thread(background);
        thread.start();

        // wait for thread to finish
        while(!done){}

        return bm;
    }

    private Runnable background = new Runnable() {
        public void run(){
            try {
                URL aURL = new URL(url);
                URLConnection conn = aURL.openConnection();
                conn.connect();
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
