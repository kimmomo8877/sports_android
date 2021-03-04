package com.example.and02.ui.tracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.and02.LoginActivity;
import com.example.and02.R;
import com.example.and02.ui.tracker.model.target.TargetInfoModel;
import com.example.and02.ui.tracker.model.target.TargetModel;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackerConfigFragment extends Fragment {
    private Button btnToTrackerMain;
    private Button btnToLogin;
    private EditText editTextAge;
    private EditText editTextHeight;
    private EditText editTextWeight;
    private EditText editTextGoal;
    private Spinner spinnerCenter;
    private String[] centerList;
    private Integer[] centerNoList;

    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestQueue = Volley.newRequestQueue(getContext());
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker_config, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.btnToTrackerMain = view.findViewById(R.id.btn_trackerConfig_toTrackerMain);
        this.btnToTrackerMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String age = editTextAge.getText().toString();
                String weight = editTextWeight.getText().toString();
                String height = editTextHeight.getText().toString();
                String goal = editTextGoal.getText().toString();

                if (age.equals("") || weight.equals("") || height.equals("") || goal.equals("") || centerList.length == 0) {
                    Toast.makeText(getContext(), "모든 설정을 완료해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int selectedIdx = spinnerCenter.getSelectedItemPosition();
                Integer centerNo = centerNoList[selectedIdx];

                SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("age", Integer.parseInt(age));
                editor.putInt("weight", Integer.parseInt(weight));
                editor.putInt("height", Integer.parseInt(height));
                editor.putInt("goal", Integer.parseInt(goal));
                editor.putInt("targetNo", centerNo);
                editor.commit();

                Navigation.findNavController(v).navigate(R.id.navigation_tracker_main);
            }
        });

        this.btnToLogin = view.findViewById(R.id.btn_trackerConfig_toLogin);
        this.btnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        this.editTextAge = view.findViewById(R.id.et_trackerConfig_age);
        this.editTextHeight = view.findViewById(R.id.et_trackerConfig_height);
        this.editTextWeight = view.findViewById(R.id.et_trackerConfig_weight);
        this.editTextGoal = view.findViewById(R.id.et_trackerConfig_goal);
        this.spinnerCenter = view.findViewById(R.id.spinner_trackerConfig_center);

        SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        int age = preferences.getInt("age", -1);
        int weight = preferences.getInt("weight", -1);
        int height = preferences.getInt("height", -1);
        int goal = preferences.getInt("goal", -1);

        editTextAge.setText(age == -1 ? "" : String.valueOf(age));
        editTextWeight.setText(weight == -1 ? "" : String.valueOf(weight));
        editTextHeight.setText(height == -1 ? "" : String.valueOf(height));
        editTextGoal.setText(goal == -1 ? "" : String.valueOf(goal));
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
        String userNo = preferences.getString("userNo", "");
        if (userNo.equals("")) {
            this.btnToLogin.setVisibility(View.VISIBLE);
            this.btnToTrackerMain.setVisibility(View.GONE);
        } else {
            this.btnToLogin.setVisibility(View.GONE);
            this.btnToTrackerMain.setVisibility(View.VISIBLE);
            getTargetList(userNo);
        }

        Log.i(getClass().getName(), "OnResume");
    }

    private void getTargetList(final String userNo) {
        String url = "http://www.kbostat.co.kr/resource/target/infor/byUserNo";
        StringRequest targetRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                Log.i("getTargetList", "getTargetList Response : " + response);
                Gson gson = new Gson();
                TargetModel targetModel = gson.fromJson(response, TargetModel.class);
                List<TargetInfoModel> targetInfo = targetModel.getTargetInfo();
                centerList = new String[targetInfo.size()];
                centerNoList = new Integer[targetInfo.size()];

                for (int i = 0; i < centerList.length; i++) {
                    centerList[i] = targetInfo.get(i).getInfra().getName();
                    centerNoList[i] = targetInfo.get(i).getTargetNo();
                }

                spinnerCenter.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, centerList));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("getTargetList", "getTargetList Error : " + error.toString());
            }
        }) {
            @Override
            public byte[] getBody() {
                Map<String, Object> userData = new HashMap<>();
                userData.put("userNo", userNo);

                Gson gson = new Gson();
                String jsonBody = gson.toJson(userData);
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(targetRequest);
    }
}