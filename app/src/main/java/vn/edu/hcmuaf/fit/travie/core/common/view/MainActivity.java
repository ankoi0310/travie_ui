package vn.edu.hcmuaf.fit.travie.core.common.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import it.koi.flashytabbar.TabFlashyAnimator;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityMainBinding;
import vn.edu.hcmuaf.fit.travie.hotel.ui.view.ExploreFragment;
import vn.edu.hcmuaf.fit.travie.invoice.ui.view.HistoryFragment;
import vn.edu.hcmuaf.fit.travie.home.ui.view.HomeFragment;
import vn.edu.hcmuaf.fit.travie.user.ui.view.ProfileFragment;

public class MainActivity extends BaseActivity {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private TabFlashyAnimator tabFlashyAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnApplyWindowInsetsListener(binding.main);

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

        tabFlashyAnimator = new TabFlashyAnimator(tabLayout, R.color.primary);
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