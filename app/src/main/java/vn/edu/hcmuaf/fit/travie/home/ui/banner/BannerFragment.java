package vn.edu.hcmuaf.fit.travie.home.ui.banner;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import vn.edu.hcmuaf.fit.travie.databinding.FragmentBannerBinding;
import vn.edu.hcmuaf.fit.travie.home.data.model.Banner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BannerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BannerFragment extends Fragment {
    FragmentBannerBinding binding;

    private BannerViewModel bannerViewModel;

    public BannerFragment() {
        // Required empty public constructor
    }

    public static BannerFragment newInstance() {
        BannerFragment fragment = new BannerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initSlider();

        bannerViewModel = new ViewModelProvider(this).get(BannerViewModel.class);
        bannerViewModel.loadBanners(1, 4);
        handleLoadBanners();
    }

    private void initSlider() {
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        binding.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        binding.imageSlider.setScrollTimeInSec(3);
        binding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.imageSlider.setAutoCycle(true);
        binding.imageSlider.startAutoCycle();
    }

    private void handleLoadBanners() {
        bannerViewModel.getBannerListResult().observe(getViewLifecycleOwner(), result -> {
            if (result.getError() != null) {
                Toast.makeText(getContext(), result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                ArrayList<Banner> banners = result.getSuccess();
                BannerAdapter adapter = new BannerAdapter(banners);
                binding.imageSlider.setSliderAdapter(adapter);
            }
        });

        bannerViewModel.loadBanners(1, 4);
    }
}