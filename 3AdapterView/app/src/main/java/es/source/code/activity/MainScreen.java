package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.widget.Toast;

import es.source.code.model.User;

public class MainScreen extends Activity {
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.icon4, R.mipmap.icon5,
            R.mipmap.icon2, R.mipmap.icon3};
    private String[] iconName = {"登录/注册", "系统帮助", "点菜", "查看订单"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        gview = (GridView) findViewById(R.id.gview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        //getData();
        Intent intent = getIntent();
        User user = new User();
//        getData();
        if (intent.getExtras().containsKey("extra_data")) {
            String data = intent.getStringExtra("extra_data");
            if (!data.equals("FromEntry")) {
//                data_list.get(2).get("点菜").getVisibility()
                getDataNoLogin();
            } else {
                getData();
//                findViewById(R.mipmap.icon2);

            }
        } else if (intent.getExtras().containsKey("FromLoginOrRegister")) {
            String data1 = intent.getStringExtra("FromLoginOrRegister");
            if (data1.equals("LoginSuccess")) {//如何从Login收到的是LoginSuccess，则将点菜和查看订单设置为显示
                getData();
                user = (User) intent.getSerializableExtra("user");
            } else if (data1.equals("RegisterSuccess")) {
                getData();
                user = (User) intent.getSerializableExtra("user");
                Toast.makeText(MainScreen.this, "欢迎您成为SCOS新用户", 1).show();

            } else {
                getDataNoLogin();
                user = null;//当传入 MainScreen 的 String 值为其他时，将 user 赋值为 NULL
            }
        }
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {
                    Intent intent2 = new Intent(MainScreen.this, LoginOrRegister.class);
                    startActivity(intent2);
                }
            }
        });

    }

    public List<Map<String, Object>> getDataNoLogin() {
        //cion和iconName的长度是相同的，这里任选其一都可以

        for (int i = 0; i < 2; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }
        return data_list;
    }


}