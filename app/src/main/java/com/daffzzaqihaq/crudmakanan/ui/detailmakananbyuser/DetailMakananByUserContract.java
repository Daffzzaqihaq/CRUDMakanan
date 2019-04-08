package com.daffzzaqihaq.crudmakanan.ui.detailmakananbyuser;

import android.content.Context;
import android.net.Uri;

import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface DetailMakananByUserContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData foodByUserList);
        void showMessage(String msg);
        void successDelete();
        void successUpdate();
        void showSpinnerCategory(List<MakananData> categoryDataList);
    }

    interface Presenter{
        void getCategory();
        void getDetailMakanan(String idMakanan);
        void updateDataMakanan(Context context,
                               Uri filePath,
                               String namaMakanan,
                               String descMakanan,
                               String idCategory,
                               String namaFotoMakanan,
                               String idMakanan);
        void deleteMakanan(String idMakanan, String namaFotoMakanan);
    }
}
