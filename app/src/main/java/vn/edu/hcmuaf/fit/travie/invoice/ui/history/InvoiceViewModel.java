package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.invoice.data.service.InvoiceService;

@Singleton
public class InvoiceViewModel extends ViewModel {
    private final MutableLiveData<InvoiceResult> invoiceResult = new MutableLiveData<>();
    private final InvoiceService invoiceService;

    @Inject
    public InvoiceViewModel(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public LiveData<InvoiceResult> getInvoices() {
        return invoiceResult;
    }

    public void fetchInvoices() {
        invoiceService.getInvoices().enqueue(new Callback<HttpResponse<List<Invoice>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<List<Invoice>>> call, @NonNull Response<HttpResponse<List<Invoice>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        invoiceResult.setValue(new InvoiceResult(httpResponse.getMessage()));
                    }

                    if (response.body() == null) {
                        invoiceResult.setValue(new InvoiceResult("Something went wrong"));
                    }

                    HttpResponse<List<Invoice>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        invoiceResult.setValue(new InvoiceResult(httpResponse.getMessage()));
                    }

                    invoiceResult.setValue(new InvoiceResult(httpResponse.getData()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<List<Invoice>>> call, @NonNull Throwable t) {
                invoiceResult.setValue(new InvoiceResult("Something went wrong"));
            }
        });
    }
}
