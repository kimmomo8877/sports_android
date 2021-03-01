package com.example.and02.ui.home;

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
import com.example.and02.MainActivity;
import com.example.and02.R;
import com.example.and02.ui.common.ListModel;
import com.facebook.drawee.view.SimpleDraweeView;
import com.ornach.nobobutton.NoboButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FacilityDetailFragment extends Fragment {

//    private RequestQueue requestQueue;

//    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");
        // Here notify the fragment that it should participate in options menu handling.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        menu.clear();
        inflater.inflate(R.menu.home_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected","yes");
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Log.i("onOptionsItemSelected","back");
                getActivity().onBackPressed();
                return true;
            case R.id.button_tracker_action:
                Log.i("onOptionsItemSelected","tracker");
                Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facilitydetail, container, false);

        final InfraModel infraModel = (InfraModel) getArguments().getSerializable("infraModel");


        Uri uri = Uri.parse(infraModel.getAttachFile());
        ImageView iv = view.findViewById(R.id.imageView_facilityDetail_main);
        Picasso.get().load(uri).fit().centerCrop().into(iv);
//        iv.setImageURI(uri);
        Log.i("imageload", String.valueOf(uri));

        NoboButton btnResv = view.findViewById(R.id.button_facilityDetail_reservation);
        btnResv.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("reservationButton", "reservation button click");
            }
        }) ;
        setButton(btnResv);
        NoboButton btnPhone = view.findViewById(R.id.button_facilityDetail_phone);
        btnPhone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = "tel:" + infraModel.getPhoneNumber();
                Log.i("reservationButton", "call button click");
                startActivity(new Intent("android.intent.action.CALL",Uri.parse(tel)));
            }
        }) ;
        setButton(btnPhone);
        NoboButton btnMap = view.findViewById(R.id.button_facilityDetail_map);
        setButton((btnMap));
        NoboButton btnSave = view.findViewById(R.id.button_facilityDetail_save);
        setButton((btnSave));

        TextView tvTitle = view.findViewById(R.id.textView_facilityDetail_title);
        tvTitle.setText(infraModel.getName());

        TextView tvAddress = view.findViewById(R.id.textView_facilityDetail_adderssDetail);
        tvAddress.setText(infraModel.getAddress());

        TextView tvHomepage = view.findViewById(R.id.textView_facilityDetail_homepageDetail);
        tvHomepage.setText(infraModel.getHomepageUrl());

        TextView tvPhone = view.findViewById(R.id.textView_facilityDetail_phoneDetail);
        tvPhone.setText(infraModel.getPhoneNumber());

        TextView tvSport = view.findViewById(R.id.textView_facilityDetail_sportDetail);
        tvSport.setText(infraModel.getFacilityDescription());

        TextView tvFacility = view.findViewById(R.id.textView_facilityDetail_facilityDetail);
        tvFacility.setText(infraModel.getEquipmentDescription());

        TextView tvEtc = view.findViewById(R.id.textView_facilityDetail_etcDetail);
        tvEtc.setText(infraModel.getEtcDescription());

        TextView tvLocation = view.findViewById(R.id.textView_facilityDetail_locationDetail);
        tvLocation.setText(infraModel.getAddress());


//        requestQueue = Volley.newRequestQueue(view.getContext());
//        doHttpRequest();

        return view;
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