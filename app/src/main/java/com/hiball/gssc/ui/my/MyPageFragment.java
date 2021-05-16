package com.hiball.gssc.ui.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hiball.gssc.LoginActivity;
import com.hiball.gssc.R;
import com.hiball.gssc.ui.common.SharedUserData;
import com.ornach.nobobutton.NoboButton;

public class MyPageFragment extends Fragment {
    private NoboButton btnLogin;
    private TextView textViewUserName;
    private TextView textViewUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        this.textViewUserName = view.findViewById(R.id.text_myPage_name);
        this.textViewUserId = view.findViewById(R.id.text_myPage_userId);

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                if (SharedUserData.isLogin(context)) {
                    SharedUserData.logout(context);
                    Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    refreshView();
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
        refreshView();
    }

    private void refreshView() {
        if (SharedUserData.isLogin(getContext())) {
            this.textViewUserName.setText(SharedUserData.getName(getContext()));
            this.textViewUserId.setText(SharedUserData.getEmail(getContext()));
            this.btnLogin.setText("로그아웃");
        } else {
            this.textViewUserName.setText("로그인해주세요.");
            this.textViewUserId.setText("");
            this.btnLogin.setText("로그인");
        }
    }
}