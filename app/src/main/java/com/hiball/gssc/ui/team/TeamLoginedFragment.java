package com.hiball.gssc.ui.team;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hiball.gssc.MainActivity;
import com.hiball.gssc.R;
import com.hiball.gssc.ui.common.ChargeModel;
import com.hiball.gssc.ui.common.CodeModel;
import com.hiball.gssc.ui.common.ListModel;
import com.hiball.gssc.ui.home.InfraCategoryModel;
import com.hiball.gssc.ui.home.InfraModel;
import com.hiball.gssc.ui.home.ListAdapter;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TeamLoginedFragment extends Fragment {

    private RecyclerView sportRecyclerView;
    private RecyclerView sportMenuRecyclerView;
    private RecyclerView facilityRecyclerView;
    private RecyclerView facilityMenuRecyclerView;
    private RecyclerView teamStoryRecyclerView;
    private RecyclerView teamMenuRecyclerView;
    private RequestQueue requestQueue;

    private TeamAdapter sportAdapter;
    private ListAdapter sportMenuAdapter;
    private List<ListModel> sportMenuResult = new ArrayList<>();
    private TeamAdapter facilityAdapter;
    private ListAdapter facilityMenuAdapter;
    private List<ListModel> facilityMenuResult = new ArrayList<>();

    private MainActivity mainActivity;
    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");


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
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("전지훈련팀로긴");
        View view = inflater.inflate(R.layout.fragment_team, container, false);


        NoboButton btnSearch = view.findViewById(R.id.button_team_search);
        btnSearch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String tel = "tel:" + infraModel.getPhoneNumber();
                Log.i("searchButton", "search button click");
//                Navigation.findNavController(view).navigate(R.id.action_navigation_team_to_searchFragment);
            }
        }) ;


        RecyclerView.LayoutManager mLayoutManager0 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager5 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager6 = new LinearLayoutManager(view.getContext());


        LinearLayoutManager horizontalLayout0
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout1
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout2
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout3
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout4
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout5
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout6
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
                Log.i("TeamLogin", response);

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

                Log.i("TeamLogin", response);

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
                Log.i("TeamLogin", response);

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
}