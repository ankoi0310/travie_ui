package vn.edu.hcmuaf.fit.travie.home.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderHotelBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

public class NearByHotelAdapter extends RecyclerView.Adapter<NearByHotelAdapter.NearByHotelViewHolder> {
    private List<Hotel> items;
    public NearByHotelAdapter(List<Hotel> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public NearByHotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderHotelBinding binding = ViewHolderHotelBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NearByHotelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NearByHotelViewHolder holder, int position) {
        holder.binding.nameTxt.setText(items.get(position).getName());

        Glide.with(holder.binding.getRoot().getContext())
                .load(items.get(position).getImages().get(0))
                .into(holder.binding.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateData(List<Hotel> items) {
        this.items = items;

        notifyDataSetChanged();
    }

    public static class NearByHotelViewHolder extends RecyclerView.ViewHolder {
        ViewHolderHotelBinding binding;

        public NearByHotelViewHolder(ViewHolderHotelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
