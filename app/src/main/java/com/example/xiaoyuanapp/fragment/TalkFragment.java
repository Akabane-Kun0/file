package com.example.xiaoyuanapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaoyuanapp.R;
import com.example.xiaoyuanapp.adapter.TalkAdapter;
import com.example.xiaoyuanapp.entity.Line;
import com.example.xiaoyuanapp.entity.TalkEntity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TalkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TalkFragment extends Fragment {

    //话题类型
    private String title;
    private List<TalkEntity> datas = new ArrayList<>();


    public TalkFragment() {
        // Required empty public constructor
    }

    public static TalkFragment newInstance(String title) {
        TalkFragment fragment = new TalkFragment();
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
        View v = inflater.inflate(R.layout.fragment_talk, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView_talk);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        //数据库查询
        BmobQuery<Line> query = new BmobQuery<Line>();
        //查询条件：数据库字段talk_type==title
        query.addWhereEqualTo("talk_type", title);
        //排序降序
        query.order("-talk_time");
//        //设置查询条数
//        query.setLimit();
        //执行查询方法
        query.findObjects(new FindListener<Line>() {
            @Override
            public void done(List<Line> object, BmobException e) {
                if(e==null) {
                    if(!datas.isEmpty()) {
                        datas.clear();
                    }
                    //该类别项为空
                    if(object.isEmpty()) {
                        TalkEntity te = new TalkEntity();
                        te.setId("暂无");
                        te.setTalk("暂无");
                        datas.add(te);
                    }
                    else {
                        for(Line line : object) {
                            //单项数据捆绑
                            TalkEntity te = new TalkEntity();
                            te.setId(line.getTalk_uid());
                            te.setTime(line.getTalk_time());
                            te.setTalk(line.getTalk_content());
                            datas.add(te);
                        }
                    }
                    TalkAdapter talkAdapter = new TalkAdapter(getActivity(), datas);
                    recyclerView.setAdapter(talkAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "查询失败", Toast.LENGTH_SHORT).show();
                }
            }

        });

        return v;
    }
}