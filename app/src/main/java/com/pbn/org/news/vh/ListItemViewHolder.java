package com.pbn.org.news.vh;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pbn.org.news.R;

public class ListItemViewHolder extends BaseVH {
    private RecyclerView listView;
    public ListItemViewHolder(View itemView) {
        super(itemView);
        listView = itemView.findViewById(R.id.item_list);
        LinearLayoutManager manager = new LinearLayoutManager(itemView.getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        listView.setLayoutManager(manager);
        listView.setAdapter(new AdapterTemp());
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

        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    private class TempVH extends RecyclerView.ViewHolder{

        public TempVH(View itemView) {
            super(itemView);
        }
    }
}
