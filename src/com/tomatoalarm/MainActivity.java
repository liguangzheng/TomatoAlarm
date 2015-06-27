package com.tomatoalarm;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;

import com.core.fragment.BaseFragment;
import com.core.utils.ToastUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tomatoalarm.dialog.AppExitDialog;
import com.tomatoalarm.fragment.AlarmFragment;
import com.tomatoalarm.fragment.LeftMenuFragment;
import com.tomatoalarm.fragment.RightMenuFragment;

public class MainActivity extends SlidingFragmentActivity {

    private BaseFragment mLeftMenuFragment;
    private BaseFragment mRightMenuFragment;
    private BaseFragment mMainFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        FragmentTransaction t = this.getSupportFragmentManager()
                .beginTransaction();
        // 主页
        setContentView(R.layout.frame_main);
        mMainFragment = new AlarmFragment();
        t.replace(R.id.framelayout_main, mMainFragment);

        // 左侧菜单
        setBehindContentView(R.layout.frame_menu_left);
        mLeftMenuFragment = new LeftMenuFragment();
        t.replace(R.id.left_menu_frame, mLeftMenuFragment);
        t.commit();
        SlidingMenu sm = getSlidingMenu();
        sm.setShadowWidthRes(R.dimen.width_slidingmenu_shadow);
        sm.setShadowDrawable(R.drawable.slidingleftmenu_shadow);
        sm.setBehindOffsetRes(R.dimen.offset_slidingmenu);
        sm.setFadeDegree(0.35f);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        // 右侧菜单
        sm.setMode(SlidingMenu.LEFT_RIGHT);
        sm.setSecondaryMenu(R.layout.frame_menu_right);
        mRightMenuFragment = new RightMenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_menu_frame, mRightMenuFragment).commit();
        sm.setSecondaryShadowDrawable(R.drawable.slidingrightmenu_shadow);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ToastUtil.clear(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            new AppExitDialog(this).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}