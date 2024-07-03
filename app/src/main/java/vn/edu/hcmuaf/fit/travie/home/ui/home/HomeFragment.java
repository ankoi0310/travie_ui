package vn.edu.hcmuaf.fit.travie.home.ui.home;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SharedViewModel;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHomeBinding;
import vn.edu.hcmuaf.fit.travie.home.ui.banner.BannerFragment;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.search.SearchHotelActivity;

public class HomeFragment extends Fragment {
    private final int NEARBY_HOTEL_SIZE = 5;
    private final int POPULAR_HOTEL_SIZE = 5;
    private final MutableLiveData<Address> address = new MutableLiveData<>();

    FragmentHomeBinding binding;
    View loadingView;

    @Inject
    SharedViewModel sharedViewModel;

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

        AnimationUtil.animateView(loadingView, View.VISIBLE, 0.4f, 200);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getLastLocation().observe(getViewLifecycleOwner(), this::getCityName);
        address.observe(getViewLifecycleOwner(), address -> {
            if (address != null) {
                binding.currentLocationTxt.setText(address.getAdminArea());
            }
        });

        initBannerSlider();

        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
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

        binding.searchCardView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchHotelActivity.class);
            startActivity(intent);
        });

        binding.nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.nearbyRecyclerView.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));
        binding.popularRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.popularRecyclerView.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));
    }

    private void initBannerSlider() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(binding.bannerFragmentContainer.getId(), BannerFragment.newInstance())
                .commit();
    }

    private void fetchHotelList() {
        hotelViewModel.fetchNearByHotelList("Hồ Chí Minh", 1, NEARBY_HOTEL_SIZE);
        hotelViewModel.fetchPopularHotelList(1, POPULAR_HOTEL_SIZE);
    }

    private void getCityName(Location location) {
        Log.d("HomeFragment", "getCityName: " + location);
        Geocoder geocoder = new Geocoder(requireContext());
        geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1, addresses -> {
            Log.d("HomeFragment", "getCityName: " + addresses);
            if (!addresses.isEmpty()) {
                address.postValue(addresses.get(0));
            }
        });
    }
}