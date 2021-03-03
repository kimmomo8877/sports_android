package com.example.and02.ui.touring;

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
import com.example.and02.ui.home.HomeAdapter;
import com.example.and02.ui.home.InfraModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class TouringAdapter extends RecyclerView.Adapter<TouringAdapter.TouringViewHolder> implements Filterable {
    private List<InfraModel> infraModelList;
    private List<InfraModel> infraModelList_orig;

    public TouringAdapter(List<InfraModel> infraModelList) {
        this.infraModelList = infraModelList;
    }

    public class TouringViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDraweeView imageView;
        private final TextView textViewTitle;

        public TouringViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.textViewTitle = itemView.findViewById(R.id.textView_title);
            this.imageView = itemView.findViewById(R.id.imageView_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
//          getFilter().filter("축구");
                    if (pos != RecyclerView.NO_POSITION) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("infraModel", infraModelList.get(pos));

                        Navigation.findNavController(v).navigate(R.id.action_navigation_touring_to_facilityDetailFragment, bundle);

                    }
//          Navigation.findNavController(v).navigate(R.id.action_home_to_facilityDetail);
//            Intent intent = new Intent(v.getContext(), HomeFragment.class);
////            intent.putExtra(bundle);
                }
            });

        }
        public TextView getTextViewTitle() {
            return textViewTitle;
        }
        public SimpleDraweeView getDraweeView() {
            return imageView;
        }
    }

    @NonNull
    @Override
    public TouringAdapter.TouringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        return new TouringAdapter.TouringViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TouringAdapter.TouringViewHolder holder, int position) {

        holder.getTextViewTitle().setText(infraModelList.get(position).getName());
        Uri uri = Uri.parse(infraModelList.get(position).getAttachFile());
        holder.getDraweeView().setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return infraModelList.size();
    }

    public List<InfraModel> getHomeModelList() {
        return infraModelList;
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
            List<InfraModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0 || constraint.toString() == "전체") {
                filteredList.addAll(infraModelList_orig);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (InfraModel item : infraModelList_orig) {
                    //TODO filter 대상 setting
                    if (item.getInfraCategoryModel().getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            infraModelList.clear();
            infraModelList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public List<InfraModel> getTeamModelList_orig() {
        return infraModelList_orig;
    }

    public void setInfraModelList_orig(List<InfraModel> infraModelList_orig) {
        this.infraModelList_orig = infraModelList_orig;
    }

}