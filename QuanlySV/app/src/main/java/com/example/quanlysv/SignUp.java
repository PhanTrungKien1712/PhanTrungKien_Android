package com.example.quanlysv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    final String DB_NAME = "user.sqlite";
    SQLiteDatabase database;
    Button btnCancel, btnSignUp;
    EditText edtId, edtEmail, edtPassword, edtxnPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        database = Database.initDatabase(this, DB_NAME);

        btnCancel = findViewById(R.id.btncancel);
        btnSignUp = findViewById(R.id.btnsignup);
        edtId = findViewById(R.id.id);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.pass);
        edtxnPassword = findViewById(R.id.xnpass);

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, Login.class);
                startActivity(i);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edtId.getText().toString();
                String email = edtEmail.getText().toString();
                String pass = edtPassword.getText().toString();
                String copass = edtxnPassword.getText().toString();

                if (id.equals("") || email.equals("") || pass.equals("") || copass.equals("")) {
                    Toast.makeText(SignUp.this, "Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (!pass.equals(copass)) {
                        Toast.makeText(SignUp.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    } else {
                        Cursor cursor = database.rawQuery("select * from Users where Email = ?", new String[]{email});
                        if (cursor.getCount() > 0) {
                            Toast.makeText(SignUp.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {
                            Cursor sv = database.rawQuery("SELECT * FROM Users WHERE UserId = ?", new String[]{id});
                            if (sv.getCount() > 0) {
                                Toast.makeText(SignUp.this, "Sinh viên đã tồn tại.", Toast.LENGTH_LONG).show();
                            } else {
                                ContentValues row = new ContentValues();
                                row.put("Email", email);
                                row.put("Pass", pass);
                                long r = database.insert("Users", null, row);
                                Toast.makeText(SignUp.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, Login.class);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });
    }
}