package com.example.and02.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.and02.MainActivity;
import com.example.and02.R;
import com.example.and02.ui.common.ListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

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
    View view = inflater.inflate(R.layout.fragment_home, container, false);

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


    IntroModel introModel1 = new IntroModel("전지훈련팀 스포츠 투어링이 처음이신가요?","서비스소개 >");
    IntroModel introModel2 = new IntroModel("모바일로 운동기록하고 위치기반 투어링을 추천 받아보세요.","모바일이용가이드 >");
    IntroModel introModel3 = new IntroModel("운영센터","운영센터소개 >");
    List<IntroModel> introModelList = new ArrayList<>();
    introModelList.add(introModel1); introModelList.add(introModel2); introModelList.add(introModel3);

    IntroAdapter introAdapter = new IntroAdapter(introModelList);
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

        sportRAdapter = new HomeAdapter(result);
        List<InfraModel> result_orig = new ArrayList<>();
        result_orig.addAll(result);
        sportRAdapter.setHomeModelList_orig(result_orig);
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

        hotelRAdapter = new HomeAdapter(result);
        List<InfraModel> result_orig = new ArrayList<>();
        result_orig.addAll(result);
        hotelRAdapter.setHomeModelList_orig(result_orig);
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

        foodRAdapter = new HomeAdapter(result);
        List<InfraModel> result_orig = new ArrayList<>();
        result_orig.addAll(result);
        foodRAdapter.setHomeModelList_orig(result_orig);
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

  private void doHttpRequestSportMenu() {
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
        }) ;
        Log.i("TEST", response);

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
        }) ;
        Log.i("TEST", response);

        hotelMenuRecyclerView.setAdapter(hotelMenuAdapter);

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

  private void doHttpRequestFoodMenu() {
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
        }) ;
        Log.i("TEST", response);

        foodMenuRecyclerView.setAdapter(foodMenuAdapter);

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
    }

    return null;
  }

}



//  @Override
//  public void onActivityCreated(Bundle savedInstanceState) {
//    super.onActivityCreated(savedInstanceState);
//    setHasOptionsMenu(true);
//    Log.i("onActivityCreated", "inside on activity created");
//  }
