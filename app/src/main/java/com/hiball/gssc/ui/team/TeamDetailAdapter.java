package com.hiball.gssc.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hiball.gssc.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class TeamDetailAdapter extends RecyclerView.Adapter<TeamDetailAdapter.TeamDetailViewHolder>  {
    private List<TeamModel> teamModelList;
    private List<TeamModel> teamModelList_orig;

    public TeamDetailAdapter(List<TeamModel> teamModelList) {
        this.teamModelList = teamModelList;
    }

    public class TeamDetailViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;

        private final SimpleDraweeView imageViewImage;

        public TeamDetailViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.imageViewImage = itemView.findViewById(R.id.imageView_teamDetail_main);
            this.textViewName = itemView.findViewById(R.id.textView_teamDetail_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("teamModel", teamModelList.get(pos));
//                        bundle.putString("searchWord", favoriteModelList.get(pos).getSearchWord());
//                    Navigation.findNavController(v).navigate(R.id.action_searchResultFragment_to_facilityMapFragment, bundle);
                    }
                }
            });

        }

        public TextView getTextViewName() {
            return textViewName;
        }
        public SimpleDraweeView getImageViewImage() {
            return imageViewImage;
        }
    }

    @NonNull
    @Override
    public TeamDetailAdapter.TeamDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_searchteam_adapter, parent, false);
        return new TeamDetailAdapter.TeamDetailViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamDetailAdapter.TeamDetailViewHolder holder, int position) {

        if (teamModelList.get(position).getName() != null ) {
            holder.getTextViewName().setText(teamModelList.get(position).getName());
        }

//        if (teamModelList.get(position).getAttachFile() != null ) {
//            Uri uri = Uri.parse(teamModelList.get(position).getAttachFile());
//            holder.getImageViewImage().setImageURI(uri);
//        }

    }

    @Override
    public int getItemCount() {
        return teamModelList.size();
    }

    public List<TeamModel> getTeamModelList() {
        return teamModelList;
    }


}
