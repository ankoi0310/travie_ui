package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentHistoryBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.service.InvoiceService;
import vn.edu.hcmuaf.fit.travie.invoice.ui.history.adapter.InvoiceAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;

    InvoiceService invoiceService;
    InvoiceViewModel viewModel;

    private InvoiceAdapter invoiceAdapter;

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

        invoiceService = RetrofitService.createService(requireContext(), InvoiceService.class);

        invoiceAdapter = new InvoiceAdapter();
        binding.invoiceRv.setAdapter(invoiceAdapter);
        binding.invoiceRv.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.invoiceRv.addItemDecoration(new SpaceItemDecoration(32, LinearLayoutManager.VERTICAL));

        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this, new InvoiceViewModelFactory(invoiceService)).get(InvoiceViewModel.class);

        // Fetch invoices
        viewModel.fetchInvoices();

        // Observe invoices
        viewModel.getInvoices().observe(getViewLifecycleOwner(), invoiceResult -> {
            if (invoiceResult == null) {
                return;
            }

            // loadingFragment.hide();
            if (invoiceResult.getError() != null) {
                Log.e("HistoryFragment", "Error: " + invoiceResult.getError());
                AppUtil.showSnackbar(binding.getRoot(), invoiceResult.getError());
            }

            if (invoiceResult.getSuccess() != null) {
                invoiceAdapter.setInvoices(invoiceResult.getSuccess());
            }
        });
    }
}