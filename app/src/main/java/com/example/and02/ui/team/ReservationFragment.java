package com.example.and02.ui.team;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.and02.MainActivity;
import com.example.and02.R;
import com.example.and02.ui.common.SharedUserData;
import com.example.and02.ui.home.InfraModel;
import com.google.gson.Gson;
import com.ornach.nobobutton.NoboButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReservationFragment extends Fragment {

    private RequestQueue requestQueue;

    private Button btnStartDate;
    private Button btnEndDate;
    private Button btnStartTime;
    private Button btnEndTime;
    private TextView textViewStartDate;
    private TextView textViewEndDate;
    private TextView textViewStartTime;
    private TextView textViewEndTime;
    private int typeChk;

    Calendar reservationCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            reservationCalendar.set(Calendar.YEAR, year);
            reservationCalendar.set(Calendar.MONDAY, month);
            reservationCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate();
        }
    };

    TimePickerDialog.OnTimeSetListener setTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            reservationCalendar.set(Calendar.HOUR, hourOfDay);
            reservationCalendar.set(Calendar.MINUTE, minute);
            updateTime();
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");
        this.requestQueue = Volley.newRequestQueue(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        menu.clear();
        inflater.inflate(R.menu.reservation_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Log.i("onOptionsItemSelected", "back");
                getActivity().onBackPressed();
                return true;
            case R.id.button_tracker_action:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("시설예약");
        View view = inflater.inflate(R.layout.fragment_reservation, container, false);

        final InfraModel infraModel = (InfraModel) getArguments().getSerializable("infraModel");

        if (infraModel.getAttachFile() != null) {
            Uri uri = Uri.parse(infraModel.getAttachFile());
            ImageView iv = view.findViewById(R.id.imageView_reservation_main);
            Picasso.get().load(uri).fit().centerCrop().into(iv);
//        iv.setImageURI(uri);
            Log.i("imageload", String.valueOf(uri));
        }

        TextView textViewTitle = view.findViewById((R.id.textView_reservation_title));
        if (!infraModel.getName().equals("null")) {
            textViewTitle.setText(infraModel.getName());
        }
        TextView textViewSubTitle = view.findViewById(R.id.textView_reservation_subTitle);
        if (!infraModel.getAddress().equals("null")) {
            textViewSubTitle.setText(infraModel.getAddress());
        }
        textViewStartDate = view.findViewById(R.id.textView_reservation_startDate);
        textViewStartTime = view.findViewById(R.id.textView_reservation_startTime);
        textViewEndDate = view.findViewById(R.id.textView_reservation_endDate);
        textViewEndTime = view.findViewById(R.id.textView_reservation_endTime);

        btnStartDate = view.findViewById(R.id.button_reservation_startDate);
        btnStartDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("startButton", "startButton");
                typeChk = 1;
                new DatePickerDialog(getContext(), setDate, reservationCalendar.get(reservationCalendar.YEAR), reservationCalendar.get(reservationCalendar.MONTH), reservationCalendar.get(reservationCalendar.DAY_OF_MONTH)).show();
            }
        });
        btnStartTime = view.findViewById(R.id.button_reservation_startTime);
        btnStartTime.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("startButton", "startButton");
                typeChk = 1;
                new TimePickerDialog(getContext(), setTime, reservationCalendar.get(reservationCalendar.HOUR), reservationCalendar.get(reservationCalendar.MINUTE), false).show();
            }
        });

        btnEndDate = view.findViewById(R.id.button_reservation_endDate);
        btnEndDate.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("endButton", "endButton");
                typeChk = 2;
                new DatePickerDialog(getContext(), setDate, reservationCalendar.get(reservationCalendar.YEAR), reservationCalendar.get(reservationCalendar.MONTH), reservationCalendar.get(reservationCalendar.DAY_OF_MONTH)).show();
            }
        });
        btnEndTime = view.findViewById(R.id.button_reservation_endTime);
        btnEndTime.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("endButton", "endButton");
                typeChk = 2;
                new TimePickerDialog(getContext(), setTime, reservationCalendar.get(reservationCalendar.HOUR), reservationCalendar.get(reservationCalendar.MINUTE), false).show();
            }
        });


        Button btnConfirm = view.findViewById(R.id.button_reservation_confirm);
        btnConfirm.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("confirmButton", "confirmButton");

                String startDate = textViewStartDate.getText() + " " + textViewStartTime.getText();
                String endDate = textViewEndDate.getText() + " " + textViewEndTime.getText();
                int result = dateCompareTo(startDate, endDate);

                if (textViewStartDate.getText().equals("0000/00/00") || textViewEndDate.getText().equals("0000/00/00")
                        || textViewStartTime.getText().equals("00:00") || textViewEndTime.getText().equals("00:00")) {
                    Toast.makeText(getContext(), "실패 (날짜 또는 시간 설정 필요)", Toast.LENGTH_LONG).show();
                } else if (result == 1) {
                    Toast.makeText(getContext(), "실패 (시작일 > 종료일)", Toast.LENGTH_LONG).show();
                } else {
                    ReservationModel reservationModel = new ReservationModel();
                    reservationModel.setInfraNo(infraModel.getInfraNo());
                    reservation(reservationModel);
                }
            }
        });


//        btnEnd = view.findViewById(R.id.button_reservation_end);
//        btnEnd.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i("endButton", "endButton");
//                typeChk = 2;
//                new DatePickerDialog(getContext(), setDate, reservationCalendar.get(Calendar.YEAR),reservationCalendar.get(reservationCalendar.MONTH), reservationCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        }) ;


//        requestQueue = Volley.newRequestQueue(view.getContext());
//        doHttpRequest();

        return view;
    }

    private int todayCompareTo(String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date time = new Date();
        String current = format.format(time);
        Date today = null;
        try {
            today = format.parse(current);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date end = null;
        try {
            end = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } //dd("current:"+current); //current: 2020-09-15 10:18:16 //dd("today:"+today); //tody:Tue Sep 15 10:18:16 KST 2020 //dd("endDate:"+endDate); //dd("end:"+end);
        int result = today.compareTo(end);
        return result;
    }


    private int dateCompareTo(String startDate, String endDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Date today = null;
        try {
            today = format.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date end = null;
        try {
            end = format.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = today.compareTo(end);
        return result;
    }


    private void reservation(final ReservationModel reservationModel) {
        String url = "http://www.kbostat.co.kr/resource/reservation";
        final StringRequest reservationRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Reservation", "Response : " + response);
//                try {
                Log.i("Reservation", "Reservation Success : ");
                Toast.makeText(getContext(), "예약 성공", Toast.LENGTH_LONG).show();
//                    JSONObject parsedResponse = new JSONObject(response);
//                    String token = parsedResponse.getString("accessToken");
//                    getUserNo(token);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Reservation", "Reservation Error : " + error.toString());
                Toast.makeText(getContext(), "예약 실패", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() {
                Map<String, Object> reservationData = new HashMap<>();
                reservationData.put("infraNo", reservationModel.getInfraNo());


                Gson gson = new Gson();
                String jsonBody = gson.toJson(reservationData);
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(reservationRequest);
    }

    public void updateDate() {
        String format = "YYYY/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        if (typeChk == 1) {
            textViewStartDate.setText(simpleDateFormat.format(reservationCalendar.getTime()));
        } else if (typeChk == 2) {
            textViewEndDate.setText(simpleDateFormat.format(reservationCalendar.getTime()));
        }
    }

    public void updateTime() {
        String format = "HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.KOREA);
        if (typeChk == 1) {
            textViewStartTime.setText(simpleDateFormat.format(reservationCalendar.getTime()));
        } else if (typeChk == 2) {
            textViewEndTime.setText(simpleDateFormat.format(reservationCalendar.getTime()));
        }
    }
}
