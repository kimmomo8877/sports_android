package com.hiball.gssc.ui.team;

import android.app.ActionBar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hiball.gssc.LoginActivity;
import com.hiball.gssc.MainActivity;
import com.hiball.gssc.R;
import com.hiball.gssc.ui.common.ChargeModel;
import com.hiball.gssc.ui.common.CodeModel;
import com.hiball.gssc.ui.common.ListModel;
import com.hiball.gssc.ui.common.SharedUserData;
import com.hiball.gssc.ui.home.HomeAdapter;
import com.hiball.gssc.ui.home.InfraCategoryModel;
import com.hiball.gssc.ui.home.InfraModel;
import com.hiball.gssc.ui.home.IntroAdapter;
import com.hiball.gssc.ui.home.IntroModel;
import com.hiball.gssc.ui.home.ListAdapter;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TeamFragment extends Fragment {

    private RecyclerView sportRecyclerView;
    private RecyclerView sportMenuRecyclerView;
    private RecyclerView facilityRecyclerView;
    private RecyclerView facilityMenuRecyclerView;
    private RecyclerView teamStoryRecyclerView;
    private RecyclerView teamMenuRecyclerView;
    private RecyclerView facilityDistanceRecyclerView;
    private RecyclerView facilityDistanceMenuRecyclerView;
    private RecyclerView hotelDistanceRecyclerView;
    private RecyclerView hotelDistanceMenuRecyclerView;
    private RecyclerView foodDistanceRecyclerView;
    private RecyclerView foodDistanceMenuRecyclerView;
    private RecyclerView saveRecyclerView;

    private RequestQueue requestQueue;

    private TeamAdapter sportAdapter;
    private ListAdapter sportMenuAdapter;
    private List<ListModel> sportMenuResult = new ArrayList<>();
    private TeamAdapter facilityAdapter;
    private ListAdapter facilityMenuAdapter;
    private List<ListModel> facilityMenuResult = new ArrayList<>();

    private TeamAdapter facilityDistanceAdapter;
    private ListAdapter facilityDistanceMenuAdapter;
    private List<ListModel> facilityDistanceMenuResult = new ArrayList<>();
    private TeamAdapter hotelDistanceAdapter;
    private ListAdapter hotelDistanceMenuAdapter;
    private List<ListModel> hotelDistanceMenuResult = new ArrayList<>();
    private TeamAdapter foodDistanceAdapter;
    private ListAdapter foodDistanceMenuAdapter;
    private List<ListModel> foodDistanceMenuResult = new ArrayList<>();
    private TeamAdapter saveAdapter;
    private List<ListModel> saveMenuResult = new ArrayList<>();

    private ReservationModel reservationModel;


    private MainActivity mainActivity;
    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");
        Context context = getContext();
        if (SharedUserData.isLogin(context)) {
//            Navigation.findNavController().navigate(R.id.action_navigation_team_to_searchFragment);
            Log.i("move", "Fragment TeamLogined");

        } else {
//            Intent intent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(intent);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.team_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected","yes");
        switch (item.getItemId()) {
            case R.id.home_tracker_button:
                Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.button_tracker_action:
                Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("전지훈련팀");
        View view = inflater.inflate(R.layout.fragment_team, container, false);

        NoboButton btnSearch = view.findViewById(R.id.button_team_search);

        btnSearch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("searchButton", "search button click");
                Navigation.findNavController(view).navigate(R.id.action_navigation_team_to_searchFragment);
            }
        }) ;

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(view.getContext());

        LinearLayoutManager horizontalLayout1
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout2
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout3
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout4
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);


        requestQueue = Volley.newRequestQueue(view.getContext());

        sportMenuRecyclerView = view.findViewById(R.id.recycler_fragment_teamSportMenu);
        sportMenuRecyclerView.setHasFixedSize(true);
        sportMenuRecyclerView.setLayoutManager(mLayoutManager1);
        sportMenuRecyclerView.setLayoutManager(horizontalLayout1);
        doHttpRequestSportMenu();

        sportRecyclerView = view.findViewById(R.id.recycler_fragment_teamSport);
        sportRecyclerView.setHasFixedSize(true);
        sportRecyclerView.setLayoutManager(mLayoutManager2);
        sportRecyclerView.setLayoutManager(horizontalLayout2);
        doHttpRequestSport();

        facilityMenuRecyclerView = view.findViewById(R.id.recycler_fragment_teamFacilityMenu);
        facilityMenuRecyclerView.setHasFixedSize(true);
        facilityMenuRecyclerView.setLayoutManager(mLayoutManager3);
        facilityMenuRecyclerView.setLayoutManager(horizontalLayout3);
        doHttpRequestFacilityMenu();

        facilityRecyclerView = view.findViewById(R.id.recycler_fragment_teamFacility);
        facilityRecyclerView.setHasFixedSize(true);
        facilityRecyclerView.setLayoutManager(mLayoutManager4);
        facilityRecyclerView.setLayoutManager(horizontalLayout4);
        doHttpRequestFacility();

        TextView sportTextView = view.findViewById(R.id.textView_fragment_teamSport);
        TextView facilityTextView = view.findViewById(R.id.textView_fragment_teamFacility);
//        TextView storyTextView = view.findViewById(R.id.textView_fragment_teamStory);

        // Logined 되었을 때 행위
//        NoboButton btnReservation = view.findViewById(R.id.button_team_reservation);

        RecyclerView.LayoutManager mLayoutManager7 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager8 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager9 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager11 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager12 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager13 = new LinearLayoutManager(view.getContext());

        LinearLayoutManager horizontalLayout7
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout8
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout9
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout10
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout11
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout12
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout13
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);




        facilityDistanceMenuRecyclerView = view.findViewById(R.id.recycler_fragment_teamFacilityDistanceMenu);
        facilityDistanceMenuRecyclerView.setHasFixedSize(true);
        facilityDistanceMenuRecyclerView.setLayoutManager(mLayoutManager7);
        facilityDistanceMenuRecyclerView.setLayoutManager(horizontalLayout7);
        doHttpRequestFacilityDistanceMenu();

        facilityDistanceRecyclerView = view.findViewById(R.id.recycler_fragment_teamFacilityDistance);
        facilityDistanceRecyclerView.setHasFixedSize(true);
        facilityDistanceRecyclerView.setLayoutManager(mLayoutManager8);
        facilityDistanceRecyclerView.setLayoutManager(horizontalLayout8);
        doHttpRequestFacilityDistance();

        hotelDistanceMenuRecyclerView = view.findViewById(R.id.recycler_fragment_teamHotelDistanceMenu);
        hotelDistanceMenuRecyclerView.setHasFixedSize(true);
        hotelDistanceMenuRecyclerView.setLayoutManager(mLayoutManager9);
        hotelDistanceMenuRecyclerView.setLayoutManager(horizontalLayout9);
        doHttpRequestHotelDistanceMenu();

        hotelDistanceRecyclerView = view.findViewById(R.id.recycler_fragment_teamHotelDistance);
        hotelDistanceRecyclerView.setHasFixedSize(true);
        hotelDistanceRecyclerView.setLayoutManager(mLayoutManager10);
        hotelDistanceRecyclerView.setLayoutManager(horizontalLayout10);
        doHttpRequestHotelDistance();

        foodDistanceMenuRecyclerView = view.findViewById(R.id.recycler_fragment_teamFoodDistanceMenu);
        foodDistanceMenuRecyclerView.setHasFixedSize(true);
        foodDistanceMenuRecyclerView.setLayoutManager(mLayoutManager11);
        foodDistanceMenuRecyclerView.setLayoutManager(horizontalLayout11);
        doHttpRequestFoodDistanceMenu();

        foodDistanceRecyclerView = view.findViewById(R.id.recycler_fragment_teamFoodDistance);
        foodDistanceRecyclerView.setHasFixedSize(true);
        foodDistanceRecyclerView.setLayoutManager(mLayoutManager12);
        foodDistanceRecyclerView.setLayoutManager(horizontalLayout12);
        doHttpRequestFoodDistance();

//        saveRecyclerView = view.findViewById(R.id.recycler_fragment_teamSave);
//        saveRecyclerView.setHasFixedSize(true);
//        saveRecyclerView.setLayoutManager(mLayoutManager13);
//        saveRecyclerView.setLayoutManager(horizontalLayout13);
//        doHttpRequestSave();

        TextView facilityTextViewDistanceTextView = view.findViewById(R.id.textView_fragment_teamFacilityDistance);
        TextView hotelTextViewDistanceTextView = view.findViewById(R.id.textView_fragment_teamHotelDistance);
        TextView foodTextViewDistanceTextView = view.findViewById(R.id.textView_fragment_teamFoodDistance);
        TextView saveTextViewDistanceTextView = view.findViewById(R.id.textView_fragment_teamSave);

        ImageView reservationImageView = view.findViewById(R.id.imageView_team_reservationImage);
        TextView reservationTitle = view.findViewById(R.id.textView_team_reservationTitle);
        TextView reservationText = view.findViewById(R.id.textView_team_reservationText);
        ImageView reservationImageView2 = view.findViewById(R.id.imageView_team_reservationImage2);


        Context context = getContext();
        String userNo = SharedUserData.getUserNo(context);

        String teamNo = new String();
        doHttpRequestReservation(teamNo, view);

        if (isLogined()) {
            btnSearch.setVisibility(View.GONE);
            sportMenuRecyclerView.setVisibility(View.GONE);
            sportRecyclerView.setVisibility(View.GONE);
            facilityMenuRecyclerView.setVisibility(View.GONE);
            facilityRecyclerView.setVisibility(View.GONE);
            sportTextView.setVisibility(View.GONE);
            facilityTextView.setVisibility(View.GONE);
//            storyTextView.setVisibility(View.GONE);
        } else {
            reservationImageView.setVisibility(View.GONE);
            reservationTitle.setVisibility(View.GONE);
            reservationText.setVisibility(View.GONE);
            reservationImageView2.setVisibility(View.GONE);
            facilityDistanceMenuRecyclerView.setVisibility(View.GONE);
            facilityDistanceRecyclerView.setVisibility(View.GONE);
            hotelDistanceMenuRecyclerView.setVisibility(View.GONE);
            hotelDistanceRecyclerView.setVisibility(View.GONE);
            foodDistanceMenuRecyclerView.setVisibility(View.GONE);
            foodDistanceRecyclerView.setVisibility(View.GONE);
            facilityTextViewDistanceTextView.setVisibility(View.GONE);
            hotelTextViewDistanceTextView.setVisibility(View.GONE);
            foodTextViewDistanceTextView.setVisibility(View.GONE);
            saveTextViewDistanceTextView.setVisibility(View.GONE);

        }

        return view;
    }

    private void doHttpRequestSport() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=1";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<InfraModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (setInfraModel(data) != null) {
                            result.add(setInfraModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sportAdapter = new TeamAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                sportAdapter.setInfraModelList_orig(result_orig);
                sportRecyclerView.setAdapter(sportAdapter);
                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFacility() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=2";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<InfraModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (setInfraModel(data) != null) {
                            result.add(setInfraModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                facilityAdapter = new TeamAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                facilityAdapter.setInfraModelList_orig(result_orig);
                facilityRecyclerView.setAdapter(facilityAdapter);

                Log.i("TEST", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestSportMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/1";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
//        List<ListModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    sportMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        sportMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sportMenuAdapter = new ListAdapter(sportMenuResult);

                sportMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = sportMenuResult.get(position);
                        sportAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;
                Log.i("TeamFragment", response);

                sportMenuRecyclerView.setAdapter(sportMenuAdapter);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFacilityMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/2";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
//        List<ListModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    facilityMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        facilityMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                facilityMenuAdapter = new ListAdapter(facilityMenuResult);

                facilityMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = facilityMenuResult.get(position);
                        facilityAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;

                facilityMenuRecyclerView.setAdapter(facilityMenuAdapter);

                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestReservation(String teamNo, final View view) {

        String url = "http://www.kbostat.co.kr/resource/reservation/team/" + "masanuniv";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<ReservationModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (setReservationModel(data) != null) {
                            result.add(setReservationModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                reservationModel = result.get(0);
                ImageView reservationImage = view.findViewById(R.id.imageView_team_reservationImage);
                reservationImage.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("searchButton", "search button click");
                        Navigation.findNavController(view).navigate(R.id.action_navigation_team_to_searchFragment);
                    }
                }) ;
                ImageView reservationImage2 = view.findViewById(R.id.imageView_team_reservationImage2);
                reservationImage2.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("searchButton", "search button click");
                        Navigation.findNavController(view).navigate(R.id.action_navigation_team_to_searchFragment);
                    }
                }) ;

                TextView reservationTitle = view.findViewById(R.id.textView_team_reservationTitle);
                TextView reservationText = view.findViewById(R.id.textView_team_reservationText);
                reservationTitle.setText(reservationModel.getInfra().getName());
                reservationText.setText(reservationModel.getStartDate() + " ~ " + reservationModel.getEndDate());

//                sportAdapter = new TeamAdapter(result);
//                List<InfraModel> result_orig = new ArrayList<>();
//                result_orig.addAll(result);
//                sportAdapter.setInfraModelList_orig(result_orig);
//                sportRecyclerView.setAdapter(sportAdapter);
                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFacilityDistance() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=2";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<InfraModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (setInfraModel(data) != null) {
                            result.add(setInfraModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                facilityDistanceAdapter = new TeamAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                facilityDistanceAdapter.setInfraModelList_orig(result_orig);
                facilityDistanceRecyclerView.setAdapter(facilityDistanceAdapter);

                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFacilityDistanceMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/2";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    facilityDistanceMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        facilityDistanceMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                facilityDistanceMenuAdapter = new ListAdapter(facilityDistanceMenuResult);

                facilityDistanceMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = facilityDistanceMenuResult.get(position);
                        facilityDistanceAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;
                Log.i("TeamFragment", response);

                facilityDistanceMenuRecyclerView.setAdapter(facilityDistanceMenuAdapter);

                Log.i("TEST", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestHotelDistance() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=16";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<InfraModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (setInfraModel(data) != null) {
                            result.add(setInfraModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hotelDistanceAdapter = new TeamAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                hotelDistanceAdapter.setInfraModelList_orig(result_orig);
                hotelDistanceRecyclerView.setAdapter(hotelDistanceAdapter);

                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestHotelDistanceMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/16";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    hotelDistanceMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        hotelDistanceMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hotelDistanceMenuAdapter = new ListAdapter(hotelDistanceMenuResult);

                hotelDistanceMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = hotelDistanceMenuResult.get(position);
                        hotelDistanceAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;

                hotelDistanceMenuRecyclerView.setAdapter(hotelDistanceMenuAdapter);

                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFoodDistance() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=17";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<InfraModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (setInfraModel(data) != null) {
                            result.add(setInfraModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                foodDistanceAdapter = new TeamAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                foodDistanceAdapter.setInfraModelList_orig(result_orig);
                foodDistanceRecyclerView.setAdapter(foodDistanceAdapter);

                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFoodDistanceMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/17";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response = new String(response.getBytes("UTF-8"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    foodDistanceMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        foodDistanceMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                foodDistanceMenuAdapter = new ListAdapter(foodDistanceMenuResult);

                foodDistanceMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = foodDistanceMenuResult.get(position);
                        foodDistanceAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;

                foodDistanceMenuRecyclerView.setAdapter(foodDistanceMenuAdapter);

                Log.i("TeamFragment", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private InfraModel setInfraModel(JSONObject data) throws JSONException {

        InfraModel infraModel = new InfraModel();
        infraModel.setInfraNo(data.getString("infraNo"));
        infraModel.setName(data.getString("name"));
        infraModel.setAddress(data.getString("address"));
        infraModel.setPhoneNumber(data.getString("phoneNumber"));
        infraModel.setHomepageUrl(data.getString("homepageUrl"));
        infraModel.setFacilityDescription(data.getString("facilityDescription"));
        infraModel.setEquipmentDescription(data.getString("equipmentDescription"));
        infraModel.setEtcDescription(data.getString("etcDescription"));
        infraModel.setAttachFiles(data.getJSONArray("attachFiles"));
        JSONObject infraCategoryObject = data.getJSONObject("infraCategory");
        InfraCategoryModel infraCategoryModel = new InfraCategoryModel();
        infraCategoryModel.setName(infraCategoryObject.getString("name"));
        infraModel.setInfraCategoryModel(infraCategoryModel);
        infraModel.setFirstPrVideoUrl(data.getString("firstPrVideoUrl"));
        infraModel.setSecondPrVideoUrl(data.getString("secondPrVideoUrl"));
        infraModel.setThirdPrVideoUrl(data.getString("thirdPrVideoUrl"));

        infraModel.setCharges(data.getJSONArray("charges"));
        if (infraModel.getCharges().length() > 0) {
            ArrayList<ChargeModel> charges = new ArrayList<ChargeModel>();
            for (int i = 0; i < infraModel.getCharges().length(); i++) {
                JSONObject chargeObject = infraModel.getCharges().getJSONObject(i);
                ChargeModel chargeModel = new ChargeModel();
                chargeModel.setCost(chargeObject.getInt("cost"));
                CodeModel codeModel = new CodeModel();
                codeModel.setName(chargeObject.getJSONObject("chargeTypeCode").getString("name"));
                chargeModel.setChargeTypeCode(codeModel);
                charges.add(chargeModel);
            }
            infraModel.setChargesModel((ChargeModel[]) charges.toArray());

        }

        if (infraModel.getAttachFiles().length() > 0) {
            JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
            StringBuilder sb = new StringBuilder(imageUrl);
            sb.append(attachObject.getString("saveFilePath"));
            infraModel.setAttachFile(sb.toString());
            return infraModel;
        }

        return infraModel;
    }

    private ReservationModel setReservationModel(JSONObject data) throws JSONException {

        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setStartDate(data.optString("startDate"));
        reservationModel.setEndDate(data.optString("endDate"));
        JSONObject infraObject = data.getJSONObject("infra");
        InfraModel infraModel = new InfraModel();
        infraModel.setLatitude(infraObject.optDouble("latitude"));
        infraModel.setLongitude(infraObject.optDouble("longitude"));
        infraModel.setName(infraObject.optString("name"));
        infraModel.setAttachFiles(infraObject.getJSONArray("attachFiles"));

        if (infraModel.getAttachFiles().length() > 0) {
            JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
            StringBuilder sb = new StringBuilder(imageUrl);
            sb.append(attachObject.getString("saveFilePath"));
            infraModel.setAttachFile(sb.toString());
        }

        reservationModel.setInfra(infraModel);
//        infraModel.setName(data.getString("name"));
//        infraModel.setAddress(data.getString("address"));
//        infraModel.setPhoneNumber(data.getString("phoneNumber"));
//        infraModel.setHomepageUrl(data.getString("homepageUrl"));
//        infraModel.setFacilityDescription(data.getString("facilityDescription"));
//        infraModel.setEquipmentDescription(data.getString("equipmentDescription"));
//        infraModel.setEtcDescription(data.getString("etcDescription"));
//        infraModel.setAttachFiles(data.getJSONArray("attachFiles"));
//        JSONObject infraCategoryObject = data.getJSONObject("infraCategory");
//        InfraCategoryModel infraCategoryModel = new InfraCategoryModel();
//        infraCategoryModel.setName(infraCategoryObject.getString("name"));
//        infraModel.setInfraCategoryModel(infraCategoryModel);
//        infraModel.setFirstPrVideoUrl(data.getString("firstPrVideoUrl"));
//        infraModel.setSecondPrVideoUrl(data.getString("secondPrVideoUrl"));
//        infraModel.setThirdPrVideoUrl(data.getString("thirdPrVideoUrl"));
//
//        infraModel.setCharges(data.getJSONArray("charges"));
//        if (infraModel.getCharges().length() > 0) {
//            ArrayList<ChargeModel> charges = new ArrayList<ChargeModel>();
//            for (int i = 0; i < infraModel.getCharges().length(); i++) {
//                JSONObject chargeObject = infraModel.getCharges().getJSONObject(i);
//                ChargeModel chargeModel = new ChargeModel();
//                chargeModel.setCost(chargeObject.getInt("cost"));
//                CodeModel codeModel = new CodeModel();
//                codeModel.setName(chargeObject.getJSONObject("chargeTypeCode").getString("name"));
//                chargeModel.setChargeTypeCode(codeModel);
//                charges.add(chargeModel);
//            }
//            infraModel.setChargesModel((ChargeModel[]) charges.toArray());
//
//        }
//
//        if (infraModel.getAttachFiles().length() > 0) {
//            JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
//            StringBuilder sb = new StringBuilder(imageUrl);
//            sb.append(attachObject.getString("saveFilePath"));
//            infraModel.setAttachFile(sb.toString());
//            return infraModel;
//        }

        return reservationModel;
    }

    private boolean isLogined() {
        Context context = getContext();
        if (SharedUserData.isLogin(context)) {
            return true;
        } else {
            return false;
        }
    }

}
