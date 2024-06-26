package vn.edu.hcmuaf.fit.travie.home.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SharedViewModel;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SharedViewModelFactory;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHomeBinding;
import vn.edu.hcmuaf.fit.travie.home.adapter.HotelAdapter;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModelFactory;

public class HomeFragment extends Fragment {
    private final MutableLiveData<Address> address = new MutableLiveData<>();

    FragmentHomeBinding binding;
    View loadingView;

    SharedViewModel sharedViewModel;

    @Inject
    HotelViewModel hotelViewModel;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MainActivity mainActivity = (MainActivity) requireActivity();
        loadingView = mainActivity.findViewById(R.id.loadingView);

        ViewCompat.setOnApplyWindowInsetsListener(mainActivity.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            layoutParams.setMargins(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setLayoutParams(layoutParams);
            return WindowInsetsCompat.CONSUMED;
        });

        AnimationUtil.animateView(loadingView, View.VISIBLE, 0.4f, 200);
        sharedViewModel = new ViewModelProvider(requireActivity(), new SharedViewModelFactory()).get(SharedViewModel.class);
        sharedViewModel.getLastLocation().observe(getViewLifecycleOwner(), this::getCityName);

        address.observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                binding.currentLocationTxt.setText(address.getAdminArea());
            }
        });

        hotelViewModel = new ViewModelProvider(requireActivity(), new HotelViewModelFactory(requireContext())).get(HotelViewModel.class);
        fetchHotelList();
        hotelViewModel.getNearByHotelList().observe(getViewLifecycleOwner(), result -> {
            if (result.getError() != null) {
                Toast.makeText(getContext(), result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                ArrayList<Hotel> hotels = result.getSuccess();
                HotelAdapter hotelAdapter = new HotelAdapter(hotels);
                binding.nearbyRecyclerView.setAdapter(hotelAdapter);
            }

            if (loadingView.getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(loadingView, View.GONE, 0, 200);
            }
        });
        hotelViewModel.getPopularHotelList().observe(getViewLifecycleOwner(), result -> {
            if (result.getError() != null) {
                Toast.makeText(getContext(), result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                ArrayList<Hotel> hotels = result.getSuccess();
                HotelAdapter hotelAdapter = new HotelAdapter(hotels);
                binding.popularRecyclerView.setAdapter(hotelAdapter);
            }

            if (binding.swipeRefreshLayout.isRefreshing()) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            if (loadingView.getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(loadingView, View.GONE, 0, 200);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(this::fetchHotelList);

        binding.nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.nearbyRecyclerView.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));
        binding.popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.popularRecyclerView.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));

//        SliderView sliderView = binding.imageSlider;
//
//        SliderAdapter adapter = new SliderAdapter(getContext());
//        sliderView.setSliderAdapter(adapter);
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
//        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        sliderView.setIndicatorSelectedColor(Color.WHITE);
//        sliderView.setIndicatorUnselectedColor(Color.GRAY);
//        sliderView.setScrollTimeInSec(3);
//        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//        sliderView.setAutoCycle(true);
//        sliderView.startAutoCycle();
    }

    private void fetchHotelList() {
        hotelViewModel.fetchNearByHotelList("Hồ Chí Minh");
        hotelViewModel.fetchPopularHotelList();
    }

    private void getCityName(Location location) {
        Geocoder geocoder = new Geocoder(requireContext());
        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1, addresses -> {
            if (!addresses.isEmpty()) {
                address.postValue(addresses.get(0));
            }
        });
    }
}