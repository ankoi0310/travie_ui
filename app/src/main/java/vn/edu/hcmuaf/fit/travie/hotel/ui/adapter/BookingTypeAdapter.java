package vn.edu.hcmuaf.fit.travie.hotel.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderBookingTypeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

public class BookingTypeAdapter extends RecyclerView.Adapter<BookingTypeAdapter.BookingTypeViewHolder> {
    Context context;
    private final List<BookingType> bookingTypes;

    public BookingTypeAdapter(List<BookingType> bookingTypes) {
        this.bookingTypes = bookingTypes;
    }

    @NonNull
    @Override
    public BookingTypeAdapter.BookingTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewHolderBookingTypeBinding binding = ViewHolderBookingTypeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BookingTypeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingTypeAdapter.BookingTypeViewHolder holder, int position) {
        BookingType bookingType = bookingTypes.get(position);
        holder.binding.nameTxt.setText(bookingType.getName());
        holder.binding.timeTxt.setText(String.format("Từ %s đến %s", bookingType.getStartTime(),
                bookingType.getEndTime()));
    }

    @Override
    public int getItemCount() {
        return bookingTypes.size();
    }

    public static class BookingTypeViewHolder extends RecyclerView.ViewHolder {
        ViewHolderBookingTypeBinding binding;

        public BookingTypeViewHolder(ViewHolderBookingTypeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
