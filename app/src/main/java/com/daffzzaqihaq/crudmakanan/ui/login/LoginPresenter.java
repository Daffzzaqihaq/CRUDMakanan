package com.daffzzaqihaq.crudmakanan.ui.login;

import android.content.Context;

import com.daffzzaqihaq.crudmakanan.data.remote.ApiClient;
import com.daffzzaqihaq.crudmakanan.data.remote.ApiInterface;
import com.daffzzaqihaq.crudmakanan.model.login.LoginData;
import com.daffzzaqihaq.crudmakanan.model.login.LoginResponse;
import com.daffzzaqihaq.crudmakanan.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
    private SessionManager mSessionManager;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
    }


    @Override
    public void doLogin(String username, String password) {
           // Mencek username dan password
           if (username.isEmpty()) {
               view.usernameError("Please fill username");
               return;
           }

           if (password.isEmpty()) {
               view.passwordError("Please fill password");
               return;
           }

           view.showProgress();

        Call<LoginResponse> call = apiInterface.loginUser(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                if (response.body() != null) {
                    if (response.body().getResult() == 1) {
                        if (response.body().getData() != null) {
                            LoginData loginData = response.body().getData();
                            String message = response.body().getMessage();
                            view.loginSuccess(message, loginData);
                        } else {
                            view.loginFailure("Data is empty");
                        }
                    } else {
                        view.loginFailure(response.body().getMessage());
                    }
                } else {
                    view.loginFailure("Data is empty");
                }
            }


            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.loginFailure(t.getMessage());

            }
        });
    }

    @Override
    public void saveDataUser(Context context, LoginData loginData) {
        // Membuat object sessionManager
        mSessionManager = new SessionManager(context);
        // Mensave data ke SharedPreference dengan menggunakan method dari class SessionManager
        mSessionManager.createSession(loginData);

    }

    @Override
    public void checkLogin(Context context) {
        mSessionManager = new SessionManager(context);
        // Mengambil data KEY_IS_LOGIN lalu memasukan ke dalam variable isLogin
        Boolean isLogin = mSessionManager.isLogin();
        // Mengecek Apakah KEY_IS_LOGIN bernilai true
        if (isLogin){
            // Menyuruh view untuk melakukan perpindahan ke MainActivity
            view.isLogin();
        }

    }
}
