package es.source.code.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.R;
import es.source.code.Thread.PayAsyncTask;
import es.source.code.adapter.MyPagerAdapter;
import es.source.code.model.User;


public class FoodOrderView extends AppCompatActivity {
    ArrayList<View> vl_viewlist;          //保存每个页面
    ArrayList<String> tb_tablist;         //保存每个标签
    ViewPager vp_viewpager;
    TabLayout tl_tablayout;
    LayoutInflater li_layoutinflater;
    View view_noorder, view_ordered;

    private PayAsyncTask payTask;
    private int orderedtotalnum;
    private double orderedtotalcost;

    private List<Map<String, Object>> orderlist_data;
    private SimpleAdapter orderlistview_adapter;
    private ListView orderlistview;

    private User user;
    private String actionmessage;

    //未下单数据
    private int[] noorderdishimage = {R.mipmap.drink2, R.mipmap.hotdish3};
    private String[] noorderdishname = {"雪碧", "热菜3"};
    private double[] noorderdishcost = {3.00, 20.00};
    private int[] noorderdishnum = {2, 1};

    //已下单数据
    private int[] ordereddishimage = {R.mipmap.hotdish2, R.mipmap.drink5};
    private String[] ordereddishname = {"热菜2", "漓泉啤酒"};
    private double[] ordereddishcost = {18.00, 5.00};
    private int[] ordereddishnum = {1, 1};

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);
        findViews();
        initList();

        Intent actionintent = getIntent();          //接收ActionBar的Intent
        actionmessage = actionintent.getStringExtra("action_flag");

        Intent userintent = getIntent();
        user = (User) userintent.getSerializableExtra("user_data");

        //添加ViewList
        view_noorder = li_layoutinflater.inflate(R.layout.no_order_list_layout, null);
        view_ordered = li_layoutinflater.inflate(R.layout.ordered_list_layout, null);
        vl_viewlist.add(view_noorder);
        vl_viewlist.add(view_ordered);

        //添加TabList
        tb_tablist.add("未下单菜");
        tb_tablist.add("已下单菜");
        tl_tablayout.setTabMode(TabLayout.MODE_FIXED);  //设置标签模式，默认系统模式

        //绑定适配器
        MyPagerAdapter pageradapter = new MyPagerAdapter(vl_viewlist, tb_tablist);
        vp_viewpager.setAdapter(pageradapter);
        tl_tablayout.setupWithViewPager(vp_viewpager);

        /*switch (actionmessage.toString()){
            case "action_chooseddish":
                vp_viewpager.setCurrentItem(0);
                break;
            case "action_checkorder":
                vp_viewpager.setCurrentItem(1);
                break;
            case "FromMainScreen":
                vp_viewpager.setCurrentItem(0);
                break;
        }*/

        //设置标签点击事件监听
        if (actionmessage.toString().equals("action_chooseddish")) {
            vp_viewpager.setCurrentItem(0);
        } else if (actionmessage.toString().equals("action_checkorder")) {
            vp_viewpager.setCurrentItem(1);
        } else {
            tl_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    vp_viewpager.setCurrentItem(tab.getPosition());
                    Toast.makeText(FoodOrderView.this, tab.getText(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }

        //显示未下单列表
        getOrderlist(noorderdishimage, noorderdishname, noorderdishcost, noorderdishnum);
        orderlistview_adapter = new SimpleAdapter(this, orderlist_data, R.layout.no_order_listitem_layout,
                new String[]{"dishimage", "dishname", "dishcost", "dishnum"}, new int[]{
                R.id.im_noorder_foodimage, R.id.tv_dishname, R.id.tv_cost, R.id.tv_num
        });
        orderlistview = (ListView) view_noorder.findViewById(R.id.lv_noorder_foodlist);
        orderlistview.setAdapter(orderlistview_adapter);

        //显示已下单列表
        getOrderlist(ordereddishimage, ordereddishname, ordereddishcost, ordereddishnum);
        final TextView tv_orderedtotalnum = (TextView) view_ordered.findViewById(R.id.tv_orderedtotalnum);
        final TextView tv_orderedtotalcost = (TextView) view_ordered.findViewById(R.id.tv_orderedtotalcost);
        orderedtotalnum = getNum(ordereddishnum);
        orderedtotalcost = getCost(ordereddishcost,ordereddishnum);
        tv_orderedtotalnum.setText(Integer.toString(orderedtotalnum));
        tv_orderedtotalcost.setText(Double.toString( orderedtotalcost));

        orderlistview_adapter = new SimpleAdapter(this, orderlist_data, R.layout.ordered_listitem_layout,
                new String[]{"dishimage", "dishname", "dishcost", "dishnum"}, new int[]{
                R.id.im_orderedfoodimage, R.id.tv_dishname, R.id.tv_cost, R.id.tv_num
        });
        orderlistview = (ListView) view_ordered.findViewById(R.id.lv_ordered_foodlist);
        orderlistview.setAdapter(orderlistview_adapter);

        Button bt_submit = (Button) view_noorder.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodOrderView.this, "下单成功", Toast.LENGTH_SHORT).show();
            }
        });

        final Button bt_pay = (Button) view_ordered.findViewById(R.id.bt_pay);
        final ProgressBar pb_payprogressbar = (ProgressBar) view_ordered.findViewById(R.id.pb_payprogressbar);
        final TextView tv_payprogress = (TextView) view_ordered.findViewById(R.id.tv_payprogress);
        final SharedPreferences sp_paybill = getSharedPreferences("Pay_Bill", MODE_PRIVATE);
        final SharedPreferences.Editor paybill_editor = null;

        pb_payprogressbar.setVisibility(View.GONE);
        tv_payprogress.setVisibility(View.GONE);

        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    Toast.makeText(FoodOrderView.this, "请先登录", Toast.LENGTH_SHORT).show();
                    Intent loginintent = new Intent(FoodOrderView.this, LoginOrRegisterActivity.class);
                    startActivity(loginintent);
                    finish();
                } else if (user.getOldUser() == true) {
                    Toast.makeText(FoodOrderView.this, "您好，老客户，本次你可享受7折优惠", Toast.LENGTH_SHORT).show();
                    payTask = new PayAsyncTask(FoodOrderView.this, pb_payprogressbar, tv_payprogress, bt_pay, sp_paybill, paybill_editor);
                    payTask.execute(tv_orderedtotalnum.getText().toString(), tv_orderedtotalcost.getText().toString());
                } else {
                    Toast.makeText(FoodOrderView.this, "新客户", Toast.LENGTH_SHORT).show();
                    payTask = new PayAsyncTask(FoodOrderView.this, pb_payprogressbar, tv_payprogress, bt_pay, sp_paybill, paybill_editor);
                    payTask.execute(tv_orderedtotalnum.getText().toString(), tv_orderedtotalcost.getText().toString());
                }
            }
        });

    }

    private void findViews() {
        li_layoutinflater = LayoutInflater.from(this);
        tl_tablayout = (TabLayout) findViewById(R.id.foodorderviewtabs);
        vp_viewpager = (ViewPager) findViewById(R.id.vp_foodorderview);

    }

    private void initList() {
        tb_tablist = new ArrayList<>();
        vl_viewlist = new ArrayList<>();
    }

    //获取订单数据
    public List<Map<String, Object>> getOrderlist(int[] dishimage, String[] dishname, double[] dishcost, int[] dishnum) {
        orderlist_data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < dishname.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("dishimage", dishimage[i]);
            map.put("dishname", dishname[i]);
            map.put("dishcost", dishcost[i]);
            map.put("dishnum", dishnum[i]);
            orderlist_data.add(map);
        }
        return orderlist_data;
    }

    public int getNum(int[] dishnum) {
        int count = 0;
        for (int i = 0; i < dishnum.length; i++) {
            count += dishnum[i];
        }
        return count;
    }

    public double getCost(double[] dishcost, int[] dishnum) {
        double cost = 0;
        for (int i = 0; i < dishcost.length; i++) {
            cost += (dishcost[i] * dishnum[i]);
        }
        return cost;
    }
}
