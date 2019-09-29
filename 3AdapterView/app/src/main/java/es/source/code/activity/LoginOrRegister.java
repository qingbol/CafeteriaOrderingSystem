package es.source.code.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.source.code.model.User;


public class LoginOrRegister extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView myProssBarText;
    User user=new User();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);
        progressBar=(ProgressBar) findViewById(R.id.myProssBarhandle);//进度条
        myProssBarText=(TextView) findViewById(R.id.myProssBarhandleText);//进度条旁边的数字

        Button myProssBarButton = (Button) findViewById(R.id.button5);//登录按钮
        myProssBarButton.setOnClickListener(new ButtonListener());
        Button button1=(Button) findViewById(R.id.button6);//返回按钮
        button1.setOnClickListener(new View.OnClickListener(){//按返回按钮时回到MainScreen
            @Override
            public void onClick(View v){//若按下“返回”按钮，向MainScreen传递字符串"Return"
                Intent intent2=new Intent(LoginOrRegister.this,MainScreen.class);
                intent2.putExtra("FromLoginOrRegister","Return");
                startActivity(intent2);
            }
        });

        Button button_register=(Button) findViewById(R.id.button7);//注册按钮
        button_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//若按下“注册”按钮，向MainScreen传递字符串"RegisterSuccess"
                Intent intent2=new Intent(LoginOrRegister.this,MainScreen.class);
                EditText myUsername=(EditText) findViewById(R.id.edit_text1);//登录名
                EditText myPassword=(EditText) findViewById(R.id.edit_text2);//登录密码
                Username username=new Username();
                if(!username.checkUsername(myPassword.getText().toString())) {//当登录名和登录密码不符合规则时，显示错误
                    myPassword.setError("输入内容不符合规则");
                }else if(!username.checkUsername(myUsername.getText().toString())){
                    myUsername.setError("输入内容不符合规则");
                }else{
                    User loginUser=new User();//如果正确，则新建 User 对象 loginUser，将用户输入框输入的用
                    //户名和密码值通过 Setter 方法赋值到 loginUser 中,并对其 oldUser 域赋值为 False，
                    loginUser.SetteruserName(myUsername.getText().toString());
                    loginUser.Setterpassword(myPassword.getText().toString());
                    loginUser.SetteroldUser(false);
                    intent2.putExtra("user",loginUser);//向MainScreen传递类loginUser
                    intent2.putExtra("FromLoginOrRegister","RegisterSuccess");
                    startActivity(intent2);
                }
            }
        });

    }
    public class Username{//这是一个验证方法：用正则表达式验证是登录名和登录密码是否符合规则
        public  boolean checkUsername(String username){
            String regex = "(^[a-zA-Z0-9]{1,100})";
            java.util.regex.Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(username);
            return m.matches();
        }
    }


    class ButtonListener implements View.OnClickListener {//使用 ProgressBar 显示登录进度和进度百分比，2 秒钟后进度条消失；
        public void onClick(View v) {
            progressBar.setVisibility(View.VISIBLE);
            myProssBarText.setVisibility(View.VISIBLE);
            progressBar.setMax(1000);
            new Thread() {
                public void run() {
                    for(int i=0;i<=1000;){
                        try {
                            Message msg = new Message();//
                            msg.what = 1;
                            msg.getData().putInt("size", i);
                            handler.sendMessage(msg);//handler在下面有定义
                            sleep(200);
                            i+=100;
                        } catch (Exception e) {
                            handler.obtainMessage(-1).sendToTarget();
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            EditText myUsername=(EditText) findViewById(R.id.edit_text1);//登录名
            EditText myPassword=(EditText) findViewById(R.id.edit_text2);//登录密码
            Username username=new Username();
            if(!username.checkUsername(myPassword.getText().toString())) {//当登录名和登录密码不符合规则时，显示错误
                myPassword.setError("输入内容不符合规则");
            }else if(!username.checkUsername(myUsername.getText().toString())){
                myUsername.setError("输入内容不符合规则");
            }else {//当登录名和登录密码符合规则时，如果记到2秒，则显示登录成功，并转跳到MainScreen，并传递字符串"LoginSuccess"
                switch (msg.what) {
                    case 1:
                        progressBar.setProgress(msg.getData().getInt("size"));
                        float num = (float) progressBar.getProgress() / (float) progressBar.getMax();
                        int result = (int) (num * 100);

                        myProssBarText.setText(result + "%");//显示进度的数字
                        if (progressBar.getProgress() == progressBar.getMax() ) {//如果记到2秒，则显示登录成功
                            Toast.makeText(LoginOrRegister.this, "登陆成功", 1).show();
                            progressBar.setVisibility(View.GONE);
                            myProssBarText.setVisibility(View.GONE);

                            User loginUser=new User();//如果正确，则新建 User 对象 loginUser，将用户输入框输入的用
                                                      //户名和密码值通过 Setter 方法赋值到 loginUser 中,并对其 oldUser 域赋值为 True，
                            loginUser.SetteruserName(myUsername.getText().toString());
                            loginUser.Setterpassword(myPassword.getText().toString());
                            loginUser.SetteroldUser(true);

                            Intent i = new Intent(LoginOrRegister.this, MainScreen.class);//当登录成功时，转跳到MainScreen

                            i.putExtra("user",loginUser);//向MainScreen传递类loginUser

                            i.putExtra("FromLoginOrRegister", "LoginSuccess");
                            startActivity(i);
                        }
                        break;

                    case -1:
                        Toast.makeText(LoginOrRegister.this, "登陆失败", 1).show();
                        break;
                }
            }
        }
    };



}