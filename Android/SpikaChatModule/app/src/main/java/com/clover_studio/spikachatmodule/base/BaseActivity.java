package com.clover_studio.spikachatmodule.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.clover_studio.spikachatmodule.R;
import com.clover_studio.spikachatmodule.dialogs.SimpleProgressDialog;
import com.clover_studio.spikachatmodule.robospice.spice.CustomSpiceManager;
import com.clover_studio.spikachatmodule.robospice.spice.SpringService;

/**
 * Created by ubuntu_ivo on 17.07.15..
 */
public class BaseActivity extends AppCompatActivity {

    private CustomSpiceManager spiceManager = new CustomSpiceManager(SpringService.class);
    SimpleProgressDialog dialog;

    Toolbar toolbar;

    protected boolean doNotHideProgressNow = false;
    protected boolean doNotShowProgressNow = false;

    /**
     * get spice manager
     * @return spice manager
     */
    public CustomSpiceManager getSpiceManager() {
        return spiceManager;
    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    /**
     * show or hide loading progress
     *
     * @param showProgress to show loading progress
     */
    public void handleProgress(boolean showProgress) {

        if(doNotHideProgressNow){
            doNotHideProgressNow = false;
            return;
        }

        if(doNotShowProgressNow){
            doNotShowProgressNow = false;
            return;
        }

        try {

            if (showProgress) {

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    dialog = null;
                }

                dialog = new SimpleProgressDialog(this);
                dialog.show();

            } else {

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                dialog = null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new SimpleProgressDialog(this);
    }

    /**
     * set toolbar for activity
     * @param toolbarId id of view when toolbar will be added
     * @param toolbarLayout layout of custom toolbar
     */
    protected void setToolbar(int toolbarId, int toolbarLayout){
        toolbar = (Toolbar) findViewById(toolbarId);
        View customToolbarView = getLayoutInflater().inflate(toolbarLayout, null);
        setSupportActionBar(toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.addView(customToolbarView);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * set toolbar title
     * @param title title to show
     */
    protected void setToolbarTitle(String title){
        TextView tvTitle = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        tvTitle.setText(title);
    }

    /**
     * set state back to menu button, to show back arrow instead burger
     */
    protected void setMenuLikeBack(){
        MaterialMenuView menuView = (MaterialMenuView) toolbar.findViewById(R.id.sidebarBtnMaterial);
        menuView.setState(MaterialMenuDrawable.IconState.ARROW);
        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void onSettingsButtonClickListener(View.OnClickListener listener){
        ImageButton settings = (ImageButton) toolbar.findViewById(R.id.settings);
        settings.setOnClickListener(listener);
    }

    /**
     * change color of toolbar
     * @param color new toolbar color
     */
    protected void changeToolbarColor(String color){
        toolbar.setBackgroundColor(Color.parseColor(color));
    }

    protected Activity getActivity(){
        return this;
    }
}
