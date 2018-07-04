package com.pbn.org.news.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.View;

import com.pbn.org.news.fragment.ChannelFragment;
import com.pbn.org.news.model.Channel;

import java.util.List;

public class NewsChannelAdapter extends FragmentStatePagerAdapter {
    private SparseArray<ChannelFragment> fragments;
    private List<Channel> channels;

    public NewsChannelAdapter(FragmentManager fm, List<Channel> datas) {
        super(fm);
        channels = datas;
        fragments = new SparseArray<ChannelFragment>(datas.size());
    }

    @Override
    public Fragment getItem(int position) {
        //TODO
//        if(fragments.get(position) != null){
//            return fragments.get(position);
//        }
        ChannelFragment fragment = ChannelFragment.newInstance(channels.get(position));
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return channels.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return channels.get(position).getTitle();
    }
}
