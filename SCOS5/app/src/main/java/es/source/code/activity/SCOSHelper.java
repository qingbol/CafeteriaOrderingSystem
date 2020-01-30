package es.source.code.activity;

/**
 * Created by Administrator on 2017/10/21.
 */


import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SCOSHelper extends AppCompatActivity {
    //////////////////////////////////////////////////////////////////发送邮件加的


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////发送邮件加的
    public static final int UPDATE_TEXT = 1;
    private GridView gview;
    SmsManager sManage;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private int[] icon = {R.drawable.img7, R.drawable.img8,// 图片封装为一个数组
            R.drawable.img9, R.drawable.img10, R.drawable.img11};
    private String[] iconName = {"用户使用协议", "关于系统", "电话人工帮助", "短信帮助", "邮件帮助"};//设置图片对应的名字
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////发送邮件加的


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////发送邮件加的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESSSCOS} , -1);
        sManage = SmsManager.getDefault();
        final String number = "5554";
        final String content = "test scos helper";


        gview = (GridView) findViewById(R.id.gview);
        data_list = new ArrayList<Map<String, Object>>();//新建List
        getData();


        //新建适配器
        String[] from = {"image", "text"};//将被添加到Map映射上的key
        int[] to = {R.id.image, R.id.text};//将绑定数据的视图的ID跟from参数对应
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener()//监听每个item的点击事件
        {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position == 0) {//点击用户使用协议

                } else if (position == 2) {////当用户点击“电话人工帮助”时，使用 ImplicitIntent 调用系统 app
                    //实现自动拨号功能，拨打目标号码为“5554”

                    Intent intent1 = new Intent(Intent.ACTION_DIAL);
                    intent1.setData(Uri.parse("tel:5554"));
                    startActivity(intent1);

                } else if (position == 3) {

//当用户点击“短信帮助”时，使用 SmsManager 自动发送短信只目标号码“5554”，内容为“test scos helper”。
// 当短信发送完毕后，使用 Toast提示“求助短信发送成功


                    PendingIntent pi = PendingIntent.getActivity(SCOSHelper.this, 0, new Intent(), 0);
                    sManage.sendTextMessage(number, null, content, pi, null);
                    Toast.makeText(SCOSHelper.this, "求助短信发送成功", Toast.LENGTH_SHORT).show();


                } else if (position == 4) {
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            MailSenderInfo mailInfo = new MailSenderInfo();
                            mailInfo.setMailServerHost("pop.qq.com");
                            mailInfo.setMailServerPort("587");
                            mailInfo.setValidate(true);
                            mailInfo.setUserName("2927551447@qq.com");
                            mailInfo.setPassword("uqmajpnxhrlqddcd");// 您的邮箱密码
                            mailInfo.setFromAddress("2927551447@qq.com");
                            mailInfo.setToAddress("skjxjj1995@163.com");
                            mailInfo.setSubject("Hello");
                            mailInfo.setContent("哈哈哈");
                            // 这个类主要来发送邮件
                            SimpleMailSender sms = new SimpleMailSender();
                            boolean isSuccess = sms.sendTextMail(mailInfo);// 发送文体格式
                            // sms.sendHtmlMail(mailInfo);//发送html格式
                            if (isSuccess) {
                                Log.i("shuxinshuxin", "发送成功");
                            } else {
                                Log.i("shuxinshuxin", "发送失败");
                            }
                        }
                    }).start();
                }
            }
        });
    }

    private Handler handler = new Handler() {////////////////////////////////////处理邮件
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
                    Toast.makeText(SCOSHelper.this, "求助邮件已发送成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public List<Map<String, Object>> getData0() {
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

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }


    /*class MailSender extends Thread {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();

            /*try {
                //创建HtmlEmail类
                HtmlEmail email = new HtmlEmail();
                //email.setSSLOnConnect(false);
                //填写邮件的主机明，我这里使用的是163
                email.setHostName("smtp.qq.com");
                email.setTLS(true);
                email.setSSL(true);
                //设置字符编码格式，防止中文乱码
                email.setCharset("gbk");
                //设置收件人的邮箱
                email.addTo("skjxjj1995@163.com");
                //设置发件人的邮箱
                email.setFrom("2927551447@qq.com");
                //填写发件人的用户名和密码
                email.setAuthentication("2927551447@qq.com", "20135991skj");
                //填写邮件主题
                email.setSubject("您好");
                //填写邮件内容
                email.setMsg("Hello" + "\n" + "Android");
                //发送邮件
                email.send();
                Message message=new Message();
                message.what=UPDATE_TEXT;
                handler.sendMessage(message);
            } catch (EmailException e) {
                // TODO Auto-generated catch block
                Log.i("TAG", "---------------->"+e.getMessage());
            }*/


            // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
            // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com


            // 收件人邮箱（替换为自己知道的有效邮箱）




   /*class MailSender extends Thread {
       @Override
       public void run() {
           // TODO Auto-generated method stub
           super.run();
               MultiMailsender.MultiMailSenderInfo mailInfo = new MultiMailsender.MultiMailSenderInfo();
               mailInfo.setMailServerHost("smtp.qq.com");
               mailInfo.setMailServerPort("25");
               mailInfo.setValidate(true);
               mailInfo.setUserName("2927551447@qq.com");
               mailInfo.setPassword("20135991skj");//您的邮箱密码
               mailInfo.setFromAddress("2927551447@qq.com");
               mailInfo.setToAddress("2837763299@qq.com");
               mailInfo.setSubject("Hello");
               mailInfo.setContent("哈哈哈哈");
               String[] receivers = new String[]{"skjxjj1995@163.com"};
               String[] ccs = receivers;
               mailInfo.setReceivers(receivers);
               mailInfo.setCcs(ccs);
               //这个类主要来发送邮件
               MultiMailsender sms = new MultiMailsender();
               sms.sendTextMail(mailInfo);//发送文体格式
               //MultiMailsender.sendHtmlMail(mailInfo);//发送html格式
               MultiMailsender.sendMailtoMultiCC(mailInfo);//发送抄送
               Message message=new Message();
               message.what=UPDATE_TEXT;
               handler.sendMessage(message);

       }
   }*/




}
























