package com.daffzzaqihaq.crudmakanan.ui.makanan;

import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;

import java.util.List;

public interface MakananContract {

    interface View{
        void showProgress();
        void hideProgress();
        void showFoodNewsList(List<MakananData> foodNewsList);
        void showFoodPopulerList(List<MakananData> foodPopulerList);
        void showFoodKategoryList(List<MakananData> foodKategoryList);
        void showFailureMessage(String msg);
    }

    interface Presenter{
        void getListFoodNews();
        void getListFoodPopular();
        void getListFoodKategory();
    }
}
