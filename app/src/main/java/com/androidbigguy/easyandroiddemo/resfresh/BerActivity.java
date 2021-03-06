package com.androidbigguy.easyandroiddemo.resfresh;

import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.androidbigguy.easyandroiddemo.BaseActivity;
import com.androidbigguy.easyandroiddemo.R;
import com.androidbigguy.easyandroid.refreshlayout.layout.EasyRefreshLayout;
import com.androidbigguy.easyandroid.refreshlayout.layout.api.RefreshLayout;
import com.androidbigguy.easyandroid.refreshlayout.layout.header.BezierRadarHeader;
import com.androidbigguy.easyandroid.refreshlayout.layout.listener.OnLoadMoreListener;
import com.androidbigguy.easyandroid.refreshlayout.layout.listener.OnRefreshListener;
import com.androidbigguy.easyandroid.utils.ActivityUtil;
import com.androidbigguy.easyandroid.utils.ToastUtil;

import java.util.ArrayList;

public class BerActivity extends BaseActivity {

    ListView lv;
    TextView tilte;
    ArrayAdapter<String> adapter;
    private EasyRefreshLayout refreshLayout;
    ArrayList data = new ArrayList();
    Toolbar toolbar;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_ber);
    }

    @Override
    protected void initView() {
        lv = findViewById(R.id.blv);
        toolbar = findViewById(R.id.toolbara);
        tilte = findViewById(R.id.title);
        tilte.setText("贝塞尔曲线模式");
        refreshLayout = findViewById(R.id.rfyb);
        refreshLayout.setRefreshHeader( new BezierRadarHeader(getApplicationContext()));
        refreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.startAty(getApplicationContext(), ResFreshActivity.class);
                finish();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtil.show(getApplicationContext(), "你点击了第" + position + "条数据");
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (data.size() < 20) {
                    adddata();
                    adapter.notifyDataSetChanged();
                    refreshLayout.finishLoadMore();
                } else {
                    refreshLayout.finishLoadMore();
                    refreshLayout.setNoMoreData(true);
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setNoMoreData(false);
                intdata();
                adapter.notifyDataSetChanged();
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected void initData() {
        intdata();
        adapter = new ArrayAdapter<String>(this, R.layout.item, data);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }


    private  void adddata() {
        for (int j=0;j<5;j++){
            data.add("新增第"+j+"条数据");

        }
    }
    private  void intdata() {
        data.clear();
        for (int i=0;i<10;i++){
            data.add("第"+i+"条数据");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtil.startAty(this,ResFreshActivity.class);
        finish();
    }
}
