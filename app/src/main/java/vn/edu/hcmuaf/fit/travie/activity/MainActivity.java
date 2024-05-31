package vn.edu.hcmuaf.fit.travie.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.cuberto.flashytabbarandroid.TabFlashyAnimator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.utils.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityMainBinding;
import vn.edu.hcmuaf.fit.travie.fragment.ExploreFragment;
import vn.edu.hcmuaf.fit.travie.fragment.HistoryFragment;
import vn.edu.hcmuaf.fit.travie.fragment.HomeFragment;
import vn.edu.hcmuaf.fit.travie.fragment.ProfileFragment;

public class MainActivity extends BaseActivity {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private TabFlashyAnimator tabFlashyAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(ContextCompat.getColor(this, R.color.surface));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mFragmentList.add(HomeFragment.newInstance());
        mFragmentList.add(ExploreFragment.newInstance());
        mFragmentList.add(HistoryFragment.newInstance());
        mFragmentList.add(ProfileFragment.newInstance());

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        FragmentStateAdapter adapter = new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getItemCount() {
                return mFragmentList.size();
            }
        };
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
        }).attach();

        tabFlashyAnimator = new TabFlashyAnimator(tabLayout);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.HOME, R.drawable.ic_home);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.EXPLORE, R.drawable.ic_explore);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.HISTORY, R.drawable.ic_history);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.PROFILE, R.drawable.ic_profile);

        tabFlashyAnimator.highLightTab(0);
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabFlashyAnimator.highLightTab(position);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabFlashyAnimator.onStart(findViewById(R.id.tabLayout));
    }

    @Override
    protected void onStop() {
        super.onStop();
        tabFlashyAnimator.onStop();
    }
}