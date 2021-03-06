package com.androidbigguy.easyandroiddemo.main;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.androidbigguy.easyandroid.banner.Banner;
import com.androidbigguy.easyandroid.banner.Transformer;
import com.androidbigguy.easyandroid.banner.listener.OnBannerListener;
import com.androidbigguy.easyandroid.baseadapter.BaseViewHolder;
import com.androidbigguy.easyandroid.logger.Logger;
import com.androidbigguy.easyandroid.refreshlayout.layout.EasyRefreshLayout;
import com.androidbigguy.easyandroid.utils.ActivityUtil;
import com.androidbigguy.easyandroid.utils.GlideUtils;
import com.androidbigguy.easyandroid.utils.ToastUtil;
import com.androidbigguy.easyandroid.view.MarqueeView;
import com.androidbigguy.easyandroiddemo.R;
import com.androidbigguy.easyandroiddemo.banner.BannarActivity;
import com.androidbigguy.easyandroiddemo.banner.GlideImageLoader;
import com.androidbigguy.easyandroiddemo.resfresh.ResFreshActivity;
import com.androidbigguy.easyandroiddemo.vlayout.BaseDelegateAdapter;
import com.androidbigguy.easyandroiddemo.vlayout.Common;
import com.androidbigguy.easyandroiddemo.vlayout.IconEntity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.alibaba.android.vlayout.layout.FixLayoutHelper.TOP_LEFT;


/**

 .---.          .-----------
 /  /   \  __  /    ------
 / /  //  \(  )/    -----
 //////    ' \/ `   ---
 //// /     :    : ---
 // /        `    '--
 //           //..\\
 =============UU====UU================
 '//||\\`
 ''``
 *  -----------------------------------------------
 * | 作  者：| AndroidBigGuy（QQ295803379）
 *  -----------------------------------------------
 * | 时  间：| 2018/5/14
 *  -----------------------------------------------
 * | 包  名：| com.ls.kylibary.main
 *  -----------------------------------------------
 * | 类  名：| ShopFragment
 *  -----------------------------------------------
 * | 简  述: | 商城首页，实现复杂布局
 *  -----------------------------------------------
 */


public class ShopFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private View view;
    private VirtualLayoutManager layoutManager;
    private EasyRefreshLayout refreshLayout;
    private DelegateAdapter delegateAdapter;
    private List<DelegateAdapter.Adapter> adapters;
    public  List<String> images=new ArrayList<>();
    private List<IconEntity> iconlist=new ArrayList<>();
    private List<IconEntity> dplist=new ArrayList<>();
    private List<IconEntity> sdplist=new ArrayList<>();
    private List<IconEntity> newlist=new ArrayList<>();
    private List<IconEntity> otnlist=new ArrayList<>();
    private List<String> info2 = new ArrayList<>();
    BaseDelegateAdapter stragshopadapter;
    BaseDelegateAdapter adapter_footer;
    private boolean hasmore=true;
    private Handler handler=new Handler();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=View.inflate(getActivity(),R.layout.fragment_shop, null);
        initView();
        initData();
        return view;
    }
    private void initView() {
        mRecyclerView =view.findViewById(R.id.shop_rvlist);
        refreshLayout=view.findViewById(R.id.shop_refreshLayout);
//        refreshLayout.autoRefresh();
//        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                if(sdplist.size()<24){
//                    for (int i=0;i<dplist.size();i++){
//                        sdplist.add(dplist.get(i));
//                    }
//                     stragshopadapter.notifyDataSetChanged();
//                    refreshLayout.finishLoadMore();
//                }else {
//                    refreshLayout.finishLoadMore();
//                    refreshLayout.finishLoadMoreWithNoMoreData();
//                    refreshLayout.setNoMoreData(true);
//                }
//            }
//        });

//        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            }
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if(hasmore) {
//                    VirtualLayoutManager lm = (VirtualLayoutManager)recyclerView.getLayoutManager();
//                    int last=0, total=0;
//                    last = lm.findLastVisibleItemPosition();
//                    total = recyclerView.getAdapter().getItemCount();
//                    if(last > 0 && last >= total  -1) {
//                        hasmore=false;
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(sdplist.size()<24){
//                                    for (int i=0;i<5;i++){
//                                        sdplist.add(newlist.get(i));
//                                    }
//                                    hasmore = true;
//                                    stragshopadapter.notifyDataSetChanged();
//                                }else {
//                                    hasmore = false;
//                                    adapter_footer.notifyDataSetChanged();
//                                }
//
//                            }
//                        },1000);
//
//                    }
//                }
//            }
//        };
//        mRecyclerView .addOnScrollListener(onScrollListener);
    }
    private void initData() {
        String[] urls = getResources().getStringArray(R.array.url4);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        gedp();
        getotn();
        geticon();
        getnew();

//初始化VirtualLayoutManager对象，与RecycleView绑定
        layoutManager = new VirtualLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
//设置每个layouthelper回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）.
        RecyclerView.RecycledViewPool recycledViewPool =new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(0,1);
        recycledViewPool.setMaxRecycledViews(1,1);
        recycledViewPool.setMaxRecycledViews(2,5);
        recycledViewPool.setMaxRecycledViews(3,8);
        recycledViewPool.setMaxRecycledViews(4,2);
        recycledViewPool.setMaxRecycledViews(5,4);
        recycledViewPool.setMaxRecycledViews(6,1);
        recycledViewPool.setMaxRecycledViews(7,8);
        recycledViewPool.setMaxRecycledViews(8,1);
        recycledViewPool.setMaxRecycledViews(9,6);
        recycledViewPool.setMaxRecycledViews(10,8);

        mRecyclerView.setRecycledViewPool(recycledViewPool);
//设置RecyclerView分割线,Item之间的间隔


//        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(10, 10, 10, 10);
//            }
//        });

        delegateAdapter = new DelegateAdapter(layoutManager, true);

        mRecyclerView.setAdapter(delegateAdapter);
        adapters = new LinkedList<>();


//        添加顶部LinearLayoutHelper（解决FixLayoutHelper放在顶部不能下拉的问题）
        LinearLayoutHelper lineadapter=new LinearLayoutHelper(1);
        lineadapter.setMargin(0,0,0,0);
        BaseDelegateAdapter linebannerAdapter =new BaseDelegateAdapter(getActivity(),lineadapter,R.layout.top_item,1, MainActivity.ViewType.BANNER);
        adapters.add(linebannerAdapter);


//        添加顶部FixLayoutHelper固定布局
        FixLayoutHelper fixLayoutHelper =new FixLayoutHelper(0,0);
        fixLayoutHelper.setAlignType(TOP_LEFT);
        BaseDelegateAdapter serchfixLayoutHelper =new BaseDelegateAdapter(getActivity(),fixLayoutHelper, R.layout.item_serch,iconlist.size(), MainActivity.ViewType.MENU){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                LinearLayout iv_ic=holder.getView(R.id.iv_serch);
                iv_ic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(getActivity(),"跳入搜索界面");
                    }
                });
            }
        };
        adapters.add(serchfixLayoutHelper);
//      添加bannar布局----------------------------------------
        LinearLayoutHelper bannerlineadapter=new LinearLayoutHelper(1);
        bannerlineadapter.setMargin(0,0,0,0);
        BaseDelegateAdapter bannerAdapter =new BaseDelegateAdapter(getActivity(),bannerlineadapter,R.layout.bannar_item,1, MainActivity.ViewType.BANNER){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                Banner tv_name= holder.getView(R.id.biv);
                tv_name.setImages(images)
                        .setImageLoader(new GlideImageLoader())//加载图片
                        .setIndicatorGravity(5)//设置指示器位置
                        .setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {
                                ToastUtil.show(getActivity(),"您点击了第"+position+"图片");
                            }
                        })
                        .setBannerAnimation(Transformer.ZoomOut)
                        .start();
            }
        };
        adapters.add(bannerAdapter);
        //添加gride菜单布局---------------------------------------------------

        //构造中传入相应的列的数量
        GridLayoutHelper gridHelper = new GridLayoutHelper(4);
        gridHelper.setMarginTop(20);
//        gridHelper.setWeights(new float[]{20.0f,20.0f,20.0f,20.0f,20.0f});
        //设置垂直方向条目的间隔
        gridHelper.setVGap(30);
        //设置水平方向条目的间隔
        gridHelper.setHGap(15);

//        gridHelper.setMarginLeft(30);
//        gridHelper.setMarginRight(30);
        gridHelper.setMarginBottom(20);
        gridHelper.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));
        //自动填充满布局，在设置完权重，若没有占满，自动填充满布局
        gridHelper.setAutoExpand(true);
        BaseDelegateAdapter MenuAdapter=new BaseDelegateAdapter(getActivity(),gridHelper, R.layout.grid_item,iconlist.size(), MainActivity.ViewType.MENU){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                TextView tv_title=holder.getView(R.id.tv_title);
                LinearLayout gd_itme=holder.getView(R.id.gd_itme);
                ImageView iv_ic=holder.getView(R.id.iv_ic);
                tv_title.setText(iconlist.get(position).getTitle());
                GlideUtils.loadImage(getActivity(),iconlist.get(position).getImageUrl(),iv_ic);
                gd_itme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                ActivityUtil.startAty(getActivity(), FilepickerActivity.class);
                                break;
                            case 1:
                                ActivityUtil.startWithdata(getActivity(),"type","utils",BannarActivity.class);
                                break;
                            case 2:
                                ActivityUtil.startWithdata(getActivity(),"type","custview",BannarActivity.class);
                                break;
                            case 3:
                                ActivityUtil.startAty(getActivity(), BannarActivity.class);
                                break;
                            case 4:
                                ActivityUtil.startAty(getActivity(), BannarActivity.class);
                                break;
                            case 5:
                                ActivityUtil.startAty(getActivity(), ResFreshActivity.class);
                                break;
                            case 6:
                                ActivityUtil.startWithdata(getActivity(),"type","custview",BannarActivity.class);
                                break;
                            case 7:
                                ToastUtil.show(getActivity(),"敬请期待");
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        adapters.add(MenuAdapter);


        ////添加头条格兰布局------------------------------------------------

        info2.add("最大网红孵化基地落户武汉");
        info2.add("第一夫人剽窃风波");
        LinearLayoutHelper linearHelpers = new LinearLayoutHelper(1);
        linearHelpers.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));

        BaseDelegateAdapter marqueeadapter=new BaseDelegateAdapter(getActivity(),linearHelpers,R.layout.vlayout_news,1, MainActivity.ViewType.TOUTIAO){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                MarqueeView marqueeView=holder.getView(R.id.marqueeView1);
                // 在代码里设置自己的动画
                marqueeView.startWithList(info2, R.anim.anim_bottom_in, R.anim.anim_top_out);
                marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        ToastUtil.show(getActivity(),textView.getText().toString());
                    }
                });
            }
        };

        adapters.add(marqueeadapter);

        ////添加一拖N布局------------------------------------------------

        OnePlusNLayoutHelper onePlusNLayoutHelperEx = new OnePlusNLayoutHelper(4);
        onePlusNLayoutHelperEx.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));
        onePlusNLayoutHelperEx.setMargin(10,10,10,10);
//        onePlusNLayoutHelperEx.setColWeights(new float[]{20.0f,20.0f,20.0f,20.0f,20.0f});
//        onePlusNLayoutHelperEx.setRowWeight(10);
        BaseDelegateAdapter onetonadpter=new BaseDelegateAdapter(getActivity(),onePlusNLayoutHelperEx,R.layout.on_item,4, MainActivity.ViewType.ONETON){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                FrameLayout ont_itme =holder.getView(R.id.ont_itme);
                ImageView iv_otn =holder.getView(R.id.iv_otn);
                GlideUtils.loadImage(getActivity(),otnlist.get(position).getImageUrl(),iv_otn);
//                Glide.with(getActivity()).load(otnlist.get(position).getImageUrl()).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_otn);

                ont_itme.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                ToastUtil.show(getActivity(),"进入该商家");
                                break;
                            case 1:
                                ToastUtil.show(getActivity(),"进入该商家");
                                break;
                            case 2:
                                ToastUtil.show(getActivity(),"进入该商家");
                                break;
                            case 3:
                                ToastUtil.show(getActivity(),"进入该商家");
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        adapters.add(onetonadpter);


        //添加店铺推荐格兰布局------------------------------------------------------

        LinearLayoutHelper dplinearHelpers = new LinearLayoutHelper(1);
        dplinearHelpers.setMargin(0,20,0,0);
        dplinearHelpers.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));
        BaseDelegateAdapter shoptitle =new BaseDelegateAdapter(getActivity(),dplinearHelpers,R.layout.like_item,1, MainActivity.ViewType.SHOPTITLE){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.tv_more).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(getActivity(),"跳入全部商家界面");
                    }
                });
            }
        };

        adapters.add(shoptitle);

        //添加推荐店铺gride布局--------------------------------------------------------------------
        GridLayoutHelper dpgridHelper = new GridLayoutHelper(2);
        dpgridHelper.setWeights(new float[]{50.0f,50.0f});
        //设置垂直方向条目的间隔
        dpgridHelper.setVGap(30);
        //设置水平方向条目的间隔
        dpgridHelper.setHGap(30);
        dpgridHelper.setMarginLeft(10);
        dpgridHelper.setMarginRight(10);
//        dpgridHelper.setMarginBottom(20);
        dpgridHelper.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));
        //自动填充满布局，在设置完权重，若没有占满，自动填充满布局
        dpgridHelper.setAutoExpand(true);
        BaseDelegateAdapter shopadapter =new BaseDelegateAdapter(getActivity(),dpgridHelper,R.layout.dp_item,dplist.size(), MainActivity.ViewType.SHOP){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);

                TextView tv_dpdes=holder.getView(R.id.tv_dpdes);
                TextView tv_dpname=holder.getView(R.id.tv_dpname);
                ImageView iv_dp=holder.getView(R.id.in_dp);
                CardView ll_dp=holder.getView(R.id.ll_dp);
                tv_dpdes.setText(dplist.get(position).getTitle());
                tv_dpname.setText(dplist.get(position).getBiaoti());
                GlideUtils.loadImage(getActivity(),dplist.get(position).getImageUrl(),iv_dp);
//                Glide.with(getActivity()).load(dplist.get(position).getImageUrl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_dp);
                ll_dp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 1:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 2:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 3:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 4:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 5:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 6:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 7:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        adapters.add(shopadapter);


        //添加热点新闻格兰布局----------------------------------------------
        LinearLayoutHelper newlinearHelpers = new LinearLayoutHelper(1);
        newlinearHelpers.setDividerHeight(10);
//        newlinearHelpers.setMarginLeft(30);
//        newlinearHelpers.setMarginRight(30);
        newlinearHelpers.setMarginTop(30);
        newlinearHelpers.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));
        BaseDelegateAdapter newtitle =new BaseDelegateAdapter(getActivity(),newlinearHelpers,R.layout.gnew_item,1, MainActivity.ViewType.NEWTITLE){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.tv_newmore).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(getActivity(),"跳入全部新闻界面");
                    }
                });
            }
        };
        adapters.add(newtitle);
        ////添加新闻Linear布局----------------------------------------------

        LinearLayoutHelper newlinearHelper = new LinearLayoutHelper(1);
//        newlinearHelper.setMarginRight(30);
//        newlinearHelper.setMarginLeft(30);
//        newlinearHelper.setMarginTop(15);
        newlinearHelper.setDividerHeight(20);
        newlinearHelper.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));

        BaseDelegateAdapter danewpter =new BaseDelegateAdapter(getActivity(),newlinearHelper,R.layout.new_item,newlist.size(), MainActivity.ViewType.NEW){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);

                TextView tv_newc=holder.getView(R.id.tv_newc);
                TextView tv_newt=holder.getView(R.id.tv_newt);
                ImageView iv_new=holder.getView(R.id.iv_new);
                CardView ll_new=holder.getView(R.id.ll_new);
                tv_newt.setText(newlist.get(position).getTitle());
                tv_newc.setText(newlist.get(position).getContent());
                GlideUtils.loadImage(getActivity(),newlist.get(position).getImageUrl(),iv_new);
//                Glide.with(getActivity()).load(newlist.get(position).getImageUrl()).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_new);

                ll_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            case 1:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            case 2:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            case 3:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            case 4:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            case 5:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            case 6:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            case 7:
                                ToastUtil.show(getActivity(),"进入第"+position+"条新闻");
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        adapters.add(danewpter);

        //添加店铺猜你喜欢格兰布局----------------------------------------------
        LinearLayoutHelper newxlinearHelpers = new LinearLayoutHelper(1);
        newxlinearHelpers.setMarginTop(30);
//        newlinearHelpers.setMarginRight(30);
        newxlinearHelpers.setBgColor(ContextCompat.getColor(getActivity(),R.color.white));
        BaseDelegateAdapter caititle =new BaseDelegateAdapter(getActivity(),newxlinearHelpers,R.layout.gnlike_item,1, MainActivity.ViewType.NEWTITLE){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                holder.getView(R.id.tv_cnewmore).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show(getActivity(),"跳入全部商家界面");
                    }
                });
            }
        };
        adapters.add(caititle);
        /**
         设置瀑布流布局////添加瀑布流布局--------------------------------------------------------
         */

        StaggeredGridLayoutHelper staggeredGridLayoutHelper = new StaggeredGridLayoutHelper();
        // 创建对象

        // 公有属性
        staggeredGridLayoutHelper.setItemCount(20);// 设置布局里Item个数
//        staggeredGridLayoutHelper.setPadding(10, 10, 10, 10);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        staggeredGridLayoutHelper.setMargin(20, 0, 20, 0);// 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离
        staggeredGridLayoutHelper.setBgColor(Color.WHITE);// 设置背景颜色
        staggeredGridLayoutHelper.setAspectRatio(3);// 设置设置布局内每行布局的宽与高的比

        // 特有属性
        staggeredGridLayoutHelper.setLane(2);// 设置控制瀑布流每行的Item数
        staggeredGridLayoutHelper.setHGap(20);// 设置子元素之间的水平间距
        staggeredGridLayoutHelper.setVGap(20);// 设置子元素之间的垂直间距
        stragshopadapter =new BaseDelegateAdapter(getActivity(),staggeredGridLayoutHelper,R.layout.dp_item,sdplist.size(), MainActivity.ViewType.STRAGLE){
            @Override
            public void onBindViewHolder(BaseViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                if (position % 2 == 0) {
                    layoutParams.mAspectRatio = 1.0f;
//                    layoutParams.height = 320 + position % 7 * 20;
                } else {

                    layoutParams.height = 340 + position % 7 * 20;
                }
                holder.itemView.setLayoutParams(layoutParams);
                TextView tv_dpname=holder.getView(R.id.tv_dpname);
                TextView tv_dpdes=holder.getView(R.id.tv_dpdes);
                ImageView iv_dp=holder.getView(R.id.in_dp);
                CardView ll_dp=holder.getView(R.id.ll_dp);
                tv_dpdes.setText(sdplist.get(position).getTitle());
                tv_dpname.setText(sdplist.get(position).getBiaoti());
                GlideUtils.loadImage(getActivity(),sdplist.get(position).getImageUrl(),iv_dp);
//                Glide.with(getActivity()).load(sdplist.get(position).getImageUrl()).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(iv_dp);
                ll_dp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (position) {
                            case 0:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 1:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 2:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 3:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 4:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 5:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 6:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            case 7:
                                ToastUtil.show(getActivity(),"进入第"+position+"家商铺");
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        adapters.add(stragshopadapter);


//        SingleLayoutHelper layoutHelper = new SingleLayoutHelper();
//        adapter_footer =new BaseDelegateAdapter(getActivity(),layoutHelper,R.layout_demo.item_footer,1, MainActivity.ViewType.NEWTITLE){
//            @Override
//            public void onBindViewHolder(BaseViewHolder holder, int position) {
//                super.onBindViewHolder(holder, position);
//                TextView textView= holder.itemView.findViewById(R.id.textview);
//                ProgressBar progressBar=holder.itemView.findViewById(R.id.progressbar);
//                if(hasmore){
//                    textView.setText("加载中...");
//                    progressBar.setVisibility(View.VISIBLE);
//                }else{
//                    textView.setText("-----我是有底线的-------");
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        };
//
//        adapters.add(adapter_footer);
//

        //设置适配器
        delegateAdapter.setAdapters(adapters);
    }
    private void getotn() {
        StringBuilder otnstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("otn.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                otnstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = otnstringBuilder.toString();
        Logger.json(result);
        Gson gson = new Gson();
        Common common = gson.fromJson(result, Common.class);
        otnlist=common.getOtn();
        Logger.i(otnlist.toString());
    }
    private void getnew() {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("news.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result =  newstringBuilder .toString();
        Logger.json(result);
        Gson gson = new Gson();
        Common common = gson.fromJson(result, Common.class);
        newlist=common.getNews();
    }

    private void gedp() {
        StringBuilder dpstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("shop.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                dpstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result =  dpstringBuilder .toString();
        Logger.json(result);
        Gson gson = new Gson();
        Common common = gson.fromJson(result, Common.class);
        dplist=common.getShop();
        sdplist=common.getShop();
    }
    private void geticon() {

        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("icon.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                stringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result =  stringBuilder .toString();
        Logger.json(result);
        Gson gson = new Gson();
        Common common = gson.fromJson(result, Common.class);
        iconlist=common.getIcom();
    }
}
