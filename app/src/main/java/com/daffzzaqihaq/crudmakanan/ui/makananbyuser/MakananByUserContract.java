package com.daffzzaqihaq.crudmakanan.ui.makananbyuser;

import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface MakananByUserContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showFoodByUser(List<MakananData> foodByUserList);
        void showFailureMessage(String msg);

    }

    interface Presenter{
        void getListByUser(String idUser);
    }
}
