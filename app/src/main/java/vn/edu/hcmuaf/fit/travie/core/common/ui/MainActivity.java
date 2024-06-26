package vn.edu.hcmuaf.fit.travie.core.common.ui;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import it.koi.flashytabbar.TabFlashyAnimator;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityMainBinding;
import vn.edu.hcmuaf.fit.travie.hotel.ui.explore.ExploreFragment;
import vn.edu.hcmuaf.fit.travie.invoice.ui.history.HistoryFragment;
import vn.edu.hcmuaf.fit.travie.home.fragment.HomeFragment;
import vn.edu.hcmuaf.fit.travie.user.fragment.ProfileFragment;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;
    private TabFlashyAnimator tabFlashyAnimator;
    private SharedViewModel sharedViewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sharedViewModel = new ViewModelProvider(this, new SharedViewModelFactory()).get(SharedViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        PageAdapter adapter = new PageAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setOffscreenPageLimit(1);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabFlashyAnimator.highLightTab(position);
            }
        });

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
        }).attach();

        tabFlashyAnimator = new TabFlashyAnimator(binding.tabLayout, R.color.primary);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.HOME, R.drawable.home);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.EXPLORE, R.drawable.location);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.HISTORY, R.drawable.clock);
        tabFlashyAnimator.addTabItem(AppConstant.MenuTitle.PROFILE, R.drawable.user);
        tabFlashyAnimator.highLightTab(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tabFlashyAnimator.onStart(binding.tabLayout);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tabFlashyAnimator.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Log.d("HomeFragment", "onRequestPermissionsResult: Permission denied");
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                sharedViewModel.setLastLocation(location);
            }
        });
    }

    public static class PageAdapter extends FragmentStateAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<Fragment>() {{
            add(HomeFragment.newInstance());
            add(ExploreFragment.newInstance());
            add(HistoryFragment.newInstance());
            add(ProfileFragment.newInstance());
        }};

        public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }
    }
}