package com.example.xiyou.twolearntodo.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xiyou.twolearntodo.R;
import com.example.xiyou.twolearntodo.model.MenuNewsModel;
import com.example.xiyou.twolearntodo.utils.DisplayUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by maple on 2016/11/1.
 */

public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MenuNewsModel> results;
    boolean mIsLoad;
    //type
    public static final int TYPE_SLIDER = 0xff01;
    //图片菜单
    public static final int TYPE_MENU = 0xff02;
    //新闻
    public static final int TYPE_NEWS = 0xff03;
    public static final int TYPE_LOAD_MORE = 0xff04;


    private int width = 120;

    int mColums;
    private View loadView;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH时mm分ss秒");

    public CommonAdapter(Context context, List<MenuNewsModel> list, int colums, boolean isLoad) {
        mContext = context;
        results = list;
        mColums = colums;
        mIsLoad = isLoad;
    }


    private OnItemClickListener mOnItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        switch (viewType) {

            case TYPE_MENU:
                // 加载数据item的布局，生成VH返回
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
                // 根据列数计算项目宽度，以使总宽度尽量填充屏幕
                int itemWidth = (mContext.getResources().getDisplayMetrics().widthPixels - (mColums + 1) * DisplayUtils.dip2px(mContext, 2) - DisplayUtils.dip2px(mContext, 16)) / mColums;
                // 下面根据比例计算您的item的高度，此处只是使用itemWidth

                int itemHeight = itemWidth;
                AbsListView.LayoutParams param = new AbsListView.LayoutParams(
                        itemWidth,
                        itemHeight);
                v.setLayoutParams(param);
                return new MenuHolder(v);

            case TYPE_NEWS:

                return new NewsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));

            //这里需要添加
            case TYPE_LOAD_MORE:
                return new LoadMoreHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_item, parent, false));


            default:
                Log.d("error", "viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof LoadMoreHolder) {
            bindLoadMore((LoadMoreHolder) holder, position);
        } else {
            if (mOnItemClickListener != null) {

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(holder.itemView, position, results.get(position).isMenu());
                    }
                });
            }
            if (holder instanceof MenuHolder) {
                bindMenu((MenuHolder) holder, position);
            } else if (holder instanceof NewsHolder) {
                bindNews((NewsHolder) holder, position);
            }
        }


    }

    private void bindLoadMore(LoadMoreHolder holder, int position) {
        holder.itemView.setVisibility(View.VISIBLE);

    }

    private void bindNews(NewsHolder holder, int position) {


        holder.time.setText(results.get(position).getMTime());

        holder.title.setText(results.get(position).getMTitle());
    }

    private void bindMenu(MenuHolder holder, int position) {
        holder.mSimpleDraweeView.setImageURI("https://tomcat.xiyoumobile.com/party-api-1.0/api/res/img?id="
                + results.get(position).getMMenuImg());
        holder.mTextView.setText(results.get(position).getName());
    }


    @Override
    public int getItemViewType(int position) {


        if (position + 1 == getItemCount() && mIsLoad) {
            return TYPE_LOAD_MORE;
        } else {
            if (results.get(position).isMenu()) {
                //如果菜单标题和图片不为空则为菜单
                return TYPE_MENU;
            } else {
                //其他类型
                return TYPE_NEWS;
            }
        }


    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_NEWS:
                            return mColums;
                        case TYPE_LOAD_MORE:
                            return mColums;
                        default:
                            return 1;
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (mIsLoad) {
            return results.size() + 1;
        } else {
            return results.size();
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    class MenuHolder extends RecyclerView.ViewHolder {


        SimpleDraweeView mSimpleDraweeView;

        TextView mTextView;

        public MenuHolder(View itemView) {
            super(itemView);


            mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.image);
            mSimpleDraweeView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            RoundingParams rp = new RoundingParams();
            rp.setRoundAsCircle(true);
            rp.setCornersRadius(width / 2);
            GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(mContext.getResources())
                    .setRoundingParams(rp)
                    .setFadeDuration(2000)
                    .build();
            mSimpleDraweeView.setHierarchy(hierarchy);


            mTextView = (TextView) itemView.findViewById(R.id.text);

        }
    }

    class NewsHolder extends RecyclerView.ViewHolder {


        TextView title;
        TextView time;

        public NewsHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            time = (TextView) itemView.findViewById(R.id.time);
        }

    }

    class LoadMoreHolder extends RecyclerView.ViewHolder {


        public LoadMoreHolder(View itemView) {
            super(itemView);
            loadView = itemView;
        }

    }

    public interface OnItemClickListener {
        void onItemClick(View view, int pos, boolean isMenu);
    }


    public void setLoadItemVisible(int visibility) {
        if (loadView != null) {
            loadView.setVisibility(visibility);
        }
    }

}
