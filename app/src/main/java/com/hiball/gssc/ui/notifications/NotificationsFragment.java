package com.hiball.gssc.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.hiball.gssc.R;

public class NotificationsFragment extends Fragment {

  private View view;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_dashboard, container, false);
    return view;
  }
}