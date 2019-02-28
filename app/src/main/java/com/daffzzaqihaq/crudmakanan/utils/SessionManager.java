package com.daffzzaqihaq.crudmakanan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.daffzzaqihaq.crudmakanan.model.login.LoginData;
import com.daffzzaqihaq.crudmakanan.ui.login.LoginActivity;

public class SessionManager {
    // Membuat variable global untuk shared preference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private final Context context;

    public SessionManager(Context context) {
        this.context = context;
        // Membuat object sharedPreference untuk siap digunakan
        pref = context.getSharedPreferences(Constant.pref_name, 0);
        // Membuat pref dengan mode edit
        editor = pref.edit();
    }

    public void createSession(LoginData loginData){
        // Memasukan data user yg sudah login ke dalam Shared Preference
        editor.putBoolean(Constant.KEY_IS_LOGIN, true);
        editor.putString(Constant.KEY_USER_ID, loginData.getId_user());
        editor.putString(Constant.KEY_USER_NAMA, loginData.getNama_user());
        editor.putString(Constant.KEY_USER_ALAMAT, loginData.getAlamat());
        editor.putString(Constant.KEY_USER_JENKEL, loginData.getJenkel());
        editor.putString(Constant.KEY_USER_NOTELP, loginData.getNo_telp());
        editor.putString(Constant.KEY_USER_USERNAME, loginData.getUsername());
        editor.putString(Constant.KEY_USER_LEVEL, loginData.getLevel());
        // Mengeksekusi penyimpanan
        editor.commit();
    }

    // Function untuk mengecek apakah user sudah pernah login
    public boolean isLogin(){
        // Mengembalikan nilai boolean dengan mengambil data dari pref KEY_IS_LOGIN
        return pref.getBoolean(Constant.KEY_IS_LOGIN, false);
    }

    // Membuat function untuk melakukan LogOut atau menghaous isi di dalam shared preference
    public void logout(){
        // Memanggil method clear untuk menghapus data sharedpreference
        editor.clear();
        // Mengeksekusi perintah clear
        editor.commit();
        // Membuat Intent untuk berpinah halaman
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
