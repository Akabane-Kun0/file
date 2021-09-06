package com.example.xiaoyuanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.entity.TalkEntity;

import java.util.List;

public class TalkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<TalkEntity> datas;

    public TalkAdapter(Context context, List<TalkEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_talk_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //数据绑定
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder vh = (ViewHolder) holder;
        TalkEntity talkEntity = datas.get(position);
        vh.tvid.setText(talkEntity.getId());
        vh.tvtime.setText(talkEntity.getTime());
        vh.tvtalk.setText(talkEntity.getTalk());

    }

    @Override
    public int getItemCount() {
        if (datas != null && datas.size() > 0) {
            return datas.size();
        }
        else {
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvid;
        private TextView tvtime;
        private TextView tvtalk;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvid = view.findViewById(R.id.uid_name);
            tvtime = view.findViewById(R.id.releaseTime);
            tvtalk = view.findViewById(R.id.text_talk);
        }
    }

}
