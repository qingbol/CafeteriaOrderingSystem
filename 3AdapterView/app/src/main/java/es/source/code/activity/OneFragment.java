package es.source.code.activity;

/**
 * Created by Administrator on 2017/10/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static es.source.code.activity.R.id.DianCai_Button;


public class OneFragment extends Fragment{

    public class LengCaiAdapter extends BaseAdapter implements View.OnClickListener {
        //上下文
        private Context context;
        //数据项
        private List<LengCai> data;
        public LengCaiAdapter(List<LengCai> data){
            this.data = data;
        }
        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if(context == null)
                context = viewGroup.getContext();
            if(view == null){
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lengcai_item,null);
                viewHolder = new ViewHolder();
                viewHolder.mTv = (TextView)view.findViewById(R.id.lengcai_name);
                viewHolder.mTv1 = (TextView)view.findViewById(R.id.lengcai_price);
                viewHolder.mBtn = (Button)view.findViewById(DianCai_Button);
                view.setTag(viewHolder);
            }
            //获取viewHolder实例
            viewHolder = (ViewHolder)view.getTag();
            //设置数据
            viewHolder.mTv.setText(data.get(i).getName());
            viewHolder.mTv.setTag(R.id.tv,i);
            viewHolder.mTv1.setText(data.get(i).getPrice());
            viewHolder.mTv1.setTag(R.id.tv1,i);
            //设置监听事件
            viewHolder.mTv.setOnClickListener(this);
            //设置数据
            viewHolder.mBtn.setTag(R.id.btn,i);
            viewHolder.mBtn.setText("点菜");
            viewHolder.mBtn.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case DianCai_Button:
//                    int b=(int)view.getTag(DianCai_Button);//判断是第几个菜的按钮

                    Button mBtn=(Button)view.findViewById(DianCai_Button);
                    if(mBtn.getText()=="点菜"){
                        mBtn.setText("退点");
                        Toast.makeText(context,"点菜成功",Toast.LENGTH_SHORT).show();
                    }else{
                        mBtn.setText("点菜");
                        Toast.makeText(context,"退点成功",Toast.LENGTH_SHORT).show();
                    }
                    break;
               /*case R.id.lengcai_name:
                   Log.d("tag", "Tv_onClick: " + "view = " + view);
                   Toast.makeText(context,"我是文本",Toast.LENGTH_SHORT).show();
                   break;*/
            }
        }

        class ViewHolder{
            TextView mTv;
            TextView mTv1;
            Button mBtn;
        }




        ///////////////////////////////////////////////////////


    }







    class LengCai{
        private String name;
        private String price;
        public LengCai(String name,String price){
            this.name=name;
            this.price=price;
        }
        public String getName(){
            return name;
        }
        public String getPrice(){
            return price;
        }
    }


    @BindView(R.id.lv)
    ListView lv;



    private LengCaiAdapter lvAdapter;
    private List<LengCai> lengcaiList=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);//将自定义布局fragment_one加载进来,第二个参数是容器
        ButterKnife.bind(this, view);
        initData();
        return view;
    }


    private void initData() {
        for (int i = 0; i < 20; i++) {
            LengCai LC1=new LengCai("凉拌萝卜丝",    "¥8");
            lengcaiList.add(LC1);
            LengCai LC2=new LengCai("果仁菠菜墩",    "¥7");
            lengcaiList.add(LC2);
            LengCai LC3=new LengCai("糖拌西红柿",    "¥5");
            lengcaiList.add(LC3);
            LengCai LC4=new LengCai("冰糖苦瓜",    "¥5");
            lengcaiList.add(LC4);
            LengCai LC5=new LengCai("香辣卤猪心",    "¥20");
            lengcaiList.add(LC5);
            LengCai LC6=new LengCai("蒜茄子",    "¥5");
            lengcaiList.add(LC6);
        }
        lvAdapter = new LengCaiAdapter(lengcaiList);//第一个参数为上下文，
        // 第二个参数为一个包含TextView，用来填充ListView的每一行的布局资源ID，第三个参数为ListView的内容
        //simple_list_item_1最为简单，只有一个TextView
        lv.setAdapter(lvAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),FoodDetailed.class);
                intent.putExtra("page",i);
                startActivity(intent);
            }
        });
    }
}
