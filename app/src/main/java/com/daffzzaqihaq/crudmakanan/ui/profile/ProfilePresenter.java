package com.daffzzaqihaq.crudmakanan.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.telecom.Call;

import com.daffzzaqihaq.crudmakanan.data.remote.ApiClient;
import com.daffzzaqihaq.crudmakanan.data.remote.ApiInterface;
import com.daffzzaqihaq.crudmakanan.model.login.LoginData;
import com.daffzzaqihaq.crudmakanan.model.login.LoginResponse;
import com.daffzzaqihaq.crudmakanan.utils.Constant;
import com.daffzzaqihaq.crudmakanan.utils.SessionManager;

import retrofit2.Callback;
import retrofit2.Response;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;
    private SharedPreferences pref;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void updateDataUser(final Context context, final LoginData loginData) {

        // Menampilkan progress dialog
        view.showProgress();

        // Membuat object call dan memanggil method updateUser serta mengirimkan datanya
        retrofit2.Call<LoginResponse> call = apiInterface.updateUser(
                Integer.valueOf(loginData.getId_user()),
                loginData.getNama_user(),
                loginData.getAlamat(),
                loginData.getJenkel(),
                loginData.getNo_telp());

        // Mengeksekusi call
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {
                view.hideProgress();
                // Mencek response dan isi body
                if (response.isSuccessful() && response.body() != null){
                    // Mencek result apakah 1
                    if (response.body().getResult() == 1){
                        // Setelah update ke server online lalu update ke SharedPreference
                        // Membuat object sharedpref yg sudah ada di sessionManager
                        pref = context.getSharedPreferences(Constant.pref_name, 0);
                        // Mengubah mode sharedpref menjadi edit
                        SharedPreferences.Editor editor = pref.edit();
                        // Memasukan data ke dalam sharedpref
                        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
                        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
                        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
                        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
                        // Apply perubahan
                        editor.apply();
                        view.showSuccessUpdateUser(response.body().getMessage());

                    }else {
                        view.showSuccessUpdateUser(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                view.hideProgress();
                view.showSuccessUpdateUser(t.getMessage());
            }
        });




        // Membuat object sharedpref yg sudah ada di sessionManager
        pref = context.getSharedPreferences(Constant.pref_name, 0);
        // Mengubah mode sharedpref menjadi edit
        SharedPreferences.Editor editor = pref.edit();
        // Memasukan data ke dalam sharedpref
        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        // Apply perubahan
        editor.apply();
        view.showSuccessUpdateUser("Your account info has been saved !");

    }

    @Override
    public void getDataUser(Context context) {
        // Pengambilan data dari SharedPreference
        pref = context.getSharedPreferences(Constant.pref_name, 0);

        // Membuat object model logindata untuk penampung
        LoginData loginData = new LoginData();

        // Memasukan data SharedPreference ke dalam model logindata
        loginData.setId_user(pref.getString(Constant.KEY_USER_ID, ""));
        loginData.setNama_user(pref.getString(Constant.KEY_USER_NAMA, ""));
        loginData.setAlamat(pref.getString(Constant.KEY_USER_ALAMAT, ""));
        loginData.setNo_telp(pref.getString(Constant.KEY_USER_NOTELP, ""));
        loginData.setJenkel(pref.getString(Constant.KEY_USER_JENKEL, ""));

        // Mengirim data model login data ke view
        view.showDataUser(loginData);

    }

    @Override
    public void logoutSession(Context context) {
        // Membuat class session manager untuk memanggil method logout
        SessionManager mSessionManager = new SessionManager(context);
        mSessionManager.logout();

    }
}
