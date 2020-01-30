package es.source.code.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import es.source.code.R;

public class SOCSEntry extends AppCompatActivity {
    float currentX, currentY;
    SharedPreferences sp_entry;
    SharedPreferences.Editor entry_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entry);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float moveDistanceX = event.getX() - currentX;

            if (moveDistanceX < 0) {
                sp_entry = getSharedPreferences("logindata",MODE_PRIVATE);
                entry_editor = sp_entry.edit();
                entry_editor.putInt("Loginstate",0);
                entry_editor.commit();
                Intent intent = new Intent(SOCSEntry.this, MainScreen.class);
                //intent.putExtra("str", "FromEntry");
                //intent.setAction("socs.intent.action.SCOSMAIN");
                startActivity(intent);
            }
        }
        return super.onTouchEvent(event);
    }

}
