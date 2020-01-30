package es.source.code.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.source.code.R;
import es.source.code.adapter.FoodDetailPagerAdapter;

import static es.source.code.R.id.iv_fooddetailimage;

public class FoodDetailed extends AppCompatActivity {
    List<View> fooddetaillist;
    ViewPager vp_fooddetailviewpager;
    LayoutInflater li_inflater;
    View view_fooddetail1,view_fooddetail2,view_fooddetail3;

    //private int [] fooddetailimage = {R.mipmap.colddish1, R.mipmap.fooddetailimage, R.mipmap.fooddetailimage2};
    //private String [] fooddetailname ={"冷菜1","热菜2","海鲜3"};
    //private double [] fooddetailcost ={1.00,5.00,10.00};
    //private List<Map<String, Object>> fooddetaildata_list;
    //private SimpleAdapter fooddetail_adapter;
    //private ListView fooddetaillistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detailed);
        findViews();
        initList();

        //添加ViewList
        view_fooddetail1 = li_inflater.inflate(R.layout.food_detail_layout,null);
        view_fooddetail2 = li_inflater.inflate(R.layout.food_detail_layout,null);
        view_fooddetail3 = li_inflater.inflate(R.layout.food_detail_layout,null);
        fooddetaillist.add(view_fooddetail1);
        fooddetaillist.add(view_fooddetail2);
        fooddetaillist.add(view_fooddetail3);

        //绑定适配器
        FoodDetailPagerAdapter viewpageradapter = new FoodDetailPagerAdapter(fooddetaillist);
        vp_fooddetailviewpager.setAdapter(viewpageradapter);

        //设置菜品详情
        //菜品1
        ImageView iv_fooddetailimage1 = (ImageView) view_fooddetail1.findViewById(iv_fooddetailimage);
        TextView tv_fooddetailname1 = (TextView) view_fooddetail1.findViewById(R.id.tv_fooddetailname);
        TextView tv_fooddetailcost1 = (TextView) view_fooddetail1.findViewById(R.id.tv_fooddetailcost);
        //EditText et_fooddetailremark1 = (EditText) view_fooddetail1.findViewById(R.id.et_fooddetailremark);
        final Button bt_fooddetailchoose1 = (Button) view_fooddetail1.findViewById(R.id.bt_fooddetailchoose);

        iv_fooddetailimage1.setImageResource(R.mipmap.colddish1);
        tv_fooddetailname1.setText("菜品1");
        tv_fooddetailcost1.setText("￥1.00");
        bt_fooddetailchoose1.setText("点菜");
        bt_fooddetailchoose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt_fooddetailchoose1.getText().toString() == "点菜") {
                    Toast.makeText(getApplicationContext(), "点菜成功", Toast.LENGTH_SHORT).show();
                    bt_fooddetailchoose1.setText("退点");
                } else {
                    bt_fooddetailchoose1.setText("点菜");
                    Toast.makeText(getApplicationContext(), "退菜成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //菜品2
        ImageView iv_fooddetailimage2 = (ImageView) view_fooddetail2.findViewById(iv_fooddetailimage);
        TextView tv_fooddetailname2 = (TextView) view_fooddetail2.findViewById(R.id.tv_fooddetailname);
        TextView tv_fooddetailcost2 = (TextView) view_fooddetail2.findViewById(R.id.tv_fooddetailcost);
        //EditText et_fooddetailremark2 = (EditText) view_fooddetail2.findViewById(R.id.et_fooddetailremark);
        final Button bt_fooddetailchoose2 = (Button) view_fooddetail2.findViewById(R.id.bt_fooddetailchoose);

        iv_fooddetailimage2.setImageResource(R.mipmap.fooddetailimage);
        tv_fooddetailname2.setText("菜品2");
        tv_fooddetailcost2.setText("￥5.00");
        bt_fooddetailchoose2.setText("点菜");
        bt_fooddetailchoose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt_fooddetailchoose2.getText().toString() == "点菜") {
                    Toast.makeText(getApplicationContext(), "点菜成功", Toast.LENGTH_SHORT).show();
                    bt_fooddetailchoose2.setText("退点");
                } else {
                    bt_fooddetailchoose2.setText("点菜");
                    Toast.makeText(getApplicationContext(), "退菜成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //菜品3
        ImageView iv_fooddetailimage3 = (ImageView) view_fooddetail3.findViewById(iv_fooddetailimage);
        TextView tv_fooddetailname3 = (TextView) view_fooddetail3.findViewById(R.id.tv_fooddetailname);
        TextView tv_fooddetailcost3 = (TextView) view_fooddetail3.findViewById(R.id.tv_fooddetailcost);
        //EditText et_fooddetailremark3 = (EditText) view_fooddetail3.findViewById(R.id.et_fooddetailremark);
        final Button bt_fooddetailchoose3 = (Button) view_fooddetail3.findViewById(R.id.bt_fooddetailchoose);

        iv_fooddetailimage3.setImageResource(R.mipmap.fooddetailimage2);
        tv_fooddetailname3.setText("菜品3");
        tv_fooddetailcost3.setText("￥10.00");
        bt_fooddetailchoose3.setText("点菜");
        bt_fooddetailchoose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt_fooddetailchoose3.getText().toString() == "点菜") {
                    Toast.makeText(getApplicationContext(), "点菜成功", Toast.LENGTH_SHORT).show();
                    bt_fooddetailchoose3.setText("退点");
                } else {
                    bt_fooddetailchoose3.setText("点菜");
                    Toast.makeText(getApplicationContext(), "退菜成功", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findViews() {
        li_inflater = LayoutInflater.from(this);
        vp_fooddetailviewpager = (ViewPager) findViewById(R.id.vp_fooddetailviewpager);
    }

    private void initList() {
        fooddetaillist = new ArrayList<>();
    }

    /*public List<Map<String, Object>> getFooddetaildata_list(){
        fooddetaildata_list = new ArrayList<Map<String, Object>>();
        for(int i = 0;i < fooddetailname.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("fooddetailimage", fooddetailimage[i]);
            map.put("fooddetailname",fooddetailname[i]);
            map.put("fooddetailcost",fooddetailcost[i]);
            fooddetaildata_list.add(map);
        }
        return fooddetaildata_list;
    }*/

}
