package vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import java.util.List;

import vn.edu.hcmuaf.fit.travie.booking.ui.checkout.CheckoutActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderRoomBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Amenity;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;
import vn.edu.hcmuaf.fit.travie.room.ui.detail.RoomDetailActivity;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    ViewHolderRoomBinding binding;
    Context context;

    private List<Room> rooms = new ArrayList<>();

    @SuppressLint("NotifyDataSetChanged")
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderRoomBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Room room = rooms.get(position);
        holder.roomTxt.setText(room.getName());
        holder.amenitiesTxt.setText(room.getAmenities().stream().map(Amenity::getName).reduce((a, b) -> a + " - " + b).orElse(""));

        Glide.with(context)
                .load(room.getImages().get(0))
                .into(holder.image);

        holder.bookBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, CheckoutActivity.class);
            intent.putExtra("room", (Parcelable) room);
            context.startActivity(intent);
        });

        holder.detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("room", room);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView roomTxt, amenitiesTxt;
        ImageView image;
        Button bookBtn;
        View detailBtn;

        public ViewHolder(ViewHolderRoomBinding binding) {
            super(binding.getRoot());
            roomTxt = binding.roomTxt;
            amenitiesTxt = binding.amenitiesTxt;
            image = binding.image;
            bookBtn = binding.bookBtn;
            detailBtn = binding.detailBtn;
        }
    }
}
