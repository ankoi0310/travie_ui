package vn.edu.hcmuaf.fit.travie.user.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.core.service.LocalStorage;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private LocalStorage localStorage;

    AuthManager authManager;

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

        authManager = new AuthManager(requireContext());
        if (!authManager.isLoggedIn()) {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

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