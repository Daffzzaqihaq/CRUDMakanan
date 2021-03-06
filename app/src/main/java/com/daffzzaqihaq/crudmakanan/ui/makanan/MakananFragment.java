package com.daffzzaqihaq.crudmakanan.ui.makanan;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daffzzaqihaq.crudmakanan.R;
import com.daffzzaqihaq.crudmakanan.adapter.MakananAdapter;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;
import com.daffzzaqihaq.crudmakanan.ui.uploadmakanan.UploadMakananActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakananFragment extends Fragment implements MakananContract.View {


    @BindView(R.id.rv_makanan_news)
    RecyclerView rvMakananNews;
    @BindView(R.id.rv_makanan_populer)
    RecyclerView rvMakananPopuler;
    @BindView(R.id.rv_kategori)
    RecyclerView rvKategori;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    // TODO 1 Menyiapkan variable yang dibutuhkan
    private ProgressDialog mProgressDialog;
    private MakananPresenter mMakananPresenter = new MakananPresenter(this);

    public MakananFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_makanan, container, false);
        unbinder = ButterKnife.bind(this, view);

        // TODO 2 MEngambil data foodnews, popular, and category
        mMakananPresenter.getListFoodNews();
        mMakananPresenter.getListFoodPopular();
        mMakananPresenter.getListFoodKategory();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMakananPresenter.getListFoodNews();
                mMakananPresenter.getListFoodPopular();
                mMakananPresenter.getListFoodKategory();
            }
        });


        return view;
    }

    @Override
    public void showProgress() {
        //mProgressDialog = new ProgressDialog(getContext());
        //mProgressDialog.setMessage("Loading....");
        //mProgressDialog.setCancelable(false);
        //mProgressDialog.show();

        swipeRefresh.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
        //mProgressDialog.dismiss();

    }

    @Override
    public void showFoodNewsList(List<MakananData> foodNewsList) {
        rvMakananNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMakananNews.setAdapter(new MakananAdapter(MakananAdapter.TYPE_1, getContext(), foodNewsList));

    }

    @Override
    public void showFoodKategoryList(List<MakananData> foodKategoryList) {
        rvKategori.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvKategori.setAdapter(new MakananAdapter(MakananAdapter.TYPE_3, getContext(), foodKategoryList));
    }

    @Override
    public void showFoodPopulerList(List<MakananData> foodPopulerList) {
        rvMakananPopuler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMakananPopuler.setAdapter(new MakananAdapter(MakananAdapter.TYPE_2, getContext(), foodPopulerList));
    }

    @Override
    public void showFailureMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.floating_action_button)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), UploadMakananActivity.class));
    }

    @Override
    public void onResume() {
        mMakananPresenter.getListFoodNews();
        mMakananPresenter.getListFoodPopular();
        mMakananPresenter.getListFoodKategory();
        super.onResume();
    }
}
