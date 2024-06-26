package vn.edu.hcmuaf.fit.travie.invoice.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.invoice.data.service.InvoiceService;
import vn.edu.hcmuaf.fit.travie.user.service.UserService;

public class InvoiceViewModel extends ViewModel {
    private final MutableLiveData<InvoiceListResult> invoices = new MutableLiveData<>();
    private final MutableLiveData<InvoiceResult> invoice = new MutableLiveData<>();

    UserService userService;
    InvoiceService invoiceService;

    public InvoiceViewModel(Context context) {
        this.userService = RetrofitService.createPrivateService(context, UserService.class);
        this.invoiceService = RetrofitService.createPrivateService(context, InvoiceService.class);
    }

    public LiveData<InvoiceListResult> getInvoices() {
        return invoices;
    }

    public LiveData<InvoiceResult> getInvoice() {
        return invoice;
    }

    public void fetchBookingHistory() {
        userService.getBookingHistory().enqueue(new Callback<HttpResponse<ArrayList<Invoice>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<ArrayList<Invoice>>> call, @NonNull Response<HttpResponse<ArrayList<Invoice>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        invoices.postValue(new InvoiceListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        invoices.postValue(new InvoiceListResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<ArrayList<Invoice>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        invoices.postValue(new InvoiceListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    invoices.postValue(new InvoiceListResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<ArrayList<Invoice>>> call, @NonNull Throwable t) {
                invoices.postValue(new InvoiceListResult(null, "Something went wrong"));
            }
        });
    }

    public void fetchInvoiceById(long id) {
        invoiceService.getInvoice(id).enqueue(new Callback<HttpResponse<Invoice>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<Invoice>> call, @NonNull Response<HttpResponse<Invoice>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        invoice.postValue(new InvoiceResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        invoice.postValue(new InvoiceResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<Invoice> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        invoice.postValue(new InvoiceResult(null, httpResponse.getMessage()));
                        return;
                    }

                    invoice.postValue(new InvoiceResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<Invoice>> call, @NonNull Throwable t) {
                invoice.postValue(new InvoiceResult(null, "Something went wrong"));
            }
        });
    }
}
