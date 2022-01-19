package com.example.quanlysv;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ActivityInfor extends AppCompatActivity {
    final String DB_NAME = "DB.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    Button btnthoat;
    TextView txtmasv, txtTen, txtns ,txtsdt;
    ImageView imgHinhDaiDien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);
        addControls();
        addEvents();
        initUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream is = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imgHinhDaiDien.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == RESQUEST_TAKE_PHOTO) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgHinhDaiDien.setImageBitmap(bitmap);
        }
    }

    private void addControls() {
        btnthoat = (Button) findViewById(R.id.btnthoat);
        txtmasv = (TextView) findViewById(R.id.txtId);
        txtTen = (TextView) findViewById(R.id.txtHoTenSV);
        txtns = (TextView) findViewById(R.id.txtNamSinh);
        txtsdt =(TextView) findViewById(R.id.txtSoDienThoai) ;
        imgHinhDaiDien = (ImageView) findViewById(R.id.imgHinhDaiDien);
    }
    private void addEvents(){
        btnthoat.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               cancel();
           }
       });
    }
    private void initUI() {
        SQLiteDatabase database = Database.initDatabase(this, DB_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM SinhVien",null);
        cursor.moveToFirst();
        String masv = cursor.getString(0);
        String ten = cursor.getString(1);
        String namsinh= cursor.getString(2);
        String sdt = cursor.getString(3);
        byte[] hinhanh = cursor.getBlob(4);
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh,0,hinhanh.length);
        imgHinhDaiDien.setImageBitmap(bitmap);
        txtmasv.setText(masv);
        txtTen.setText(ten);
        txtns.setText(namsinh);
        txtsdt.setText(sdt);
    }
    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}