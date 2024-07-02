package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.data.service.ChooseTimeByHourHandler;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderHourBinding;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    ViewHolderHourBinding binding;
    Context context;
    private final List<Integer> hours;

    private final ChooseTimeByHourHandler handler = ChooseTimeByHourHandler.getInstance();

    private int currentHour;

    @Setter
    private OnHourClickListener onHourClickListener;

    public HourAdapter(List<Integer> hours, int firstHours) {
        this.hours = hours;
        this.currentHour = firstHours;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrentHour(int currentHour) {
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
        int hour = hours.get(position);
        holder.hourTxt.setText(String.format(Locale.getDefault(), "%d" + AppConstant.HOUR_SUFFIX, hour));

        if (hour == currentHour) {
            holder.binding.getRoot().setEnabled(true);
            holder.hourTxt.setBackgroundResource(R.drawable.bg_lavender_rounded_8);
            holder.hourTxt.setTextColor(context.getColor(R.color.primary));
        } else if (handler.getMaxHours() < hour) {
            holder.binding.getRoot().setEnabled(false);
            holder.hourTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
            holder.hourTxt.setTextColor(context.getColor(R.color.text_secondary));
        } else {
            holder.binding.getRoot().setEnabled(true);
            holder.hourTxt.setBackgroundResource(R.drawable.bg_rounded_secondary);
            holder.hourTxt.setTextColor(context.getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public static class HourViewHolder extends RecyclerView.ViewHolder {
        ViewHolderHourBinding binding;
        TextView hourTxt;

        public HourViewHolder(ViewHolderHourBinding binding, OnHourClickListener listener) {
            super(binding.getRoot());
            hourTxt = binding.hourTxt;
            this.binding = binding;
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
