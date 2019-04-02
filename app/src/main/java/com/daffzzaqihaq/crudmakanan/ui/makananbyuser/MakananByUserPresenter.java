package com.daffzzaqihaq.crudmakanan.ui.makananbyuser;

import com.daffzzaqihaq.crudmakanan.data.remote.ApiClient;
import com.daffzzaqihaq.crudmakanan.data.remote.ApiInterface;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MakananByUserPresenter implements MakananByUserContract.Presenter {

    private final MakananByUserContract.View view;
    private ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

    public MakananByUserPresenter(MakananByUserContract.View view) {
        this.view = view;
    }

    @Override
    public void getListByUser(String idUser) {
        view.showProgress();

        if (idUser.isEmpty()){
            view.showFailureMessage("ID User tidak ada");
            return;
        }

        Call<MakananResponse> call = mApiInterface.getMakananByUser(Integer.valueOf(idUser));
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        view.showFoodByUser(response.body().getMakananDataList());
                    } else {
                        view.showFailureMessage(response.body().getMessage());

                    }
                }else {
                    view.showFailureMessage("Data Kosong");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.showFailureMessage(t.getMessage());
            }
        });
    }
}
