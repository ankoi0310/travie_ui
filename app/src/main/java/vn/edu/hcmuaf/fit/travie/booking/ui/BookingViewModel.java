package vn.edu.hcmuaf.fit.travie.booking.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.model.LinkCreationResponse;
import vn.edu.hcmuaf.fit.travie.booking.data.service.BookingRequestHolder;
import vn.edu.hcmuaf.fit.travie.booking.data.service.BookingService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

public class BookingViewModel extends ViewModel {
    private final MutableLiveData<BookingResult> bookingResult = new MutableLiveData<>();
    private final MutableLiveData<CheckoutResult> checkoutResult = new MutableLiveData<>();

    private final BookingService bookingService;

    public BookingViewModel(Context context) {
        this.bookingService = RetrofitService.createPrivateService(context, BookingService.class);
    }

    public LiveData<BookingResult> getBookingResult() {
        return bookingResult;
    }

    public LiveData<CheckoutResult> getCheckoutResult() {
        return checkoutResult;
    }

    public void checkout() {
        BookingRequest bookingRequest = BookingRequestHolder.getInstance().getBookingRequest();
        bookingService.booking(bookingRequest).enqueue(new retrofit2.Callback<HttpResponse<LinkCreationResponse>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<LinkCreationResponse>> call, @NonNull retrofit2.Response<HttpResponse<LinkCreationResponse>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        bookingResult.postValue(new BookingResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        bookingResult.postValue(new BookingResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<LinkCreationResponse> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        bookingResult.postValue(new BookingResult(null, httpResponse.getMessage()));
                        return;
                    }

                    bookingResult.postValue(new BookingResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<LinkCreationResponse>> call, @NonNull Throwable t) {
                bookingResult.postValue(new BookingResult(null, t.getMessage()));
            }
        });
    }

    public void completeCheckout(int code) {
        bookingService.completeCheckout(code).enqueue(new retrofit2.Callback<HttpResponse<Invoice>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<Invoice>> call, @NonNull retrofit2.Response<HttpResponse<Invoice>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        checkoutResult.postValue(new CheckoutResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        checkoutResult.postValue(new CheckoutResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<Invoice> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        checkoutResult.postValue(new CheckoutResult(null, httpResponse.getMessage()));
                        return;
                    }

                    checkoutResult.postValue(new CheckoutResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<Invoice>> call, @NonNull Throwable t) {
                bookingResult.postValue(new BookingResult(null, t.getMessage()));
            }
        });
    }

    public void cancelBooking(int code) {
        bookingService.cancelCheckout(code).enqueue(new retrofit2.Callback<HttpResponse<Invoice>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<Invoice>> call, @NonNull retrofit2.Response<HttpResponse<Invoice>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        checkoutResult.postValue(new CheckoutResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        checkoutResult.postValue(new CheckoutResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<Invoice> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        checkoutResult.postValue(new CheckoutResult(null, httpResponse.getMessage()));
                        return;
                    }

                    checkoutResult.postValue(new CheckoutResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<Invoice>> call, @NonNull Throwable t) {
                checkoutResult.postValue(new CheckoutResult(null, t.getMessage()));
            }
        });
    }
}