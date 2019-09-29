package es.source.code.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Intent intent=getIntent();
        Button button1=(Button) findViewById(R.id.button1);
        Button button2=(Button) findViewById(R.id.button2);
        Button button3=(Button) findViewById(R.id.button3);//登录/注册按钮
        button3.setOnClickListener(new View.OnClickListener(){//按登录/注册按钮时到LoginOrRegister界面
            @Override
            public void onClick(View v){
                //String FromLoginOrRegister="Return";
                Intent intent2=new Intent(MainScreen.this,LoginOrRegister.class);
                startActivity(intent2);
            }
        });
        if(intent.getExtras().containsKey("extra_data")){
            String data=intent.getStringExtra("extra_data");
            if(!data.equals("FromEntry")){
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
            }
        }else if(intent.getExtras().containsKey("FromLoginOrRegister")){
            String data1=intent.getStringExtra("FromLoginOrRegister");
            if(data1.equals("LoginSuccess")){//如何从Login收到的是LoginSuccess，则将点菜和查看订单设置为显示
                if(button1.getVisibility()==View.INVISIBLE){
                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                }
            }
        }

    }
}*/


import android.app.Activity;

import android.widget.Toast;

import es.source.code.model.User;

public class MainScreen extends Activity {
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private int[] icon = { R.drawable.img4, R.drawable.img5,// 图片封装为一个数组
            R.drawable.img2, R.drawable.img3};
    private String[] iconName = { "登录/注册", "系统帮助","点菜", "查看订单" };//设置图片对应的名字
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        gview = (GridView) findViewById(R.id.gview);
        data_list = new ArrayList<Map<String, Object>>();//新建List
        Intent intent=getIntent();
        User user=new User();
        if(intent.getExtras().containsKey("extra_data")){
            String data=intent.getStringExtra("extra_data");
            if(!data.equals("FromEntry")){
                getData0();//根据不同的条件获取不同的数据，当不是从Entry跳入时，隐藏导航项：点菜、查看订单
            }else{
                getData();//当从Entry跳入的时候，正常显示
            }
        }else if(intent.getExtras().containsKey("FromLoginOrRegister")){
            String data1=intent.getStringExtra("FromLoginOrRegister");
            if(data1.equals("LoginSuccess")){//如何从Login收到的是LoginSuccess，则将点菜和查看订单设置为显示
               getData();
                user=(User)intent.getSerializableExtra("user");
            }else if(data1.equals("RegisterSuccess")){
                getData();
                user=(User)intent.getSerializableExtra("user");
                Toast.makeText(MainScreen.this, "欢迎您成为SCOS新用户", 1).show();

            }else{
                user=null;//当传入 MainScreen 的 String 值为其他时，将 user 赋值为 NULL
            }
        }
        //新建适配器
        String [] from ={"image","text"};//将被添加到Map映射上的key
        int [] to = {R.id.image,R.id.text};//将绑定数据的视图的ID跟from参数对应
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener()//监听每个item的点击事件
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                if(position==0){//当点击登录/注册按钮时跳到LoginOrRegister界面
                    Intent intent2=new Intent(MainScreen.this,LoginOrRegister.class);
                    startActivity(intent2);
                }else if(position==2){////////////////////////////当点击点菜时跳到"FoodView界面
                    Intent intent3=new Intent(MainScreen.this,FoodView.class);
                    //intent3.putExtra("user_toFoodView",user);
                    startActivity(intent3);
                }
            }
        });
    }
    public List<Map<String, Object>> getData0(){
        Map<String, Object> map = new HashMap<String, Object>();//获取登录/注册的数据
        map.put("image", icon[0]);
        map.put("text", iconName[0]);
        data_list.add(map);

        Map<String, Object> map1 = new HashMap<String, Object>();//获取系统帮助的数据
        map1.put("image", icon[1]);
        map1.put("text", iconName[1]);
        data_list.add(map1);

        return data_list;
    }
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }


}