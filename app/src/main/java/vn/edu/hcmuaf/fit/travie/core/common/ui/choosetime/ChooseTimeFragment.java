package vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseTimeFragment extends Fragment {
    FragmentChooseTimeBinding binding;

    private static final String ARG_BOOKINGTYPES = "bookingTypes";

    private ArrayList<BookingType> bookingTypes;

    public ChooseTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param bookingTypes List of booking types.
     * @return A new instance of fragment ChooseTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseTimeFragment newInstance(ArrayList<BookingType> bookingTypes) {
        ChooseTimeFragment fragment = new ChooseTimeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_BOOKINGTYPES, bookingTypes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookingTypes = getArguments().getParcelableArrayList(ARG_BOOKINGTYPES);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseTimeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}