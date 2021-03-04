package com.example.and02;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button btnLogin;
    private Button btnCancel;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.requestQueue = Volley.newRequestQueue(getApplicationContext());

        this.editTextUserName = findViewById(R.id.et_login_id);
        this.editTextPassword = findViewById(R.id.et_login_pass);
        this.btnLogin = findViewById(R.id.btn_login_login);
        this.btnCancel = findViewById(R.id.btn_login_cancel);

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = editTextUserName.getText().toString();
                String password = editTextPassword.getText().toString();

                if (userName.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "이메일, 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }

                login(userName, password);
            }
        });

        this.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void login(final String username, final String password) {
        String url = "http://www.kbostat.co.kr/auth/auth/login";
        final StringRequest trackingRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Login", "Response : " + response);
                try {
                    JSONObject parsedResponse = new JSONObject(response);
                    String token = parsedResponse.getString("accessToken");
                    getUserNo(token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", "Login Error : " + error.toString());
                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public byte[] getBody() {
                Map<String, Object> loginData = new HashMap<>();
                loginData.put("email", username);
                loginData.put("password", password);

                Gson gson = new Gson();
                String jsonBody = gson.toJson(loginData);
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(trackingRequest);
    }

    private void getUserNo(final String token) {
        String url = "http://www.kbostat.co.kr/auth/user/me";
        final StringRequest trackingRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Login", "getUserNo : " + response);
                try {
                    JSONObject parsedResponse = new JSONObject(response);
                    String userNo = parsedResponse.getString("userNo");
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userNo", userNo);
                    editor.putString("token", token);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "로그인되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    Log.i("Login", "parsing failed getUserNo : " + e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", "errorResponse getUserNo : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                header.put("Authorization", token);
                return header;
            }
        };

        requestQueue.add(trackingRequest);
    }
}