package vn.edu.hcmuaf.fit.travie.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import vn.edu.hcmuaf.fit.travie.Home.MainActivity;
import vn.edu.hcmuaf.fit.travie.R;

public class FragmentBodySearch extends Fragment {
    TextView check_in;
    TextView check_out;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_body_search, container, false);

        check_in = view.findViewById(R.id.check_in);
        check_out = view.findViewById(R.id.check_out);

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        check_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Format ngày đã chọn
                                String selectedDate = String.format("%d/%d/%d", dayOfMonth, month + 1, year);

                                // Cập nhật văn bản trong TextView "check_in"
                                check_in.setText(selectedDate);
                            }
                        }, year, month, day);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });

        check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Format ngày đã chọn
                                String selectedDate = String.format("%d/%d/%d", dayOfMonth, month + 1, year);

                                // Cập nhật văn bản trong TextView "check_in"
                                check_out.setText(selectedDate);
                            }
                        }, year, month, day);

                // Hiển thị DatePickerDialog
                datePickerDialog.show();
            }
        });

        return view;
    }
}