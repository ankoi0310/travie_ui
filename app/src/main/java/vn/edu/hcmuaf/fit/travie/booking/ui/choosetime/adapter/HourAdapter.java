package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderHourBinding;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    ViewHolderHourBinding binding;
    Context context;
    private final List<String> hours;

    private String currentHour;

    @Setter
    private OnHourClickListener onHourClickListener;

    public HourAdapter(List<String> hours) {
        this.hours = hours;
        this.currentHour = hours.get(0);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrentHour(String currentHour) {
        this.currentHour = currentHour;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HourAdapter.HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderHourBinding.inflate(LayoutInflater.from(context), parent, false);
        return new HourViewHolder(binding, onHourClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HourAdapter.HourViewHolder holder, int position) {
        holder.hourTxt.setText(hours.get(position));

        if (hours.get(position).equals(currentHour)) {
            holder.hourTxt.setBackgroundResource(R.drawable.bg_lavender_rounded_8);
            holder.hourTxt.setTextColor(context.getColor(R.color.primary));
        } else {
            holder.hourTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
            holder.hourTxt.setTextColor(context.getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public static class HourViewHolder extends RecyclerView.ViewHolder {
        TextView hourTxt;

        public HourViewHolder(ViewHolderHourBinding binding, OnHourClickListener listener) {
            super(binding.getRoot());
            hourTxt = binding.hourTxt;

            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onHourClick(position);
                    }
                }
            });
        }
    }

    public interface OnHourClickListener {
        void onHourClick(int position);
    }
}
