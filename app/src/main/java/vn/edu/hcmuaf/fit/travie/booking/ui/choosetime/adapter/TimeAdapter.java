package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderTimeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    ViewHolderTimeBinding binding;
    Context context;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    private final List<LocalTime> times;
    private LocalTime currentTime;
    private final int firstHours;
    private final BookingType bookingType;
    private boolean isToday = true;

    @Setter
    private OnTimeClickListener onTimeClickListener;

    public TimeAdapter(List<LocalTime> times, int firstHours, BookingType bookingType) {
        this.times = times;
        this.firstHours = firstHours;
        LocalTime now = LocalTime.now();
        if (now.isBefore(bookingType.getStartTime())) {
            this.currentTime = times.get(0);
        } else {
            times.stream().filter(time -> time.isAfter(now)).findFirst().ifPresentOrElse(
                    time -> this.currentTime = time,
                    () -> this.currentTime = times.get(0)
            );
        }
        this.bookingType = bookingType;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrentTime(LocalTime currentTime, boolean isToday) {
        this.currentTime = currentTime;
        this.isToday = isToday;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimeAdapter.TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderTimeBinding.inflate(LayoutInflater.from(context), parent, false);
        return new TimeViewHolder(binding, onTimeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAdapter.TimeViewHolder holder, int position) {
        LocalTime time = times.get(position);
        holder.timeTxt.setText(times.get(position).format(formatter));

        if (isToday) {
            if (time.equals(currentTime)) {
                holder.binding.getRoot().setEnabled(true);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_lavender_rounded_8);
                holder.timeTxt.setTextColor(context.getColor(R.color.primary));
            } else if (time.isBefore(LocalTime.now())) {
                holder.binding.getRoot().setEnabled(false);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
                holder.timeTxt.setTextColor(context.getColor(R.color.text_secondary));
            } else if (time.plusHours(firstHours).isAfter(bookingType.getEndTime())) {
                holder.binding.getRoot().setEnabled(false);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
                holder.timeTxt.setTextColor(context.getColor(R.color.text_secondary));
            } else {
                holder.binding.getRoot().setEnabled(true);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
                holder.timeTxt.setTextColor(context.getColor(R.color.black));
            }
        } else {
            if (time.equals(currentTime)) {
                holder.binding.getRoot().setEnabled(true);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_lavender_rounded_8);
                holder.timeTxt.setTextColor(context.getColor(R.color.primary));
            } else if (time.equals(bookingType.getStartTime()) && currentTime.equals(bookingType.getStartTime())) {
                holder.binding.getRoot().setEnabled(true);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_lavender_rounded_8);
                holder.timeTxt.setTextColor(context.getColor(R.color.primary));
            } else if (time.isAfter(bookingType.getEndTime())) {
                holder.binding.getRoot().setEnabled(false);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
                holder.timeTxt.setTextColor(context.getColor(R.color.text_secondary));
            } else {
                holder.binding.getRoot().setEnabled(true);
                holder.timeTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
                holder.timeTxt.setTextColor(context.getColor(R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        ViewHolderTimeBinding binding;
        TextView timeTxt;

        public TimeViewHolder(ViewHolderTimeBinding binding, OnTimeClickListener listener) {
            super(binding.getRoot());
            timeTxt = binding.timeTxt;
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onTimeClick(position);
                    }
                }
            });
        }
    }

    public interface OnTimeClickListener {
        void onTimeClick(int position);
    }
}
