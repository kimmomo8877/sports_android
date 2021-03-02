package com.example.and02.ui.team;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> implements Filterable {
    private List<FavoriteModel> favoriteModelList;
    private List<FavoriteModel> favoriteModelList_orig;

    public FavoriteAdapter(List<FavoriteModel> favoriteModelList) {
        this.favoriteModelList = favoriteModelList;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;

        public FavoriteViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.textViewTitle = itemView.findViewById(R.id.textView_favorite_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Log.i("FavAdapter", favoriteModelList.get(pos).getSearchWord());
                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("infraModel", searchModelList.get(pos));
                        bundle.putString("searchWord", favoriteModelList.get(pos).getSearchWord());
                        Navigation.findNavController(v).navigate(R.id.action_searchFragment_to_searchResultFragment, bundle);
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
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_favorite_adapter, parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.FavoriteViewHolder holder, int position) {

        if (favoriteModelList.get(position).getSearchWord() != null ) {
            holder.getTextViewTitle().setText(favoriteModelList.get(position).getSearchWord());
        }

    }

    @Override
    public int getItemCount() {
        return favoriteModelList.size();
    }

    public List<FavoriteModel> getFavoriteModelList() {
        return favoriteModelList;
    }


    @Override
    public Filter getFilter() {
        return dataFilter;
    }
    private Filter dataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FavoriteModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(favoriteModelList_orig);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (FavoriteModel item : favoriteModelList_orig) {
                    if (item.getSearchWord().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            favoriteModelList.clear();
            favoriteModelList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public List<FavoriteModel> getFavoriteModelList_orig() {
        return favoriteModelList_orig;
    }

    public void setFavoriteModelList_orig(List<FavoriteModel> favoriteList_orig) {
        this.favoriteModelList_orig = favoriteList_orig;
    }
}