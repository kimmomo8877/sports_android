package com.example.and02.ui.team;

import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
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

public class SearchFacilityAdapter extends RecyclerView.Adapter<SearchFacilityAdapter.SearchFacilityViewHolder>  {
    private List<InfraModel> infraModelList;
    private List<InfraModel> infraModelList_orig;

    public SearchFacilityAdapter(List<InfraModel> infraModelList) {
        this.infraModelList = infraModelList;
    }

    public class SearchFacilityViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewName;
        private final TextView textViewAddress;
        private final SimpleDraweeView imageViewImage;

        public SearchFacilityViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.imageViewImage = itemView.findViewById(R.id.imageView_searchFacility_image);
            this.textViewName = itemView.findViewById(R.id.textView_searchFacility_name);
            this.textViewAddress = itemView.findViewById(R.id.textView_searchFacility_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("infraModel", infraModelList.get(pos));
//                        bundle.putString("searchWord", favoriteModelList.get(pos).getSearchWord());
                        Navigation.findNavController(v).navigate(R.id.action_searchResultFragment_to_facilityMapFragment, bundle);
                    }
                }
            });

        }

        public TextView getTextViewName() {
            return textViewName;
        }

        public TextView getTextViewAddress() {
            return textViewAddress;
        }

        public SimpleDraweeView getImageViewImage() {
            return imageViewImage;
        }
    }

    @NonNull
    @Override
    public SearchFacilityAdapter.SearchFacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_searchfacility_adapter, parent, false);
        return new SearchFacilityAdapter.SearchFacilityViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchFacilityAdapter.SearchFacilityViewHolder holder, int position) {

        if (infraModelList.get(position).getName() != null ) {
            holder.getTextViewName().setText(infraModelList.get(position).getName());
        }

        if (infraModelList.get(position).getAddress() != null ) {
            holder.getTextViewAddress().setText(infraModelList.get(position).getAddress());
        }

        if (infraModelList.get(position).getAttachFile() != null ) {
            Uri uri = Uri.parse(infraModelList.get(position).getAttachFile());
            holder.getImageViewImage().setImageURI(uri);
        }

    }

    @Override
    public int getItemCount() {
        return infraModelList.size();
    }

    public List<InfraModel> getInfraModelList() {
        return infraModelList;
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
