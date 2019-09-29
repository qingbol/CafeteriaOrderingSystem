package es.source.code.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

public class SCOSEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.entry);
    }
    float x1=0;
    float x2=0;
    float y1=0;
    float y2=0;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            x1=event.getX();
            y1=event.getY();
        }
        if(event.getAction()==MotionEvent.ACTION_UP) {
            x2 = event.getX();
            y2 = event.getY();
            if (y1 - y2 > 50) {
                Toast.makeText(SCOSEntry.this, "查看选项请向左滑动",Toast.LENGTH_SHORT).show();
            } else if (y2 - y1 > 50) {
                Toast.makeText(SCOSEntry.this, "查看选项请向左滑动",Toast.LENGTH_SHORT).show();
            } else if (x1 - x2 > 50) {
                Intent intent = new Intent("scos.intent.action.SCOSMAIN");
                intent.addCategory("scos.intent.category.SCOSLAUNCHER");
                intent.putExtra("extra_data" ,"FromEntry");
                startActivity(intent);
            } else if (x2 - x1 > 50) {
                Toast.makeText(SCOSEntry.this, "查看选项请向左滑动",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }
}
