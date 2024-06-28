package vn.edu.hcmuaf.fit.travie.hotel.ui.viewall;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityViewAllHotelBinding;

public class ViewAllHotelActivity extends BaseActivity {
    ActivityViewAllHotelBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllHotelBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}