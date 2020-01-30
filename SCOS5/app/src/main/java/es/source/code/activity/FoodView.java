package es.source.code.activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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

import es.source.code.model.User;
import es.source.code.service.ServerObserverService;

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
    private Handler handler = new Handler();
    private Messenger mService;
    private boolean isConn;
    MenuItem  gMenuItem=null;


    private Messenger mMessenger = new Messenger(new sMessageHandler());
    class sMessageHandler extends Handler
    {
        @Override
        public void handleMessage(Message msgFromServer)
        {
            switch (msgFromServer.what)
            {
                case 10://////////////////当服务器传进来的是10的时候，解析数据
                    Bundle Breceice=msgFromServer.getData();
                    //接收数据
                    String[] strings=Breceice.getStringArray("FoodName");
                    int[] ints=Breceice.getIntArray("Inventory");
                    Intent intent1 = new Intent(FoodView.this,FoodView.class);
                    intent1.putExtra("FoodViewtoOneFragment",1);
                    Bundle bundle=new Bundle();
                    bundle.putIntArray("Number", ints);
                    bundle.putStringArray("Name", strings);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                    break;
            }
            super.handleMessage(msgFromServer);
        }
    }
    private ServiceConnection mConn = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mService = new Messenger(service);
            isConn = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mService = null;
            isConn = false;
        }
    };
    private void bindServiceInvoked()
    {
        Intent intent3= new Intent(this,ServerObserverService.class);
        bindService(intent3, mConn, Context.BIND_AUTO_CREATE);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//加载menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        gMenuItem= menu.findItem(R.id.menu_start_updata);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除工具栏
        requestWindowFeature(Window.FEATURE_CONTEXT_MENU);
        setContentView(R.layout.activity_food_view);
        bindServiceInvoked();//绑定
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
        User user=(User)getIntent().getSerializableExtra("user_toFoodView");///////////获得从MainScreen传递过来的参数user
        if(item.getItemId() == R.id.menu_under_order){
            Intent intent1=new Intent(FoodView.this,FoodOrderView.class);
            intent1.putExtra("page",1);
            intent1.putExtra("toFoodOrderView",user);
            startActivity(intent1);
        }
        else if(item.getItemId() ==R.id.menu_view_order){
            Intent intent2=new Intent(FoodView.this,FoodOrderView.class);
            intent2.putExtra("page",0);
            intent2.putExtra("toFoodOrderView",user);
            startActivity(intent2);
        }else if(item.getItemId()==R.id.menu_start_updata){//“启动实时更新”，当用户点击该 Action 时：
            //启动 ServerObserverService 服务，并向 ServerObserverService 发送
            //信息 Message 属性 what 值为 1
                gMenuItem.setTitle("停止实时更新");
                try {
                    Message msgFromClient = Message.obtain(null, 1, 0, 0);// h
                    msgFromClient.replyTo = mMessenger;
                    if (isConn) {
                        //往服务端发送消息
                        mService.send(msgFromClient);
                    }
                }catch (RemoteException e)
                {
                    e.printStackTrace();
                }
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
    @Override
    protected void onStop()
    {
        super.onDestroy();
        unbindService(mConn);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(mConn);
    }

}


































