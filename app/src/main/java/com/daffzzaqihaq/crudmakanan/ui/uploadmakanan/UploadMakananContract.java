package com.daffzzaqihaq.crudmakanan.ui.uploadmakanan;

import android.content.Context;
import android.net.Uri;

import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface UploadMakananContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showMessage(String msg);
        void succesUpload();
        void showSpinnerCategory(List<MakananData> categoryDataList);
    }

    interface Presenter{
        void getCategory();
        void uploadMakanan(Context context, Uri filePath, String namaMakanan, String dscMakanan, String idCategory);

    }
}
