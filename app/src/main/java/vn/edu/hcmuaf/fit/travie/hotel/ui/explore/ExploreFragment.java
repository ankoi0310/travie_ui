package vn.edu.hcmuaf.fit.travie.hotel.ui.explore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentExploreBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;

public class ExploreFragment extends Fragment {
    private FragmentExploreBinding binding;
    HotelViewModel hotelViewModel;

    private ExploreHotelAdapter hotelAdapter;
    private int currentPage = 1;
    private boolean loadMore = true;
    private final int LIMIT_HOTEL_LIST_SIZE = 20; // if hotel list reaches this limit, stop binding.loading more

    public ExploreFragment() {
        // Required empty public constructor
    }

    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.loading.setVisibility(View.VISIBLE);

        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);
        hotelViewModel.fetchExploreHotelList(1, 5);
        handleLoadExploreHotel();

        binding.rvHotel.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvHotel.setHasFixedSize(true);
        binding.rvHotel.addItemDecoration(new SpaceItemDecoration(16));
        handleLoadingMore();
    }

    private void handleLoadingMore() {
        binding.rvHotel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (totalItemCount <= (lastVisibleItem + 1) && loadMore) {
                        binding.loading.setVisibility(View.VISIBLE);

                        new Handler(Looper.getMainLooper()).postDelayed(() -> hotelViewModel.fetchExploreHotelList(++currentPage, 5), 1000);
                    }
                }
            }
        });
    }

    private void handleLoadExploreHotel() {
        hotelViewModel.getExploreResult().observe(getViewLifecycleOwner(), result -> {
            if (result.getError() != null) {
                Toast.makeText(requireContext(), result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                ArrayList<Hotel> hotels = result.getSuccess();

                hotelAdapter = (ExploreHotelAdapter) binding.rvHotel.getAdapter();
                if (hotelAdapter != null) {
                    if (hotels.isEmpty() || hotelAdapter.getItemCount() >= LIMIT_HOTEL_LIST_SIZE) {
                        loadMore = false;
                    } else {
                        loadMore = true;
                        hotelAdapter.addHotels(hotels);
                    }
                } else {
                    hotelAdapter = new ExploreHotelAdapter(hotels);
                }

                binding.rvHotel.setAdapter(hotelAdapter);
            }

            if (binding.loading.getVisibility() == View.VISIBLE) {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }
}