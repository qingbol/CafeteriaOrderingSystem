package es.source.code.Thread;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by SamChen on 2017/10/23.
 */

public class PayAsyncTask extends AsyncTask<Object, Integer, String> {
    ProgressBar progressBar;
    TextView textView;
    Button button;
    SharedPreferences sp_paybill;
    SharedPreferences.Editor paybill_editor;
    Context context;

    public PayAsyncTask(Context context, ProgressBar progressBar, TextView textView, Button button, SharedPreferences sp_paybill, SharedPreferences.Editor paybill_editor) {
        this.context = context;
        this.progressBar = progressBar;
        this.textView = textView;
        this.button = button;
        this.sp_paybill = sp_paybill;
        this.paybill_editor = paybill_editor;
    }

    public PayAsyncTask(ProgressBar progressBar, TextView textView) {
        this.progressBar = progressBar;
        this.textView = textView;

    }

    //onPreExecute方法用于在后台任务前的一些UI操作
    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        textView.setText("正在付款......");
        super.onPreExecute();
    }

    //doInBackground方法内部执行后台任务，不能在此方法内修改UI
    @Override
    protected String doInBackground(Object[] params) {
        //模拟结账
        paybill_editor = sp_paybill.edit();
        paybill_editor.putString("菜品总数", (String) params[0]);
        paybill_editor.putString("订单总价", (String) params[1]);
        paybill_editor.commit();

        for (double i = 0; i < 100; i += (100 / 6)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 96) {
                publishProgress(100);
            } else {
                publishProgress((int) i);
            }
        }
        String result = "付款成功";

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    //onProgressUpdate方法用于进度信息更新
    @Override
    protected void onProgressUpdate(Integer[] values) {
        progressBar.setProgress(values[0]);
        textView.setText("付款中......" + values[0] + "%");
        if (values[0] == 100) {
            textView.setText("100%......支付成功");
        }
        super.onProgressUpdate(values);
    }

    //onPostExecute方法用于在后台任务执行完后更新UI，显示结果
    @Override
    protected void onPostExecute(String o) {
        //textView.setText(o);
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        button.setEnabled(false);
        Toast.makeText(context, "本次付款金额：" + sp_paybill.getString("订单总价", null) + "元,增加积分：" + sp_paybill.getString("订单总价", null), Toast.LENGTH_SHORT).show();
        super.onPostExecute(o);
    }
}
