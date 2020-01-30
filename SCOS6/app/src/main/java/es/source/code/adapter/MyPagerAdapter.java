package es.source.code.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by SamChen on 2017/10/14.
 */

public class MyPagerAdapter extends PagerAdapter {
    private ArrayList<View> vl_viewlist;
    private ArrayList<String> tb_tablist;
    //private Context context;


    public MyPagerAdapter(ArrayList<View> vl_viewlist) {
        this.vl_viewlist = vl_viewlist;
    }

    public MyPagerAdapter(ArrayList<View> vlviewlist, ArrayList<String> tbtablist){
        this.vl_viewlist = vlviewlist;
        this.tb_tablist = tbtablist;
        //this.context = context;
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

    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return tb_tablist.get(position);
    }
}
