package vn.edu.hcmuaf.fit.travie.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import vn.edu.hcmuaf.fit.travie.Home.MainActivity;
import vn.edu.hcmuaf.fit.travie.R;

public class FragmentHeadSearch extends Fragment {
    ImageButton imageButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_head_search, container, false);

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