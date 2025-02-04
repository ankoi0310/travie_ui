package vn.edu.hcmuaf.fit.travie.core.common.ui;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Optional;

import it.koi.flashytabbar.TabFlashyAnimator;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityMainBinding;
import vn.edu.hcmuaf.fit.travie.hotel.ui.explore.ExploreFragment;
import vn.edu.hcmuaf.fit.travie.invoice.ui.history.HistoryFragment;
import vn.edu.hcmuaf.fit.travie.home.ui.home.HomeFragment;
import vn.edu.hcmuaf.fit.travie.user.ui.profile.ProfileFragment;

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

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        PageAdapter adapter = new PageAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
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
        tabFlashyAnimator.addTabItem(getString(AppConstant.MenuTitle.HOME), R.drawable.home);
        tabFlashyAnimator.addTabItem(getString(AppConstant.MenuTitle.EXPLORE), R.drawable.location);
        tabFlashyAnimator.addTabItem(getString(AppConstant.MenuTitle.HISTORY), R.drawable.clock);
        tabFlashyAnimator.addTabItem(getString(AppConstant.MenuTitle.PROFILE), R.drawable.user);
        tabFlashyAnimator.highLightTab(0);
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            Optional<String> path = data.getPathSegments().stream().findFirst();

            if (path.isPresent()) {
                if (path.get().equals("explore")) {
                    binding.viewPager.setCurrentItem(1);
                }
            }
        }
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
        public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return switch (position) {
                case 1 -> ExploreFragment.newInstance();
                case 2 -> HistoryFragment.newInstance();
                case 3 -> ProfileFragment.newInstance();
                default -> HomeFragment.newInstance();
            };
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}