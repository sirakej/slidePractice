package com.example.oluwafemi.slidepractice;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class welcomeSlide extends AppCompatActivity {

    private Button skip , next;
    private ViewPager slider;
    private int[] layouts;
    private LinearLayout linearDots;
    private TextView[] dots;
    private MyViewPagerAdapter myViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_slide);

       // skip =(Button) findViewById(R.id.btn_skip);
        //next =(Button) findViewById(R.id.btn_next);
        slider=(ViewPager) findViewById(R.id.pager);
        linearDots=(LinearLayout) findViewById(R.id.layout_dots);

        layouts = new int[]{
                R.layout.activity_design_1,
                R.layout.activity_design_2,
                R.layout.activity_design_3,
        };

        changeStatusBarColor();/*
        this called method/instances is for the slider
         */

        myViewPagerAdapter = new MyViewPagerAdapter();
        slider.setAdapter(myViewPagerAdapter);

        // this is for the timer
        slider.setCurrentItem(0);
        myViewPagerAdapter.setTimer(slider, 7);
        // this is for the dot movement and changing the button
        slider.addOnPageChangeListener(ViewPagerPageChangeListener);



        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getItem(+1);
                if(current < layouts.length){
                    slider.setCurrentItem(current);

                }else {
                    launchHomeScreen();
                }
            }
        });


        }

        private void launchHomeScreen(){
            Intent intent = new Intent(welcomeSlide.this,MainActivity.class);
            startActivity(intent);

        }

        private void addBottomDots(int currentPage){
         dots = new TextView[layouts.length];
         linearDots.removeAllViews();
         for(int i = 0 ; i<dots.length; i++) {
             dots[i] = new TextView(this);
             dots[i].setText(Html.fromHtml("&#8226"));
             dots[i].setTextSize(35);
             linearDots.addView(dots[i]);
         }

    }

    private int getItem(int i){
        return slider.getCurrentItem();
    }

    // change the text after reaching a particular slide

    ViewPager.OnPageChangeListener ViewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if(position == layouts.length - 1){
                next.setText("GOT IT");
                skip.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    } ;


    private void changeStatusBarColor() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        }
    }
    public class MyViewPagerAdapter extends PagerAdapter{

        // the slider method definition in the class

        final Handler handler = new Handler();
        public Timer swipeTimer ;


        public void setTimer(final ViewPager slider, int time){
            final Runnable Update = new Runnable() {
                int NUM_PAGES = 3;
                int currentPage = 0 ;
                public void run() {
                    if (currentPage == NUM_PAGES ) {
                        currentPage = 0;
                    }
                    slider.setCurrentItem(currentPage++, true);
                }
            };


            swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 1000, time*1000);
        }






        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter(){

        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater =(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position] , container, false);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view,Object obj) {
            return view == obj;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }




    }

