package com.daffzzaqihaq.crudmakanan.ui.detailmakananbyuser;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daffzzaqihaq.crudmakanan.R;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;
import com.daffzzaqihaq.crudmakanan.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMakananByUserActivity extends AppCompatActivity implements DetailMakananByUserContract.View {

    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.fab_choose_picture)
    FloatingActionButton fabChoosePicture;
    @BindView(R.id.layoutPicture)
    CardView layoutPicture;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_desc)
    EditText edtDesc;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.layoutSaveMakanan)
    CardView layoutSaveMakanan;
    @BindView(R.id.btn_update)
    Button btnUpdate;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private DetailMakananByUserPresenter mDetailMakananByUserPresenter = new DetailMakananByUserPresenter(this);
    private Uri filePath;
    private String idCategory, idMakanan;
    private MakananData mMakananData;
    private String namaFotoMakanan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_makanan_by_user);
        ButterKnife.bind(this);

        // Melakukan pengecekan dan permission untuk bisa mengakses gallery
        PermissionGallery();

        // Menagkap id makanan yang dikirimkan dari activity sebelumnya
        idMakanan = getIntent().getStringExtra(Constant.KEY_EXTRA_ID_MAKANAN);

        // Mengambil data detail makanan
        mDetailMakananByUserPresenter.getDetailMakanan(idMakanan);

        // Mengambil data category untuk ditampilkan di spinner
        mDetailMakananByUserPresenter.getCategory();

        // Mensetting swipeRefresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(false);
                // Mengambil data detail makanan
                mDetailMakananByUserPresenter.getDetailMakanan(idMakanan);

                // Mengambil data category untuk ditampilkan di spinner
                mDetailMakananByUserPresenter.getCategory();
            }
        });
    }

    private void PermissionGallery() {
        // Mencek apakah user sudah memberikan permission untuk mengakses external storage
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.STORAGE_PERMISSION_CODE);
    }


    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);

    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);

    }

    @Override
    public void showDetailMakanan(MakananData makananData) {
        // Kita ambil semua data detail makanan
        mMakananData = makananData;

        // Mengambil
        namaFotoMakanan = makananData.getFotoMakanan();

        // Mengambil id Category
        idCategory = makananData.getIdKategori();

        // Menampilkan semua data ke layar
        edtName.setText(makananData.getNamaMakanan());
        edtDesc.setText(makananData.getDescMakanan());

        // Memilih spinner sesuai dengan category makanan yang ada di dalam database
        spinCategory.setSelection(Integer.valueOf(idCategory));

        // Menampilkan gambar makanan
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
        Glide.with(this).load(makananData.getUrlMakanan()).apply(options).into(imgPicture);


    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successDelete() {
        finish();

    }

    @Override
    public void successUpdate() {
        mDetailMakananByUserPresenter.getCategory();
    }

    @Override
    public void showSpinnerCategory(final List<MakananData> categoryDataList) {
        // Membuat data penampung untuk spinner
       // List<String> listSpinner = new ArrayList<>();

        String [] namaCategory = new String[categoryDataList.size()];
        String [] mIdCategory = new String[categoryDataList.size()];

        for (int i = 0; i < categoryDataList.size(); i++){
           // listSpinner.add(categoryDataList.get(i).getNamaKategori());
            namaCategory[i] = categoryDataList.get(i).getNamaKategori();
            mIdCategory[i] = categoryDataList.get(i).getIdKategori();
        }

        // Membuat adapter spinner
        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, namaCategory);
        // Kita setting untuk menampilkan spinner dengan 1 line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        // Memasukan adapter ke spinner
        spinCategory.setAdapter(categorySpinnerAdapter);

        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Mengambil id category sesuai dengan pilihan user
                idCategory = categoryDataList.get(position).getIdKategori();
                swipeRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        hideProgress();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    @OnClick({R.id.fab_choose_picture, R.id.btn_update, R.id.btn_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fab_choose_picture:
                // Mengambil gambar dari storage
                ShowFileChooser();
                break;
            case R.id.btn_update:
                mDetailMakananByUserPresenter.updateDataMakanan(this, filePath, edtName.getText().toString(), edtDesc.getText().toString(), idCategory, namaFotoMakanan, idMakanan);
                break;
            case R.id.btn_delete:
                mDetailMakananByUserPresenter.deleteMakanan(idMakanan, namaFotoMakanan);
                break;
        }
    }

    private void ShowFileChooser() {
        // Membuat objetc intent untuk dapat memilih data
        Intent intentGallery = new Intent(Intent.ACTION_PICK);
        intentGallery.setType("image/*");
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentGallery, "Select Picture"), Constant.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE && requestCode == RESULT_OK && data != null && data.getData() != null){

        }

        // Mengambil data foto dan memasukan ke dalam variable filepath
        filePath = data.getData();

        try {
            // Mengambil data gambar lalu di convert ke bitmap
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            imgPicture.setImageBitmap(bitmap);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        // Mengambil data category untuk ditampilkan di spinner

        mDetailMakananByUserPresenter.getCategory();
        super.onResume();
    }
}
