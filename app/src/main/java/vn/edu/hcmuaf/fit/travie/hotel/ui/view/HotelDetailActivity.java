package vn.edu.hcmuaf.fit.travie.hotel.ui.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import io.noties.markwon.Markwon;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.view.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityHotelDetailBinding;

public class HotelDetailActivity extends BaseActivity {
    ActivityHotelDetailBinding binding;

    private AppBarLayout appBarLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewMap.viewTxt.setText(R.string.view_map);

        final Markwon markwon = Markwon.create(this);
        String introductionText = "Emphasis, aka italics, with *asterisks* or _underscores_.\n\n" +
                "Strong emphasis, aka bold, with **asterisks** or __underscores__.\n\n" +
                "Combined emphasis with **asterisks and _underscores_**.\n\n" +
                "Strikethrough uses two tildes. ~Scratch this.~";
        markwon.setMarkdown(binding.introductionTxt, introductionText);

//        BookingTypeAdapter bookingTypeAdapter = new BookingTypeAdapter(BookingType.listDemo());
//        binding.bookingTypeRv.setLayoutManager(new LinearLayoutManager(this));
//        binding.bookingTypeRv.setAdapter(bookingTypeAdapter);

        String text = "Đặt phòng **theo giờ**: Huỷ miễn phí trước giờ nhận phòng **1 tiếng**.\n\n" +
                "Đặt phòng **qua đêm**: Huỷ miễn phí trước giờ nhận phòng **2 tiếng**.\n\n" +
                "Đặt phòng **theo ngày**: Huỷ miễn phí trước giờ nhận phòng **12 tiếng**.\n\n" +
                "Lưu ý:\n\n" +
                "- Có thể được huỷ miễn phí tong vòng **5 phút** kể từ thời điểm đặt phòng thành " +
                "công nhưng thời điểm yêu cầu huỷ không được quá giờ nhận phòng.\n" +
                "- Không cho phép huỷ phòng với phòng Flash Sale và Ưu đãi không hoàn huỷ.";
        markwon.setMarkdown(binding.cancellationPolicyTxt, text);

        appBarLayout = findViewById(R.id.appBarLayout);
        toolbar = findViewById(R.id.toolbar);
        final int colorStart = getColor(R.color.transparent);
        final int colorEnd = getColor(R.color.white);

        appBarLayout.setOnApplyWindowInsetsListener((v, insets) -> {
            final WindowInsets systemWindowInsets = insets.consumeSystemWindowInsets();
            v.setPadding(v.getPaddingLeft(), systemWindowInsets.getSystemWindowInsetTop(), v.getPaddingRight(), v.getPaddingBottom());
            return systemWindowInsets;
        });
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}