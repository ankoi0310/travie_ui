package vn.edu.hcmuaf.fit.travie.user.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.service.UserService;
import vn.edu.hcmuaf.fit.travie.user.adapter.ProfileMenuAdapter;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    private UserService userService;
    private UserProfile userProfile;
    private ActivityResultLauncher<Intent> activityResultLauncher;

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

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            userProfile = intent.getParcelableExtra("userProfile", UserProfile.class);
                            if (userProfile != null) {
                                setProfile();
                            }
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userService = RetrofitService.createPrivateService(requireContext(), UserService.class);

        getUserProfile();

        // Create LayoutManager
        binding.recyclerViewProfileMenu.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    private void getUserProfile() {
        userService.getProfile().enqueue(new Callback<HttpResponse<UserProfile>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<UserProfile>> call, @NonNull Response<HttpResponse<UserProfile>> response) {
                if (!response.isSuccessful()) {
                    Log.e("UserProfile", response.message());
                    return;
                }

                HttpResponse<UserProfile> httpResponse = response.body();
                if (httpResponse == null || !httpResponse.isSuccess() || httpResponse.getData() == null) {
                    Log.e("UserProfile", "Failed to get user profile");
                    return;
                }

                userProfile = httpResponse.getData();
                setProfile();
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<UserProfile>> call, @NonNull Throwable t) {
                Log.e("UserProfile", Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    private void setProfile() {
        binding.fullNameTxt.setText(userProfile.getFullName());
        binding.usernameTxt.setText(userProfile.getUsername());

        Glide.with(requireContext()).load(userProfile.getAvatar()).into(binding.avatar);

        ProfileMenuAdapter adapter = new ProfileMenuAdapter(userProfile, activityResultLauncher);
        binding.recyclerViewProfileMenu.setAdapter(adapter);
    }
}
