package vn.edu.hcmuaf.fit.travie.adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.core.utils.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderProfileMenuBinding;
import vn.edu.hcmuaf.fit.travie.domain.ProfileMenuItem;

public class ProfileMenuAdapter extends RecyclerView.Adapter<ProfileMenuAdapter.ProfileMenuViewHolder> {
    private final List<ProfileMenuItem> profileMenuItems = AppConstant.getProfileMenuItems();

    @NonNull
    @Override
    public ProfileMenuAdapter.ProfileMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderProfileMenuBinding binding = ViewHolderProfileMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ProfileMenuViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileMenuAdapter.ProfileMenuViewHolder holder, int position) {
        ProfileMenuItem profileMenuItem = profileMenuItems.get(position);
        holder.binding.title.setText(profileMenuItem.getTitle());
        holder.binding.icon.setImageDrawable(ContextCompat.getDrawable(holder.binding.getRoot().getContext(), profileMenuItem.getIcon()));
        holder.binding.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.binding.getRoot().getContext(), profileMenuItem.getCardBackgroundColor()));

        holder.binding.menuItem.setOnClickListener(v -> {
            // start new activity
            Intent intent = new Intent(v.getContext(), profileMenuItem.getActivity());
            v.getContext().startActivity(intent);
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
