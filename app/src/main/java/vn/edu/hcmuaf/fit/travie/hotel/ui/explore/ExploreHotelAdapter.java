package vn.edu.hcmuaf.fit.travie.hotel.ui.explore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderExploreHotelBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.hoteldetail.HotelDetailActivity;

public class ExploreHotelAdapter extends RecyclerView.Adapter<ExploreHotelAdapter.ViewHolder> {
    ViewHolderExploreHotelBinding binding;
    Context context;
    List<Hotel> hotels = new ArrayList<>();

    @NonNull
    @Override
    public ExploreHotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderExploreHotelBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreHotelAdapter.ViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.binding.hotelTxt.setText(hotel.getName());
        holder.binding.averageMarkTxt.setText(String.valueOf(hotel.getAverageMark()));
        holder.binding.reviewCountTxt.setText(String.format(Locale.getDefault(), "(%d)",
                hotel.getReviews().size()));

        String price = AppUtil.formatCurrency(hotel.getRooms().get(0).getFirstHoursOrigin());
        holder.binding.priceTxt.setText(price);

        holder.binding.hotelContainer.setOnClickListener(v -> {
            Intent intent = new Intent(context, HotelDetailActivity.class);
            intent.putExtra("hotel", (Parcelable) hotel);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderExploreHotelBinding binding;

        public ViewHolder(@NonNull ViewHolderExploreHotelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
