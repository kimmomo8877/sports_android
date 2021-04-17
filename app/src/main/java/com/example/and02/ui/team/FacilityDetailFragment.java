package com.example.and02.ui.team;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.and02.LoginActivity;
import com.example.and02.MainActivity;
import com.example.and02.R;
import com.example.and02.ui.common.ListModel;
import com.example.and02.ui.common.SharedUserData;
import com.example.and02.ui.home.InfraModel;
import com.facebook.drawee.view.SimpleDraweeView;
import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;
import com.ornach.nobobutton.NoboButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class FacilityDetailFragment extends Fragment {

//    private RequestQueue requestQueue;
//    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";

    private InfraModel infraModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        menu.clear();
        inflater.inflate(R.menu.facilitydetail_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.home:
                Log.i("onOptionsItemSelected", "back");
                getActivity().onBackPressed();
                return true;
            case R.id.button_tracker_action:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Calls Icon Click", LENGTH_SHORT).show();
                return true;
            default:
                Log.i("itemId", String.valueOf(item.getItemId()));
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("시설상세");
        View view = inflater.inflate(R.layout.fragment_facilitydetail, container, false);

        infraModel = (InfraModel) getArguments().getSerializable("infraModel");

        if (infraModel.getAttachFile() != null) {
            Uri uri = Uri.parse(infraModel.getAttachFile());
            ImageView iv = view.findViewById(R.id.imageView_facilityDetail_main);
            Picasso.get().load(uri).fit().centerCrop().into(iv);
//        iv.setImageURI(uri);
            Log.i("imageload", String.valueOf(uri));
        }
        NoboButton btnResv = view.findViewById(R.id.button_facilityDetail_reservation);
        btnResv.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("reservationButton", "reservation button click");
                Context context = getContext();
                if (SharedUserData.isLogin(context)) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("infraModel", infraModel);
                    Navigation.findNavController(view).navigate(R.id.action_facilityDetailFragment_to_reservationFragment, bundle);
//                    SharedUserData.logout(context);
//                    btnLogin.setText("로그인");
//                    Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        setButton(btnResv);
        NoboButton btnPhone = view.findViewById(R.id.button_facilityDetail_phone);
        btnPhone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = "tel:" + infraModel.getPhoneNumber();
                Log.i("phone call", tel);
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
            }
        });
        setButton(btnPhone);

        NoboButton btnMap = view.findViewById(R.id.button_facilityDetail_map);
        btnMap.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MapButton", "Map button click");
                if (Double.isNaN(infraModel.getLatitude()) && Double.isNaN(infraModel.getLongitude())) {
                    Toast.makeText(getActivity(), "위치 정보가 등록 되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("infraModel", infraModel);
                    Navigation.findNavController(view).navigate(R.id.action_facilityDetailFragment_to_facilityMapFragment, bundle);
                }
            }
        });
        setButton((btnMap));

        NoboButton btnSave = view.findViewById(R.id.button_facilityDetail_save);
        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(Intent.ACTION_VIEW)
//                        .setData(Uri.parse(infraModel.getFirstPrVideoUrl()))
//                        .setPackage("com.google.android.youtube"));
                Intent intent = new Intent(getContext(), PopupActivity.class);
                intent.putExtra("type", PopupType.SELECT);
                intent.putExtra("gravity", PopupGravity.LEFT);
                intent.putExtra("title", "홍보영상");
                intent.putExtra("content", "시청을 원하는 영상을 선택하세요");
                intent.putExtra("buttonLeft", "영상(1)");
                intent.putExtra("buttonRight", "영상(2)");
                startActivityForResult(intent, 1);
            }
        });
        setButton((btnSave));

        TextView tvTitle = view.findViewById(R.id.textView_facilityDetail_title);
        if (!infraModel.getName().equals("null")) {
            tvTitle.setText(infraModel.getName());
        }

        TextView tvAddress = view.findViewById(R.id.textView_facilityDetail_adderssDetail);
        if (!infraModel.getAddress().equals("null")) {
            tvAddress.setText(infraModel.getAddress());
        }

        TextView tvHomepage = view.findViewById(R.id.textView_facilityDetail_homepageDetail);
        if (!infraModel.getHomepageUrl().equals("null")) {
            tvHomepage.setText(infraModel.getHomepageUrl());
        }

        TextView tvPhone = view.findViewById(R.id.textView_facilityDetail_phoneDetail);
        if (!infraModel.getPhoneNumber().equals("null")) {
            tvPhone.setText(infraModel.getPhoneNumber());
        }

        TextView tvSport = view.findViewById(R.id.textView_facilityDetail_sportDetail);
        if (!infraModel.getFacilityDescription().equals("null")) {
            tvSport.setText(infraModel.getFacilityDescription());
        }
        TextView tvFacility = view.findViewById(R.id.textView_facilityDetail_facilityDetail);
        if (!infraModel.getEquipmentDescription().equals("null")) {
            tvFacility.setText(infraModel.getEquipmentDescription());
        }
        TextView tvEtc = view.findViewById(R.id.textView_facilityDetail_etcDetail);
        if (!infraModel.getEtcDescription().equals("null")) {
            tvEtc.setText(infraModel.getEtcDescription());
        }

        TextView tvLocation = view.findViewById(R.id.textView_facilityDetail_locationDetail);
        if (!infraModel.getAddress().equals("null")) {
            tvLocation.setText(infraModel.getAddress());
        }


//        requestQueue = Volley.newRequestQueue(view.getContext());
//        doHttpRequest();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
        if (requestCode == 1) {
            PopupResult result = (PopupResult) data.getSerializableExtra("result");
            if (result == PopupResult.LEFT) {
                if (!infraModel.getFirstPrVideoUrl().equals("null")) {
                    startActivity(new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse(infraModel.getFirstPrVideoUrl()))
                            .setPackage("com.google.android.youtube"));
                } else {
                    Toast.makeText(getActivity(), "홍보영상이 등록되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                }

            } else if (result == PopupResult.RIGHT) {
                if (!infraModel.getSecondPrVideoUrl().equals("null")) {
                    startActivity(new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse(infraModel.getSecondPrVideoUrl()))
                            .setPackage("com.google.android.youtube"));
                } else {
                    Toast.makeText(getActivity(), "홍보영상이 등록되어 있지 않습니다.", Toast.LENGTH_LONG).show();
                }

            }
        }
//        }
    }

    public void setButton(NoboButton button) {
        button.setTextColor(Color.BLACK);
//        button.setAllCaps(true);
//        button.setIconSize(5);
//        button.setFontIcon("\uf138");
        button.setIconPosition(NoboButton.POSITION_TOP);
        button.setBackgroundColor(Color.WHITE);
        button.setFocusColor(Color.GRAY);
        button.setBorderColor(Color.BLACK);
        button.setBorderWidth(2);
        button.setRadius(10);
    }

//    private void doHttpRequest() {
//        String url = "http://www.kbostat.co.kr/resource/infra/" + getArguments().getString("infraNo");
//        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                if (response != null)
//                {
//                    try {
//                        response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//                List<InfraModel> result = new ArrayList<>();
//                try {
//                    JSONArray root = new JSONArray(response);
//                    for (int i = 0; i < root.length(); i++) {
//                        JSONObject data = root.getJSONObject(i);
//                        InfraModel infraModel = new InfraModel();
//                        infraModel.setName(data.getString("name"));
//                        infraModel.setAttachFiles(data.getJSONArray("attachFiles"));
//                        if (infraModel.getAttachFiles().length() > 0) {
//                            JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
//                            StringBuilder sb = new StringBuilder(imageUrl);
//                            sb.append(attachObject.getString("saveFilePath"));
//                            infraModel.setAttachFile(sb.toString());
//                            result.add(infraModel);
//                        }
////            result.add(infraModel);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Log.i("TEST", response);
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("Error", String.valueOf(error));
//            }
//        });
//
//        requestQueue.add(request);
//    }
}