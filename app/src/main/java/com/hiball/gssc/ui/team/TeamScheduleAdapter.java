package com.hiball.gssc.ui.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiball.gssc.R;
import com.hiball.gssc.ui.common.ScheduleModel;

import java.util.ArrayList;
import java.util.List;

public class TeamScheduleAdapter extends RecyclerView.Adapter<TeamScheduleAdapter.TeamScheduleViewHolder> implements Filterable {
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

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            // 삭제할 할 경우 로직
            List<ScheduleModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(scheduleModelList_orig);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ScheduleModel item : scheduleModelList_orig) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            results.count  = filteredList.size();
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                scheduleModelList.clear();
                scheduleModelList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }

    };

    public List<ScheduleModel> getScheduleModelList_orig() {
        return scheduleModelList_orig;
    }

    public void setScheduleModelList_orig(List<ScheduleModel> scheduleModelList_orig) {
        this.scheduleModelList_orig = scheduleModelList_orig;
    }


}
