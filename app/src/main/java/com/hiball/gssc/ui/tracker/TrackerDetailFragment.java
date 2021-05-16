package com.hiball.gssc.ui.tracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hiball.gssc.R;
import com.hiball.gssc.ui.tracker.model.ActivityModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.inbody.inbodysdk.IB_BleManager;
import com.inbody.inbodysdk.IB_SDKConst;
import com.ornach.nobobutton.NoboButton;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class TrackerDetailFragment extends Fragment {
    private double height = 175.0;
    private double weight = 66.5;
    private int age = 40;
    private String gender = "M";
    private String DEVICE_NAME = "InBodyBand2";
    private static final String LOG_TAG = "Band";

    private final IB_BleManager inBodyBLEManager = IB_BleManager.getInstance();

    private BottomNavigationView navigationView;
    private NoboButton btnRefresh;
    private NoboButton btnRunHR;
    private NoboButton btnRunInBody;
    private NoboButton btnStartActivity;
    private NoboButton btnEndActivity;
    private TextView textDashboardTimer;
    private TextView textTrackingCount;
    private TextView textTrackingDistance;
    private TextView textTrackingCalories;
    private TextView textTrackingTime;
    private ProgressDialog progressDialog;

    private Timer timer;
    private int timerCounter = 0;
    private int targetExerNo = 0;
    private ActivityModel firstActivity;
    private ActivityModel tempActivity;
    private ActivityModel completeActivity;

    private boolean onActivity;

    private RequestQueue requestQueue;

    private void startTimer() {
        Log.i(LOG_TAG, "Timer Start");

        if (this.timer == null) {
            this.timerCounter = 0;
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    timerCounter++;
                    final int minute = timerCounter / 60;
                    final int second = timerCounter % 60;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textDashboardTimer.setText(minute + " : " + second);
                        }
                    });
                }
            };

            this.timer = new Timer();
            this.timer.schedule(timerTask, 0, 1000);
        }
    }

    private void endTimer() {
        this.timer.cancel();
        this.timer = null;
    }

    private void showToastMessage(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void dismissLoadingDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    private void showLoadingDialog(String message) {
        this.progressDialog = new ProgressDialog(getContext());
        this.progressDialog.setMessage(message);
        this.progressDialog.setCancelable(true);
        this.progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
        this.progressDialog.show();
    }

    private void enableButtonByStartActivity() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnRefresh.setEnabled(true);
                btnRunHR.setEnabled(true);
                btnRunInBody.setEnabled(true);
                btnStartActivity.setEnabled(false);
                btnEndActivity.setEnabled(true);
            }
        });
    }

    private void enableButtonByInit() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnRefresh.setEnabled(false);
                btnRunHR.setEnabled(false);
                btnRunInBody.setEnabled(false);
                btnStartActivity.setEnabled(true);
                btnEndActivity.setEnabled(false);
            }
        });
    }

    enum BandFunction {
        NOTHING,
        GET_HR,
        INIT_ACTIVITY,
        GET_ACTIVITY,
        END_ACTIVITY,
        GET_BCA,
        RUN_HR,
        RUN_INBODY,
        RESET,
        SYNC
    }

    private BandFunction toRunFunc = BandFunction.NOTHING;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.navigationView = getActivity().findViewById(R.id.nav_view);
        navigationView.setVisibility(View.GONE);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (onActivity) {
                    endActivity();
                } else {
                    NavController controller = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    controller.popBackStack();
                    navigationView.setVisibility(View.VISIBLE);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        inBodyBLEManager.SetCallback(CallbackInBodyBLEManager);
        inBodyBLEManager.InitSDKWithCallback(height, weight, age, gender, DEVICE_NAME, true, getActivity());
    }

    private void initActivityDashboard() {
        this.textTrackingCalories.setText("0");
        this.textTrackingCount.setText("0");
        this.textTrackingDistance.setText("0");
        this.textTrackingTime.setText("0");
    }

    private void endActivity() {
        showLoadingDialog("밴드에 접근중... (운동종료)");
        toRunFunc = BandFunction.END_ACTIVITY;
        inBodyBLEManager.ConnectDisconnectWithCallback(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_tracker_button:
                Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_menu_search:
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.onActivity = false;
        this.requestQueue = Volley.newRequestQueue(view.getContext());

        this.btnRefresh = view.findViewById(R.id.btn_refresh);
        this.btnRunHR = view.findViewById(R.id.btn_start_hr);
        this.btnRunInBody = view.findViewById(R.id.btn_start_inbody);
        this.btnStartActivity = view.findViewById(R.id.btn_start_tracking);
        this.btnEndActivity = view.findViewById(R.id.btn_end_tracking);

        this.textDashboardTimer = view.findViewById(R.id.text_dashboardTimer);
        this.textTrackingCalories = view.findViewById(R.id.text_activityCalories);
        this.textTrackingCount = view.findViewById(R.id.text_activityCount);
        this.textTrackingDistance = view.findViewById(R.id.text_activityDistance);
        this.textTrackingTime = view.findViewById(R.id.text_activityTime);

        this.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inBodyBLEManager.CheckBluetoothState()) {
                    showLoadingDialog("운동 데이터를 불러오는중...");
                    toRunFunc = BandFunction.GET_ACTIVITY;
                    inBodyBLEManager.ConnectDisconnectWithCallback(true);
                }
            }
        });

        this.btnStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("밴드에 접근중... (운동시작)");
                firstActivity = null;
                tempActivity = null;
                completeActivity = null;

                initActivityDashboard();
                toRunFunc = BandFunction.INIT_ACTIVITY;
//                toRunFunc = BandFunction.SYNC;

                inBodyBLEManager.ConnectDisconnectWithCallback(true);
            }
        });

        this.btnEndActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endActivity();
            }
        });

        this.btnRunHR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("밴드 전극에 손가락을 올려주세요. (심박 측정중...)");
                toRunFunc = BandFunction.RUN_HR;
                inBodyBLEManager.ConnectDisconnectWithCallback(true);
            }
        });

        this.btnRunInBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog("밴드 전극에 손가락을 올려주세요. (인바디 측정중...)");
                toRunFunc = BandFunction.RUN_INBODY;
                inBodyBLEManager.ConnectDisconnectWithCallback(true);
            }
        });

        enableButtonByInit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker_detail, container, false);
    }

    private String currentTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        return format.format(date);
    }

    IB_BleManager.BLECallback CallbackInBodyBLEManager = new IB_BleManager.BLECallback() {

        @Override
        public void CallbackInitSDK(JSONObject retVal) {
            Log.d(IB_SDKConst.TAG, "CallbackInitSDK was called.");

//      btnConnectDisconnect.setEnabled(true);
//      try {
//        if (retVal.getInt("IsAlreadyPaired") == IB_SDKConst.NOTPAIRED)
//          btnRemove.setEnabled(false);
//        else
//          btnRemove.setEnabled(true);
//      } catch (JSONException e) {
//        e.printStackTrace();
//      }
//
//      spinner.setEnabled(true);
        }

        @Override
        public void CallbackSelectDevice(JSONObject retVal) {
//      try {
//        if (retVal.getInt("IsAlreadyPaired") == IB_SDKConst.NOTPAIRED)
//          btnRemove.setEnabled(false);
//        else
//          btnRemove.setEnabled(true);
//
//        Log.d(IB_SDKConst.TAG, "CallbackSelectDevice was called : " + retVal);
//      } catch (JSONException e) {
//        e.printStackTrace();
//      }
        }

        @Override
        public void CallbackRemoveDevice(JSONObject retVal) {
//      btnRemove.setEnabled(false);
//      Log.d(IB_SDKConst.TAG, "CallbackRemoveDevice was called : " + retVal);
//      try {
//        String DetailLog = retVal.get("DetailLog").toString();
//        listAdapter.add(DetailLog);
//      } catch (JSONException e) {
//        e.printStackTrace();
//      }
        }

        @Override
        public void CallbackConnectDisconnect(final JSONObject retVal) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Log.d(IB_SDKConst.TAG, "CallbackConnectDisconnect was called : " + retVal);
                        int state = 0;
                        int errorCode = -1;
                        String Log = "";
                        String DetailLog = "";

                        try {
                            state = retVal.getInt("BleState");
                            errorCode = retVal.getInt("ErrorCode");
                            Log = retVal.get("Log").toString();
                            DetailLog = retVal.get("DetailLog").toString();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (errorCode == -3 || errorCode == -1) {
                            showToastMessage("기기접근불가 (Timeout)");
                            dismissLoadingDialog();
                            return;
                        } else if (errorCode == 6 || errorCode == 133) {
                            showToastMessage("기기접근불가 (Abnormal Disconnected)");
                            dismissLoadingDialog();
                            return;
                        } else if (errorCode == 0 && state == 0) {
                            showToastMessage("기기접근불가 (Busy)");
                            dismissLoadingDialog();
                            return;
                        } else if (errorCode == -9) {
                            showToastMessage("기기를 찾을 수 없습니다.");
                            dismissLoadingDialog();
                            return;
                        } else {
                            showToastMessage("Status Code : " + errorCode);
//                            dismissLoadingDialog();
                        }

                        switch (state) {
                            case IB_SDKConst.IDLE:
//              spinner.setEnabled(true);
//              btnRemove.setEnabled(true);
//              btnConnectDisconnect.setText("Connect");
                                break;

                            case IB_SDKConst.SCANNING: {

//              try {
//                scannedList = (ArrayList<IB_BTDeviceInfo>) retVal.get("ScannedList");
//                android.util.Log.e("UpdateScannedList", "" + scannedList.toString());
//
//                listAdapter2.clear();
//                for (IB_BTDeviceInfo deviceInfo : scannedList) {
//                  BluetoothDevice device = deviceInfo.getBluetoothDevice();
//                  android.util.Log.e("UpdateScannedList", "device Name : " + device.getName() + " MacAddr : " + device.getAddress() + " SerialNumber : " + deviceInfo.getSerialNumber());
//
//                  String deviceListText = device.getName() + " " + device.getAddress() + " SN " + deviceInfo.getSerialNumber();
//                  listAdapter2.add(deviceListText);
//                }
//                listAdapter2.notifyDataSetChanged();
//
//              } catch (JSONException e) {
//                e.printStackTrace();
//              }

                                break;
                            }
                            case IB_SDKConst.STOPSCANN:
                                break;

                            case IB_SDKConst.CONNECTING:
                                break;

                            case IB_SDKConst.CONNECTED:
//              if (DEVICE_NAME.contains("InBodyBand")) {
//                _InBodyBLEManager.SetSyncWithCallback();
//              }
                                if (toRunFunc == BandFunction.INIT_ACTIVITY || toRunFunc == BandFunction.GET_ACTIVITY || toRunFunc == BandFunction.END_ACTIVITY) {
                                    inBodyBLEManager.SetProfileSyncWithCallback();
                                    inBodyBLEManager.GetActivityDataWithCallback(true);
                                } else if (toRunFunc == BandFunction.RUN_HR) {
                                    inBodyBLEManager.StartBandHRTestWithCallback(false);
                                } else if (toRunFunc == BandFunction.RUN_INBODY) {
                                    inBodyBLEManager.StartBandInBodyTestWithCallback(0, 0, false, true);
                                } else if (toRunFunc == BandFunction.SYNC) {
                                    inBodyBLEManager.SetProfileSyncWithCallback();
                                }


                                break;
                            case IB_SDKConst.UPGRADING:
                                break;
                            case IB_SDKConst.UPGRADED:
                                inBodyBLEManager.ConnectDisconnectWithCallback(false);
                                break;
                        }
                    }
                });
            }
        }

        /*
        CallbackSetSync, 싱크 콜백
         */
        @Override
        public void CallbackSetSync(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetSync : " + retVal.toString());
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "GetDeviceInfo : " + inBodyBLEManager.GetDeviceInfo());

            try {
                JSONObject JsonObj = inBodyBLEManager.GetDeviceInfo();
                String Firmware = JsonObj.getString("Firmware");
                int ver = Integer.parseInt(Firmware.substring(Firmware.length() - 3));

                if (ver < 34)
                    inBodyBLEManager.StartFirmwareUpgradeWithCallback("");
                else
                    inBodyBLEManager.ConnectDisconnectWithCallback(false);  // Disconnect, 연결끊기
                //_InBodyBLEManager.SetBand2SettingWithCallback(null, 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //_InBodyBLEManager.ConnectDisconnectWithCallback();  // Disconnect, 연결끊기
            //_InBodyBLEManager.StartFirmwareUpgradeWithCallback("");
            //_InBodyBLEManager.GetBcaDataWithCallback(false);
            //_InBodyBLEManager.SetBandFactoryReset();

//            _InBodyBLEManager.SetMobileNumberWithCallback("01012347777");
        }

        /*
        CallbackSetMobileNumber, 밴드2 전화번호 설정 콜백
         */
        @Override
        public void CallbackSetMobileNumber(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetMobileNumber : " + retVal.toString());
            inBodyBLEManager.ConnectDisconnectWithCallback(false);
        }

        /*
        CallbackGetBcaData, 체성분 데이터
         */
        @Override
        public void CallbackGetBcaData(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackGetBcaData : " + retVal.toString());

            try {
                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());
                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS)
                    inBodyBLEManager.GetHRDataWithCallback(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackGetHRData, 심박 데이터
         */
        @Override
        public void CallbackGetHRData(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackGetHRData : " + retVal.toString());

            try {
                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());
                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS) {
//                    inBodyBLEManager.GetActivityDataWithCallback(false); // HR Data was always clear automatic
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackGetActivityData, 활동량 데이터
         */
        @Override
        public void CallbackGetActivityData(JSONObject retVal) {
            Log.d(LOG_TAG, "CallbackGetActivityData : " + retVal.toString());
            if (retVal.has("JsonData")) {
                try {
                    String jsonText = retVal.getString("JsonData");
                    Gson gson = new Gson();
                    ActivityModel parsedActivityData = gson.fromJson(jsonText, ActivityModel.class);

                    if (toRunFunc == BandFunction.INIT_ACTIVITY) {
                        firstActivity = parsedActivityData;
                        postBeginRequestData();
                    } else if (toRunFunc == BandFunction.GET_ACTIVITY) {
                        handleActivityData(parsedActivityData, false);
                    } else {
                        // END Activity
                        handleActivityData(parsedActivityData, true);
                    }
                } catch (JSONException e) {
                    Log.e(LOG_TAG, "parsingFailed : callbackGetActivityData");
                    e.printStackTrace();
                }
            }
        }

        private void handleActivityData(ActivityModel parsedActivityData, boolean isEnd) {
            if (parsedActivityData.getIsComplete() == IB_SDKConst.SUCCESS) {
                if (completeActivity == null) {
                    parsedActivityData.minus(firstActivity);
                }

                tempActivity = parsedActivityData;
                calculateAllActivity();
            } else {
                if (completeActivity == null) {
                    parsedActivityData.minus(firstActivity);
                    completeActivity = parsedActivityData;
                }
                completeActivity.add(parsedActivityData);
            }

            postTrackingData(parsedActivityData, isEnd);
        }

        private void calculateAllActivity() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int trackingTime = tempActivity.getWalkTime() + tempActivity.getRunTime();
                    int trackingCount = tempActivity.getWalk() + tempActivity.getRun();
                    int trackingDistance = tempActivity.getWalkDistance() + tempActivity.getRunDistance();
                    int trackingCalories = tempActivity.getWalkCalories() + tempActivity.getRunCalories();

                    if (completeActivity != null) {
                        trackingTime += (completeActivity.getWalkTime() + completeActivity.getRunTime());
                        trackingCount += (completeActivity.getWalk() + completeActivity.getRun());
                        trackingDistance += (completeActivity.getWalkDistance() + completeActivity.getRunDistance());
                        trackingCalories += (completeActivity.getWalkCalories() + completeActivity.getRunCalories());
                    }

                    textTrackingTime.setText(String.valueOf(trackingTime));
                    textTrackingCount.setText(String.valueOf(trackingCount));
                    textTrackingDistance.setText(String.valueOf(trackingDistance));
                    textTrackingCalories.setText(String.valueOf(trackingCalories));
                }
            });
        }

        /*
        CallbackGetSleepData, 수면 데이터
         */
        @Override
        public void CallbackGetSleepData(JSONObject retVal) {

            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackGetSleepData : " + retVal.toString());

            try {
                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());
                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS)
                    inBodyBLEManager.StartBandInBodyTestWithCallback(0, 0, false, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackStartBandInBodyTest, 밴드 인바디 검사
         */
        @Override
        public void CallbackStartBandInBodyTest(JSONObject retVal) {

            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackStartBandInBodyTest : " + retVal.toString());

            try {
                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());
                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS)
                    inBodyBLEManager.StartBandHRTestWithCallback(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackStartBandHRTest, 밴드 심박 검사
         */
        @Override
        public void CallbackStartBandHRTest(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackStartBandHRTest : " + retVal.toString());

            try {
                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());
                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS) {
                    int heartRate = JsonObj.getInt("HR");
                    SharedActivityData.saveHrCount(getContext(), heartRate);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackSetProfileSync, 신상 정보 전송
         */
        @Override
        public void CallbackSetProfileSync(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetProfileSync : " + retVal.toString());
            inBodyBLEManager.SetWaitWithCallback();
        }

        /*
        CallbackSetWait, 통신중 대기
         */
        @Override
        public void CallbackSetWait(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetWait : " + retVal.toString());
            inBodyBLEManager.SetBand1SettingWithCallback(null, 0);
        }

        /*
        CallbackSetBand1Setting, 밴드1 세팅 완료
         */
        @Override
        public void CallbackSetBand1Setting(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetBand1Setting : " + retVal.toString());
            inBodyBLEManager.SetBand2SettingWithCallback(null, 0);
        }

        /*
        CallbackSetBand2Setting, 밴드2 세팅 완료
         */
        @Override
        public void CallbackSetBand2Setting(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetBand2Setting : " + retVal.toString());
            inBodyBLEManager.ConnectDisconnectWithCallback(false);
            //_InBodyBLEManager.SetBandTimeAlarmWithCallback(null, 0);
        }

        /*
        CallbackSetBandTimeAlarm, 밴드2 시간알람
         */
        @Override
        public void CallbackSetBandTimeAlarm(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetBandTimeAlarm : " + retVal.toString());
            inBodyBLEManager.SetEZtrainingWithCallback(1, 1, 5);
        }

        /*
        CallbackSetEZtraining, 이지트레이닝
         */
        @Override
        public void CallbackSetEZtraining(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetEZtraining : " + retVal.toString());
            inBodyBLEManager.ConnectDisconnectWithCallback(false);  // 연결끊기
        }

        @Override
        public void CallbackSetInBodyON(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetInBodyON : " + retVal.toString());

            try {
                final String Log = retVal.get("Log").toString();
                final String DetailLog = retVal.get("DetailLog").toString();
                int ErrorCode = retVal.getInt("ErrorCode");

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
//            ((TextView) findViewById(R.id.deviceName)).setText(Log);
//            if (!"".equals(DetailLog))
//              listAdapter.add(DetailLog);
                    }
                });

                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());
                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS) {
                    if (ErrorCode != IB_SDKConst.WEIGHTCHECK)
                        inBodyBLEManager.ConnectDisconnectWithCallback(false);  // Disconnect, 연결끊기
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void CallbackSetInBodyH20(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackSetInBodyH20 : " + retVal.toString());

            try {
                final String Log = retVal.get("Log").toString();
                final String DetailLog = retVal.get("DetailLog").toString();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
//            ((TextView) findViewById(R.id.deviceName)).setText(Log);
//            if (!"".equals(DetailLog))
//              listAdapter.add(DetailLog);
                    }
                });

                if (!"".equals(retVal.get("JsonData").toString())) {
                    inBodyBLEManager.ConnectDisconnectWithCallback(false);  // Disconnect, 연결끊기
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackGetPPGHR 부정맥 PPG 심박수 가져오기
         */
        @Override
        public void CallbackGetPPGHR(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackGetPPGHR : " + retVal.toString());

            try {
                final String Log = retVal.get("Log").toString();
                final String DetailLog = retVal.get("DetailLog").toString();

                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());

                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS)
                    inBodyBLEManager.GetEcgRawDataCntWithCallback(false);
                //_InBodyBLEManager.ConnectDisconnectWithCallback();  // Disconnect, 연결끊기

                final int remain = JsonObj.getInt("RemainData");

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
//            ((TextView) findViewById(R.id.deviceName)).setText(Log);
//            listAdapter.add("PPG Remain : " + remain);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackGetEcgRawDataCnt 부정맥 ECG RawData
         */
        @Override
        public void CallbackGetEcgRawDataCnt(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackGetEcgRawDataCnt : " + retVal.toString());

            try {
                final String Log = retVal.get("Log").toString();
                final String DetailLog = retVal.get("DetailLog").toString();

                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());
                final int DataCnt = JsonObj.getInt("DataCnt");

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
//            ((TextView) findViewById(R.id.deviceName)).setText(Log);
//            listAdapter.add("ECG Data Count : " + DataCnt);
                    }
                });

                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS)
                    if (DataCnt < 1)
                        inBodyBLEManager.ConnectDisconnectWithCallback(false);  // Disconnect, 연결끊기
                    else
                        inBodyBLEManager.GetEcgRawDataWithCallback(false, 1);

            } catch (JSONException e) {
                inBodyBLEManager.ConnectDisconnectWithCallback(false);  // Disconnect, 연결끊기
                e.printStackTrace();
            }
        }

        /*
        CallbackGetEcgRawData 부정맥 ECG RawData
         */
        @Override
        public void CallbackGetEcgRawData(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackGetEcgRawData : " + retVal.toString());

            try {
                final String Log = retVal.get("Log").toString();
                final String DetailLog = retVal.get("DetailLog").toString();

                JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());

                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS)
                    inBodyBLEManager.ConnectDisconnectWithCallback(false);  // Disconnect, 연결끊기

                final int remain = JsonObj.getInt("RemainData");

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
//            ((TextView) findViewById(R.id.deviceName)).setText(Log);
//            listAdapter.add("ECG Remain : " + remain);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        /*
        CallbackStartEcgPpg ECG PPG 측정시작
         */
        @Override
        public void CallbackStartEcgPpg(JSONObject retVal) {
            if (IB_SDKConst.ISDEBUG > 3)
                Log.d(IB_SDKConst.TAG, "CallbackStartEcgPpg : " + retVal.toString());

            try {
                final String Log = retVal.get("Log").toString();
                final String DetailLog = retVal.get("DetailLog").toString();

                final JSONObject JsonObj = new JSONObject(retVal.get("JsonData").toString());

                if (JsonObj.getInt("IsComplete") == IB_SDKConst.SUCCESS)
                    inBodyBLEManager.ConnectDisconnectWithCallback(false);  // Disconnect, 연결끊기

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void postBeginRequestData() {
        String url = "http://www.kbostat.co.kr/resource/tracking/begin";
        final StringRequest trackingRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(LOG_TAG, "postBeginRequestData response : " + response);
                try {
                    JSONObject ret = new JSONObject(response);
                    JSONObject targetExercises = ret.getJSONObject("targetExercises");
                    targetExerNo = targetExercises.getInt("targetExerNo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                enableButtonByStartActivity();
                startTimer();
                dismissLoadingDialog();
                onActivity = true;
                showToastMessage("운동을 시작합니다.");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "postBeginRequestData Error : " + error.toString());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> bodyComposition = new HashMap<>();
                bodyComposition.put("timestamp", currentTimestamp());

                Map<String, Object> beginData = new HashMap<>();
                beginData.put("exerType", "Tracking");
                beginData.put("userNo", "player01");
                beginData.put("bodyComposition", bodyComposition);

                Gson gson = new Gson();
                String jsonBody = gson.toJson(beginData);
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

    private void postTrackingData(final ActivityModel activityModel, final boolean isEnd) {
        String url = "http://www.kbostat.co.kr/resource/tracking/" + (isEnd ? "end" : "exercise");
        final StringRequest trackingRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(LOG_TAG, "postTrackingData response : " + response);
                if (getContext() != null) {
                    if (isEnd) {
                        dismissLoadingDialog();
                        endTimer();
                        enableButtonByInit();
                        onActivity = false;
                        SharedActivityData.saveActivityCount(getContext(), activityModel.getWalk() + activityModel.getRun());
                        showToastMessage("운동이 종료되었습니다.");
                    } else {
                        dismissLoadingDialog();
                        showToastMessage("운동 데이터 가져오기 성공.");
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(LOG_TAG, "poastTrackingData Error : " + error.toString());
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, Object> trackingData = new HashMap<>();
                trackingData.put("timestamp", activityModel.getDate() + " " + activityModel.getTime());
                trackingData.put("walk", activityModel.getWalk());
                trackingData.put("walkTime", activityModel.getWalkTime());
                trackingData.put("walkCalories", activityModel.getWalkCalories());
                trackingData.put("walkDistance", activityModel.getWalkDistance());

                trackingData.put("run", activityModel.getRun());
                trackingData.put("runTime", activityModel.getRunTime());
                trackingData.put("runCalories", activityModel.getRunCalories());
                trackingData.put("runDistance", activityModel.getRunDistance());

                SharedPreferences preferences = getContext().getSharedPreferences("User", Context.MODE_PRIVATE);
                String userNo = preferences.getString("userNo", "");
                int targetNo = preferences.getInt("targetNo", 0);

                Map<String, Object> root = new HashMap<>();
                root.put("exerType", "Tracking");
                root.put("userNo", userNo);
                root.put("targetNo", targetNo);
                root.put("targetExerNo", targetExerNo);
                root.put("tracking", trackingData);

                Gson gson = new Gson();
                String jsonBody = gson.toJson(root);
                return jsonBody.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json");
                return header;
            }
        };

        requestQueue.add(trackingRequest);
    }
}