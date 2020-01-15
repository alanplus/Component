package com.alan.widget.viewpager.translate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by Mouse on 2019/3/14.
 */
public class BaseTransformAdapter<T> extends PagerAdapter {

    protected Context context;
    protected List<T> list;
    protected int mChildCount;

    public BaseTransformAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return null == list ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
