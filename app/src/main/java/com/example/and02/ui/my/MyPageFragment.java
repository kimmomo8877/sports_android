package com.example.and02.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.and02.LoginActivity;
import com.example.and02.R;
import com.example.and02.ui.common.SharedUserData;
import com.ornach.nobobutton.NoboButton;

public class MyPageFragment extends Fragment {
    private NoboButton btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.btnLogin = view.findViewById(R.id.btn_myPage_login);
        if (SharedUserData.isLogin(getContext())) {
            this.btnLogin.setText("로그아웃");
        } else {
            this.btnLogin.setText("로그인");
        }

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                if (SharedUserData.isLogin(context)) {
                    SharedUserData.logout(context);
                    btnLogin.setText("로그인");
                    Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SharedUserData.isLogin(getContext())) {
            btnLogin.setText("로그아웃");
        } else {
            btnLogin.setText("로그인");
        }
    }
}