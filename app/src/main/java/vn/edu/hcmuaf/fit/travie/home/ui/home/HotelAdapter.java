package vn.edu.hcmuaf.fit.travie.home.ui.home;

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
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderHotelBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.hoteldetail.HotelDetailActivity;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

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
        Hotel hotel = hotels.get(position);
        holder.nameTxt.setText(hotel.getName());
        holder.firstHoursTxt.setText(String.format(Locale.getDefault(), "%s giờ đầu", hotel.getFirstHours()));
        holder.averageMarkTxt.setText(String.valueOf(hotel.getAverageMark()));
        holder.reviewCountTxt.setText(String.format(Locale.getDefault(), "(%d)", hotel.getReviews().size()));

        if (hotel.getRooms() != null) {
            int minPrice = hotel.getRooms().stream().mapToInt(Room::getFirstHoursOrigin).min().orElse(0);
            holder.discountPriceTxt.setText(AppUtil.formatCurrency(minPrice));
        }

        Glide.with(context)
                .load(hotel.getImages().get(0))
                .into(holder.pic);

        holder.hotelCardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetailActivity.class);
            intent.putExtra("hotelId", hotel.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        View hotelCardView;
        TextView nameTxt, firstHoursTxt, discountPriceTxt, averageMarkTxt, reviewCountTxt;
        ImageView pic;


        public HotelViewHolder(ViewHolderHotelBinding binding) {
            super(binding.getRoot());
            nameTxt = binding.nameTxt;
            pic = binding.pic;
            hotelCardView = binding.hotelCardView;
            firstHoursTxt = binding.firstHoursTxt;
            discountPriceTxt = binding.discountPriceTxt;
            averageMarkTxt = binding.averageMarkTxt;
            reviewCountTxt = binding.reviewCountTxt;
        }
    }
}
