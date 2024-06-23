package vn.edu.hcmuaf.fit.travie.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderHotelBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    private Context context;
    private final ArrayList<Hotel> hotels;

    public HotelAdapter(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewHolderHotelBinding binding = ViewHolderHotelBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HotelViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        holder.nameTxt.setText(hotels.get(position).getName());

        Glide.with(context)
                .load(hotels.get(position).getImages().get(0))
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView nameTxt;
        ImageView pic;

        public HotelViewHolder(ViewHolderHotelBinding binding) {
            super(binding.getRoot());
            nameTxt = binding.nameTxt;
            pic = binding.pic;
        }
    }
}
