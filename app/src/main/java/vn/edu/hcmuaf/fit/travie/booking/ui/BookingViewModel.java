package vn.edu.hcmuaf.fit.travie.booking.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeResult;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.invoice.data.service.InvoiceService;

public class BookingViewModel extends ViewModel {
    private final MutableLiveData<ChooseTimeResult> chooseTimeResult = new MutableLiveData<>();

    private final MutableLiveData<BookingRequest> bookingRequest = new MutableLiveData<>(new BookingRequest());
    private final MutableLiveData<BookingResult> bookingResult = new MutableLiveData<>();

    private final InvoiceService invoiceService;

    public BookingViewModel(Context context) {
        this.invoiceService = RetrofitService.createService(context, InvoiceService.class);
    }

    public void setChooseTimeResult(ChooseTimeResult result) {
        chooseTimeResult.setValue(result);
    }

    public LiveData<ChooseTimeResult> getChooseTimeResult() {
        return chooseTimeResult;
    }

    public void setBookingRequest(BookingRequest request) {
        bookingRequest.setValue(request);
    }

    public LiveData<BookingRequest> getBookingRequest() {
        if (bookingRequest.getValue() == null) {
            bookingRequest.setValue(new BookingRequest());
        }

        return bookingRequest;
    }

    public LiveData<BookingResult> getBookingResult() {
        return bookingResult;
    }

    public void checkout() {

    }
}
