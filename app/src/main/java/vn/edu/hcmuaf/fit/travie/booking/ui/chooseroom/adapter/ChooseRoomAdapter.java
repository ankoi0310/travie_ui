package vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.service.BookingRequestHolder;
import vn.edu.hcmuaf.fit.travie.booking.ui.checkout.CheckoutActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.BookingUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderChooseRoomBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Amenity;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;
import vn.edu.hcmuaf.fit.travie.room.ui.detail.RoomDetailActivity;

public class ChooseRoomAdapter extends RecyclerView.Adapter<ChooseRoomAdapter.ViewHolder> {
    Context context;
    ViewHolderChooseRoomBinding binding;

    private final ArrayList<Room> rooms;

    BookingRequestHolder bookingRequestHolder = BookingRequestHolder.getInstance();

    public ChooseRoomAdapter(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderChooseRoomBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BookingRequest bookingRequest = bookingRequestHolder.getBookingRequest();

        Room room = rooms.get(position);
        holder.roomTxt.setText(room.getName());
        holder.amenitiesTxt.setText(room.getAmenities().stream().map(Amenity::getName).reduce((a, b) -> a + " - " + b).orElse(""));
        holder.roomRemainTxt.setText(String.format(Locale.getDefault(), "Còn %d phòng", room.getNumOfRooms()));

        switch (bookingRequest.getBookingType().getUnit()) {
            case HOUR:
                holder.priceTxt.setText(AppUtil.formatCurrency(room.getFirstHoursOrigin()));
                break;
            case DAY:
                holder.priceTxt.setText(AppUtil.formatCurrency(room.getOneDayOrigin()));
                break;
            case OVERNIGHT:
                holder.priceTxt.setText(AppUtil.formatCurrency(room.getOvernightOrigin()));
                break;
        }

        Glide.with(context)
                .load(room.getImages().get(0))
                .into(holder.image);

        holder.bookBtn.setOnClickListener(v -> {
            bookingRequest.setRoom(room);
            BookingUtil.calculateTotalPrice(bookingRequest);
            bookingRequestHolder.setBookingRequest(bookingRequest);

            Intent intent = new Intent(context, CheckoutActivity.class);
            context.startActivity(intent);
        });

        holder.detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetailActivity.class);
            intent.putExtra("room", room);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomTxt, amenitiesTxt, priceTxt, roomRemainTxt;
        ImageView image;
        Button bookBtn;
        View detailBtn;

        public ViewHolder(ViewHolderChooseRoomBinding binding) {
            super(binding.getRoot());
            roomTxt = binding.roomTxt;
            amenitiesTxt = binding.amenitiesTxt;
            image = binding.image;
            bookBtn = binding.bookBtn;
            detailBtn = binding.detailBtn;
            priceTxt = binding.discountPriceTxt;
            roomRemainTxt = binding.roomRemainTxt;
        }
    }
}
