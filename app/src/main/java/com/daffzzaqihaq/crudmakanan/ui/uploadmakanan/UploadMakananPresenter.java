package com.daffzzaqihaq.crudmakanan.ui.uploadmakanan;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.daffzzaqihaq.crudmakanan.data.remote.ApiClient;
import com.daffzzaqihaq.crudmakanan.data.remote.ApiInterface;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananResponse;
import com.daffzzaqihaq.crudmakanan.model.uploadmakanan.UploadMakananResponse;
import com.daffzzaqihaq.crudmakanan.utils.Constant;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadMakananPresenter implements UploadMakananContract.Presenter {

    // TODO 1 Menyiapkan variable yg dibutuhkan
    private final UploadMakananContract.View view;
    private ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public UploadMakananPresenter(UploadMakananContract.View view) {
        this.view = view;
    }

    @Override
    public void getCategory() {
        view.showProgress();
        Call<MakananResponse> call = apiInterface.getKategoriMakanan();
        call.enqueue(new Callback<MakananResponse>() {
            @Override
            public void onResponse(Call<MakananResponse> call, Response<MakananResponse> response) {
                view.hideProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showSpinnerCategory(response.body().getMakananDataList());
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data Kosong...");
                }
            }

            @Override
            public void onFailure(Call<MakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.i("cek failure", "onFailure" + t.getMessage());


            }
        });

    }

    @Override
    public void uploadMakanan(Context context, Uri filePath, String namaMakanan, String dscMakanan, String idCategory) {
        view.showProgress();

        if (namaMakanan.isEmpty()){
            view.showMessage("Nama makanan tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (dscMakanan.isEmpty()){
            view.showMessage(" Desc tidak boleh kosong");
            view.hideProgress();
            return;
        }

        if (filePath == null){
            view.showMessage(" Silahkan memilih gambar");
            view.hideProgress();
            return;
        }

        // Mengambil alamat file image
        Log.i("isi", "uploadMakanan: "+String.valueOf(filePath));
        File myFile = new File(filePath.getPath());
        Log.i("isi 2", "myfile: "+String.valueOf(myFile));

        Uri selectedImage = getImageContentUri(context, myFile, filePath);
        Log.i("isi 3", "selected: "+String.valueOf(selectedImage));
        File imageFile = null;
        if (selectedImage != null){

            String partImage = getPath(context, selectedImage);
            Log.i("isi 4", "partimg: "+partImage);

            imageFile = new File(partImage);
            Log.i("isi 5", "imgfile: "+String.valueOf(imageFile));
        }else {
            imageFile = myFile;
            Log.i("isi imageFile else", "imgFile: " + String.valueOf(imageFile));
        }


        // Mengambil id user di dalam shared pref
        SharedPreferences pref = context.getSharedPreferences(Constant.pref_name, 0);
        String iduser = pref.getString(Constant.KEY_USER_ID, "");

        // Mengambil date sekarang untuk upload makanan
        String dateNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // Memasukan data yang diperlukan ke dalam request body dengan tipe from-data
        // Memasukan imagefile ke dalam requestbody
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), imageFile);
        MultipartBody.Part mPartImage = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);

        // Memasukan nama, desc dan inserttime
        RequestBody mNamaMakanan = RequestBody.create(MediaType.parse("multipart/from-data"), namaMakanan);
        RequestBody mDescMakanan = RequestBody.create(MediaType.parse("multipart/from-data"), dscMakanan);
        RequestBody mInsertTime = RequestBody.create(MediaType.parse("multipart/from-data"), dateNow);

        // Mengirim data ke API
        Call<UploadMakananResponse> call = apiInterface.uploadMakanan(Integer.valueOf(iduser), Integer.valueOf(idCategory), mNamaMakanan, mDescMakanan, mInsertTime, mPartImage);
        call.enqueue(new Callback<UploadMakananResponse>() {
            @Override
            public void onResponse(Call<UploadMakananResponse> call, Response<UploadMakananResponse> response) {
                view.showProgress();
                if (response.body() != null){
                    if (response.body().getResult() == 1){
                        view.showMessage(response.body().getMessage());
                        view.succesUpload();
                    }else {
                        view.showMessage(response.body().getMessage());
                    }
                }else {
                    view.showMessage("Data kosong");
                }
            }

            @Override
            public void onFailure(Call<UploadMakananResponse> call, Throwable t) {
                view.hideProgress();
                view.showMessage(t.getMessage());
                Log.i("Cek failure", "Onfailure" + t.getMessage());

            }
        });

    }

    private String getPath(Context context, Uri filepath) {
        Cursor cursor = context.getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ",
                new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    private Uri getImageContentUri(Context context, File imageFile, Uri filePath) {

        String fileAbsolutePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{fileAbsolutePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
// Apabila file gambar sudah pernah diapakai namun ada kondisi lain yang belum diketahui
// Apabila file gambar sudah pernah dipakai pengambilan bukan di galery

            Log.i("Isi Selected if", "Masuk cursor ada");
            return filePath;

        } else {
            Log.i("Isi Selected else", "cursor tidak ada");
            if (imageFile.exists()) {
// Apabila file gambar baru belum pernah di pakai
                Log.i("Isi Selected else", "imagefile exists");
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, fileAbsolutePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
// Apabila file gambar sudah pernah dipakai
// Apabila file gambar sudah pernah dipakai di galery
                Log.i("Isi Selected else", "imagefile tidak exists");
                return null;
            }
        }
    }
}
