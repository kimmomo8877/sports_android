package com.example.and02.ui.tracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.app.progresviews.ProgressLine;
import com.app.progresviews.ProgressWheel;
import com.example.and02.R;
import com.ornach.nobobutton.NoboButton;

public class TrackerMainFragment extends Fragment {
    private NoboButton btnToTrackerDetail;
    private ProgressWheel progressWheelActivity;
    private ProgressLine progressLineActivity;
    private ProgressLine progressLineHeartRate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button event
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.btnToTrackerDetail = view.findViewById(R.id.btn_trackerMain_toTrackerDetail);
        this.btnToTrackerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("amount", "Data1");
                Navigation.findNavController(v).navigate(R.id.navigation_tracker_detail, bundle);
            }
        });

        this.progressWheelActivity = view.findViewById(R.id.progressWheel_trackerMain_activity);
        this.progressLineActivity = view.findViewById(R.id.progressBar_trackerMain_activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        int activityCount = SharedActivityData.getActivityCount(getContext());
        int goal = SharedActivityData.getGoal(getContext());
        float percentage = (float) activityCount / goal;
        int wheelPercentage = (int) (percentage * 360);
        int barPercentage = (int) (percentage * 100);

        this.progressWheelActivity.setPercentage(wheelPercentage);
        this.progressWheelActivity.setStepCountText(String.valueOf(activityCount));

        this.progressLineActivity.setmPercentage(barPercentage);
        this.progressLineActivity.setmValueText(activityCount);

    }
}