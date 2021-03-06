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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.RequestQueue;
import com.example.and02.LoginActivity;
import com.example.and02.R;
import com.example.and02.ui.common.SharedUserData;
import com.example.and02.ui.home.InfraModel;
import com.google.android.material.tabs.TabLayout;
import com.ornach.nobobutton.NoboButton;
import com.squareup.picasso.Picasso;

public class TeamDetailFragment extends Fragment {

//    private RequestQueue requestQueue;
//    private String imageUrl = "http://www.kbostat.co.kr/resource/static-file";
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
            inflater.inflate(R.menu.teamdetail_nav_menu, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // handle item selection
            Log.i("onOptionsItemSelected","yes");
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Log.i("onOptionsItemSelected","back");
                    getActivity().onBackPressed();
                    return true;
                case R.id.action_teamDetail_home:
                    Log.i("onOptionsItemSelected","tracker");
                    Toast.makeText(getActivity(), "Click Home", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_teamDetail_schedule:
                    Log.i("onOptionsItemSelected","tracker");
                    Toast.makeText(getActivity(), "Click Schedule", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_teamDetail_story:
                    Log.i("onOptionsItemSelected","tracker");
                    Toast.makeText(getActivity(), "Click Story", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_teamDetail_picture:
                    Log.i("onOptionsItemSelected","tracker");
                    Toast.makeText(getActivity(), "Click Picture", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        public View onCreateView(@NonNull LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_team, container, false);

            final TeamModel teamModel = (TeamModel) getArguments().getSerializable("teamModel");



//        requestQueue = Volley.newRequestQueue(view.getContext());
//        doHttpRequest();

            return view;
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
