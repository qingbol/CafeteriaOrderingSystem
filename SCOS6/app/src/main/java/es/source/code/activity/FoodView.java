package es.source.code.activity;

import android.app.Service;
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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.R;
import es.source.code.adapter.ListViewAdapter;
import es.source.code.adapter.MyPagerAdapter;
import es.source.code.model.User;
import es.source.code.service.ServerObserverService;
import es.source.code.service.UpdateService;

public class FoodView extends AppCompatActivity {
    ArrayList<View> vl_viewlist;          //保存每个页面
    ArrayList<String> tb_tablist;         //保存每个标签
    ViewPager vp_viewpager;
    TabLayout tl_tablayout;
    LayoutInflater li_layoutinflater;
    View view_colddish, view_hotdish, view_seafood, view_drink;
    private User user;

    //冷菜数据
    private int[] colddishimage = {R.mipmap.colddish1, R.mipmap.colddish2, R.mipmap.colddish3, R.mipmap.colddish4};
    private String[] colddish = {"冷菜1", "冷菜2", "冷菜3", "冷菜4"};
    private double[] colddishcost = {8.00, 10.00, 9.00, 6.00};

    //热菜数据
    private int[] hotdishimage = {R.mipmap.fooddetailimage, R.mipmap.hotdish2, R.mipmap.hotdish3, R.mipmap.hotdish4, R.mipmap.hotdish5, R.mipmap.fooddetailimage, R.mipmap.hotdish2, R.mipmap.hotdish3, R.mipmap.hotdish4, R.mipmap.hotdish5, R.mipmap.fooddetailimage, R.mipmap.hotdish2, R.mipmap.hotdish3, R.mipmap.hotdish4, R.mipmap.hotdish5};
    private String[] hotdish = {"热菜1", "热菜2", "热菜3", "热菜4", "热菜5", "5", "6", "7", "8", "9", "5", "6", "7", "8", "9"};
    private double[] hotdishcost = {15.00, 18.00, 20.00, 12.00, 22.00, 15.00, 18.00, 20.00, 12.00, 22.00, 15.00, 18.00, 20.00, 12.00, 22.00};

    //海鲜数据
    private int[] seafoodimage = {R.mipmap.seafood1, R.mipmap.seafood2, R.mipmap.seafood3};
    private String[] seafood = {"海鲜1", "海鲜2", "海鲜3"};
    private double[] seafoodcost = {20.00, 30.00, 55.00};

    //酒水数据
    private int[] drinkimage = {R.mipmap.drink1, R.mipmap.drink2, R.mipmap.drink3, R.mipmap.drink4, R.mipmap.drink5};
    private String[] drink = {"可乐", "雪碧", "美年达", "矿泉水", "漓泉啤酒"};
    private double[] drinkcost = {3.00, 3.00, 3.00, 1.50, 5.00};

    private List<Map<String, Object>> dishlist_data;
    private SimpleAdapter dishlist_adapter;
    private ListView dishlistview;

    private Messenger ServiceMessenger;
    private Messenger FoodViewMessenger;
    private String foodname;
    private int stock;

    private Handler sMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle datafromservice = null;
            if (msg.what == 10) {                    //获取Service传来的msg
                datafromservice = msg.getData();
                foodname = datafromservice.getString("foodname");
                stock = datafromservice.getInt("foodnumber");
            }
            super.handleMessage(msg);
        }
    };

    //Service绑定状态监听
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    //添加ActionBar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.foodview_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //ActonBar点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_realtimeupdate:                         //点击“启动实时更新”
                Intent serviceintent = new Intent(FoodView.this, ServerObserverService.class);
                bindService(serviceintent, connection, BIND_AUTO_CREATE);
                startService(new Intent(this, UpdateService.class));
                if (FoodViewMessenger == null) {
                    FoodViewMessenger = new Messenger(sMessageHandler);
                }
                Message msg = new Message();
                if (item.getTitle().equals("启动实时更新")) {
                    msg.what = 1;
                    msg.replyTo = FoodViewMessenger;
                    try {
                        FoodViewMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    item.setTitle("停止实时更新");
                } else if (item.getTitle().equals("停止实时更新")) {
                    msg.what = 0;
                    msg.replyTo = FoodViewMessenger;
                    try {
                        FoodViewMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    item.setTitle("启动实时更新");
                }
                break;
            case R.id.action_chooseddish:               //点击“已点菜品”
                Toast.makeText(this, "已点菜品", Toast.LENGTH_SHORT).show();
                Intent action_chooseddish_intent = new Intent(FoodView.this, FoodOrderView.class);
                action_chooseddish_intent.putExtra("action_flag", "action_chooseddish");
                startActivity(action_chooseddish_intent);
                break;
            case R.id.action_checkorder:               //点击“查看订单”
                Toast.makeText(this, "查看订单", Toast.LENGTH_SHORT).show();
                Intent action_checkorder_intent = new Intent(FoodView.this, FoodOrderView.class);
                action_checkorder_intent.putExtra("action_flag", "action_checkorder");
                startActivity(action_checkorder_intent);
                break;
            case R.id.action_callservice:              //点击“呼叫服务”
                Toast.makeText(this, "呼叫服务", Toast.LENGTH_SHORT).show();
                Intent testservice = new Intent(this, UpdateService.class);
                startService(testservice);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        findViews();
        initList();

        user = (User) getIntent().getSerializableExtra("user_data");

        //添加ViewList
        view_colddish = li_layoutinflater.inflate(R.layout.food_list_layout, null);
        view_hotdish = li_layoutinflater.inflate(R.layout.food_list_layout, null);
        view_seafood = li_layoutinflater.inflate(R.layout.food_list_layout, null);
        view_drink = li_layoutinflater.inflate(R.layout.food_list_layout, null);
        vl_viewlist.add(view_colddish);
        vl_viewlist.add(view_hotdish);
        vl_viewlist.add(view_seafood);
        vl_viewlist.add(view_drink);

        //添加TabList
        tb_tablist.add("冷菜");
        tb_tablist.add("热菜");
        tb_tablist.add("海鲜");
        tb_tablist.add("酒水");
        tl_tablayout.setTabMode(TabLayout.MODE_FIXED);  //设置标签模式，默认系统模式

        //添加标签元素
        //tl_tablayout.addTab(tl_tablayout.newTab().setText(tb_tablist.get(0)));
        //tl_tablayout.addTab(tl_tablayout.newTab().setText(tb_tablist.get(1)));
        //tl_tablayout.addTab(tl_tablayout.newTab().setText(tb_tablist.get(2)));
        //tl_tablayout.addTab(tl_tablayout.newTab().setText(tb_tablist.get(3)));

        //绑定适配器
        MyPagerAdapter pageradapter = new MyPagerAdapter(vl_viewlist, tb_tablist);
        vp_viewpager.setAdapter(pageradapter);
        tl_tablayout.setupWithViewPager(vp_viewpager);

        //设置标签点击事件监听
        tl_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp_viewpager.setCurrentItem(tab.getPosition());
                Toast.makeText(FoodView.this, tab.getText(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        //显示冷菜菜单
        getDish_list(colddishimage, colddish, colddishcost);
        dishlist_adapter = new ListViewAdapter(this, dishlist_data, R.layout.dish_listitem_layout,
                new String[]{"dishimage", "dishname", "dishcost", "dishstock"},
                new int[]{R.id.im_foodimage, R.id.tv_dishname, R.id.tv_cost, R.id.tv_stock});
        dishlistview = (ListView) view_colddish.findViewById(R.id.foodlist);
        dishlistview.setAdapter(dishlist_adapter);

        //显示热菜菜单
        getDish_list(hotdishimage, hotdish, hotdishcost);
        dishlist_adapter = new ListViewAdapter(this, dishlist_data, R.layout.dish_listitem_layout,
                new String[]{"dishimage", "dishname", "dishcost", "dishstock"},
                new int[]{R.id.im_foodimage, R.id.tv_dishname, R.id.tv_cost, R.id.tv_stock});
        dishlistview = (ListView) view_hotdish.findViewById(R.id.foodlist);
        dishlistview.setAdapter(dishlist_adapter);

        //显示海鲜菜单
        getDish_list(seafoodimage, seafood, seafoodcost);
        dishlist_adapter = new ListViewAdapter(this, dishlist_data, R.layout.dish_listitem_layout,
                new String[]{"dishimage", "dishname", "dishcost", "dishstock"},
                new int[]{R.id.im_foodimage, R.id.tv_dishname, R.id.tv_cost, R.id.tv_stock});
        dishlistview = (ListView) view_seafood.findViewById(R.id.foodlist);
        dishlistview.setAdapter(dishlist_adapter);

        //显示酒水菜单
        getDish_list(drinkimage, drink, drinkcost);
        dishlist_adapter = new ListViewAdapter(this, dishlist_data, R.layout.dish_listitem_layout,
                new String[]{"dishimage", "dishname", "dishcost", "dishstock"},
                new int[]{R.id.im_foodimage, R.id.tv_dishname, R.id.tv_cost, R.id.tv_stock});
        dishlistview = (ListView) view_drink.findViewById(R.id.foodlist);
        dishlistview.setAdapter(dishlist_adapter);

        /*dishlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });*/

    }

    private void findViews() {
        li_layoutinflater = LayoutInflater.from(this);
        tl_tablayout = (TabLayout) findViewById(R.id.tabs);
        vp_viewpager = (ViewPager) findViewById(R.id.vp_view);

    }

    private void initList() {
        tb_tablist = new ArrayList<>();
        vl_viewlist = new ArrayList<>();
    }

    //获取菜单列表数据
    public List<Map<String, Object>> getDish_list(int[] dishimage, String[] dishname, double[] dishcost) {
        dishlist_data = new ArrayList<Map<String, Object>>();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < dishname.length; i++) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("dishimage", dishimage[i]);
                map.put("dishname", dishname[i]);
                map.put("dishcost", dishcost[i]);
            /*if (foodname.equals(dishname[i])) {
                map.put("dishstock", stock);
            }
            else {*/
                map.put("dishstock", 10);
                //}
                dishlist_data.add(map);
            }
        }
        return dishlist_data;
    }

}
