package vn.edu.hcmuaf.fit.travie.home.ui.banner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderBannerBinding;
import vn.edu.hcmuaf.fit.travie.home.data.model.Banner;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.BannerViewHolder> {
    private Context context;
    private final List<Banner> items;

    public BannerAdapter(List<Banner> items) {
        this.items = items;
    }

    @Override
    public BannerViewHolder onCreateViewHolder(ViewGroup parent) {
        context = parent.getContext();
        ViewHolderBannerBinding binding = ViewHolderBannerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BannerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BannerViewHolder viewHolder, final int position) {
        Banner banner = items.get(position);

        viewHolder.textViewDescription.setText(banner.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView).load(banner.getImage()).fitCenter().into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner.getUrl()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public static class BannerViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public BannerViewHolder(ViewHolderBannerBinding binding) {
            super(binding.getRoot());
            imageViewBackground = binding.ivAutoImageSlider;
            imageGifContainer = binding.ivGifContainer;
            textViewDescription = binding.tvAutoImageSlider;
        }
    }

}
