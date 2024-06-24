package vn.edu.hcmuaf.fit.travie.booking.data.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.model.LinkCreationResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;

public interface BookingService {
    @POST("booking")
    Call<HttpResponse<LinkCreationResponse>> booking(@Body BookingRequest bookingRequest);
}
