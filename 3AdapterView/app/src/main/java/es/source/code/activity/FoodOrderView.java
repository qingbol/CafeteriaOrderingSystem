package es.source.code.activity;

/**
 * Created by Administrator on 2017/10/19.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

//import android.support.design.widget.Tablayout;


public class FoodOrderView extends AppCompatActivity  {
    private TextView title, item_weixin, item_tongxunlu, item_faxian, item_me;
    private ViewPager vp;
    private NoOrderFragment oneFragment;
    private UnderOrderFragment twoFragment;

    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private android.support.design.widget.TabLayout mTableLayout;//是一个可切换的布局

    //////String[] titles = new String[]{"微信", "通讯录", "发现", "我"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//加载menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        ////getSupportActionBar().hide();
        setContentView(R.layout.food_order_view);
        initViews();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(4);//ViewPager的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        //vp.setCurrentItem(0);//初始设置ViewPager选中第一帧

        mTableLayout = (TabLayout) findViewById(R.id.main_tab);
        mTableLayout.setupWithViewPager(vp);// tab_layout.setupWithViewPager(viewPager);//这两句是将ViewPager和TabLayout联系起来
        mTableLayout.setTabMode(android.support.design.widget.TabLayout.MODE_FIXED);// tab_layout.setTabsFromPagerAdapter(mAdapter); 这三个方法。
        Intent intent=getIntent();
        int page = intent.getIntExtra("page",0);
        vp.setCurrentItem(page);
    }

    /**
     * 初始化布局View
     */
    private void initViews() {
        vp = (ViewPager) findViewById(R.id.mainViewPager);
        oneFragment = new NoOrderFragment();
        twoFragment = new UnderOrderFragment();

        //给FragmentList添加数据
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);

    }

    /**
     * 点击底部Text 动态修改ViewPager的内容
     */



    public class FragmentAdapter extends FragmentPagerAdapter {//配适器算法

        List<Fragment> fragmentList = new ArrayList<Fragment>();

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }//返回当前Fragment的位置

        @Override
        public int getCount() {
            return fragmentList.size();
        }//返回fragment的大小

        ////////////////////////////自己加的
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "已下单菜";
                default:
                    return "未下单菜";

            }
        }

    }

    /*
     *由ViewPager的滑动修改底部导航Text的颜色
     */

}
