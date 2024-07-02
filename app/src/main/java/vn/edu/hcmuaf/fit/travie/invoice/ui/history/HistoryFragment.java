package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHistoryBinding;
import vn.edu.hcmuaf.fit.travie.invoice.ui.adapter.InvoiceAdapter;
import vn.edu.hcmuaf.fit.travie.user.ui.UserViewModel;
import vn.edu.hcmuaf.fit.travie.user.ui.UserViewModelFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;
    View loadingView;

    UserViewModel userViewModel;

    public HistoryFragment() {
        // Required empty public constructor
    }

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
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
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity mainActivity = (MainActivity) requireActivity();
        loadingView = mainActivity.findViewById(R.id.loadingView);
        AnimationUtil.animateView(loadingView, View.VISIBLE, 0.4f, 200);

        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(requireContext())).get(UserViewModel.class);
        userViewModel.fetchBookingHistory();
        handleFetchingBookingHistory();

        binding.swipeRefreshLayout.setOnRefreshListener(() -> userViewModel.fetchBookingHistory());

        binding.invoiceRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.invoiceRv.addItemDecoration(new SpaceItemDecoration(32));
    }

    private void handleFetchingBookingHistory() {
        userViewModel.getInvoices().observe(getViewLifecycleOwner(), result -> {
            if (result.getError() != null) {
                Toast.makeText(requireContext(), result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                InvoiceAdapter invoiceAdapter = new InvoiceAdapter(result.getSuccess());
                binding.invoiceRv.setAdapter(invoiceAdapter);
            }

            if (binding.swipeRefreshLayout.isRefreshing()) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            if (loadingView.getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(loadingView, View.GONE, 0, 200);
            }
        });
    }
}