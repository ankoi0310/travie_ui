package vn.edu.hcmuaf.fit.travie.home.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHomeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.model.Hotel;
import vn.edu.hcmuaf.fit.travie.home.adapter.NearByHotelAdapter;
import vn.edu.hcmuaf.fit.travie.hotel.service.HotelService;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    NearByHotelAdapter nearByHotelAdapter;

    HotelService hotelService;

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((MainActivity) requireActivity()).mainComponent.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        hotelService = RetrofitService.createService(requireContext(), HotelService.class);

        nearByHotelAdapter = new NearByHotelAdapter(new ArrayList<>());
        binding.nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.nearbyRecyclerView.addItemDecoration(new SpaceItemDecoration(8, RecyclerView.HORIZONTAL));
        binding.nearbyRecyclerView.setAdapter(nearByHotelAdapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        binding.nearbyProgressBar.setVisibility(View.VISIBLE);
        binding.nearbyRecyclerView.setVisibility(View.GONE);
        hotelService.getHotels().enqueue(new Callback<HttpResponse<List<Hotel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Response<HttpResponse<List<Hotel>>> response) {
                binding.nearbyProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                    List<Hotel> nearByHotelList = response.body().getData();
                    nearByHotelAdapter.updateData(nearByHotelList);
                    binding.nearbyRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Throwable t) {
                binding.nearbyProgressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}