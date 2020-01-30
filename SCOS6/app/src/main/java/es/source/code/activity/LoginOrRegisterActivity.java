package es.source.code.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.source.code.EventBus.MessageEvent;
import es.source.code.R;
import es.source.code.Utils.LoginUtils;
import es.source.code.model.User;

public class LoginOrRegisterActivity extends AppCompatActivity {

    EditText et_username, et_password;
    ProgressBar pb_loginPB;
    //ProgressDialog proDialog;
    SharedPreferences sp_logindata;    //声明SharePreferences对象
    SharedPreferences.Editor sp_editor;
    String username, password;
    int loginstate;

    /*private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1)
                //buildDialog();
                //proDialog.show();
                pb_loginPB.setVisibility(View.VISIBLE);
            super.handleMessage(msg);
        }
    };*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowProgressBarEvent(MessageEvent messageEvent) {
        if (messageEvent.getMessagewhat() == 1) {
            pb_loginPB.setVisibility(View.VISIBLE);
        }
    }

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        EventBus.getDefault().register(this);      //注册EventBus

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        pb_loginPB = (ProgressBar) findViewById(R.id.pb_loginPB);
        Button bt_login = (Button) findViewById(R.id.bt_login);
        Button bt_back = (Button) findViewById(R.id.bt_back);
        Button bt_register = (Button) findViewById(R.id.bt_register);
        //final ProgressBar pb_loginPB = (ProgressBar) findViewById(R.id.pb_loginPB);

        //pb_loginPB.setVisibility(View.GONE);

        sp_logindata = getSharedPreferences("logindata", MODE_PRIVATE);
        username = sp_logindata.getString("Username", null);
        password = sp_logindata.getString("Password", null);
        loginstate = sp_logindata.getInt("Loginstate", 0);
        if (username == null) {
            bt_login.setVisibility(View.GONE);
        } else {
            bt_register.setVisibility(View.GONE);
            et_username.setText(username);
            et_password.setText(password);
        }

        bt_login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View v) {

                if (isRightInput(et_username.getText().toString()) && isRightInput(et_password.getText().toString())) {
                    //loginThread.start();
                    EventBus.getDefault().post(new MessageEvent(1));
                    final User loginUser = new User();
                    loginUser.setUserName(et_username.getText().toString());
                    loginUser.setPassword(et_password.getText().toString());
                    loginUser.setOldUser(true);

                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            Bundle data = msg.getData();
                            String key = data.getString("result");

                            try {
                                JSONObject json = new JSONObject(key);
                                int resultcode = (int) json.get("RESULTCODE");
                                if (resultcode == 1) {
                                    Intent loginintent = new Intent(LoginOrRegisterActivity.this, MainScreen.class);
                                    //Intent tofoodorederviewintent = new Intent(LoginOrRegisterActivity.this, FoodOrderView.class);
                                    loginintent.putExtra("str", "LoginSuccess");
                                    loginintent.putExtra("user_data", loginUser);
                                    //tofoodorederviewintent.putExtra("user_data",loginUser);
                                    loginintent.setAction("socs.intent.action.SCOSMAIN");
                                    setResult(0, loginintent);
                                    //startActivity(loginintent);
                                    //setResult(1,tofoodorederviewintent);
                                    //finish();
                                    Toast.makeText(LoginOrRegisterActivity.this, "用户名密码正确", Toast.LENGTH_SHORT).show();
                                } else if (resultcode == 0) {
                                    //pb_loginPB.setVisibility(View.GONE);
                                    Toast.makeText(LoginOrRegisterActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            super.handleMessage(msg);
                        }
                    };

                } else if (!isRightInput(et_username.getText().toString())) {
                    et_username.setError("输入内容不符合规则");
                } else if (!isRightInput(et_password.getText().toString())) {
                    et_password.setError("输入内容不符合规则");
                }
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("HandlerLeak")
            @Override
            public void onClick(View v) {
                if (isRightInput(et_username.getText().toString()) && isRightInput(et_password.getText().toString())) {
                    //loginThread.start();
                    EventBus.getDefault().post(new MessageEvent(1));
                    final User loginUser = new User();
                    loginUser.setUserName(et_username.getText().toString());
                    loginUser.setPassword(et_password.getText().toString());
                    loginUser.setOldUser(false);

                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            Bundle data = msg.getData();
                            String key = data.getString("result");

                            try {
                                JSONObject json = new JSONObject(key);
                                int resultcode = (int) json.get("RESULTCODE");
                                if (resultcode == 1) {
                                    Intent registerintent = new Intent(LoginOrRegisterActivity.this, MainScreen.class);
                                    //Intent tofoodorderviewintent = new Intent(LoginOrRegisterActivity.this, FoodOrderView.class);
                                    registerintent.putExtra("str", "RegisterSuccess");
                                    registerintent.putExtra("user_data", loginUser);
                                    //tofoodorderviewintent.putExtra("user_data",loginUser);
                                    registerintent.setAction("socs.intent.action.SCOSMAIN");

                                    setResult(0, registerintent);
                                    //setResult(1,tofoodorderviewintent);
                                    //finish();
                                    Toast.makeText(LoginOrRegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                } else if (resultcode == 0) {
                                    Toast.makeText(LoginOrRegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            super.handleMessage(msg);
                        }
                    };
                } else if (!isRightInput(et_username.getText().toString())) {
                    et_username.setError("输入内容不符合规则");
                } else if (!isRightInput(et_password.getText().toString())) {
                    et_password.setError("输入内容不符合规则");
                }
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sp_logindata.contains("Username")) {
                    saveLogindata(0);
                }
                Intent backintent = new Intent(LoginOrRegisterActivity.this, MainScreen.class);
                backintent.putExtra("str", "Return");
                backintent.setAction("socs.intent.action.SCOSMAIN");
                setResult(0, backintent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);          //取消事件注册
    }

    public void saveLogindata(int loginstate) {
        //将用户名和密码写入SharePreferences
        sp_editor = sp_logindata.edit();
        sp_editor.putString("Username", et_username.getText().toString());
        sp_editor.putString("Password", et_password.getText().toString());
        sp_editor.putInt("Loginstate", loginstate);
        sp_editor.commit();
    }

    public static boolean isRightInput(String iput) {
        if (iput != null && iput.length() >= 0) {
            return iput.matches("^[a-zA-Z0-9]+$");
        } else {
            return false;
        }
    }

    /*private void buildDialog(){
        proDialog = new ProgressDialog(LoginOrRegisterActivity.this);
        proDialog.setTitle("正在登录");
        proDialog.setMessage("请稍等...");
        proDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }*/

    //handler.sendMessageDelayed(handler.obtainMessage(),2000);
    /*Thread loginThread = new Thread(new Runnable() {
        @Override
        public void run() {
            saveLogindata(1);         //向SharedPreferences中写数据
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage(msg);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //proDialog.dismiss();
            //pb_loginPB.setVisibility(View.GONE);
            finish();
        }
    });*/

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void loginEvent(MessageEvent messageEvent) {
        saveLogindata(1);         //向SharedPreferences中写数据
        int messagewhat = messageEvent.getMessagewhat();

        String url = LoginUtils.BASE_URL + "/LoginValidator";
        Map<String, String> params = new HashMap<String, String>();
        String userName = et_username.getText().toString();
        String password = et_password.getText().toString();
        params.put("username", userName);
        params.put("password", password);

        String result = LoginUtils.Connect(url, params);
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString("result", result);
        msg.setData(data);
        handler.sendMessage(msg);

        try {
            JSONObject jsonobj = new JSONObject(result);
            int resultcode = (int) jsonobj.get("RESULTCODE");
            if (resultcode == 1) {
                saveLogindata(1);
            } else if (resultcode == 0) {
                Log.v("RESULTCODE", String.valueOf(resultcode));
                saveLogindata(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //proDialog.dismiss();
        //pb_loginPB.setVisibility(View.GONE);
        finish();
    }
}




