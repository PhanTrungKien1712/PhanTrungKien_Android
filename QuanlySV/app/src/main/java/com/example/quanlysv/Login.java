package com.example.quanlysv;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    final String DATABASE_NAME = "user.sqlite";

    int id;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Login = (Button) findViewById(R.id.btnlogin);
        Button Signup =(Button) findViewById(R.id.btnsignup);
        EditText edtuser = (EditText) findViewById(R.id.email);
        EditText edtpass = (EditText) findViewById(R.id.pass);
        database = Database.initDatabase(this, DATABASE_NAME);


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }

            private void signup() {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                Cursor cursor = database.rawQuery("SELECT * FROM Users", null);
                String email;
                String pass;

                String User = edtuser.getText().toString();
                String Pass = edtpass.getText().toString();
                for(int i = 0; i < cursor.getCount(); i++){
                    cursor.moveToPosition(i);
                    email=cursor.getString(1);
                    pass=cursor.getString(2);
                    if (User.equals("") || Pass.equals("")){
                        Toast.makeText(getApplicationContext(), "Chưa nhập đủ thông tài khoản",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        if (User.equalsIgnoreCase(email) && Pass.equalsIgnoreCase(pass)) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), R.string.login_ok, Toast.LENGTH_SHORT).show();
                            break;
                        }
                        if(i==cursor.getCount()-1){
                            TextView btnerr = (TextView) findViewById(R.id.err);
                            btnerr.setText("Tài khoản không tồn tại");
                        }
                    }

                }
            }
        });
    }

    public void userOnclick(View view) {
        TextView btnerr = (TextView) findViewById(R.id.err);
        btnerr.setText("");
    }

    public void passwordOnclick(View view) {
        TextView btnerr = (TextView) findViewById(R.id.err);
        btnerr.setText("");
    }
}