package es.source.code.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.R;
import es.source.code.model.Constant;
import es.source.code.model.User;

public class MainScreen extends AppCompatActivity {
    public static final int LOGINORREGISTER = 1;
    public static final int RETURN = 0;
    //Button menuBttun, orderBttun, loginorregisterButtn, helpButtun;
    TextView sendTest;
    //存放图标的数组
    private int[] icons = {
            R.mipmap.login,
            R.mipmap.help,
            R.mipmap.menu,
            R.mipmap.order
    };

    //存放标题的数组
    private String[] items = {"登录/注册", "系统帮助", "菜单", "订单"};
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private GridView gridview;
    private User user;

    SharedPreferences sp_logindata;
    SharedPreferences.Editor sp_editor;
    int loginstate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Log.v("MainScreen", "onCreate");

        String[] from = {"image", "list"};
        int[] to = {R.id.iv_icon, R.id.tv_list};

        data_list = new ArrayList<Map<String, Object>>();
        Intent recintent = getIntent();
        Constant.str = recintent.getStringExtra("str");
        sendTest = (TextView) findViewById(R.id.tv_sendtest);
        sendTest.setText(Constant.str);
        sp_logindata = getSharedPreferences("logindata", MODE_PRIVATE);
        loginstate = sp_logindata.getInt("Loginstate", -1);
        if (loginstate == 0) {
            getHideData();
        } else if (loginstate == 1) {
            getData();
        }


        sim_adapter = new SimpleAdapter(this, data_list, R.layout.grid_item, from, to);//实现SimpleAdapter的构造函数
        gridview = (GridView) findViewById(R.id.gv_GridView);
        gridview.setAdapter(sim_adapter);//绑定适配器


        //添加点击事件
        gridview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent loginintent = new Intent();
                        loginintent.setClass(MainScreen.this, LoginOrRegisterActivity.class);
                        loginintent.setAction("socs.intent.action.SOCSLOGIN");
                        startActivityForResult(loginintent, 0);
                        Log.v("MainScreen", "Click login");
                        break;
                    case 1:
                        Intent helpintent = new Intent(MainScreen.this, SCOSHelper.class);
                        startActivity(helpintent);
                        Log.v("MainScreen", "Click help");
                        break;
                    case 2:
                        Intent menuintent = new Intent(MainScreen.this, FoodView.class);
                        menuintent.putExtra("user_data", user);
                        startActivity(menuintent);
                        Log.v("MainScreen", "Click menu");
                        break;
                    case 3:
                        Intent orderintent = new Intent(MainScreen.this, FoodOrderView.class);
                        orderintent.putExtra("user_data", user);
                        orderintent.putExtra("action_flag", "FromMainScreen");
                        startActivity(orderintent);
                        Log.v("MainScreen", "Click order");
                        break;
                }
            }
        });

        //menuBttun = (Button) findViewById(R.id.bt_menu);
        //orderBttun = (Button) findViewById(R.id.bt_order);
        //loginorregisterButtn = (Button) findViewById(R.id.bt_loginorregister);
        //helpButtun = (Button) findViewById(R.id.bt_help);

        /*Intent recintent = getIntent();
        Constant.str = recintent.getStringExtra("str");
        sendTest = (TextView) findViewById(R.id.tv_sendtest);
        sendTest.setText(Constant.str);
        if (!Constant.str.equals("FromEntry")) {
            menuBttun.setVisibility(View.INVISIBLE);
            orderBttun.setVisibility(View.INVISIBLE);
        }
        loginorregisterButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuintent = new Intent();
                menuintent.setClass(MainScreen.this, LoginOrRegisterActivity.class);
                menuintent.setAction("socs.intent.action.SOCSLOGIN");
                startActivityForResult(menuintent, 0);
            }
        });*/
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null){
            return;
        }
        if (resultCode == 0) {
            loginstate = sp_logindata.getInt("Loginstate", -1);
            Constant.str = data.getStringExtra("str");
            user = (User) data.getSerializableExtra("user_data");
            sendTest.setText(Constant.str);
            if (loginstate == 0) {
                getHideData();
                sim_adapter.notifyDataSetInvalidated();
            }
            if ((loginstate == 1) && user.getOldUser()) {
                getData();
                sim_adapter.notifyDataSetInvalidated();
                //user = (User) data.getSerializableExtra("user_data");
            }
            if ((loginstate == 1) && !user.getOldUser()) {
                getData();
                sim_adapter.notifyDataSetChanged();
                //user = (User) data.getSerializableExtra("user_data");
                Toast.makeText(this, "欢迎您成为SCOS新用户", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public List<Map<String, Object>> getData() {
        data_list.clear();
        for (int i = 0; i < items.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            //map.clear();
            map.put("image", icons[i]);
            map.put("list", items[i]);
            data_list.add(map);
        }
        return data_list;
    }

    public List<Map<String, Object>> getHideData() {
        data_list.clear();
        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            //map.clear();
            map.put("image", icons[i]);
            map.put("list", items[i]);
            data_list.add(map);
        }
        return data_list;
    }

}



