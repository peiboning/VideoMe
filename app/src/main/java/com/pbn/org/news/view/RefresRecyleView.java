package com.pbn.org.news.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.pbn.org.news.list.HeaderViewHolder;
import com.pbn.org.news.vh.SimpleVH;

public class RefresRecyleView extends RecyclerView{
    private static final int TYPE_HEADER_VIEW = 10000;
    private static final int TYPE_FOOTER_VIEW = 10001;
    private AdapterWrap adapterWrap;
    private RefreshHeaderView headerView;
    private OnRefreshListener mListener;
    private FootView mFootView;

    public interface OnRefreshListener {
        void onPullToRefreshing();
        void onLoadMore();
    }

    public RefresRecyleView(Context context) {
        super(context);
        init();
    }

    public RefresRecyleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RefresRecyleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        headerView = new RefreshHeaderView(getContext());
        mFootView = new FootView(getContext());
        adapterWrap = new AdapterWrap();
    }

    public int getHeaderViewNum(){
        return 1;
    }

    public void setOnRefreshListener(OnRefreshListener listener){
        this.mListener = listener;
    }

    public void refreshOver(int size){
        if(headerView.getState() == IResfreshHeaderView.STATE_RESFRESHING){
            headerView.updateNumTip(size);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        adapterWrap.setAdapter(adapter,getContext());
        super.setAdapter(adapterWrap);
    }

    public void notifyDataSetChanged(){
        Log.e("ChannelFragment", " wrap notifyDataSetChanged");
        adapterWrap.notifyDataSetChanged();
    }

    public void loadMore(){
        if(null != mFootView){
            mFootView.setStatus(FootView.STATE_LOADING);
        }
        if(null != mListener){
            mListener.onLoadMore();
        }
    }

    public void loadMoreComplete(){
        if(null != mFootView){
            mFootView.setStatus(FootView.STATE_COMPLETE);
        }
    }



    private int mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mLastY = (int) e.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) (e.getRawY() - mLastY);
                mLastY = (int) e.getRawY();
                if(dy>20){
                    dy = 20;
                }
                if(isOnTop() && (headerView.getState() <= IResfreshHeaderView.STATE_RELEASE_REFRESH)){
                    headerView.onMove(dy/2);
                    if(headerView.getVisbleHeight() > 0){
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(isOnTop()&& headerView.getState() == IResfreshHeaderView.STATE_RELEASE_REFRESH){
                    headerView.release2Resfresh();
                    if(null != mListener){
                        mListener.onPullToRefreshing();
                    }
                }else {
                    headerView.reset(false);
                }
                break;
            default:
                break;
        }
        boolean flag = super.onTouchEvent(e);
        Log.e("isOnTop", "flag is " + flag);
        return flag;
    }

    public boolean isOnTop() {
        Log.e("isOnTop", (headerView.getParent() != null) +"");
        return (null != headerView && headerView.getParent() != null);
    }

    private class AdapterWrap extends Adapter{
        private Adapter adapter;
        private Context mContext;

        public void setAdapter(Adapter adapter, Context context){
            this.adapter = adapter;
            mContext = context;
        }

        public boolean isHeader(int pos){
            return pos == 0;
        }

        public boolean isFoot(int pos){
            return pos == getItemCount() - 1;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if(viewType == TYPE_HEADER_VIEW){
                return new HeaderViewHolder(headerView);
            }else if(viewType == TYPE_FOOTER_VIEW){
                return new SimpleVH(mFootView);
            }else{
                return adapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if(isHeader(position) || isFoot(position)){
                Log.e("ChannelFragment", " wrap onBindViewHolder is header");
            }else{
                adapter.onBindViewHolder(holder, position-1);
            }
        }

        @Override
        public int getItemCount() {
            return adapter.getItemCount() + 2;
        }

        @Override
        public int getItemViewType(int position) {
            if(isHeader(position)){
                return TYPE_HEADER_VIEW;
            }else if(isFoot(position)){
                return TYPE_FOOTER_VIEW;
            }else{
                return adapter.getItemViewType(position - 1);
            }
        }
    }

    public void startRefresh(){
        scrollToPosition(0);
        if((headerView.getState() <= IResfreshHeaderView.STATE_RELEASE_REFRESH)){
            headerView.release2Resfresh();
            if(null != mListener){
                mListener.onPullToRefreshing();
            }
        }
    }

}
