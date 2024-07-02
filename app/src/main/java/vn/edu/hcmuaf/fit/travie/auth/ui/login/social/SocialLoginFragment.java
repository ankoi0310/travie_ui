package vn.edu.hcmuaf.fit.travie.auth.ui.login.social;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.FACEBOOK_PERMISSIONS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.LoginManager;

import vn.edu.hcmuaf.fit.travie.databinding.FragmentSocialLoginBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SocialLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SocialLoginFragment extends Fragment {
    FragmentSocialLoginBinding binding;

    public SocialLoginFragment() {
        // Required empty public constructor
    }

    public static SocialLoginFragment newInstance() {
        SocialLoginFragment fragment = new SocialLoginFragment();
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
        binding = FragmentSocialLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnFacebook.setOnClickListener(v -> LoginManager.getInstance().logInWithReadPermissions(requireActivity(), FACEBOOK_PERMISSIONS));
    }
}