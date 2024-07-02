package vn.edu.hcmuaf.fit.travie.user.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.ProfileMenu;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderProfileMenuBinding;
import vn.edu.hcmuaf.fit.travie.user.ui.changepassword.ChangePasswordActivity;
import vn.edu.hcmuaf.fit.travie.user.ui.profiledetail.ProfileDetailActivity;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.ProfileMenuViewHolder> {
    ViewHolderProfileMenuBinding binding;
    Context context;

    private final List<ProfileMenu> profileMenuItems = ProfileMenu.getProfileMenuItems();
    private AuthManager authManager;

    public ProfileMenuAdapter() {
    }

    @NonNull
    @Override
    public ProfileMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        authManager = new AuthManager(context);
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
                    context.startActivity(intentProfileDetail);
                    break;
                case CHANGE_PASSWORD:
                    Intent intentChangePassword = new Intent(context, ChangePasswordActivity.class);
                    context.startActivity(intentChangePassword);
                    break;
                case HELP:
                    break;
                case LOGOUT:
                    showLogoutAlert();
                    break;
            }
        });
    }

    private void showLogoutAlert() {
        // Show alert dialog to confirm logout first
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
        builder.setPositiveButton("Đăng xuất", (dialog, which) -> {
            dialog.dismiss();
            logout();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void logout() {
        authManager.clearLoggedInUser();
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
