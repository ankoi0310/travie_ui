package vn.edu.hcmuaf.fit.travie.user.adapter;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.INTENT_USER_PROFILE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.auth.activity.LoginActivity;
import vn.edu.hcmuaf.fit.travie.core.service.TokenManager;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.ProfileMenu;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderProfileMenuBinding;
import vn.edu.hcmuaf.fit.travie.user.ui.changepassword.ChangePasswordActivity;
import vn.edu.hcmuaf.fit.travie.user.activity.ProfileDetailActivity;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.ProfileMenuViewHolder> {
    ViewHolderProfileMenuBinding binding;
    Context context;

    UserProfile userProfile;
    private final List<ProfileMenu> profileMenuItems = ProfileMenu.getProfileMenuItems();
    private TokenManager tokenManager;

    public ProfileMenuAdapter(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfileMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        tokenManager = new TokenManager(context);
        binding = ViewHolderProfileMenuBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProfileMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMenuViewHolder holder, int position) {
        ProfileMenu profileMenu = profileMenuItems.get(position);
        holder.binding.menuTitleTxt.setText(profileMenu.getTitle());
        holder.binding.menuIcon.setImageDrawable(ContextCompat.getDrawable(context, profileMenu.getIcon()));
        holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(context, profileMenu.getCardBackgroundColor()));

        holder.binding.menuItem.setOnClickListener(v -> {
            switch (profileMenu) {
                case PROFILE_DETAIL:
                    Intent intentProfileDetail = new Intent(context, ProfileDetailActivity.class);
                    intentProfileDetail.putExtra(INTENT_USER_PROFILE, userProfile);
                    context.startActivity(intentProfileDetail);
                    break;
                case CHANGE_PASSWORD:
                    Intent intentChangePassword = new Intent(context, ChangePasswordActivity.class);
                    context.startActivity(intentChangePassword);
                    break;
                case PAYMENT:
                    break;
                case HELP:
                    break;
                case LOGOUT:
                    handleLogout();
                    break;
            }
        });
    }

    private void handleLogout() {
        tokenManager.clearLoggedInUser();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return profileMenuItems.size();
    }

    public static class ProfileMenuViewHolder extends RecyclerView.ViewHolder {
        ViewHolderProfileMenuBinding binding;

        public ProfileMenuViewHolder(ViewHolderProfileMenuBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
