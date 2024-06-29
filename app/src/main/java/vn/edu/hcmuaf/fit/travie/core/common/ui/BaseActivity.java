package vn.edu.hcmuaf.fit.travie.core.common.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Optional;

import vn.edu.hcmuaf.fit.travie.booking.ui.checkout.CheckoutFailActivity;
import vn.edu.hcmuaf.fit.travie.booking.ui.checkout.CheckoutSuccessActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Locale.setDefault(new Locale("vi", "VN"));
    }

    @Override
    protected void onNewIntent(@NonNull Intent intent) {
        super.onNewIntent(intent);
        handleDeepLink(intent);
    }

    private void handleDeepLink(Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            Optional<String> path = data.getPathSegments().stream().findFirst();
            int orderCode;

            if (path.isPresent()) {
                switch (path.get()) {
                    case "checkout-cancel":
                        orderCode = Integer.parseInt(data.getQueryParameter("orderCode"));
                        Intent cancelIntent = new Intent(this, CheckoutFailActivity.class);
                        cancelIntent.putExtra("orderCode", orderCode);
                        startActivity(cancelIntent);
                        break;
                    case "checkout-success":
                        orderCode = Integer.parseInt(data.getQueryParameter("orderCode"));
                        Intent successIntent = new Intent(this, CheckoutSuccessActivity.class);
                        successIntent.putExtra("orderCode", orderCode);
                        startActivity(successIntent);
                        break;
                }
            }
        }
    }
}