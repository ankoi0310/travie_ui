package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.invoice.data.service.InvoiceService;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InvoiceViewModelFactory implements ViewModelProvider.Factory {
    private final InvoiceService invoiceService;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InvoiceViewModel.class)) {
            return (T) new InvoiceViewModel(invoiceService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
