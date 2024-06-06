package vn.edu.hcmuaf.fit.travie.home.ui.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import vn.edu.hcmuaf.fit.travie.BuildConfig;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHomeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.home.ui.adapter.NearByHotelAdapter;
import vn.edu.hcmuaf.fit.travie.hotel.data.datasource.network.HotelApi;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        SliderView sliderView = binding.imageSlider;
//
//        SliderAdapter adapter = new SliderAdapter(getContext());
//        sliderView.setSliderAdapter(adapter);
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
//        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        sliderView.setIndicatorSelectedColor(Color.WHITE);
//        sliderView.setIndicatorUnselectedColor(Color.GRAY);
//        sliderView.setScrollTimeInSec(3);
//        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//        sliderView.setAutoCycle(true);
//        sliderView.startAutoCycle();

        // set format for datetime from server
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json
                        , typeOfT, context) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), formatter))
                .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json
                        , typeOfT, context) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString()))
//                .setDateFormat("dd-MM-yyyy HH:mm:ss")
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();

        HotelApi hotelApi = retrofit.create(HotelApi.class);
        hotelApi.getHotels().enqueue(new Callback<List<Hotel>>() {
            @Override
            public void onResponse(@NonNull Call<List<Hotel>> call, @NonNull Response<List<Hotel>> response) {
                if (!response.isSuccessful()) {
                    Log.d("HomeFragment", "onResponse: " + response.code());
                    Gson gson = new Gson();
                    String json = gson.toJson(response.errorBody());
                    Log.d("HomeFragment", "onResponse: " + json);
                    return;
                }
                List<Hotel> hotels = response.body();
                NearByHotelAdapter adapter = new NearByHotelAdapter(hotels);
                binding.nearbyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.nearbyRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Hotel>> call, @NonNull Throwable throwable) {
                Log.d("HomeFragment", "onFailure: " + throwable.getMessage());
            }
        });
    }
}