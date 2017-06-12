package com.feicuiedu.hunttreasure.treasure;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.feicuiedu.hunttreasure.MainActivity;
import com.feicuiedu.hunttreasure.R;
import com.feicuiedu.hunttreasure.commons.ActivityUtils;
import com.feicuiedu.hunttreasure.treasure.list.TreasureListFragment;
import com.feicuiedu.hunttreasure.treasure.map.MapFragment;
import com.feicuiedu.hunttreasure.user.UserPrefs;
import com.feicuiedu.hunttreasure.user.account.AccountActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation)
    NavigationView mNavigationView;
    private ImageView mIvIcon;

    private ActivityUtils mActivityUtils;
    private MapFragment mMapFragment;
    private TreasureListFragment mListFragment;
    private FragmentManager mSupportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        // 通过id找到MapFragment
        mSupportFragmentManager = getSupportFragmentManager();
        mMapFragment = (MapFragment) mSupportFragmentManager.findFragmentById(R.id.mapFragment);

        // 进入页面，将宝藏数据的缓存清空
        TreasureRepo.getInstance().clear();

        mActivityUtils = new ActivityUtils(this);

        // toolbar的处理
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // drawerLayout设置监听
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        toggle.syncState();// 同步状态
        mDrawerLayout.addDrawerListener(toggle);

        // 设置Navigation每一条的选中监听
        mNavigationView.setNavigationItemSelectedListener(this);

        mIvIcon = (ImageView) mNavigationView.getHeaderView(0).findViewById(R.id.iv_usericon);
        mIvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到个人信息的页面
                mActivityUtils.startActivity(AccountActivity.class);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // 更新侧滑上面的头像信息
        String photo = UserPrefs.getInstance().getPhoto();
        if (photo!=null){
            // 加载头像
            Glide.with(this)
                    .load(photo)
                    .error(R.mipmap.user_icon)
                    .placeholder(R.mipmap.user_icon)// 占位图
                    .dontAnimate()
                    .into(mIvIcon);
        }
    }

    // Navigation每一条的选中监听
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_hide:// 埋藏宝藏

                // 切换到埋藏宝藏的视图
                mMapFragment.changeUIMode(2);

                break;
            case R.id.menu_logout:// 退出登录

                // 清空登录用户数据
                UserPrefs.getInstance().clearUser();
                mActivityUtils.startActivity(MainActivity.class);
                finish();
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // 准备
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // 选项菜单里面action_toggle的菜单项
        MenuItem item = menu.findItem(R.id.action_toggle);

        // 根据显示的视图不一样，设置不一样的图标
        if (mListFragment!=null&&mListFragment.isAdded()){
            item.setIcon(R.drawable.ic_map);
        }else {
            item.setIcon(R.drawable.ic_view_list);
        }
        return true;
    }

    // 创建
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 菜单的填充
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return true;
    }

    // 选择某一个选项菜单项
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_toggle:
                showListFragment();
                // 更新选项菜单的视图:onPrepareOptionsMenu触发
                invalidateOptionsMenu();
                break;
        }
        return true;
    }

    // 显示或隐藏列表的视图
    public void showListFragment(){

        // add : show和hide

        // 如果列表正在展示
        if (mListFragment!=null&&mListFragment.isAdded()){
            // 将Fragment弹出回退栈
            mSupportFragmentManager.popBackStack();
            // 移除Fragment
            mSupportFragmentManager.beginTransaction().remove(mListFragment).commit();
            return;
        }
        mListFragment = new TreasureListFragment();

        // 在布局的fragment_container（Framelayout上展示Fragment）
        mSupportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,mListFragment)
                // 添加到回退栈
                .addToBackStack(null)
                .commit();
    }

    // 处理back返回键
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // MapFragment里面视图的普通的视图，可以退出
            if (mMapFragment.clickbackPrssed()) {
                super.onBackPressed();
            }
        }
    }
}
