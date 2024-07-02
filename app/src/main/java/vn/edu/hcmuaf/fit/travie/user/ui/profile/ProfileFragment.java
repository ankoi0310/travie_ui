package vn.edu.hcmuaf.fit.travie.user.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.LocalStorage;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentProfileBinding;
import vn.edu.hcmuaf.fit.travie.user.ui.UserViewModel;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private LocalStorage localStorage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        localStorage = new LocalStorage(requireContext());
        loadLoggedInUser();

        binding.recyclerViewProfileMenu.setAdapter(new ProfileMenuAdapter());
        binding.recyclerViewProfileMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void loadLoggedInUser() {
        String nickname = localStorage.getString(LocalStorage.USER_NICKNAME);
        String phone = localStorage.getString(LocalStorage.USER_PHONE);
        String avatar = localStorage.getString(LocalStorage.USER_AVATAR);

        binding.nickname.setText(nickname);
        binding.phone.setText(phone);

        Glide.with(requireContext()).load(avatar).into(binding.avatar);
    }
}