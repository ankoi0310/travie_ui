package vn.edu.hcmuaf.fit.travie.core.common.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthService;
import vn.edu.hcmuaf.fit.travie.auth.viewmodel.AuthViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.checkout.CheckoutFailActivity;
import vn.edu.hcmuaf.fit.travie.booking.ui.checkout.CheckoutSuccessActivity;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

public class BaseActivity extends AppCompatActivity {
    private CallbackManager callbackManager;

    AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Locale.setDefault(new Locale("vi", "VN"));

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(BaseActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull FacebookException exception) {
                        Toast.makeText(BaseActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

    private void handleFacebookAccessToken(AccessToken token) {
        AuthService authService = RetrofitService.createPublicService(AuthService.class);

    }
}