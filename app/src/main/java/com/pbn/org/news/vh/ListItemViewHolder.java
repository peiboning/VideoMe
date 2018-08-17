package com.pbn.org.news.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.R;
import com.pbn.org.news.model.common.NewsBean;
import com.pbn.org.news.model.quyue.QueyueNewsBean;
import com.pbn.org.news.view.CustomSizeImageView;

import java.util.Iterator;
import java.util.List;

public class ListItemViewHolder extends BaseVH {
    private RecyclerView listView;
    private AdapterTemp adapterTemp;
    List<NewsBean> data;
    public ListItemViewHolder(View itemView) {
        super(itemView);
        listView = itemView.findViewById(R.id.item_list);
        LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext()){
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        };
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setLayoutManager(manager);
    }

    @Override
    public void bind(NewsBean bean) {


    }

    @Override
    public void bind(List<NewsBean> datas) {
        if(adapterTemp == null){
            adapterTemp = new AdapterTemp();
            listView.setAdapter(adapterTemp);
        }
        data = datas;
//        fliterData();
        Log.e("AdapterTemp", "data size is " + data.size());
        adapterTemp.notifyDataSetChanged();
    }

    private void fliterData() {
        if(null != data && data.size() > 0){
            Iterator<NewsBean> i = data.iterator();
            while (i.hasNext()){
                if(i.next().getTemplate() != QueyueNewsBean.TYPE_ITEM_LIST_VIDEO){
                    i.remove();
                }
            }
        }
    }

    private class AdapterTemp extends RecyclerView.Adapter{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            TempVH vh = new TempVH(inflater.inflate(R.layout.temp_list, null));
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Log.e("AdapterTemp", "onBindViewHolder");
            ((TempVH)holder).bind(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class TempVH extends BaseVH{

        private CustomSizeImageView imageView;
        public TempVH(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_imageview);
        }

        @Override
        public void bind(NewsBean bean){
            loadImage(imageView, bean.getImages().get(0).getUrl());
        }
    }
}
