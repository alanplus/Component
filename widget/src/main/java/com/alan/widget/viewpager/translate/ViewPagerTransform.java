package com.alan.widget.viewpager.translate;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ViewPagerTransform extends WViewPager {
    private static final float MIN_SCALE = 0.95f;
    private static final float MIN_ALPHA = 1f;
    private boolean scrollble = true;
    
    public ViewPagerTransform(Context context) {
        this(context, null);
    }

    public ViewPagerTransform(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setPageTransformer(false, new DepthPageTransformer());
        this.setOffscreenPageLimit(2);  // 避免卡顿
        
        this.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @SuppressLint("NewApi")
			@Override
            public void onChildViewAdded(View parent, View child) {
                child.setScaleY(MIN_SCALE);
                child.setAlpha(MIN_ALPHA);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
            }
        });
    }

    public class DepthPageTransformer implements WViewPager.PageTransformer {

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @SuppressLint("NewApi")
        public void transformPage(View view, float position) {

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setScaleY(MIN_SCALE);
                view.setAlpha(MIN_ALPHA);
            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)* (1 - Math.abs(position));
                float alphaFactor = MIN_ALPHA + (1 - MIN_ALPHA)* (1 - Math.abs(position));
                view.setScaleY(scaleFactor);
                view.setAlpha(alphaFactor);
            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                // Counteract the default slide transition

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)* (1 - Math.abs(position));
                float alphaFactor = MIN_ALPHA + (1 - MIN_ALPHA)* (1 - Math.abs(position));
                view.setScaleY(scaleFactor);
                view.setAlpha(alphaFactor);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setScaleY(MIN_SCALE);
                view.setAlpha(MIN_ALPHA);
            }
        }
    }
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	return !scrollble?false:super.onInterceptTouchEvent(ev);  
    }
    
    @Override  
    public boolean onTouchEvent(MotionEvent ev) { 
        return !scrollble?true:super.onTouchEvent(ev);  
    }  
  
    /**是否可滑动*/
    public void setScrollble(boolean scrollble) {  
        this.scrollble = scrollble;  
    }
}
