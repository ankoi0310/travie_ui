package vn.edu.hcmuaf.fit.travie;

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

import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityInvoiceDetailBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.LinkTextItem;

public class InvoiceDetailActivity extends BaseActivity {
    private final List<LinkTextItem> linkTextItems = new ArrayList<LinkTextItem>() {{
        add(new LinkTextItem("Dieu khoan va Chinh sach", InvoiceDetailActivity.class));
        add(new LinkTextItem("Lien he ngay", InvoiceDetailActivity.class));
    }};

    ActivityInvoiceDetailBinding binding;

    private EditText cancelPolicyTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cancelPolicyTxt = findViewById(R.id.cancelPolicyTxt);

        initClickableText();
    }

    private void initClickableText() {
        String fullText = "Khong the huy voi phong Flash Sale.\n\n" +
                "Toi dong y voi Dieu khoan va Chinh sach dat phong.\n\n" +
                "Dich vu ho tro khach hang - Lien he ngay";

        SpannableString spannableString = new SpannableString(fullText);

        for (LinkTextItem item : linkTextItems) {
            int startIndex = fullText.indexOf(item.getText());
            int endIndex = startIndex + item.getText().length();

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(InvoiceDetailActivity.this, item.getActivity());
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull android.text.TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getColor(R.color.text_primary));
                    ds.setUnderlineText(true);
                }
            };

            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        cancelPolicyTxt.setText(spannableString);
        cancelPolicyTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }
}