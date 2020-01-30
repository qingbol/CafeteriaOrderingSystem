package es.source.code.activity;

/**
 * Created by Administrator on 2017/10/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
    private LocalBroadcastManager localBroadcastManager;
    /*public String[] LengCaiNameArray=new String[]{"","","","","",""};
    public int[] LengCaiNumberArray=new int[]{0,0,0,0,0,0};*/
    public String[] LengCaiNameArray=new String[6];
    public int[] LengCaiNumberArray=new int[6];

    public List<LengCai> lengcaiList=new ArrayList<>();



    /* public class LengCaiAdapter extends ArrayAdapter<LengCai>{
         private int resourceId;
         public LengCaiAdapter(Context context, int textViewResourceId, List<LengCai> objects){//第一个参数为当前上下文，第二个参数为要加载的布局，第三个为要加载的内容
             super(context,textViewResourceId,objects);
             resourceId=textViewResourceId;
         }
         @Override
         public View getView(int position, View convertView, ViewGroup parent){
             LengCai lengcai=getItem(position);
             View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
             ImageView lengcaiImage=(ImageView)view.findViewById(R.id.lengcai_image);
             TextView lengcaiName=(TextView)view.findViewById(R.id.lengcai_name);
             TextView lengcaiPrice=(TextView)view.findViewById(R.id.lengcai_price);
             Button button=(Button)view.findViewById(R.id.DianCai_Button);
             lengcaiImage.setImageResource(lengcai.getImageId());
             lengcaiName.setText(lengcai.getName());
             lengcaiPrice.setText(lengcai.getPrice());
             return view;
         }
     }*/
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
                viewHolder.lengcai_name = (TextView)view.findViewById(R.id.lengcai_name);
                viewHolder.lengcai_number=(TextView)view.findViewById(R.id.lengcai_number) ;//////////////////////////////菜的数量
                viewHolder.lengcai_price = (TextView)view.findViewById(R.id.lengcai_price);
                viewHolder.DianCai_Button = (Button)view.findViewById(DianCai_Button);
                view.setTag(viewHolder);
            }
            //获取viewHolder实例
            viewHolder = (ViewHolder)view.getTag();
            //设置数据
            viewHolder.lengcai_name.setText(data.get(i).getName());
            viewHolder.lengcai_name.setTag(R.id.tv,i);
            viewHolder.lengcai_name.setOnClickListener(this);
            viewHolder.lengcai_number.setText(data.get(i).getNumber());
            viewHolder.lengcai_number.setTag(R.id.tv2,i);
            viewHolder.lengcai_number.setOnClickListener(this);
            viewHolder.lengcai_price.setText(data.get(i).getPrice());
            viewHolder.lengcai_price.setTag(R.id.tv1,i);
            //设置监听事件
            viewHolder.lengcai_price.setOnClickListener(this);
            //设置数据
            viewHolder.DianCai_Button.setTag(R.id.btn,i);
            viewHolder.DianCai_Button.setText("点菜");
            viewHolder.DianCai_Button.setOnClickListener(this);
            return view;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case DianCai_Button:
                    int b=(int)view.getTag(R.id.btn);
                    Intent intent=new Intent("From FoodView OneFragment to FoodOrderView");
                    intent.putExtra("to_FoodOrderView",b);
                    localBroadcastManager.sendBroadcast(intent);
                    //判断是第几个菜的按钮
                    Button mBtn=(Button)view.findViewById(R.id.DianCai_Button);
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
            TextView lengcai_name;
            TextView lengcai_price;
            TextView lengcai_number;//////////////////////////////菜的数量
            Button DianCai_Button;
        }





        ///////////////////////////////////////////////////////


    }







    public class LengCai{
        private String name;
        private String price;
        private int number;
        public LengCai(String name,String price,int number){
            this.name=name;
            this.price=price;
            this.number=number;
        }
        public String getName(){
            return name;
        }
        public String getPrice(){
            return price;
        }
        public String getNumber(){
            return String.valueOf(number);
        }
    }


    @BindView(R.id.lv)
    ListView lv;



    public LengCaiAdapter lvAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_one, container, false);//将自定义布局fragment_one加载进来,第二个参数是容器
        ButterKnife.bind(this, view);
       /* Bundle bundle =this.getArguments();
        LengCaiNameArray=bundle.getStringArray("toOneFragmentName");
        LengCaiNumberArray=bundle.getIntArray("toOneFragmentNumber");*/
        Intent intent1=getActivity().getIntent();///////////////////////////////////当传入的是1时，根据FoodView传入的数据进行初始化
        int a=intent1.getIntExtra("FoodViewtoOneFragment",0);



        if(a==1){
            Bundle b=intent1.getExtras();
            LengCaiNumberArray=b.getIntArray("Number");
            LengCaiNameArray=b.getStringArray("Name");
        }

            initData();


        localBroadcastManager=LocalBroadcastManager.getInstance(getContext());/////广播加的
        return view;
    }


    public void initData() {
       /* for(int j=0;j<6;j++){
            System.out.println(LengCaiNumberArray[j]);////////////////////////////////////////调试
        }*/
        for (int i = 0; i < 20; i++) {//

            LengCai LC1=new LengCai(LengCaiNameArray[0],    "¥8",LengCaiNumberArray[0]);
            lengcaiList.add(LC1);
            LengCai LC2=new LengCai(LengCaiNameArray[1],    "¥7",LengCaiNumberArray[1]);
            lengcaiList.add(LC2);
            LengCai LC3=new LengCai(LengCaiNameArray[2],    "¥5",LengCaiNumberArray[2]);
            lengcaiList.add(LC3);
            LengCai LC4=new LengCai(LengCaiNameArray[3],    "¥5",LengCaiNumberArray[3]);
            lengcaiList.add(LC4);
            LengCai LC5=new LengCai(LengCaiNameArray[4],    "¥20",LengCaiNumberArray[4]);
            lengcaiList.add(LC5);
            LengCai LC6=new LengCai(LengCaiNameArray[5],    "¥5",LengCaiNumberArray[5]);
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
