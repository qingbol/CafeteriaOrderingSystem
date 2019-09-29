package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class FoodView extends AppCompatActivity  {
    private ViewPager vp;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FouthFragment fouthFragment;
    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private android.support.design.widget.TabLayout mTableLayout;//是一个可切换的布局
    private Toolbar mToolbar;



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
        requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        setContentView(R.layout.activity_food_view);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        initViews();
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(4);//ViewPager的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧

        mTableLayout = (TabLayout) findViewById(R.id.main_tab_foodview);
        mTableLayout.setupWithViewPager(vp);// tab_layout.setupWithViewPager(viewPager);//这两句是将ViewPager和TabLayout联系起来
        mTableLayout.setTabMode(android.support.design.widget.TabLayout.MODE_FIXED);// tab_layout.setTabsFromPagerAdapter(mAdapter); 这三个方法。

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// TODO Auto-generated method stub
        if(item.getItemId() == R.id.menu_under_order){
            Intent intent1=new Intent(FoodView.this,FoodOrderView.class);
            intent1.putExtra("page",1);
            startActivity(intent1);
        }
        else if(item.getItemId() ==R.id.menu_view_order){
            Intent intent2=new Intent(FoodView.this,FoodOrderView.class);
            intent2.putExtra("page",0);
            startActivity(intent2);
        }
        return true;
    }

    /**
     * 初始化布局View
     */
    private void initViews() {

        vp = (ViewPager) findViewById(R.id.mainViewPager_foodview);
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();
        threeFragment = new ThreeFragment();
        fouthFragment = new FouthFragment();
        //给FragmentList添加数据
        mFragmentList.add(oneFragment);
        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fouthFragment);
    }



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
                    return "冷菜";
                case 1:
                    return "热菜";
                case 2:
                    return "海鲜";
                default:
                    return "酒水";
            }
        }
    }

    /*
     *由ViewPager的滑动修改底部导航Text的颜色
     */

}






