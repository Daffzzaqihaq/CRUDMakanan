package com.daffzzaqihaq.crudmakanan.ui.main;

import android.content.Context;

import com.daffzzaqihaq.crudmakanan.utils.SessionManager;

public class MainPresenter implements MainContract.Presenter {
    @Override
    public void logoutSession(Context context) {
        // Membuat object sessionManager
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();

    }
}
