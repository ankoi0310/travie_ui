package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderTimeBinding;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    ViewHolderTimeBinding binding;
    Context context;
    private final List<String> times;

    private String currentTime;

    @Setter
    private OnTimeClickListener onTimeClickListener;

    public TimeAdapter(List<String> times) {
        this.times = times;
        this.currentTime = times.get(0);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setTimes(List<String> times) {
        this.times.clear();
        this.times.addAll(times);
        this.currentTime = times.get(0);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
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
        holder.timeTxt.setText(times.get(position));

        if (times.get(position).equals(currentTime)) {
            holder.timeTxt.setBackgroundResource(R.drawable.bg_lavender_rounded_8);
            holder.timeTxt.setTextColor(context.getColor(R.color.primary));
        } else {
            holder.timeTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
            holder.timeTxt.setTextColor(context.getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return times.size();
    }

    public static class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView timeTxt;

        public TimeViewHolder(ViewHolderTimeBinding binding, OnTimeClickListener listener) {
            super(binding.getRoot());
            timeTxt = binding.timeTxt;

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
