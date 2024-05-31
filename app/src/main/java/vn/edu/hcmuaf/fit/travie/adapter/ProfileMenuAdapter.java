package vn.edu.hcmuaf.fit.travie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.core.utils.enums.ProfileMenu;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderProfileMenuBinding;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.ProfileMenuViewHolder> {
    Context context;
    private final List<ProfileMenu> profileMenuItems = ProfileMenu.getProfileMenuItems();

    @NonNull
    @Override
    public ProfileMenuAdapter.ProfileMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewHolderProfileMenuBinding binding = ViewHolderProfileMenuBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ProfileMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMenuAdapter.ProfileMenuViewHolder holder, int position) {
        ProfileMenu profileMenu = profileMenuItems.get(position);
        holder.binding.menuTitleTxt.setText(profileMenu.getTitle());
        holder.binding.menuIcon.setImageDrawable(ContextCompat.getDrawable(context, profileMenu.getIcon()));
        holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(context, profileMenu.getCardBackgroundColor()));

        holder.binding.menuItem.setOnClickListener(v -> {
            switch (profileMenu) {
                case PROFILE_DETAIL:
                    Intent intent = new Intent(context, profileMenu.getActivity());
                    context.startActivity(intent);
                    break;
                case CHANGE_PASSWORD:
                    break;
                case PAYMENT:
                    break;
                case HELP:
                    break;
                case LOGOUT:
                    break;
            }
        });
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
