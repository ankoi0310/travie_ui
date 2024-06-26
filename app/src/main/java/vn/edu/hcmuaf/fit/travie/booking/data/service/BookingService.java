package vn.edu.hcmuaf.fit.travie.booking.data.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.model.LinkCreationResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

public interface BookingService {
    @POST("booking")
    Call<HttpResponse<LinkCreationResponse>> booking(@Body BookingRequest bookingRequest);

    @GET("booking/cancel-checkout")
    Call<HttpResponse<Invoice>> cancelCheckout(@Query("code") int code);

    @GET("booking/complete-checkout")
    Call<HttpResponse<Invoice>> completeCheckout(@Query("code") int code);
}