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

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> implements Filterable {
    private List<SearchModel> searchModelList;
    private List<SearchModel> searchModelList_orig;

    public SearchAdapter(List<SearchModel> searchModelList) {
        this.searchModelList = searchModelList;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;

        public SearchViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.textViewTitle = itemView.findViewById(R.id.textView_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("infraModel", searchModelList.get(pos));
//                        Navigation.findNavController(v).navigate(R.id.action_navigation_team_to_facilityDetailFragment, bundle);
                    }
                }
            });

        }
        public TextView getTextViewTitle() {
            return textViewTitle;
        }
    }

    @NonNull
    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        return new SearchAdapter.SearchViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchViewHolder holder, int position) {

        holder.getTextViewTitle().setText(searchModelList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return searchModelList.size();
    }

    public List<SearchModel> getSearchModelList() {
        return searchModelList;
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
            List<SearchModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0 || constraint.toString() == "전체") {
                filteredList.addAll(searchModelList_orig);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (SearchModel item : searchModelList_orig) {
                    //TODO filter 대상 setting
//                    if (item.getInfraCategoryModel().getName().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
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
                searchModelList.clear();
                searchModelList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };

    public List<SearchModel> getTeamModelList_orig() {
        return searchModelList_orig;
    }

    public void setSearchModelList_orig(List<SearchModel> searchModelList_orig) {
        this.searchModelList_orig = searchModelList_orig;
    }
}
