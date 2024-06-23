package vn.edu.hcmuaf.fit.travie.hotel.ui.explore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentExploreBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.service.HotelService;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;

public class ExploreFragment extends Fragment {
    FragmentExploreBinding binding;
    HotelService hotelService;
    HotelViewModel hotelViewModel;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        binding.rvExplore.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvExplore.addItemDecoration(new SpaceItemDecoration(16));
        binding.rvExplore.setAdapter(new ExploreHotelAdapter());
    }
}