package com.daffzzaqihaq.crudmakanan.ui.detailmakanan;

import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daffzzaqihaq.crudmakanan.R;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;
import com.daffzzaqihaq.crudmakanan.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMakanan extends AppCompatActivity implements DetailMakananContract.View {

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.txt_info)
    TextView txtInfo;
    @BindView(R.id.rv_progress)
    RelativeLayout rvProgress;
    @BindView(R.id.img_makanan_detail)
    ImageView imgMakananDetail;
    @BindView(R.id.txt_name_makanan_detail)
    TextView txtNameMakananDetail;
    @BindView(R.id.txt_time_makanan_detail)
    TextView txtTimeMakananDetail;
    @BindView(R.id.txt_name_user)
    TextView txtNameUser;
    @BindView(R.id.txt_desc_makanan)
    TextView txtDescMakanan;
    @BindView(R.id.card_view_detail)
    CardView cardViewDetail;
    @BindView(R.id.sv_detail)
    ScrollView svDetail;

    private DetailMakananPresenter mDetailMakananPresenter = new DetailMakananPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Detail Makanan");
        String idMakanan = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_MAKANAN);
        mDetailMakananPresenter.getDetailMakanan(idMakanan);
    }

    @Override
    public void showProgress() {
        rvProgress.setVisibility(View.VISIBLE);
        svDetail.setVisibility(View.GONE);

    }

    @Override
    public void hideProgress() {
        rvProgress.setVisibility(View.GONE);
        svDetail.setVisibility(View.VISIBLE);

    }

    @Override
    public void showDetailMakanan(MakananData makananData) {
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(this).load(makananData.getUrlMakanan()).apply(options).into(imgMakananDetail);

        txtNameMakananDetail.setText(makananData.getNamaMakanan());
        txtDescMakanan.setText(makananData.getDescMakanan());
        txtNameUser.setText(makananData.getNamaUser());
        txtTimeMakananDetail.setText(makananData.getInsertTime());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
               break;
            }
        }
        return true;
    }

    @Override
    public void showFailureMessage(String msg) {
        svDetail.setVisibility(View.GONE);
        rvProgress.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
        txtInfo.setText(msg);

    }

    private String newDate(String insertTime) {
        // Membuat variable penampung tanggal
        Date date = null;

        // Membuat penampung date dengan format yang baru
        String newDate = insertTime;

        //Membuat format sesuai dengan tanggal yg sudah dimiliki
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Mengubah tanggal yg dimiliki menjadi tipe date
        try {
            date = sdf.parse(insertTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Kita cek format date yang kita miliki sesuai dengan yang kita inginkan
        if (date != null) {
            // Mengubah date yang dimiliki menjadi format date yang baru
            newDate = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(date);
        }
        return newDate;
    }
}
