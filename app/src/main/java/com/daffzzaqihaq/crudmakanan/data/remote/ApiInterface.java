package com.daffzzaqihaq.crudmakanan.data.remote;

import com.daffzzaqihaq.crudmakanan.model.detail.DetailMakananResponse;
import com.daffzzaqihaq.crudmakanan.model.login.LoginResponse;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananResponse;
import com.daffzzaqihaq.crudmakanan.model.uploadmakanan.UploadMakananResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiInterface {

    // Membuat endpoint Login
    @FormUrlEncoded
    @POST("loginuser.php")
    Call<LoginResponse> loginUser(
                @Field("username") String username,
                @Field("password") String password

    );

    // Menambahkan endpoint untuk register
    @FormUrlEncoded
    @POST("registeruser.php")
    Call<LoginResponse> registerUser(@Field("username")String username,
                                     @Field("password")String password,
                                     @Field("namauser")String namauser,
                                     @Field("alamat")String alamat,
                                     @Field("jenkel")String jenkel,
                                     @Field("notelp")String notelp,
                                     @Field("level")String level
                                     );

    // Membuat endpoint untuk update
    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> updateUser(
            @Field("iduser")int iduser,
            @Field("namauser")String namauser,
            @Field("alamat")String alamat,
            @Field("jenkel")String jenkel,
            @Field("notelp")String notelp
    );

    // Mengambil data kategori
    @GET("getkategori.php")
    Call<MakananResponse> getKategoriMakanan();

    // Mengambil data makanan baru
    @GET("getmakananbaru.php")
    Call<MakananResponse> getMakananBaru();

    // Mengambil data makanan populer
    @GET("getmakananpopuler.php")
    Call<MakananResponse> getMakananPopuler();

    // Mengupload makanan
    @Multipart
    @POST("uploadmakanan.php")
    Call<UploadMakananResponse> uploadMakanan(
            @Part("iduser") int iduser,
            @Part("idkategori") int idkategori,
            @Part("namamakanan")RequestBody namamakanan,
            @Part("descmakanan")RequestBody descmakanan,
            @Part("timeinsert") RequestBody timeinsert,
            @Part MultipartBody.Part image
            );

    // Mengambil detail makanan
    @GET("getdetailmakanan.php")
    Call<DetailMakananResponse> getDetailMakanan(@Query("idmakanan") int idMakanan);

    // Mengambil data makanan berdasarkan id category
    @GET("getmakananbykategori.php")
    Call<MakananResponse> getMakananByCategory(@Query("idkategori") int idCategory);

    // Mengambil data makanan user yg udah di upload maa dia and ntar bisa di edit ma dia
    @GET("getmakananbyuser.php")
    Call<MakananResponse> getMakananByUser(@Query("id_user") int idUser);

    // Delete Makanan
    @POST("deletemakanan.php")
    Call<MakananResponse> deleteMakanan(
            @Field("idmakanan") int idMakanan,
            @Field("fotomakanan") String namaFotoMakanan
    );

    // Mengupdate makanan
    @Multipart
    @POST("updatemakanan.php")
    Call<MakananResponse> updateMakanan(
            @Part("idmakanan") int idMakanan,
            @Part("idkategori") int idCategory,
            @Part("namamakanan") RequestBody namaMakanan,
            @Part("descmakanan") RequestBody descMakanan,
            @Part("fotomakanan") RequestBody fotoMakanan,
            @Part("inserttime") RequestBody insertTime,
            @Part MultipartBody.Part image
    );

}
