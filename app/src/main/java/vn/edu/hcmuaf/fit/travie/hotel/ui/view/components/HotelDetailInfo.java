package vn.edu.hcmuaf.fit.travie.hotel.ui.view.components;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.noties.markwon.Markwon;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHotelDetailInfoBinding;

public class HotelDetailInfo extends Fragment {
    FragmentHotelDetailInfoBinding binding;
    Context context;

    private boolean isExpanded = true;

    public HotelDetailInfo() {
        // Required empty public constructor
    }

    public static HotelDetailInfo newInstance() {
        HotelDetailInfo fragment = new HotelDetailInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHotelDetailInfoBinding.inflate(inflater, container, false);
        context = requireContext();

        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(binding.toolbar);
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle("");
        }

        binding.toolbar.setNavigationOnClickListener(v -> activity.finish());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
        binding.viewMap.viewTxt.setText(R.string.view_map);

        final Markwon markwon = Markwon.create(requireContext());
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
    }

    private void initUI() {
        binding.nestedScrollView.setOnApplyWindowInsetsListener((v, insets) -> {
            v.setFitsSystemWindows(false);
            v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(),
                    insets.getSystemWindowInsetBottom());
            return insets.consumeSystemWindowInsets();
        });

        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            isExpanded = Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() != 0;

            float percentage = Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange();
            binding.toolbar.setNavigationIconTint(AppUtil.blendColors(context.getColor(R.color.icon),
                    context.getColor(R.color.white), percentage));

            for (int i = 0; i < binding.toolbar.getMenu().size(); i++) {
                MenuItem item = binding.toolbar.getMenu().getItem(i);

                if (item.getIcon() != null) {
                    item.getIcon().setTint(AppUtil.blendColors(context.getColor(R.color.icon),
                            context.getColor(R.color.white), percentage));
                }
            }
        });
    }
}