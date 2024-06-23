package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.invoice.data.service.InvoiceService;

@Singleton
public class InvoiceViewModel extends ViewModel {
    private final MutableLiveData<InvoiceResult> invoice = new MutableLiveData<>();
    private final InvoiceService invoiceService;

    @Inject
    public InvoiceViewModel(Context context) {
        this.invoiceService = RetrofitService.createService(context, InvoiceService.class);
    }

    public LiveData<InvoiceResult> getInvoices() {
        return invoice;
    }

    public void fetchInvoices() {
        invoiceService.getInvoices().enqueue(new Callback<HttpResponse<ArrayList<Invoice>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<ArrayList<Invoice>>> call, @NonNull Response<HttpResponse<ArrayList<Invoice>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        invoice.postValue(new InvoiceResult(null, httpResponse.getMessage()));
                    }

                    if (response.body() == null) {
                        invoice.postValue(new InvoiceResult(null, "Something went wrong"));
                    }

                    HttpResponse<ArrayList<Invoice>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        invoice.postValue(new InvoiceResult(null, httpResponse.getMessage()));
                    }

                    invoice.postValue(new InvoiceResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<ArrayList<Invoice>>> call, @NonNull Throwable t) {
                invoice.postValue(new InvoiceResult(null, "Something went wrong"));
            }
        });
    }
}
