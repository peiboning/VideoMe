package com.pbn.org.news.channel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.R;
import com.pbn.org.news.channel.inte.IChannelEditor;
import com.pbn.org.news.channel.inte.IOnMyItemClick;
import com.pbn.org.news.channel.viewholder.MyItemVH;
import com.pbn.org.news.channel.viewholder.MyVH;
import com.pbn.org.news.channel.viewholder.OtherItemVH;
import com.pbn.org.news.channel.viewholder.OtherVH;
import com.pbn.org.news.model.Channel;
import com.pbn.org.news.vh.EmptyVH;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter implements IChannelEditor, IOnMyItemClick {
    private List<Channel> showDatas;
    private List<Channel> hiddenDatas;
    private Context mContext;
    private boolean isEditable;
    private RecyclerView recyclerView;

    public ChannelAdapter(Context context, RecyclerView view) {
        this.mContext = context;
        recyclerView = view;
    }

    public void setDatas(List<Channel> showDatas, List<Channel> hiddenDatas){
        this.showDatas = showDatas;
        this.hiddenDatas = hiddenDatas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        if(viewType == Channel.TYPE_MY){
            view = inflater.inflate(R.layout.channel_mgr_my, null);
            return new MyVH(view, this);
        }else if(viewType == Channel.TYPE_OTHER){
            view = inflater.inflate(R.layout.channel_mgr_other, null);
            return new OtherVH(view);
        }else if(viewType == Channel.TYPE_MY_CHANNEL){
            view = inflater.inflate(R.layout.channel_mgr_my_item, null);
            return new MyItemVH(view, this);
        }else if(viewType == Channel.TYPE_OTHER_CHANNEL){
            view = inflater.inflate(R.layout.channel_mgr_other_item, null);
            return new OtherItemVH(view);
        }else {
            return new EmptyVH(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyItemVH){
            ((MyItemVH) holder).show(showDatas.get(position-1));
        }else if (holder instanceof OtherItemVH){
            ((OtherItemVH) holder).show(hiddenDatas.get(position - showDatas.size() - 2));
        }
    }

    @Override
    public int getItemCount() {
        return showDatas.size() + hiddenDatas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return Channel.TYPE_MY;
        }
        if(position>0 && position < showDatas.size() + 1){
            return Channel.TYPE_MY_CHANNEL;
        }
        if(position == showDatas.size() + 1){
            return Channel.TYPE_OTHER;
        }
        return Channel.TYPE_OTHER_CHANNEL;
    }

    @Override
    public void onEditStateChange(boolean isEdit) {
        isEditable = isEdit;
        int cc = recyclerView.getChildCount();
        for(int i = 0;i<cc;i++){
            View view = recyclerView.getChildAt(i);
            View editIcon = view.findViewById(R.id.channel_edit_icon);
            if(null != editIcon){
                editIcon.setVisibility(isEditable ? View.VISIBLE : View.GONE);
            }
        }
    }

    @Override
    public void onMyItemClick(MyItemVH vh) {
        if(isEditable){
            int clickPos = vh.getAdapterPosition();
            moveMyItemToOtherItem(clickPos);
        }
    }

    private void moveMyItemToOtherItem(int srcPos){
        int realPos = srcPos - 1;
        if(realPos > showDatas.size()){
            return;
        }
        Channel channel = showDatas.remove(realPos);
        hiddenDatas.add(0, channel);
        notifyItemMoved(srcPos, showDatas.size() + 2);
    }

    public boolean isEditable(){
        return isEditable;
    }
}
