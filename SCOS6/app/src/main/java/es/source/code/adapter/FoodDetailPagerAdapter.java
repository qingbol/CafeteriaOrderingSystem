package es.source.code.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by SamChen on 2017/10/16.
 */

public class FoodDetailPagerAdapter extends PagerAdapter {
    private List<View> vl_viewlist;

    public FoodDetailPagerAdapter(List<View> vl_viewlist) {
        this.vl_viewlist = vl_viewlist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = vl_viewlist.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(vl_viewlist.get(position));
    }

    @Override
    public int getCount() {
        return vl_viewlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
