package com.daffzzaqihaq.crudmakanan.ui.login;

import com.daffzzaqihaq.crudmakanan.model.login.LoginData;

public interface LoginContract {

    interface View {
        void showProgress();
        void hideProgress();
        void loginSuccess(String msg, LoginData loginData);
        void loginFailure(String msg);
        void usernameError(String msg);
        void passwordError(String msg);
    }

    interface Presenter {
        void doLogin(String username, String password);

    }
}