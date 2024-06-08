package vn.edu.hcmuaf.fit.travie.home.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.databinding.ImageSliderLayoutItemBinding;
import vn.edu.hcmuaf.fit.travie.hotel.model.SliderItem;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    private final Context context;
    private final List<SliderItem> mSliderItems = new ArrayList<SliderItem>() {{
        add(new SliderItem("Hãy đến với Travie", "https://i.imgur.com/1w1Q1Zv.jpg"));
        add(new SliderItem("Khám phá thế giới", "https://i.imgur.com/1w1Q1Zv.jpg"));
        add(new SliderItem("Trải nghiệm tuyệt vời", "https://i.imgur.com/1w1Q1Zv.jpg"));
        add(new SliderItem("Hãy đến với Travie", "https://i.imgur.com/1w1Q1Zv.jpg"));
        add(new SliderItem("Khám phá thế giới", "https://i.imgur.com/1w1Q1Zv.jpg"));
        add(new SliderItem("Trải nghiệm tuyệt vời", "https://i.imgur.com/1w1Q1Zv.jpg"));
    }};

    public SliderAdapter(Context context) {
        this.context = context;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        ImageSliderLayoutItemBinding binding = ImageSliderLayoutItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new SliderAdapterVH(binding);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        SliderItem sliderItem = mSliderItems.get(position);

        viewHolder.textViewDescription.setText(sliderItem.getDescription());
        viewHolder.textViewDescription.setTextSize(16);
        viewHolder.textViewDescription.setTextColor(Color.WHITE);
        Glide.with(viewHolder.itemView).load(sliderItem.getImageUrl()).fitCenter().into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(v -> Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getCount() {
        return mSliderItems.size();
    }

    public static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
        ImageSliderLayoutItemBinding binding;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(ImageSliderLayoutItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            imageViewBackground = binding.ivAutoImageSlider;
            imageGifContainer = binding.ivGifContainer;
            textViewDescription = binding.tvAutoImageSlider;
        }
    }

}
