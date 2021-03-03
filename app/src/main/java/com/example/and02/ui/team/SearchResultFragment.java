package com.example.and02.ui.team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.and02.R;
import com.example.and02.ui.common.UserModel;
import com.example.and02.ui.home.InfraCategoryModel;
import com.example.and02.ui.home.InfraModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment {

    private RecyclerView searchFacilityRecyclerView;
    private RecyclerView searchTeamRecyclerView;
    private RequestQueue requestQueue;

    private SearchFacilityAdapter searchFacilityAdapter;
    private SearchTeamAdapter searchTeamAdapter;
    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";

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
        inflater.inflate(R.menu.searchresult_nav_menu, menu);
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
        View view = inflater.inflate(R.layout.fragment_searchresult, container, false);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(view.getContext());

        requestQueue = Volley.newRequestQueue(view.getContext());

        Button btnFacility = view.findViewById(R.id.button_searchResult_facility);
        btnFacility.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
//                View view = inflater.inflate(R.layout.fragment_searchresult, container, false);

            }
        });

        Button btnTeam = view.findViewById(R.id.button_searchResult_team);
        btnTeam.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        searchFacilityRecyclerView = view.findViewById(R.id.recycler_searchResult_facility);
        searchFacilityRecyclerView.setHasFixedSize(true);
        searchFacilityRecyclerView.setLayoutManager(mLayoutManager1);
        doHttpRequestFacility();


        searchTeamRecyclerView = view.findViewById(R.id.recycler_searchResult_team);
        searchTeamRecyclerView.setHasFixedSize(true);
        searchTeamRecyclerView.setLayoutManager(mLayoutManager2);
        doHttpRequestTeam();


        return view;
    }

    private void doHttpRequestFacility() {

        String url = "http://www.kbostat.co.kr/resource/infra?searchWord=" + getArguments().getString("searchWord");;
        Log.i("url_facility", url);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
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

                searchFacilityAdapter = new SearchFacilityAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
//                searchFacilityAdapter.setFavoriteModelList_orig(result_orig);
                searchFacilityRecyclerView.setAdapter(searchFacilityAdapter);
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

    private void doHttpRequestTeam() {

        String url = "http://www.kbostat.co.kr/resource/team?searchWord=" + getArguments().getString("searchWord");;
        Log.i("url_team", url);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null)
                {
                    try {
                        response=new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<TeamModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (setTeamModel(data) != null) {
                            result.add(setTeamModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                searchTeamAdapter = new SearchTeamAdapter(result);
                List<TeamModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
//                searchFacilityAdapter.setFavoriteModelList_orig(result_orig);
                searchTeamRecyclerView.setAdapter(searchTeamAdapter);
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
        if (infraModel.getAttachFiles().length() > 0) {
            JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
            StringBuilder sb = new StringBuilder(imageUrl);
            sb.append(attachObject.getString("saveFilePath"));
            infraModel.setAttachFile(sb.toString());
            return infraModel;
        } else {
            return infraModel;
        }

    }

    private TeamModel setTeamModel(JSONObject data) throws JSONException {

        TeamModel teamModel = new TeamModel();

//        JSONObject userObject = data.getJSONObject("userModel");
//        UserModel userModel = new UserModel();
//        userModel.setName(userObject.getString("name"));
//        userModel.setUserNo(userObject.getString("userNo"));

        teamModel.setTeamNo(data.getString("teamNo"));
        teamModel.setName(data.getString("name"));
        teamModel.setPhoneNumber(data.getString("phoneNumber"));
        teamModel.setHomepageUrl(data.getString("homepageUrl"));
        teamModel.setRegisteApprove(data.getBoolean("registeApprove"));
//        teamModel.setClassificationCodeId(data.getInt("classificationCodeId"));
//        teamModel.setBelongedCodeId(data.getInt("belongedCodeId"));
//        teamModel.setGenderCodeId(data.getInt("genderCodeId"));
//        teamModel.setRegionCodeId(data.getInt("regionCodeId"));
//        teamModel.setSportCodeId(data.getInt("sportCodeId"));

        return teamModel;

//        if (infraModel.getAttachFiles().length() > 0) {
//            JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
//            StringBuilder sb = new StringBuilder(imageUrl);
//            sb.append(attachObject.getString("saveFilePath"));
//            infraModel.setAttachFile(sb.toString());
//            return infraModel;
//        } else {
//            return infraModel;
//        }

    }

}



// http://www.kbostat.co.kr/resource/infra?serachWord=searchWord"
// http://www.kbostat.co.kr/resource/team?serachWord=searchWord"