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
import com.hiball.gssc.ui.common.BoardModel;
import com.hiball.gssc.ui.common.BoardWriterModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TeamStoryFragment extends Fragment {

    private RecyclerView teamStoryRecyclerView;
    private RequestQueue requestQueue;

    private TeamStoryAdapter teamStoryAdapter;
    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreated", "inside on activity created");
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar.

        inflater.inflate(R.menu.teamstory_nav_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.item_menu_teamStory_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                teamStoryAdapter.getFilter().filter(newText);
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
            case R.id.item_menu_teamStory_search:
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("팀스토리");
        View view = inflater.inflate(R.layout.fragment_teamstory, container, false);

        final TeamModel teamModel = (TeamModel) getArguments().getSerializable("teamModel");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());

        requestQueue = Volley.newRequestQueue(view.getContext());

        teamStoryRecyclerView = view.findViewById(R.id.recycler_teamStory);
        teamStoryRecyclerView.setHasFixedSize(true);
        teamStoryRecyclerView.setLayoutManager(mLayoutManager);
        doHttpRequestTeamStory(teamModel);

        return view;
    }

    private void doHttpRequestTeamStory(TeamModel teamModel) {
        String url = "http://www.kbostat.co.kr/resource/board/team/" + teamModel.getTeamNo() + "/34/content";
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
                List<BoardModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (data.getInt("boardNo") != 34) {
                            continue;
                        }
                        if (setBoardModel(data) != null) {
                            result.add(setBoardModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                teamStoryAdapter = new TeamStoryAdapter(result);
                List<BoardModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                teamStoryAdapter.setBoardModelList_orig(result_orig);
                teamStoryRecyclerView.setAdapter(teamStoryAdapter);
                Log.i("TeamStory", response);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", String.valueOf(error));
            }
        });


        requestQueue.add(request);
    }

    private BoardModel setBoardModel(JSONObject data) throws JSONException {

        BoardModel boardModel = new BoardModel();

        boardModel.setTitle(data.getString("title"));
        boardModel.setContent(data.getString("content"));
        boardModel.setRegisteDate(data.getString("registeDate"));

        JSONObject writerData = data.getJSONObject("writer");
        BoardWriterModel boardWriterModel = new BoardWriterModel();
        boardWriterModel.setName(writerData.getString("name"));
        boardWriterModel.setRegisteDate(writerData.getString("registeDate"));
        boardModel.setWriter(boardWriterModel);

        boardModel.setAttachFiles(data.getJSONArray("attachFiles"));
        if (boardModel.getAttachFiles() != null) {
            if (boardModel.getAttachFiles().length() > 0) {
                JSONObject attachObject = boardModel.getAttachFiles().getJSONObject(0);
                StringBuilder sb = new StringBuilder(imageUrl);
                sb.append(attachObject.getString("saveFilePath"));
                boardModel.setAttachFile(sb.toString());
            } else {
            }
        }
        return boardModel;
    }


}
