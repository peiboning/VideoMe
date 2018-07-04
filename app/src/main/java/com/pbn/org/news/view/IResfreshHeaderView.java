package com.pbn.org.news.view;

public interface IResfreshHeaderView {
    int STATE_DEF = -1;
    int STATE_RESET = 1;
    int STATE_MOVE = 2;
    int STATE_RELEASE_REFRESH = 3;
    int STATE_RESFRESHING = 4;

    void onMove(int progress);
    void release2Resfresh();
    void reset();
}
