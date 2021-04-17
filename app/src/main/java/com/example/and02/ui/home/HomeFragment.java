package com.example.and02.ui.home;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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
import com.example.and02.ui.common.ChargeModel;
import com.example.and02.ui.common.CodeModel;
import com.example.and02.ui.common.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private Button btnTracker;

    private RecyclerView introRecyclerView;
    private RecyclerView sportRRecyclerView;
    private RecyclerView hotelRRecyclerView;
    private RecyclerView foodRRecyclerView;
    private RecyclerView sportMenuRecyclerView;
    private RecyclerView hotelMenuRecyclerView;
    private RecyclerView foodMenuRecyclerView;
    private RequestQueue requestQueue;

    private HomeAdapter sportRAdapter;
    private ListAdapter sportMenuAdapter;
    private List<ListModel> sportMenuResult = new ArrayList<>();
    private HomeAdapter hotelRAdapter;
    private ListAdapter hotelMenuAdapter;
    private List<ListModel> hotelMenuResult = new ArrayList<>();
    private HomeAdapter foodRAdapter;
    private ListAdapter foodMenuAdapter;
    private List<ListModel> foodMenuResult = new ArrayList<>();

    private MainActivity mainActivity;
    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.home_nav_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected", "yes");
        switch (item.getItemId()) {
            case R.id.home_tracker_button:
//        Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_navigation_tracker_main);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//    this.btnTracker = view.findViewById(R.id.home_tracker_button);
//    this.btnTracker.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        System.out.println("");
//      }
//    });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("홈");
//    Resources res = getResources();
//    Bitmap bMap = BitmapFactory.decodeResource(res, R.drawable.sport_main);
//    BitmapDrawable actionBarBackground = new BitmapDrawable(res, bMap);
//    ((MainActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(actionBarBackground);
        ((MainActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4EBAE6")));
        view = inflater.inflate(R.layout.fragment_home, container, false);

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

        introRecyclerView = view.findViewById(R.id.intro_recycler_view);
        introRecyclerView.setHasFixedSize(true);
        introRecyclerView.setLayoutManager(mLayoutManager0);
        introRecyclerView.setLayoutManager(horizontalLayout0);


        IntroModel introModel1 = new IntroModel("전지훈련팀 스포츠 투어링이 처음이신가요?", "서비스소개 >");
        IntroModel introModel2 = new IntroModel("모바일로 운동기록하고 위치기반 투어링을 추천 받아보세요.", "모바일이용가이드 >");
        IntroModel introModel3 = new IntroModel("운영센터", "운영센터소개 >");
        List<IntroModel> introModelList = new ArrayList<>();
        introModelList.add(introModel1);
        introModelList.add(introModel2);
        introModelList.add(introModel3);

        IntroAdapter introAdapter = new IntroAdapter(introModelList);

        introAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.i("intro adapter Click", String.valueOf(position));
                if (position == 0) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kbostat.co.kr/pr/system"));
                    startActivity(browserIntent);
                } else if (position == 1) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kbostat.co.kr/pr/system"));
                    startActivity(browserIntent);
                } else if (position == 2) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kbostat.co.kr/pr/system"));
                    startActivity(browserIntent);
                }
            }
        });

        introRecyclerView.setAdapter(introAdapter);

        sportMenuRecyclerView = view.findViewById(R.id.recycler_fragment_homeSportMenu);
        sportMenuRecyclerView.setHasFixedSize(true);
        sportMenuRecyclerView.setLayoutManager(mLayoutManager1);
        sportMenuRecyclerView.setLayoutManager(horizontalLayout1);
        doHttpRequestSportMenu();

        sportRRecyclerView = view.findViewById(R.id.recycler_fragment_homeSport);
        sportRRecyclerView.setHasFixedSize(true);
        sportRRecyclerView.setLayoutManager(mLayoutManager2);
        sportRRecyclerView.setLayoutManager(horizontalLayout2);
        doHttpRequestSportR();

        hotelMenuRecyclerView = view.findViewById(R.id.recycler_fragment_homeHotelMenu);
        hotelMenuRecyclerView.setHasFixedSize(true);
        hotelMenuRecyclerView.setLayoutManager(mLayoutManager3);
        hotelMenuRecyclerView.setLayoutManager(horizontalLayout3);
        doHttpRequestHotelMenu();

        hotelRRecyclerView = view.findViewById(R.id.recycler_fragment_homeHotel);
        hotelRRecyclerView.setHasFixedSize(true);
        hotelRRecyclerView.setLayoutManager(mLayoutManager4);
        hotelRRecyclerView.setLayoutManager(horizontalLayout4);
        doHttpRequestHotelR();

        foodMenuRecyclerView = view.findViewById(R.id.recycler_fragment_homeFoodMenu);
        foodMenuRecyclerView.setHasFixedSize(true);
        foodMenuRecyclerView.setLayoutManager(mLayoutManager5);
        foodMenuRecyclerView.setLayoutManager(horizontalLayout5);
        doHttpRequestFoodMenu();

        foodRRecyclerView = view.findViewById(R.id.recycler_fragment_homeFood);
        foodRRecyclerView.setHasFixedSize(true);
        foodRRecyclerView.setLayoutManager(mLayoutManager6);
        foodRRecyclerView.setLayoutManager(horizontalLayout6);
        doHttpRequestFoodR();

        return view;
    }

    private void doHttpRequestSportR() {
        String url = "http://www.kbostat.co.kr/resource/infra?parentInfraCategory=1&recommandation=true";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
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

                sportRAdapter = new HomeAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                sportRAdapter.setHomeModelList_orig(result_orig);
                sportRRecyclerView.setAdapter(sportRAdapter);
                Log.i("Sport : ", response);

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

                if (response != null) {
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

                hotelRAdapter = new HomeAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                hotelRAdapter.setHomeModelList_orig(result_orig);
                hotelRRecyclerView.setAdapter(hotelRAdapter);

                Log.i("Hotel : ", response);

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

                if (response != null) {
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

                foodRAdapter = new HomeAdapter(result);
                List<InfraModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                foodRAdapter.setHomeModelList_orig(result_orig);
                foodRRecyclerView.setAdapter(foodRAdapter);

                Log.i("Food : ", response);

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

                if (response != null) {
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
                        sportRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                });
                Log.i("SportMenu", response);

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

    private void doHttpRequestHotelMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/16";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
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
                    hotelMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        hotelMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                hotelMenuAdapter = new ListAdapter(hotelMenuResult);

                hotelMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = hotelMenuResult.get(position);
                        hotelRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                });

                hotelMenuRecyclerView.setAdapter(hotelMenuAdapter);

                Log.i("Hotel Menu", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });

        requestQueue.add(request);
    }

    private void doHttpRequestFoodMenu() {
        String url1r = "http://www.kbostat.co.kr/resource/infra-category/17";
        StringRequest request = new StringRequest(url1r, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
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
                    foodMenuResult.add(menuModel);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ListModel listModel = new ListModel();
                        listModel.setMenu(data.getString("name"));
                        foodMenuResult.add(listModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                foodMenuAdapter = new ListAdapter(foodMenuResult);

                foodMenuAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Log.i("List Adapter Click", String.valueOf(position));
                        ListModel filterWord = foodMenuResult.get(position);
                        foodRAdapter.getFilter().filter(filterWord.getMenu());
                    }
                });

                foodMenuRecyclerView.setAdapter(foodMenuAdapter);

                Log.i("FoodMenu", response);

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
        infraModel.setInfraNo(data.optString("infraNo"));
        infraModel.setName(data.optString("name"));
        infraModel.setAddress(data.optString("address"));
        infraModel.setPhoneNumber(data.optString("phoneNumber"));
        infraModel.setHomepageUrl(data.optString("homepageUrl"));
        infraModel.setFacilityDescription(data.optString("facilityDescription"));
        infraModel.setEquipmentDescription(data.optString("equipmentDescription"));
        infraModel.setEtcDescription(data.optString("etcDescription"));
        infraModel.setAttachFiles(data.optJSONArray("attachFiles"));
        JSONObject infraCategoryObject = data.optJSONObject("infraCategory");
        InfraCategoryModel infraCategoryModel = new InfraCategoryModel();
        infraCategoryModel.setName(infraCategoryObject.optString("name"));
        infraModel.setInfraCategoryModel(infraCategoryModel);
        infraModel.setFirstPrVideoUrl(data.optString("firstPrVideoUrl"));
        infraModel.setSecondPrVideoUrl(data.optString("secondPrVideoUrl"));
        infraModel.setThirdPrVideoUrl(data.optString("thirdPrVideoUrl"));

        infraModel.setLatitude(data.optDouble("latitude"));
        infraModel.setLongitude(data.optDouble("longitude"));

        infraModel.setCharges(data.optJSONArray("charges"));
        if (infraModel.getCharges().length() > 0) {
            ArrayList<ChargeModel> charges = new ArrayList<ChargeModel>();
            for (int i = 0; i < infraModel.getCharges().length(); i++) {
                JSONObject chargeObject = infraModel.getCharges().getJSONObject(i);
                ChargeModel chargeModel = new ChargeModel();
                chargeModel.setCost(chargeObject.optInt("cost"));
                CodeModel codeModel = new CodeModel();
                codeModel.setName(chargeObject.optJSONObject("chargeTypeCode").getString("name"));
                chargeModel.setChargeTypeCode(codeModel);
                charges.add(chargeModel);
            }
            infraModel.setChargesModel((ChargeModel[]) charges.toArray());

        }

        if (infraModel.getAttachFiles().length() > 0) {
            JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
            StringBuilder sb = new StringBuilder(imageUrl);
            sb.append(attachObject.optString("saveFilePath"));
            infraModel.setAttachFile(sb.toString());
            return infraModel;
        }

        return infraModel;
    }

}
