package com.example.and02.ui.touring;

import android.graphics.Color;
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
import com.example.and02.MainActivity;
import com.example.and02.R;
import com.example.and02.ui.common.ListModel;
import com.example.and02.ui.home.InfraCategoryModel;
import com.example.and02.ui.home.InfraModel;
import com.example.and02.ui.home.ListAdapter;
import com.example.and02.ui.team.TeamAdapter;
import com.ornach.nobobutton.NoboButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TouringFragment extends Fragment {

    private RecyclerView sportRRecyclerView;
    private RecyclerView sportRMenuRecyclerView;
    private RecyclerView festivalRRecyclerView;
    private RecyclerView festivalRMenuRecyclerView;
    private RecyclerView travelRRecyclerView;
    private RecyclerView travelRMenuRecyclerView;
    private RecyclerView hotelRRecyclerView;
    private RecyclerView hotelRMenuRecyclerView;
    private RecyclerView foodRRecyclerView;
    private RecyclerView foodRMenuRecyclerView;
    private RequestQueue requestQueue;

    private TouringAdapter sportRAdapter;
    private ListAdapter sportRMenuAdapter;
    private List<ListModel> sportRMenuResult = new ArrayList<>();
    private TouringAdapter festivalRAdapter;
    private ListAdapter festivalRMenuAdapter;
    private List<ListModel> festivalRMenuResult = new ArrayList<>();

    private TouringAdapter travelRAdapter;
    private ListAdapter travelRMenuAdapter;
    private List<ListModel> travelRMenuResult = new ArrayList<>();
    private TouringAdapter hotelRAdapter;
    private ListAdapter hotelRMenuAdapter;
    private List<ListModel> hotelRMenuResult = new ArrayList<>();
    private TouringAdapter foodRAdapter;
    private ListAdapter foodRMenuAdapter;
    private List<ListModel> foodRMenuResult = new ArrayList<>();



    private MainActivity mainActivity;
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
        inflater.inflate(R.menu.home_nav_menu, menu);
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
        View view = inflater.inflate(R.layout.fragment_touring, container, false);


        NoboButton btnSearch = view.findViewById(R.id.button_touring_search);
        btnSearch.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String tel = "tel:" + infraModel.getPhoneNumber();
                Log.i("searchButton", "search button click");
//                startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
//                Navigation.findNavController(view).navigate(R.id.action_navigation_team_to_searchFragment);
            }
        }) ;


        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager5 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager6 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager7 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager8 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager9 = new LinearLayoutManager(view.getContext());
        RecyclerView.LayoutManager mLayoutManager10 = new LinearLayoutManager(view.getContext());



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
        LinearLayoutManager horizontalLayout7
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout8
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout9
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager horizontalLayout10
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);

        requestQueue = Volley.newRequestQueue(view.getContext());

        sportRMenuRecyclerView = view.findViewById(R.id.recycler_fragment_touringSportMenu);
        sportRMenuRecyclerView.setHasFixedSize(true);
        sportRMenuRecyclerView.setLayoutManager(mLayoutManager1);
        sportRMenuRecyclerView.setLayoutManager(horizontalLayout1);
        doHttpRequestSportRMenu();

        sportRRecyclerView = view.findViewById(R.id.recycler_fragment_touringSport);
        sportRRecyclerView.setHasFixedSize(true);
        sportRRecyclerView.setLayoutManager(mLayoutManager2);
        sportRRecyclerView.setLayoutManager(horizontalLayout2);
        doHttpRequestSportR();

        festivalRMenuRecyclerView = view.findViewById(R.id.recycler_fragment_touringFestivalMenu);
        festivalRMenuRecyclerView.setHasFixedSize(true);
        festivalRMenuRecyclerView.setLayoutManager(mLayoutManager3);
        festivalRMenuRecyclerView.setLayoutManager(horizontalLayout3);
        doHttpRequestFestivalRMenu();

        festivalRRecyclerView = view.findViewById(R.id.recycler_fragment_touringFestival);
        festivalRRecyclerView.setHasFixedSize(true);
        festivalRRecyclerView.setLayoutManager(mLayoutManager4);
        festivalRRecyclerView.setLayoutManager(horizontalLayout4);
        doHttpRequestFestivalR();

        travelRMenuRecyclerView = view.findViewById(R.id.recycler_fragment_touringTravelMenu);
        travelRMenuRecyclerView.setHasFixedSize(true);
        travelRMenuRecyclerView.setLayoutManager(mLayoutManager5);
        travelRMenuRecyclerView.setLayoutManager(horizontalLayout5);
        doHttpRequestTravelRMenu();

        travelRRecyclerView = view.findViewById(R.id.recycler_fragment_touringTravel);
        travelRRecyclerView.setHasFixedSize(true);
        travelRRecyclerView.setLayoutManager(mLayoutManager6);
        travelRRecyclerView.setLayoutManager(horizontalLayout6);
        doHttpRequestTravelR();

        hotelRMenuRecyclerView = view.findViewById(R.id.recycler_fragment_touringHotelMenu);
        hotelRMenuRecyclerView.setHasFixedSize(true);
        hotelRMenuRecyclerView.setLayoutManager(mLayoutManager7);
        hotelRMenuRecyclerView.setLayoutManager(horizontalLayout7);
        doHttpRequestHotelRMenu();

        hotelRRecyclerView = view.findViewById(R.id.recycler_fragment_touringHotel);
        hotelRRecyclerView.setHasFixedSize(true);
        hotelRRecyclerView.setLayoutManager(mLayoutManager8);
        hotelRRecyclerView.setLayoutManager(horizontalLayout8);
        doHttpRequestHotelR();

        foodRMenuRecyclerView = view.findViewById(R.id.recycler_fragment_touringFoodMenu);
        foodRMenuRecyclerView.setHasFixedSize(true);
        foodRMenuRecyclerView.setLayoutManager(mLayoutManager9);
        foodRMenuRecyclerView.setLayoutManager(horizontalLayout9);
        doHttpRequestFoodRMenu();

        foodRRecyclerView = view.findViewById(R.id.recycler_fragment_touringFood);
        foodRRecyclerView.setHasFixedSize(true);
        foodRRecyclerView.setLayoutManager(mLayoutManager10);
        foodRRecyclerView.setLayoutManager(horizontalLayout10);
        doHttpRequestFoodR();

        return view;
    }

    private void doHttpRequestSportR() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=1&recommandation=true";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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

                sportRAdapter = new TouringAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                sportRAdapter.setInfraModelList_orig(result_orig);
                sportRRecyclerView.setAdapter(sportRAdapter);
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

    private void doHttpRequestSportRMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/1";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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
//        List<ListModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    sportRMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        sportRMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sportRMenuAdapter = new ListAdapter(sportRMenuResult);

                sportRMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = sportRMenuResult.get(position);
                        sportRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;
                Log.i("TEST", response);

                sportRMenuRecyclerView.setAdapter(sportRMenuAdapter);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }


    private void doHttpRequestFestivalR() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=15&recommandation=true";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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

                festivalRAdapter = new TouringAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                festivalRAdapter.setInfraModelList_orig(result_orig);
                festivalRRecyclerView.setAdapter(festivalRAdapter);
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

    private void doHttpRequestFestivalRMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/15";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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
//        List<ListModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    festivalRMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        festivalRMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                festivalRMenuAdapter = new ListAdapter(festivalRMenuResult);

                festivalRMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = festivalRMenuResult.get(position);
                        festivalRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;
                Log.i("TEST", response);

                festivalRMenuRecyclerView.setAdapter(festivalRMenuAdapter);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestTravelR() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=3&recommandation=true";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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

                travelRAdapter = new TouringAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                travelRAdapter.setInfraModelList_orig(result_orig);
                travelRRecyclerView.setAdapter(travelRAdapter);
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

    private void doHttpRequestTravelRMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/3";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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
//        List<ListModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    travelRMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        travelRMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                travelRMenuAdapter = new ListAdapter(travelRMenuResult);

                travelRMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = travelRMenuResult.get(position);
                        travelRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;
                Log.i("TEST", response);

                travelRMenuRecyclerView.setAdapter(travelRMenuAdapter);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestHotelR() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=16&recommandation=true";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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

                hotelRAdapter = new TouringAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                hotelRAdapter.setInfraModelList_orig(result_orig);
                hotelRRecyclerView.setAdapter(hotelRAdapter);
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

    private void doHttpRequestHotelRMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/16";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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
//        List<ListModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    hotelRMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        hotelRMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hotelRMenuAdapter = new ListAdapter(hotelRMenuResult);

                hotelRMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = hotelRMenuResult.get(position);
                        hotelRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;
                Log.i("TEST", response);

                hotelRMenuRecyclerView.setAdapter(hotelRMenuAdapter);


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFoodR() {
        String url1r = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=17&recommandation=true";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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

                foodRAdapter = new TouringAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                foodRAdapter.setInfraModelList_orig(result_orig);
                foodRRecyclerView.setAdapter(foodRAdapter);
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

    private void doHttpRequestFoodRMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/17";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
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
//        List<ListModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    ListModel menuModel = new ListModel();
                    menuModel.setMenu("전체");
                    foodRMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        foodRMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                foodRMenuAdapter = new ListAdapter(foodRMenuResult);

                foodRMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = foodRMenuResult.get(position);
                        foodRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                }) ;
                Log.i("TEST", response);

                foodRMenuRecyclerView.setAdapter(foodRMenuAdapter);


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
        }

        return null;
    }

}
