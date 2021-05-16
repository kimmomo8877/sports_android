package com.hiball.gssc.ui.team;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.hiball.gssc.ui.common.ScheduleModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TeamScheduleFragment extends Fragment {

    private RecyclerView teamScheduleRecyclerView;
    private RequestQueue requestQueue;

    private TeamScheduleAdapter teamScheduleAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.

        inflater.inflate(R.menu.teamschedule_nav_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_menu_teamSchedule_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                teamScheduleAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected","yes");
        switch (item.getItemId()) {
            case R.id.home_tracker_button:
                Toast.makeText(getActivity(), "Calls Icon Click", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item_menu_teamSchedule_search:
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("팀일정");
        View view = inflater.inflate(R.layout.fragment_teamschedule, container, false);

        final TeamModel teamModel = (TeamModel) getArguments().getSerializable("teamModel");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());

        requestQueue = Volley.newRequestQueue(view.getContext());

        teamScheduleRecyclerView = view.findViewById(R.id.recycler_teamSchedule);
        teamScheduleRecyclerView.setHasFixedSize(true);
        teamScheduleRecyclerView.setLayoutManager(mLayoutManager);
        doHttpRequestTeamSchedule(teamModel);

        return view;
    }

    private void doHttpRequestTeamSchedule(TeamModel teamModel) {
        String url = "http://www.kbostat.co.kr/resource/schedule/team/" + teamModel.getTeamNo();
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
                List<ScheduleModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        ScheduleModel scheduleModel = new ScheduleModel();
                        scheduleModel.setTitle(data.getString("title"));
                        scheduleModel.setStartDate(data.getString("startDate"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                teamScheduleAdapter = new TeamScheduleAdapter(result);
                List<ScheduleModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
//                boardAdapter.setBoardModelList_orig(result_orig);
                teamScheduleRecyclerView.setAdapter(teamScheduleAdapter);
                Log.i("TeamSchedule", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });


        requestQueue.add(request);
    }


}
