package vn.edu.hcmuaf.fit.travie.hotel.ui.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.databinding.ActivitySearchHotelBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModelFactory;
import vn.edu.hcmuaf.fit.travie.hotel.ui.explore.ExploreHotelAdapter;

public class SearchHotelActivity extends BaseActivity {
    ActivitySearchHotelBinding binding;

    HotelViewModel hotelViewModel;

    private ExploreHotelAdapter hotelAdapter;
    private int currentPage = 1;
    private boolean loadMore = true;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        hotelViewModel = new HotelViewModelFactory(this).create(HotelViewModel.class);
        handleSearchHotel();

        binding.hotelListContainer.setOnTouchListener((View v, MotionEvent event) -> {
            if (getCurrentFocus() != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                getCurrentFocus().clearFocus();
            }
            return true;
        });
        binding.searchEdt.requestFocus();

        binding.rvHotel.setLayoutManager(new LinearLayoutManager(this));
        binding.rvHotel.setHasFixedSize(true);
        binding.rvHotel.addItemDecoration(new SpaceItemDecoration(16));
        handleLoadingMore();
    }

    private void handleLoadingMore() {
        binding.rvHotel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (totalItemCount <= (lastVisibleItem + 1) && loadMore) {
                        binding.loading.setVisibility(View.VISIBLE);
                        hotelViewModel.search(Objects.requireNonNull(binding.searchEdt.getText()).toString(), ++currentPage, 5);
                    }
                }
            }
        });
    }

    private void handleSearchHotel() {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = () -> {
            String keyword = Objects.requireNonNull(binding.searchEdt.getText()).toString();
            if (keyword.isEmpty()) {
                if (hotelAdapter != null) {
                    hotelAdapter.clear();
                }

                binding.tvMessage.setText(R.string.please_enter_keyword);
                return;
            }

            currentPage = 1; // Reset page
            binding.tvMessage.setVisibility(View.GONE);
            binding.loading.setVisibility(View.VISIBLE);
            hotelViewModel.search(keyword, currentPage, 5);
        };

        binding.searchEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeCallbacks(runnable);
            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.postDelayed(runnable, 500);
            }
        });

        hotelViewModel.getSearchResult().observe(this, result -> {
            if (result.getError() != null) {
                Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                ArrayList<Hotel> hotels = result.getSuccess();

                hotelAdapter = (ExploreHotelAdapter) binding.rvHotel.getAdapter();
                if (hotelAdapter != null) {
                    if (currentPage == 1) {
                        hotelAdapter.clear();
                    }

                    if (hotels.isEmpty()) {
                        loadMore = false;
                        binding.tvMessage.setVisibility(View.VISIBLE);
                    } else {
                        loadMore = true;
                        binding.tvMessage.setVisibility(View.GONE);
                        hotelAdapter.addHotels(hotels);
                    }
                } else {
                    hotelAdapter = new ExploreHotelAdapter(hotels);
                }

                binding.tvMessage.setText(hotelAdapter.getItemCount() == 0 ? R.string.no_data_matching : R.string.data_loaded);
                binding.rvHotel.setAdapter(hotelAdapter);
            }

            if (binding.loading.getVisibility() == View.VISIBLE) {
                binding.loading.setVisibility(View.GONE);
            }
        });
    }
}