package com.pbn.org.news.status_bar;

import android.view.Window;

/**
 *
 * @author peiboning
 */

interface IStatusBar {
    /**
     * Set the status bar color
     *
     * @param window The window to set the status bar color
     * @param color  Color value
     */
    void setStatusBarColor(Window window, int color);
}
