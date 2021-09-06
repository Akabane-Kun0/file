package com.example.xiaoyuanapp.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.adapter.DemandAdapter;
import com.example.xiaoyuanapp.entity.DemandEntity;
import com.example.xiaoyuanapp.entity.Shop;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static android.content.Context.MODE_PRIVATE;
import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DemandFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DemandFragment extends Fragment {

    private String title;
    private List<DemandEntity> datas = new ArrayList<>();


    public DemandFragment() {
        // Required empty public constructor
    }

    public static DemandFragment newInstance(String title) {
        DemandFragment fragment = new DemandFragment();

        fragment.title = title;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_demand, container, false);

        //获取fragment的页面，设置主体布局管理器。
        RecyclerView recyclerView = v.findViewById(R.id.recyclerView_demand);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //获取Shared Preferencesd对象
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("XiaoYuan", MODE_PRIVATE);
        //获取内容
        String sp_ds = sharedPreferences.getString("demand_search", null);


        //查询是否有条件
        if(sp_ds==null || sp_ds.length()==0) {

            //数据库查询，无条件，遍历查询
            BmobQuery<Shop> query = new BmobQuery<Shop>();
            //查询条件：数据库字段demand_type==title
            query.addWhereEqualTo("demand_type", title);
//            //排序升序
//            query.order("demand_time");
//            //设置查询条数
//            query.setLimit(50);

            //执行查询方法
            query.findObjects(new FindListener<Shop>() {
                @Override
                public void done(List<Shop> object, BmobException e) {
                    if(e==null) {
                        //再次查询
                        if(!datas.isEmpty()) {
                            datas.clear();
                        }
                        //该类别项无内容
                        if(object.isEmpty()) {
                            DemandEntity de = new DemandEntity();
                            de.setName("暂无");
                            de.setDescription("暂无");
                            de.setPrice("0元");
                            de.setTime("暂无");
                            datas.add(de);
                        }
                        else {
                            for(Shop shop : object) {
                                //单项数据捆绑
                                DemandEntity de = new DemandEntity();
                                de.setName(shop.getDemand_name());
                                de.setDescription(shop.getDemand_description());
                                de.setPrice(shop.getDemand_price());
                                de.setTime(shop.getDemand_time());
//                                //设置图片
//                                de.setPic(shop.getDemand_pic());
                                datas.add(de);
                            }
                        }

                        //单个fragment数据与页面封装
                        DemandAdapter demandAdapter = new DemandAdapter(getActivity(), datas);
                        recyclerView.setAdapter(demandAdapter);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return v;

        }
        else {
            //检索查询，条件sp_ds
            //条件1
            BmobQuery<Shop> eq1 = new BmobQuery<Shop>();
            eq1.addWhereEqualTo("demand_name", sp_ds);
            //条件2
            BmobQuery<Shop> eq2 = new BmobQuery<Shop>();
            eq2.addWhereEqualTo("demand_type", title);
            //封装
            List<BmobQuery<Shop>> andQuerys = new ArrayList<BmobQuery<Shop>>();
            andQuerys.add(eq1);
            andQuerys.add(eq2);
            //查询
            BmobQuery<Shop> query = new BmobQuery<Shop>();
            query.and(andQuerys);

            //执行查询方法
            query.findObjects(new FindListener<Shop>() {

                @Override
                public void done(List<Shop> object, BmobException e) {
                    if(e==null) {
                        //再次查询
                        if(!datas.isEmpty()) {
                            datas.clear();
                        }
                        //该类别项无内容
                        if(object.isEmpty()) {
                            DemandEntity de = new DemandEntity();
                            de.setName("暂无");
                            de.setDescription("暂无");
                            de.setPrice("0元");
                            de.setTime("暂无");
                            datas.add(de);
                        }
                        else {
                            for(Shop shop : object) {
                                //单项数据捆绑
                                DemandEntity de = new DemandEntity();
                                de.setName(shop.getDemand_name());
                                de.setDescription(shop.getDemand_description());
                                de.setPrice(shop.getDemand_price());
                                de.setTime(shop.getDemand_time());
//                                //设置图片
//                                de.setPic(shop.getDemand_pic());
                                datas.add(de);
                            }
                        }

                        //单个fragment数据与页面封装
                        DemandAdapter demandAdapter = new DemandAdapter(getActivity(), datas);
                        recyclerView.setAdapter(demandAdapter);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            return v;

        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}