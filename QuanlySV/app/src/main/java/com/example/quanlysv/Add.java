package com.example.quanlysv;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Add extends AppCompatActivity {
    final String DB_NAME = "DB.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;
    int id = +1;
    Button btnLuu, btnHuy,btnChonAnh, btnChupAnh;
    EditText edtTen, edtSdt, edtId, edtNamSinh;
    ImageView imgHinhDaiDien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        addControls();
        addEvents();
    }
    private void addControls() {
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        edtId = (EditText) findViewById(R.id.edtId);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtNamSinh = (EditText) findViewById(R.id.edtNamSinh);
        edtSdt = (EditText) findViewById(R.id.edtSdt);
        imgHinhDaiDien = (ImageView) findViewById(R.id.imgHinhDaiDien);
        btnChonAnh = (Button) findViewById(R.id.btnChonAnh);
        btnChupAnh = (Button) findViewById(R.id.btnChupAnh);
    }
    private void addEvents(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
          }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
        btnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnChupAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgHinhDaiDien.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgHinhDaiDien.setImageBitmap(bitmap);
            }
        }
    }

    private void insert(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable)  imgHinhDaiDien.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
        byte[] img = getByteArrayFromImageView(imgHinhDaiDien);
        String id = edtId.getText().toString();
        String ten = edtTen.getText().toString();
        String namsinh = edtNamSinh.getText().toString();
        String sdt = edtSdt.getText().toString();
        if (id.equals("") || ten.equals("") || sdt.equals("") ||namsinh.equals("")){
            Toast.makeText(getApplicationContext(), "Chưa nhập đủ thông tin sinh viên",Toast.LENGTH_SHORT).show();
        }
        else{
            ContentValues contentValues = new ContentValues();
            contentValues.put("MaSV", id);
            contentValues.put("HoTen", ten);
            contentValues.put("NamSinh", namsinh);
            contentValues.put("DienThoai", sdt);
            contentValues.put("HinhAnh", img);
            SQLiteDatabase database = Database.initDatabase(this, DB_NAME);
            database.insert("SinhVien", null,contentValues);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Thêm thành công",Toast.LENGTH_SHORT).show();
        }
    }

    public byte[] getByteArrayFromImageView(ImageView imgv) {

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    private  void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }
    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
    }
    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}



