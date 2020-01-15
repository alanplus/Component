package com.alan.widget.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mouse on 2018/2/8.
 */

public abstract class ViewPaperAdapter extends PagerAdapter {

    private Context mContext;
    private List<View> viewList;

    private View mCurrentView;

    public ViewPaperAdapter(Context context) {
        super();
        this.mContext = context;
        viewList = new ArrayList<>();
    }


    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view;
        if (null != viewList && viewList.size() > 0) {
            view = viewList.remove(0);
        } else {
            view = initView(position);
        }
        container.addView(view);
        view.setTag(position);
        initData(view, position);
        return view;
    }

    protected abstract View initView(int position);

    protected abstract View initData(View view, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        viewList.add((View) object);
    }

    public void destroy() {
        viewList.clear();
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        mCurrentView = (View) object;
    }

    public View getCurrentCard() {
        return  mCurrentView;
    }
}
