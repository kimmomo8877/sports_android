package com.example.and02.ui.team;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and02.R;
import com.example.and02.ui.home.InfraModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class SearchTeamAdapter  extends RecyclerView.Adapter<SearchTeamAdapter.SearchTeamViewHolder>  {
private List<TeamModel> teamModelList;
private List<TeamModel> teamModelList_orig;

public SearchTeamAdapter(List<TeamModel> teamModelList) {
        this.teamModelList = teamModelList;
        }

public class SearchTeamViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewName;
    private final SimpleDraweeView imageViewImage;

    public SearchTeamViewHolder(@NonNull final View itemView) {
        super(itemView);

        this.imageViewImage = itemView.findViewById(R.id.imageView_searchTeam_image);
        this.textViewName = itemView.findViewById(R.id.textView_searchTeam_name);

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
    public SearchTeamAdapter.SearchTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_searchteam_adapter, parent, false);
        return new SearchTeamAdapter.SearchTeamViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchTeamAdapter.SearchTeamViewHolder holder, int position) {

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


//    @Override
//    public Filter getFilter() {
//        return dataFilter;
//    }
//    private Filter dataFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<FavoriteModel> filteredList = new ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(favoriteModelList_orig);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (FavoriteModel item : favoriteModelList_orig) {
//                    if (item.getSearchWord().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            favoriteModelList.clear();
//            favoriteModelList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };
//
//    public List<FavoriteModel> getFavoriteModelList_orig() {
//        return favoriteModelList_orig;
//    }
//
//    public void setFavoriteModelList_orig(List<FavoriteModel> favoriteList_orig) {
//        this.favoriteModelList_orig = favoriteList_orig;
//    }
}

