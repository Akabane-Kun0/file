package com.example.xiaoyuanapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.activity.ContactActivity;

import java.util.List;
import java.util.Map;

public class ContactAdapter extends SimpleAdapter {

    Context context;

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public ContactAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View v = super.getView(position, convertView, parent);

        TextView phone = v.findViewById(R.id.tv_phone);
        ImageButton btn= v.findViewById(R.id.ib_sms);
//        final int p = position;
//        Log.d("Position", Integer.toString(position));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                Toast.makeText(context, "删除"+Integer.toString(p),2000).show();
//                Toast.makeText(context, "12333333", Toast.LENGTH_SHORT).show();

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(Uri.parse("smsto:" + phone.getText().toString()));
//                sendIntent.putExtra("sms_body", "123");
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(sendIntent);
            }
        });
        return v;
    }

}
