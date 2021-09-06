package com.example.xiaoyuanapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.entity.DemandEntity;

import java.util.List;

public class DemandAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private List<DemandEntity> datas;

    public DemandAdapter(Context context, List<DemandEntity> datas) {
        this.mContext = context;
        this.datas = datas;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_demand_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //数据绑定
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolder vh = (ViewHolder) holder;
        DemandEntity demandEntity = datas.get(position);
        vh.tvname.setText(demandEntity.getName());
        vh.tvdescription.setText(demandEntity.getDescription());
        vh.tvprice.setText(demandEntity.getPrice());
        vh.tvtime.setText(demandEntity.getTime());
//        vh.tvpic.setText(demandEntity.getPic());

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

        private TextView tvname;
        private TextView tvdescription;
        private TextView tvprice;
        private TextView tvtime;
        private TextView tvpic;

        public ViewHolder(@NonNull View view) {
            super(view);
            tvname = view.findViewById(R.id.name_commodity);
            tvdescription = view.findViewById(R.id.description_commodity);
            tvprice = view.findViewById(R.id.price_commodity);
            tvtime = view.findViewById(R.id.releaseTime_commodity);
//            tvpic = view.findViewById(R.id.img_commodity);
        }
    }

}
