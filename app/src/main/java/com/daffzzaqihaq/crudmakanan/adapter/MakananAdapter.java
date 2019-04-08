package com.daffzzaqihaq.crudmakanan.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.daffzzaqihaq.crudmakanan.R;
import com.daffzzaqihaq.crudmakanan.model.makanan.MakananData;
import com.daffzzaqihaq.crudmakanan.ui.detailmakanan.DetailMakanan;
import com.daffzzaqihaq.crudmakanan.ui.detailmakananbyuser.DetailMakananByUserActivity;
import com.daffzzaqihaq.crudmakanan.ui.makananbycategory.MakananByCategoryActivity;
import com.daffzzaqihaq.crudmakanan.ui.uploadmakanan.UploadMakananActivity;
import com.daffzzaqihaq.crudmakanan.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MakananAdapter extends RecyclerView.Adapter<MakananAdapter.ViewHolder> {
    // TYPE 1 Makanan Baru
    public static final int TYPE_1 = 1;
    // TYPE 2 Popular Food
    public static final int TYPE_2 = 2;
    // TYPE 3 Category
    public static final int TYPE_3 = 3;
    // TYPE 4 Food By Category
    public static final int TYPE_4 = 4;
    // TYPE 5 User's Food
    public static final int TYPE_5 = 5;

    Integer viewType;
    private final Context context;
    private final List<MakananData> makananDataList;

    public MakananAdapter(Integer viewType, Context context, List<MakananData> makananDataList) {
        this.viewType = viewType;
        this.context = context;
        this.makananDataList = makananDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case TYPE_1:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_news, null);
                return new FoodNewsViewHolder(view);

            case TYPE_2:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_populer, null);
                return new FoodPopulerViewHolder(view);

            case TYPE_3:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_kategory, null);
                return new FoodKategoriViewHolder(view);

            case TYPE_4:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodNewsViewHolder(view);

            case TYPE_5:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food_by_category, null);
                return new FoodByUserViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        // Mengambil data lalu memasukan ke dalam model
        final MakananData makananData = makananDataList.get(i);

        int mViewType = viewType;
        switch (mViewType) {
            case TYPE_1:
                // Memuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder = (FoodNewsViewHolder) viewHolder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options).into(foodNewsViewHolder.imgMakanan);

                // Menampilkan title dan jumlah view
                foodNewsViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder.txtView.setText(makananData.getView());

                foodNewsViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membuat onClick
                foodNewsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke detail makanan
                        context.startActivity(new Intent(context, DetailMakanan.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });

                break;
            case TYPE_2:
                FoodPopulerViewHolder foodPopulerViewHolder = (FoodPopulerViewHolder) viewHolder;

                RequestOptions options2 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options2).into(foodPopulerViewHolder.imgMakanan);

                foodPopulerViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodPopulerViewHolder.txtView.setText(makananData.getView());

                // Menampilkan waktu upload
                foodPopulerViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                foodPopulerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke detail makanan
                        context.startActivity(new Intent(context, DetailMakanan.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });
                break;
            case TYPE_3:
                FoodKategoriViewHolder foodKategoriViewHolder = (FoodKategoriViewHolder) viewHolder;

                RequestOptions options1 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options1).into(foodKategoriViewHolder.image);

                foodKategoriViewHolder.txtNamaKategory.setText(makananData.getNamaKategori());

                foodKategoriViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke detail makanan
                        context.startActivity(new Intent(context, MakananByCategoryActivity.class).putExtra(Constant.KEY_EXTRA_ID_CATEGORY, makananData.getIdKategori()));
                    }
                });
                break;

            case TYPE_4:
                // Memuat holder untuk dapat mengakses widget
                FoodNewsViewHolder foodNewsViewHolder2 = (FoodNewsViewHolder) viewHolder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options4 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options4).into(foodNewsViewHolder2.imgMakanan);

                // Menampilkan title dan jumlah view
                foodNewsViewHolder2.txtTitle.setText(makananData.getNamaMakanan());
                foodNewsViewHolder2.txtView.setText(makananData.getView());

                foodNewsViewHolder2.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membuat onClick
                foodNewsViewHolder2.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke detail makanan
                        context.startActivity(new Intent(context, MakananByCategoryActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdKategori()));
                    }
                });

            case TYPE_5:
                // Memuat holder untuk dapat mengakses widget
                FoodByUserViewHolder foodByUserViewHolder = (FoodByUserViewHolder) viewHolder;

                // Requestoption untuk error dan placeholder gambar
                RequestOptions options5 = new RequestOptions().error(R.drawable.ic_broken_image).placeholder(R.drawable.ic_broken_image);
                Glide.with(context).load(makananData.getUrlMakanan()).apply(options5).into(foodByUserViewHolder.imgMakanan);

                // Menampilkan title dan jumlah view
                foodByUserViewHolder.txtTitle.setText(makananData.getNamaMakanan());
                foodByUserViewHolder.txtView.setText(makananData.getView());

                foodByUserViewHolder.txtTime.setText(newDate(makananData.getInsertTime()));

                // Membuat onClick
                foodByUserViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Berpindah halaman ke detail makanan
                        context.startActivity(new Intent(context, DetailMakananByUserActivity.class).putExtra(Constant.KEY_EXTRA_ID_MAKANAN, makananData.getIdMakanan()));
                    }
                });

        }

    }

    private String newDate(String insertTime) {
        // Membuat variable penampung tanggal
        Date date = null;

        // Membuat penampung date dengan format yang baru
        String newDate = insertTime;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Mengubah tanggal yg dimiliki menjadi tipe date
        try {
            //Membuat format sesuai dengan tanggal yg sudah dimiliki
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


    @Override
    public int getItemCount() {
        return makananDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class FoodNewsViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodNewsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodPopulerViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;

        public FoodPopulerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodKategoriViewHolder extends ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.txt_nama_kategory)
        TextView txtNamaKategory;

        public FoodKategoriViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FoodByUserViewHolder extends ViewHolder {
        @BindView(R.id.img_makanan)
        ImageView imgMakanan;
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.img_view)
        ImageView imgView;
        @BindView(R.id.txt_view)
        TextView txtView;
        @BindView(R.id.txt_time)
        TextView txtTime;
        public FoodByUserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
