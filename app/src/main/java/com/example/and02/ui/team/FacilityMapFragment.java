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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.example.and02.ui.common.SharedUserData;
import com.example.and02.ui.home.InfraModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lakue.lakuepopupactivity.PopupActivity;
import com.lakue.lakuepopupactivity.PopupGravity;
import com.lakue.lakuepopupactivity.PopupResult;
import com.lakue.lakuepopupactivity.PopupType;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FacilityMapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
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
        inflater.inflate(R.menu.facilitymap_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.home_tracker_button:
                Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("시설지도검색");
        View view = inflater.inflate(R.layout.fragment_facilitymap, container, false);

        infraModel = (InfraModel) getArguments().getSerializable("infraModel");

        /*Fragment내에서는 mapView로 지도를 실행*/
        mapView = (MapView) view.findViewById(R.id.map_fragment_facility);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this); // 비동기적 방식으로 구글 맵 실행

        Button btnTitle = view.findViewById(R.id.button_facilityMap_title);
        btnTitle.setText(infraModel.getName());
        btnTitle.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("infraModel", infraModel);
                Navigation.findNavController(view).navigate(R.id.action_facilityMapFragment_to_facilityDetailFragment, bundle);
            }
        });
//        setButton(btnTitle);

        NoboButton btnResv = view.findViewById(R.id.button_facilityMap_reservation);
        btnResv.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("reservationButton", "reservation button click");
                Context context = getContext();
                if (SharedUserData.isLogin(context)) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("infraModel", infraModel);
                    Navigation.findNavController(view).navigate(R.id.action_facilityMapFragment_to_reservationFragment, bundle);
//                    SharedUserData.logout(context);
//                    btnLogin.setText("로그인");
//                    Toast.makeText(getActivity(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        }) ;
        setButton(btnResv);
        NoboButton btnPhone = view.findViewById(R.id.button_facilityMap_phone);
        btnPhone.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel = "tel:" + infraModel.getPhoneNumber();
                Log.i("reservationButton", "call button click");
                startActivity(new Intent("android.intent.action.DIAL",Uri.parse(tel)));
            }
        }) ;
        setButton(btnPhone);
        NoboButton btnMap = view.findViewById(R.id.button_facilityMap_map);
        setButton((btnMap));


        NoboButton btnSave = view.findViewById(R.id.button_facilityMap_save);
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


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this.getActivity());

        CameraUpdate cameraUpdate;
// Updates the location and zoom of the MapView
        if (infraModel.getLatitude() == 0 || infraModel.getLongitude() == 0) {
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.23862191772334, 128.69238760279316), 14);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(35.23862191772334, 128.69238760279316))
                    .title("경상남도도청"));
        } else {
            cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(infraModel.getLatitude(), infraModel.getLongitude()), 14);
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(infraModel.getLatitude(), infraModel.getLongitude()))
                    .title(infraModel.getName()));
        }

        googleMap.animateCamera(cameraUpdate);


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

//    @Override
//    public void onMapReady(final GoogleMap googleMap) {
//
//        mapView = googleMap;
//
//        LatLng SEOUL = new LatLng(37.56, 126.97);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SEOUL);
//        markerOptions.title("서울");
//        markerOptions.snippet("한국의 수도");
//        mMap.addMarker(markerOptions);
//
//
//        // 기존에 사용하던 다음 2줄은 문제가 있습니다.
//
//        // CameraUpdateFactory.zoomTo가 오동작하네요.
//        //mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
//        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 10));
//
//
//    }
}
