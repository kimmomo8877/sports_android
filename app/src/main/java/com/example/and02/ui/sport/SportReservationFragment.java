package com.example.and02.ui.sport;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.and02.R;
import com.example.and02.ui.common.SharedUserData;
import com.google.gson.Gson;
import com.ornach.nobobutton.NoboButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class SportReservationFragment extends Fragment {
    private TextView textSelectTargetDate;
    private Spinner spinnerSelectPlayer;
    private Spinner spinnerSelectCenter;
    private Spinner spinnerSelectTargetTime;
    private DatePickerDialog datePickerDialog;
    private NoboButton btnRequestReservation;

    private RequestQueue requestQueue;

    private String[] centerIdList;
    private String[] centerNameList;
    private List<CenterDomain> centerDomainList;
    private List<String> targetTimeList;
    private String[] playerNoList;
    private String[] playerNameList;
    private String targetDate;
    private String teamNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestQueue = Volley.newRequestQueue(getContext());
        getCenterList();
        getPlayerList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sport_reservation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.btnRequestReservation = view.findViewById(R.id.btn_sportReservation_requestReservation);
        this.btnRequestReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReservationData();
            }
        });


        this.textSelectTargetDate = view.findViewById(R.id.text_sportReservation_targetDate);
        this.textSelectTargetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        this.spinnerSelectCenter = view.findViewById(R.id.spinner_sportReservation_center);
        this.spinnerSelectCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getTargetTimeList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                getTargetTimeList();
            }
        });

        this.spinnerSelectPlayer = view.findViewById(R.id.spinner_sportReservation_player);

        this.spinnerSelectTargetTime = view.findViewById(R.id.spinner_sportReservation_targetTime);
        this.spinnerSelectTargetTime.setEnabled(false);
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH);
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        this.datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                targetDate = year + "-" + String.format("%02d", (month + 1)) + "-" + String.format("%02d", dayOfMonth);
                textSelectTargetDate.setText(targetDate);
                getTargetTimeList();
            }
        }, currentYear, currentMonth, currentDay);
    }

    private void getCenterList() {
        String url = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=2&childInfraCategory=28";
        final StringRequest trackingRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                Log.i(getClass().getName(), "getCenterList : " + response);
                try {
                    JSONArray parsedResponse = new JSONArray(response);
                    int size = parsedResponse.length();
                    centerIdList = new String[size];
                    centerNameList = new String[size];
                    centerDomainList = new ArrayList<>();

                    for (int index = 0; index < size; index++) {
                        JSONObject centerObject = parsedResponse.getJSONObject(index);
                        String centerId = centerObject.getString("infraNo");
                        String centerName = centerObject.getString("name");
                        String startTime = centerObject.getString("reservationStartTime");
                        String endTime = centerObject.getString("reservationEndTime");
                        int timeUnit = centerObject.getInt("reservationTimeUnit");

                        List<String> reservationTimeList = makeReservationTime(startTime, endTime, timeUnit);
                        CenterDomain centerDomain = new CenterDomain();
                        centerDomain.setCenterNo(centerId);
                        centerDomain.setCenterName(centerName);
                        centerDomain.setReservationTime(reservationTimeList);
                        centerDomainList.add(centerDomain);

                        centerIdList[index] = centerId;
                        centerNameList[index] = centerName;
                    }

                    spinnerSelectCenter.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, centerNameList));
                } catch (JSONException e) {
                    Log.i(getClass().getName(), "parsing failed getCenterList : " + e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", "errorResponse getCenterList : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(trackingRequest);
    }

    private void postReservationData() {
        if (centerIdList.length == 0 || playerNoList.length == 0 || targetTimeList == null || targetTimeList.size() == 0 || targetDate == null) {
            Toast.makeText(getContext(), "모든값을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int timeIndex = spinnerSelectTargetTime.getSelectedItemPosition();
        int centerIndex = spinnerSelectCenter.getSelectedItemPosition();
        int playerIndex = spinnerSelectPlayer.getSelectedItemPosition();

        String infraNo = centerIdList[centerIndex];
        String playerNo = playerNoList[playerIndex];
        String targetTime = targetTimeList.get(timeIndex);

        CenterRequestDomain requestDomain = new CenterRequestDomain();
        requestDomain.setInfraNo(infraNo);
        requestDomain.setReservaterNo(playerNo);
        requestDomain.setRegisterNo(SharedUserData.getUserNo(getContext()));
        requestDomain.setStartDate(targetDate + " " + targetTime.split("~")[0]);
        requestDomain.setEndDate(targetDate + " " + targetTime.split("~")[1]);
        requestDomain.setTeamNo(teamNo);
        final List<CenterRequestDomain> requestDomainList = new ArrayList<>();
        requestDomainList.add(requestDomain);

        String url = "http://www.kbostat.co.kr/resource/reservation/multiple";
        final StringRequest reservationRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(getClass().getName(), "postReservationData response : " + response);
                Toast.makeText(getContext(), "예약되었습니다.", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();
//                getActivity().getFragmentManager().beginTransaction().remove(getParentFragment()).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getClass().getName(), "postReservationData Error : " + error.toString());
                Toast.makeText(getContext(), "예약에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public byte[] getBody() {
                Gson gson = new Gson();
                String jsonRequest = gson.toJson(requestDomainList);
                return jsonRequest.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(reservationRequest);
    }

    private void getTargetTimeList() {
        // 측정센터 선택여부
        if (this.centerIdList.length == 0 || this.targetDate == null) {
            return;
        }

        int selectedCenterPosition = this.spinnerSelectCenter.getSelectedItemPosition();
        CenterDomain centerDomain = centerDomainList.get(selectedCenterPosition);
        final List<String> reservationTimeList = centerDomain.getReservationTime();

        String url = "http://www.kbostat.co.kr/resource/reservation/infra/" + centerDomain.getCenterNo() + "?startDay=" + targetDate;

        final StringRequest trackingRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                Log.i(getClass().getName(), "getTargetTimeList : " + response);
                spinnerSelectTargetTime.setEnabled(true);

                try {
                    JSONArray parsedResponse = new JSONArray(response);
                    int size = parsedResponse.length();
                    targetTimeList = new ArrayList<>();
                    List<String> reservedStartHour = new ArrayList<>();
                    for (int index = 0; index < size; index++) {
                        JSONObject targetTimeObject = parsedResponse.getJSONObject(index);
                        String startDate = targetTimeObject.getString("startDate");
                        String toDeleteStartHour = startDate.split(" ")[1].split(":")[0];
                        reservedStartHour.add(toDeleteStartHour);
                    }

                    List<String> resultTime = new ArrayList<>();
                    for (String reservationTime : reservationTimeList) {
                        String enableStartHour = reservationTime.split("~")[0].split(":")[0];

                        if (!reservedStartHour.contains(enableStartHour)) {
                            targetTimeList.add(reservationTime);
                        }
                    }

                    spinnerSelectTargetTime.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, targetTimeList));
                } catch (JSONException e) {
                    Log.i(getClass().getName(), "parsing failed getTargetTimeList : " + e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getClass().getName(), "errorResponse getTargetTimeList : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(trackingRequest);
    }

    private void getPlayerList() {
        String userNo = SharedUserData.getUserNo(getContext());
        String url = "http://www.kbostat.co.kr/resource/user/" + userNo + "/belonged-team";
        final StringRequest trackingRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                Log.i(getClass().getName(), "getBelongedTeam : " + response);
                try {
                    JSONArray parsedResponse = new JSONArray(response);
                    int size = parsedResponse.length();
                    for (int index = 0; index < size; index++) {
                        JSONObject teamObject = parsedResponse.getJSONObject(index);
                        teamNo = teamObject.getString("belongedTeamNo");
                        getPlayerListByTeam(teamNo);
                        break;
                    }
                } catch (JSONException e) {
                    Log.i(getClass().getName(), "parsing failed getCenterList : " + e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getClass().getName(), "errorResponse getTargetTime : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(trackingRequest);
    }

    private void getPlayerListByTeam(String teamNo) {
        String url = "http://www.kbostat.co.kr/resource/tmp-team-player/" + teamNo;
        final StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
                Log.i(getClass().getName(), "getPlayerListByTeam : " + response);
                try {
                    JSONArray parsedResponse = new JSONArray(response);
                    int size = parsedResponse.length();
                    playerNameList = new String[size];
                    playerNoList = new String[size];
                    for (int index = 0; index < size; index++) {
                        JSONObject playerObject = parsedResponse.getJSONObject(index);
                        playerNoList[index] = playerObject.getString("tmpRegistedNo");
                        playerNameList[index] = playerObject.getString("userName");
                    }

                    spinnerSelectPlayer.setAdapter(new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, playerNameList));
                } catch (JSONException e) {
                    Log.i(getClass().getName(), "parsing failed getPlayerListByTeam : " + e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(getClass().getName(), "errorResponse getPlayerListByTeam : " + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(request);
    }

    private List<String> makeReservationTime(String startTime, String endTime, int timeUnit) {
        List<String> result = new ArrayList<>();
        String[] startSplit = startTime.split(":");
        int startHour = Integer.parseInt(startSplit[0]);
        int startMinute = Integer.parseInt(startSplit[1]);

        String[] endSplit = endTime.split(":");
        int endHour = Integer.parseInt(endSplit[0]);
        int endMinute = Integer.parseInt(endSplit[1]);

        for (int hour = startHour; hour < endHour; hour += timeUnit) {
            String targetTime = String.format("%02d", hour) + ":" + String.format("%02d", startMinute) + "~" + String.format("%02d", hour + timeUnit) + ":" + String.format("%02d", startMinute);
            result.add(targetTime);
        }

        return result;
    }
}