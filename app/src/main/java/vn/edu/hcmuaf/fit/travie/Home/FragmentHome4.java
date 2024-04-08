package vn.edu.hcmuaf.fit.travie.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.nearestHotel.NearestHotel;
import vn.edu.hcmuaf.fit.travie.newHotel.NewHotel;

public class FragmentHome4 extends Fragment {
    TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home4, container, false);

        textView = view.findViewById(R.id.seeAll);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the desired activity or fragment
                Intent intent = new Intent(getActivity(), NewHotel.class);
                startActivity(intent);
            }
        });

        return view;
    }
}