package vn.edu.hcmuaf.fit.travie;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView stepTitle;
    private LinearLayout step1Layout, step2Layout, step3Layout;
    private Button btnPrevious, btnNext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        Spinner spinner = findViewById(R.id.tinh_tp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tinh_tp, android.R.layout.simple_spinner_item);
        //
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0, false); // Không cho phép hint được chọn


        //
        stepTitle = findViewById(R.id.step_title);

        // Initialize views
        step1Layout = findViewById(R.id.step1Layout);
        step2Layout = findViewById(R.id.step2Layout);
        step3Layout = findViewById(R.id.step3Layout);
        btnPrevious = findViewById(R.id.btnPrevious);
        btnNext = findViewById(R.id.btnNext);
    }

    // Method to handle Previous button click
    public void onPreviousClicked(View view) {
        if (step3Layout.getVisibility() == View.VISIBLE) {
            step3Layout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.VISIBLE);
            btnNext.setText("Next");
            stepTitle.setText("2/3");
        } else if (step2Layout.getVisibility() == View.VISIBLE) {
            step2Layout.setVisibility(View.GONE);
            step1Layout.setVisibility(View.VISIBLE);
            btnPrevious.setVisibility(View.GONE);
            stepTitle.setText("1/3");
        }
    }

    // Method to handle Next button click
    public void onNextClicked(View view) {
        if (step1Layout.getVisibility() == View.VISIBLE) {
            step1Layout.setVisibility(View.GONE);
            step2Layout.setVisibility(View.VISIBLE);
            stepTitle.setText("2/3");
            btnPrevious.setVisibility(View.VISIBLE);
        } else if (step2Layout.getVisibility() == View.VISIBLE) {
            step2Layout.setVisibility(View.GONE);
            step3Layout.setVisibility(View.VISIBLE);
            stepTitle.setText("3/3");
            btnNext.setText("Submit");
        } else {
            // Here you can submit the form or perform any other action
            // You can retrieve the input values from EditText fields and perform necessary operations
            // For example:
            // EditText field1_step1 = findViewById(R.id.field1_step1);
            // String value = field1_step1.getText().toString();
            // Perform your submission or other action here
        }



    }
}
