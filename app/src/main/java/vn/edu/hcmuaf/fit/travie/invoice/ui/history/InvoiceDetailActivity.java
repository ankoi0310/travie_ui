package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityInvoiceDetailBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.LinkTextItem;

public class InvoiceDetailActivity extends BaseActivity {

    ActivityInvoiceDetailBinding binding;

    private EditText cancelPolicyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}