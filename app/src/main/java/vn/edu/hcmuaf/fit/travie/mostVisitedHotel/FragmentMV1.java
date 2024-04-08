package vn.edu.hcmuaf.fit.travie.mostVisitedHotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import vn.edu.hcmuaf.fit.travie.Home.MainActivity;
import vn.edu.hcmuaf.fit.travie.R;

public class FragmentMV1 extends Fragment {
    ImageButton imageButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_m_v1, container, false);

        // Find the TextView
         imageButton = view.findViewById(R.id.iconButton); // Assuming your TextView has an id of textView

        // Set OnClickListener to the TextView
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the desired activity or fragment
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}