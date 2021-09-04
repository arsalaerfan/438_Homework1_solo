package com.example.a438_homework1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private static int userId;
    private static String user;
    private EditText username;
    private EditText password;
    private Button login;
    private TextView textViewResult;
    private List<User> allUsers = new ArrayList<>();

    String correctPass = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<User>> call = jsonPlaceHolderApi.getUser();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                }

                List<User> users = response.body();
                allUsers.addAll(users);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check username
                if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Empty Data", Toast.LENGTH_LONG).show();
                } else if (validUsername(allUsers, username.getText().toString())) {
                    //check password
                    if (validPassword(password.getText().toString(), correctPass)){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", username.getText().toString());
                        bundle.putInt("userId", userId);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }else{
                        Toast.makeText(LoginActivity.this, "Password", Toast.LENGTH_LONG).show();
                        password.setBackgroundColor(Color.RED);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Username", Toast.LENGTH_LONG).show();
                    username.setBackgroundColor(Color.RED);
                }
            }
        });

    }

    public static boolean validUsername (List<User> allUsers, String user){
        for (User findUser : allUsers) {
            if (findUser.getUsername().equals(user)) {
                userId = findUser.getId();
                return true;
            }
        }
        return false;
    }


    public static boolean validPassword(String userPassword, String correctPassword){
        if(userPassword.equals(correctPassword)) {
            return true;
        }
        return false;
    }

}
