package es.source.code.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.source.code.EventBus.MessageEvent;
import es.source.code.R;
import es.source.code.Thread.MailSender;
import es.source.code.model.MailInfo;

public class SCOSHelper extends AppCompatActivity {
    //存放图标的数组
    private int[] help_icon = {
            R.mipmap.protocol,
            R.mipmap.aboutsystem,
            R.mipmap.phonecall,
            R.mipmap.sendmsg,
            R.mipmap.email
    };

    //存放标题的数组
    private String[] help_title = {"用户使用协议", "关于系统", "电话人工帮助", "短信帮助", "邮件帮助"};

    private List<Map<String, Object>> help_item_data;
    private SimpleAdapter sim_helpmenu_adapter;
    private GridView gv_helpgrid;

    //邮件相关信息
    private static final String HOST = "smtp.qq.com";
    private static final String PORT = "465";
    private static final String FROM_ADD = "765199602@qq.com";
    private static final String FROM_PSW = "qrxfezcuncwqbdeb";
    //private static final String FROM_PSW = "csy19940311";

    public volatile boolean exit = false;

    /*private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(SCOSHelper.this, "无法验证邮箱服务器，求助邮件发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(SCOSHelper.this, "求助邮件发送成功", Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMessageEvent(MessageEvent messageEvent){
        int messagewhat = messageEvent.getMessagewhat();
        switch (messagewhat){
            case 0:
                Toast.makeText(SCOSHelper.this, "无法验证邮箱服务器，求助邮件发送失败", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(SCOSHelper.this, "求助邮件发送成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoshelper);

        EventBus.getDefault().register(this);          //注册事件

        help_item_data = new ArrayList<>();
        help_item_data = getHelpItemData();

        sim_helpmenu_adapter = new SimpleAdapter(this, help_item_data, R.layout.help_grid_item_layout,
                new String[]{"helpicon", "helptitle"}, new int[]{R.id.iv_helpitemicon, R.id.tv_helpitemtitle});
        gv_helpgrid = (GridView) findViewById(R.id.gv_helpgrid);
        gv_helpgrid.setAdapter(sim_helpmenu_adapter);

        //点击事件监听
        gv_helpgrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        Uri callnumber = Uri.parse("tel:5554");
                        Intent dialintent = new Intent(Intent.ACTION_DIAL, callnumber);
                        startActivity(dialintent);
                        break;
                    case 3:
                        SmsManager smanager = SmsManager.getDefault();
                        PendingIntent pi_sendmsgintent = PendingIntent.getActivity(SCOSHelper.this, 0, new Intent(), 0);
                        smanager.sendTextMessage("5554", null, "test scos helper", pi_sendmsgintent, null);
                        Toast.makeText(SCOSHelper.this, "求助短信发送成功", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        sendMailThread.start();
                        //EventBus.getDefault().post(new MessageEvent());
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);             //取消注册事件
    }

    Thread sendMailThread = new Thread(new Runnable() {
        @Override
        public void run() {
            //Message msg = null;
            int messagewhat = -1;
            while (!exit) {
                MailInfo mailInfo = new MailInfo();
                mailInfo.setMailServerHost(HOST);
                mailInfo.setMailServerPort(PORT);
                mailInfo.setValidate(true);
                mailInfo.setUserName(FROM_ADD);
                mailInfo.setPassword(FROM_PSW);
                mailInfo.setFromAddress(FROM_ADD);
                //mailInfo.setToAddress("waterzhj@ustc.edu.cn");              //老师邮箱
                mailInfo.setToAddress("765199602@qq.com");
                mailInfo.setSubject("救助");
                mailInfo.setContent("这是一分自动发送的测试救助邮件，Intent隐式启动Android系统的邮件app无法自动发送邮件。" +
                        "我认为该功能不用自动发送更好！！！");

                MailSender sms = new MailSender();
                //msg = new Message();
                //sms.sendTextMail(mailInfo);
                if (sms.sendTextMail(mailInfo)) {
                    messagewhat = 1;
                }else {
                    messagewhat = 0;
                }
                exit = true;
            }
            //handler.sendMessage(msg);
            EventBus.getDefault().post(new MessageEvent(messagewhat));
            finish();
        }
    });

    /*@Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onSendMailEvent(MessageEvent messageEvent){
        while (!exit) {
            MailInfo mailInfo = new MailInfo();
            mailInfo.setMailServerHost(HOST);
            mailInfo.setMailServerPort(PORT);
            mailInfo.setValidate(true);
            mailInfo.setUserName(FROM_ADD);
            mailInfo.setPassword(FROM_PSW);
            mailInfo.setFromAddress(FROM_ADD);
            //mailInfo.setToAddress("waterzhj@ustc.edu.cn");              //老师邮箱
            mailInfo.setToAddress("765199602@qq.com");
            mailInfo.setSubject("救助");
            mailInfo.setContent("这是一分自动发送的测试救助邮件，Intent隐式启动Android系统的邮件app无法自动发送邮件。" +
                    "我认为该功能不用自动发送更好！！！");

            MailSender sms = new MailSender();
            //sms.sendTextMail(mailInfo);
            if (sms.sendTextMail(mailInfo)) {
                messageEvent.setMessagewhat(1);
            }else {
                messageEvent.setMessagewhat(0);
            }
            exit = true;
        }
        finish();
    }*/


    public List<Map<String, Object>> getHelpItemData() {
        for (int i = 0; i < help_title.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("helpicon", help_icon[i]);
            map.put("helptitle", help_title[i]);
            help_item_data.add(map);
        }
        return help_item_data;
    }
}
