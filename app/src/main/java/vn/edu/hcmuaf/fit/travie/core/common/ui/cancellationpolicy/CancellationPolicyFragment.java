package vn.edu.hcmuaf.fit.travie.core.common.ui.cancellationpolicy;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentCancellationPolicyBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.LinkTextItem;
import vn.edu.hcmuaf.fit.travie.invoice.ui.invoicedetail.InvoiceDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CancellationPolicyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancellationPolicyFragment extends Fragment {
    private final List<LinkTextItem> linkTextItems = new ArrayList<LinkTextItem>() {{
        add(new LinkTextItem("Điều khoản và Chính sách", InvoiceDetailActivity.class));
        add(new LinkTextItem("Liên hệ ngay", InvoiceDetailActivity.class));
    }};

    FragmentCancellationPolicyBinding binding;
    TextView cancelPolicyTxt;

    public CancellationPolicyFragment() {
        // Required empty public constructor
    }

    public static CancellationPolicyFragment newInstance() {
        CancellationPolicyFragment fragment = new CancellationPolicyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCancellationPolicyBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelPolicyTxt = binding.cancelPolicyTxt;

        initClickableText();
    }

    private void initClickableText() {
        String htmlText = "<p>Không thể huỷ với phòng Flash Sale.</p>" +
                "<p>Tôi đồng ý với Điều khoản và Chính sách đặt phòng.</p>" +
                "<p>Dịch vụ hỗ trợ khách hàng - Liên hệ ngay</p>";

        SpannableString spannableString = new SpannableString(HtmlCompat.fromHtml(htmlText, HtmlCompat.FROM_HTML_MODE_LEGACY));
        String plainText = spannableString.toString();

        for (LinkTextItem item : linkTextItems) {
            int startIndex = plainText.indexOf(item.getText());
            int endIndex = startIndex + item.getText().length();

            if (startIndex == -1 || endIndex > plainText.length()) {
                continue;
            }

            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(requireActivity(), item.getActivity());
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull android.text.TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(requireActivity().getColor(R.color.text_primary));
                    ds.setUnderlineText(true);
                }
            };

            spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        cancelPolicyTxt.setText(spannableString);
        cancelPolicyTxt.setMovementMethod(LinkMovementMethod.getInstance());
    }
}