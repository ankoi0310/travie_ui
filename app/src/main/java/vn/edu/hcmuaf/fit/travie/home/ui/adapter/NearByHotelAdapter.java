package vn.edu.hcmuaf.fit.travie.home.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderHotelBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

public class NearByHotelAdapter extends RecyclerView.Adapter<NearByHotelAdapter.NearByHotelViewHolder> {
    private final List<Hotel> items;
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
        Log.d("TAG", "onBindViewHolder: " + items.get(position).getName());
        holder.binding.nameTxt.setText(items.get(position).getName());

        Glide.with(holder.itemView.getContext())
                .load(items.get(position).getImages().get(0))
                .into(holder.binding.pic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class NearByHotelViewHolder extends RecyclerView.ViewHolder {
        ViewHolderHotelBinding binding;

        public NearByHotelViewHolder(ViewHolderHotelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
