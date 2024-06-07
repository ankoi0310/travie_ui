package vn.edu.hcmuaf.fit.travie.home.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHomeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.HotelModel;
import vn.edu.hcmuaf.fit.travie.home.ui.adapter.NearByHotelAdapter;
import vn.edu.hcmuaf.fit.travie.hotel.ui.viewmodel.HotelViewModel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.viewmodel.HotelViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    NearByHotelAdapter nearByHotelAdapter;

    @Inject
    HotelViewModelFactory viewModelFactory;

    HotelViewModel hotelViewModel;

    // TODO: Rename and change types of parameters
    private List<HotelModel> nearByHotelList;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        hotelViewModel = new ViewModelProvider(this, viewModelFactory).get(HotelViewModel.class);

        nearByHotelAdapter = new NearByHotelAdapter(nearByHotelList);
        binding.nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        binding.nearbyRecyclerView.addItemDecoration(new SpaceItemDecoration(8));
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

        hotelViewModel.getNearByHotelResult().observe(getViewLifecycleOwner(), result -> {
            if (result == null) {
                return;
            }

            if (result instanceof Result.Success) {
                nearByHotelList = ((Result.Success<List<HotelModel>, DataError>) result).getData();
                nearByHotelAdapter.updateData(nearByHotelList);
                showNearByHotelList();
            } else {
                Result.Error<List<HotelModel>, DataError> error = (Result.Error<List<HotelModel>, DataError>) result;
                if (error.getError() != null) {
                    Toast.makeText(getContext(), error.getError().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.nearbyProgressBar.setVisibility(View.VISIBLE);
        binding.nearbyRecyclerView.setVisibility(View.GONE);
        hotelViewModel.searchNearByHotelList();
    }

    private void showNearByHotelList() {
        binding.nearbyProgressBar.setVisibility(View.GONE);
        binding.nearbyRecyclerView.setVisibility(View.VISIBLE);
    }

    public static class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            }
        }
    }
}