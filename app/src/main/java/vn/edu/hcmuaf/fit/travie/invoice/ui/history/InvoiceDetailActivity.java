package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import android.os.Bundle;

import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityInvoiceDetailBinding;

public class InvoiceDetailActivity extends BaseActivity {

    ActivityInvoiceDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}