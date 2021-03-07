package com.example.and02.ui.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and02.R;
import com.example.and02.ui.common.BoardModel;
import com.example.and02.ui.common.ScheduleModel;

import java.util.List;

public class TeamScheduleAdapter extends RecyclerView.Adapter<TeamScheduleAdapter.TeamScheduleViewHolder>  {
    private List<ScheduleModel> scheduleModelList;
    private List<ScheduleModel> scheduleModelList_orig;

    public TeamScheduleAdapter(List<ScheduleModel> scheduleModelList) {
        this.scheduleModelList = scheduleModelList;
    }

    public class TeamScheduleViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDate;

        public TeamScheduleViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.textViewTitle = itemView.findViewById(R.id.textView_teamDetail_scheduleTitle);
            this.textViewDate = itemView.findViewById(R.id.textView_teamDetail_scheduleStartDate);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition() ;
//                    if (pos != RecyclerView.NO_POSITION) {
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("teamModel", teamModelList.get(pos));
//                    }
//                }
//            });

        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewDate() {
            return textViewDate;
        }
    }

    @NonNull
    @Override
    public TeamScheduleAdapter.TeamScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_detailteam_schedule, parent, false);
        return new TeamScheduleAdapter.TeamScheduleViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamScheduleAdapter.TeamScheduleViewHolder holder, int position) {

        if (scheduleModelList.get(position).getTitle() != null ) {
            holder.getTextViewTitle().setText(scheduleModelList.get(position).getTitle());
        }

        if (scheduleModelList.get(position).getRegisteDate() != null ) {
            holder.getTextViewDate().setText(scheduleModelList.get(position).getStartDate());
        }
    }

    @Override
    public int getItemCount() {
        return scheduleModelList.size();
    }

    public List<ScheduleModel> getScheduleModelList() {
        return scheduleModelList;
    }


}
