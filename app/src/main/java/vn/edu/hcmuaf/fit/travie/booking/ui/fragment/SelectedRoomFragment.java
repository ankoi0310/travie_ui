package vn.edu.hcmuaf.fit.travie.booking.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import vn.edu.hcmuaf.fit.travie.databinding.FragmentSelectedRoomBinding;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedRoomFragment extends Fragment {
    FragmentSelectedRoomBinding binding;

    private static final String ARG_ROOM = "room";

    private Room room;

    public SelectedRoomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param room Room.
     * @return A new instance of fragment SelectedRoomFragment.
     */
    public static SelectedRoomFragment newInstance(Room room) {
        SelectedRoomFragment fragment = new SelectedRoomFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ROOM, room);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            room = getArguments().getParcelable(ARG_ROOM, Room.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectedRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.hotelTxt.setText(room.getHotel() != null ? room.getHotel().getName() : "");
        binding.roomTxt.setText(room.getName());
        binding.addressTxt.setText(room.getHotel() != null ? room.getHotel().getAddress().getFullAddress() : "");

        Glide.with(requireContext())
                .load(room.getImages().get(0))
                .into(binding.hotelImg);
    }
}