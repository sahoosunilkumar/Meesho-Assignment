package com.meesho.assignment.features.pulls.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meesho.assignment.BR;
import com.meesho.assignment.R;
import com.meesho.assignment.background.network.api.Status;
import com.meesho.assignment.background.network.model.IResponse;
import com.meesho.assignment.databinding.FragmentPullRepositoryBinding;
import com.meesho.assignment.exceptions.InvalidRepositoryException;
import com.meesho.assignment.features.pulls.model.Pagination;
import com.meesho.assignment.features.pulls.model.PullRequest;
import com.meesho.assignment.features.pulls.model.PullResponse;
import com.meesho.assignment.features.pulls.repository.model.Pull;
import com.meesho.assignment.features.pulls.viewmodel.DateFormatter;
import com.meesho.assignment.features.pulls.viewmodel.PullsViewModel;
import com.meesho.uiwidget.adapter.AdapterDelegate;
import com.meesho.uiwidget.adapter.DatabindingAdapter;
import com.meesho.uiwidget.adapter.DividerItemDecorationFilter;

import java.util.Objects;

import static android.support.v7.widget.RecyclerView.EdgeEffectFactory.DIRECTION_TOP;

public class PullRepositoryFragment extends Fragment implements Observer<IResponse<PullResponse>> {

    private PullsViewModel mViewModel;
    private FragmentPullRepositoryBinding mBinding;
    private final AdapterDelegate<Pull> childAdapterDelegate = new AdapterDelegate<>(Pull.class, R.layout.item_pull_request_list, BR.pullItem);

    private DatabindingAdapter simpleAdapter;
    private DividerItemDecorationFilter dividerItemDecorationFilter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PullsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate layout, bind fields and etc
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_pull_repository,
                container,
                false);
        // bind ViewModel
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        childAdapterDelegate.addVariable(BR.formatter, mViewModel.getFormatter());
        simpleAdapter = new DatabindingAdapter(childAdapterDelegate);
        mBinding.recyclerView.setAdapter(simpleAdapter);

        mViewModel.getApiResponse().observe(this, this);
        dividerItemDecorationFilter = getDivider(mBinding.recyclerView.getContext());
        dividerItemDecorationFilter.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(mBinding.recyclerView.getContext(), R.drawable.divider)));
        mBinding.recyclerView.addItemDecoration(dividerItemDecorationFilter);
        mBinding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(DIRECTION_TOP)) {
                    mViewModel.execute();
                }
            }
        });
        mBinding.submitBtn.setOnClickListener(v ->
                    mViewModel.initialize(mBinding.repoET.getText().toString())
        );
    }

    private DividerItemDecorationFilter getDivider(Context context) {
        return new DividerItemDecorationFilter(context, DividerItemDecorationFilter.VERTICAL) {
            @Override
            public boolean showDivider(View child, int position) {
                return true;
            }
        };
    }

    @Override
    public void onChanged(IResponse<PullResponse> pullResponseIResponse) {
        mBinding.setInProgress(pullResponseIResponse.getStatus() == Status.IN_PROGRESS);
        if(pullResponseIResponse.getStatus() == Status.ERROR){
            if(pullResponseIResponse.getError() instanceof InvalidRepositoryException){
                simpleAdapter.clearItems();
                Toast.makeText(getActivity(), getString(R.string.error_pull_repository), Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getActivity(), pullResponseIResponse.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }else if(pullResponseIResponse.getStatus() == Status.SUCCESS){
            simpleAdapter.addItems(pullResponseIResponse.getData().getPulls());
        }
    }
}
