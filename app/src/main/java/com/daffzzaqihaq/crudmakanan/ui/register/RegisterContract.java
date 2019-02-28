package com.daffzzaqihaq.crudmakanan.ui.register;

import android.widget.EditText;

import com.daffzzaqihaq.crudmakanan.model.login.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSuccess(String message);
    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);


    }
}
