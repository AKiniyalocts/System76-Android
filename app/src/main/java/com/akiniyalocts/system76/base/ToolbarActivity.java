package com.akiniyalocts.system76.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

/**
 * Created by anthony on 8/18/15.
 */
public abstract class ToolbarActivity extends ButterKnifeActivity {

    /**
     *
     * @return Toolbar to set as ActionBar from your layout
     */
    public abstract Toolbar getToolBar();

    /**
     * Sets your support action bar from your toolbar.
     * Sets home as up.
     * Sets Title for action bar
     * @param title ActionBar Title
     */
    public void setupHomeSupportActionBar(String title){

        if(getToolBar() != null){

            setSupportActionBar(getToolBar());

            ActionBar supportActionBar = getSupportActionBar();

            if(supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                supportActionBar.setHomeButtonEnabled(true);
                supportActionBar.setTitle(title);
            }
        }

    }
}
