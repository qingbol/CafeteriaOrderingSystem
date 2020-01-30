package es.source.code.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import es.source.code.R;
import es.source.code.activity.FoodDetailed;

/**
 * Created by SamChen on 2017/10/15.
 */

public class ListViewAdapter extends SimpleAdapter {
    Context context;

    public ListViewAdapter(Context context, List<? extends Map<String, Object>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        final Button bt_choose = (Button) view.findViewById(R.id.bt_choose);
        bt_choose.setTag(position);
        bt_choose.setText("点菜");
        bt_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                             //"点菜"按钮事件响应
                if (bt_choose.getText().toString() == "点菜") {
                    Toast.makeText(context, "点菜成功", Toast.LENGTH_SHORT).show();
                    bt_choose.setText("退点");
                } else {
                    bt_choose.setText("点菜");
                    Toast.makeText(context, "退菜成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "选择了" + getItem(position), Toast.LENGTH_SHORT).show();
                Intent detailintent = new Intent(context, FoodDetailed.class);
                context.startActivity(detailintent);
            }
        });
        return view;
    }
}
