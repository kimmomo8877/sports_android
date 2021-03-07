package com.example.and02.ui.team;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
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
import com.example.and02.ui.common.BoardModel;
import com.example.and02.ui.common.BoardWriterModel;
import com.example.and02.ui.common.CodeModel;
import com.example.and02.ui.common.ScheduleModel;
import com.example.and02.ui.common.SharedUserData;
import com.example.and02.ui.home.HomeAdapter;
import com.example.and02.ui.home.InfraModel;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.tabs.TabLayout;
import com.ornach.nobobutton.NoboButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TeamDetailFragment extends Fragment {

    private RecyclerView boardRecyclerView;
    private BoardAdapter boardAdapter;
    private RecyclerView reservationRecyclerView;
    private ReservationAdapter reservationAdapter;
    private RecyclerView teamScheduleRecyclerView;
    private TeamScheduleAdapter teamScheduleAdapter;
    private RecyclerView teamStoryRecyclerView;
    private TeamStoryAdapter teamStoryAdapter;

    private RequestQueue requestQueue;
    private MainActivity mainActivity;
    private View view;

    //    private RequestQueue requestQueue;
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
        inflater.inflate(R.menu.teamdetail_nav_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        Log.i("onOptionsItemSelected", "yes");
        Bundle bundle = new Bundle();
        bundle.putSerializable("teamModel", (TeamModel) getArguments().getSerializable("teamModel"));
        switch (item.getItemId()) {
            case R.id.navigation_home:
                Log.i("onOptionsItemSelected", "back");
                getActivity().onBackPressed();
                return true;
            case R.id.action_teamDetail_home:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Click Home", Toast.LENGTH_SHORT).show();
//                Navigation.findNavController(view).navigate(R.id.action_teamDetailFragment_to_, bundle);
                return true;
            case R.id.action_teamDetail_notice:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Click Notice", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_teamDetailFragment_to_teamNoticeFragment, bundle);
                return true;
            case R.id.action_teamDetail_reservation:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Click Notice", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_teamDetailFragment_to_teamReservationFragment, bundle);
                return true;
            case R.id.action_teamDetail_schedule:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Click Schedule", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_teamDetailFragment_to_teamScheduleFragment, bundle);
                return true;
            case R.id.action_teamDetail_story:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Click Story", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_teamDetailFragment_to_teamStoryFragment, bundle);
                return true;
            case R.id.action_teamDetail_picture:
                Log.i("onOptionsItemSelected", "tracker");
                Toast.makeText(getActivity(), "Click Picture", Toast.LENGTH_SHORT).show();
//                Navigation.findNavController(view).navigate(R.id.action_teamDetailFragment_to_teamScheduleFragment, bundle);
                return true;
            default:
                getActivity().onBackPressed();
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("팀상세");
        view = inflater.inflate(R.layout.fragment_teamdetail, container, false);

        final TeamModel teamModel = (TeamModel) getArguments().getSerializable("teamModel");
        requestQueue = Volley.newRequestQueue(view.getContext());

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(view.getContext());
        LinearLayoutManager horizontalLayout1
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(view.getContext());
        LinearLayoutManager horizontalLayout2
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(view.getContext());
        LinearLayoutManager horizontalLayout3
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager4 = new LinearLayoutManager(view.getContext());
        LinearLayoutManager horizontalLayout4
                = new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false);

        boardRecyclerView = view.findViewById(R.id.recycler_teamDetail_noticeBoard);
        boardRecyclerView.setHasFixedSize(true);
        boardRecyclerView.setLayoutManager(mLayoutManager1);
        boardRecyclerView.setLayoutManager(horizontalLayout1);
        doHttpRequestNoticeBoard(teamModel);

        reservationRecyclerView = view.findViewById(R.id.recycler_teamDetail_teamReservation);
        reservationRecyclerView.setHasFixedSize(true);
        reservationRecyclerView.setLayoutManager(mLayoutManager2);
        reservationRecyclerView.setLayoutManager(horizontalLayout2);
        doHttpRequestReservation(teamModel);

        teamScheduleRecyclerView = view.findViewById(R.id.recycler_teamDetail_teamSchedule);
        teamScheduleRecyclerView.setHasFixedSize(true);
        teamScheduleRecyclerView.setLayoutManager(mLayoutManager3);
        teamScheduleRecyclerView.setLayoutManager(horizontalLayout3);
        doHttpRequestTeamSchedule(teamModel);

        teamStoryRecyclerView = view.findViewById(R.id.recycler_teamDetail_teamStory);
        teamStoryRecyclerView.setHasFixedSize(true);
        teamStoryRecyclerView.setLayoutManager(mLayoutManager4);
        teamStoryRecyclerView.setLayoutManager(horizontalLayout4);
        doHttpRequestTeamStory(teamModel);

        SimpleDraweeView btnImageMain = view.findViewById(R.id.imageView_teamDetail_main);
        if (teamModel.getAttachFile() != null ) {
            Uri uri = Uri.parse(teamModel.getAttachFile());
            btnImageMain.setImageURI(uri);
        }

        TextView textViewTitle = view.findViewById((R.id.textView_teamDetail_title));
        textViewTitle.setText(teamModel.getName());

        TextView textViewBelong = view.findViewById((R.id.textView_teamDetail_belongDetail));
        textViewBelong.setText(teamModel.getBelongCode().getName());

        TextView textViewSport = view.findViewById((R.id.textView_teamDetail_sportDetail));
        textViewSport.setText(teamModel.getSportCode().getName());

        TextView textViewPhone = view.findViewById((R.id.textView_teamDetail_phoneDetail));
        textViewPhone.setText(teamModel.getPhoneNumber());


        return view;
    }

    private void doHttpRequestNoticeBoard(TeamModel teamModel) {
        String url = "http://www.kbostat.co.kr/resource/board/team/" + teamModel.getTeamNo() + "/1/content";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<BoardModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);
                        if (data.getInt("boardNo") != 1) {
                            continue;
                        }
                        if (setBoardModel(data) != null) {
                            result.add(setBoardModel(data));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                boardAdapter = new BoardAdapter(result);
                List<BoardModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                boardAdapter.setBoardModelList_orig(result_orig);
                boardRecyclerView.setAdapter(boardAdapter);
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

    private void doHttpRequestReservation(TeamModel teamModel) {
        String url = "http://www.kbostat.co.kr/resource/reservation?teamNo=" + teamModel.getTeamNo() + "&parentInfraCategoryNo=1&page=0&size=9";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                List<ReservationModel> result = new ArrayList<>();
                try {
                    JSONArray root = new JSONArray(response);
                    for (int i = 0; i < root.length(); i++) {
                        JSONObject data = root.getJSONObject(i);

                        JSONObject infraData = data.getJSONObject("infra");
                        InfraModel infraModel = new InfraModel();
                        if (infraData.getString("name") != null ) {
                            infraModel.setName(infraData.getString("name"));
                        }
                        infraModel.setAttachFiles(infraData.getJSONArray("attachFiles"));
                        if (infraModel.getAttachFiles() != null) {
                            if (infraModel.getAttachFiles().length() > 0) {
                                JSONObject attachObject = infraModel.getAttachFiles().getJSONObject(0);
                                StringBuilder sb = new StringBuilder(imageUrl);
                                sb.append(attachObject.getString("saveFilePath"));
                                infraModel.setAttachFile(sb.toString());
                            } else {
                            }
                        }

                        ReservationModel reservationModel = new ReservationModel();
                        reservationModel.setInfra(infraModel);
                        reservationModel.setRegisteDate(data.getString("registeDate"));

                        result.add(reservationModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                reservationAdapter = new ReservationAdapter(result);
                List<ReservationModel> result_orig = new ArrayList<>();
                result_orig.addAll(result);
                reservationAdapter.setReservationModelList_orig(result_orig);
                reservationRecyclerView.setAdapter(reservationAdapter);
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

    private void doHttpRequestTeamSchedule(TeamModel teamModel) {
        String url = "http://www.kbostat.co.kr/resource/schedule/team/" + teamModel.getTeamNo();
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
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
                teamScheduleAdapter.setScheduleModelList_orig(result_orig);
                teamScheduleRecyclerView.setAdapter(teamScheduleAdapter);
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

    private void doHttpRequestTeamStory(TeamModel teamModel) {
        String url = "http://www.kbostat.co.kr/resource/board/team/" + teamModel.getTeamNo() + "/34/content";
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response != null) {
                    try {
                        response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
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

}
