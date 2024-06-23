package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import vn.edu.hcmuaf.fit.travie.R;

public class ChooseTimeBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public ChooseTimeBottomSheetDialogFragment() {
        // Required empty public constructor
    }

    public static ChooseTimeBottomSheetDialogFragment newInstance() {
        return new ChooseTimeBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_time_bottom_sheet, container, false);

        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) view.getParent());
        behavior.setPeekHeight(0);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setExpandedOffset(0);

        return view;
    }
}
