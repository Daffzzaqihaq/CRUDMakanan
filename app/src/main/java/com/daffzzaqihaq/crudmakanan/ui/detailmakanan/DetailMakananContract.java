package com.daffzzaqihaq.crudmakanan.ui.detailmakanan;

import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;

public interface DetailMakananContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showDetailMakanan(MakananData makananData);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getDetailMakanan(String idMakanan);
    }
}
