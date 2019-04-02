package com.daffzzaqihaq.crudmakanan.ui.makanan;

import android.util.Log;

import com.daffzzaqihaq.crudmakanan.data.remote.ApiClient;
import com.daffzzaqihaq.crudmakanan.data.remote.ApiInterface;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananPresenter implements MakananContract.Presenter {

    // TODO 1 Menyiapkan variable yang dibutuhkan
    private final  MakananContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananPresenter(MakananContract.View view) {
        this.view = view;
    }

    @Override
    public void getListFoodNews() {
        view.showProgress();
        Call<MakananResponse> call = mApiInterface.getMakananBaru();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null && response.isSuccessful()){
                    view.showFoodNewsList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data is empty");
                    Log.e(response.message(), "makbar berhasil");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
                Log.e(t.getMessage(), "makbar gagal");

            }
        });

    }

    @Override
    public void getListFoodPopular() {
        view.showProgress();
        Call<MakananResponse> call = mApiInterface.getMakananPopuler();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null && response.isSuccessful()){
                    view.showFoodPopulerList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data is empty");
                    Log.e(response.message(), "pomak berhasil");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());
                Log.e(t.getMessage(), "pomak gagal");

            }
        });

    }

    @Override
    public void getListFoodKategory() {
        view.showProgress();
        Call<MakananResponse> call = mApiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null && response.isSuccessful()){
                    view.showFoodKategoryList(response.body().getMakananDataList());
                }else {
                    view.showFailureMessage("Data is empty");
                    Log.e(response.message(), "Katmak berhasil");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showFailureMessage(t.getMessage());

                Log.e(t.getMessage(), "gagal ");

            }
        });

    }
}
