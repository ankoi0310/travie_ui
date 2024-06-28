package vn.edu.hcmuaf.fit.travie.hotel.ui.explore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderExploreHotelBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.hoteldetail.HotelDetailActivity;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

public class ExploreHotelAdapter extends RecyclerView.Adapter<ExploreHotelAdapter.ViewHolder> {
    ViewHolderExploreHotelBinding binding;
    Context context;
    ArrayList<Hotel> hotels;

    public ExploreHotelAdapter(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    @NonNull
    @Override
    public ExploreHotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderExploreHotelBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreHotelAdapter.ViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.hotelTxt.setText(hotel.getName());
        holder.averageMarkTxt.setText(String.valueOf(hotel.getAverageMark()));
        holder.reviewCountTxt.setText(String.format(Locale.getDefault(), "(%d)",
                hotel.getReviews().size()));

        assert hotel.getRooms() != null;
        int minPrice = hotel.getRooms().stream().mapToInt(Room::getFirstHoursOrigin).min().orElse(0);
        holder.priceTxt.setText(AppUtil.formatCurrency(minPrice));

        Glide.with(context)
                .load(hotel.getImages().get(0))
                .into(holder.hotelImg);

        holder.hotelContainer.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetailActivity.class);
            intent.putExtra("hotelId", hotel.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addHotels(ArrayList<Hotel> hotels) {
        this.hotels.addAll(hotels);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear() {
        hotels.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View hotelContainer;
        TextView hotelTxt, averageMarkTxt, reviewCountTxt, priceTxt;
        ImageView hotelImg;

        public ViewHolder(@NonNull ViewHolderExploreHotelBinding binding) {
            super(binding.getRoot());
            hotelContainer = binding.hotelContainer;
            hotelTxt = binding.hotelTxt;
            averageMarkTxt = binding.averageMarkTxt;
            reviewCountTxt = binding.reviewCountTxt;
            priceTxt = binding.priceTxt;
            hotelImg = binding.hotelImg;
        }
    }
}
