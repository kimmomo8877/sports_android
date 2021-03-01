package com.example.and02.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.INotificationSideChannel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.and02.R;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> implements Filterable {
  private List<InfraModel> homeModelList;
  private List<InfraModel> homeModelList_orig;

  public HomeAdapter(List<InfraModel> homeModelList) {
    this.homeModelList = homeModelList;
  }

  public class HomeViewHolder extends RecyclerView.ViewHolder {
    private final SimpleDraweeView imageView;
    private final TextView textViewTitle;

    public HomeViewHolder(@NonNull final View itemView) {
      super(itemView);

      this.textViewTitle = itemView.findViewById(R.id.textView_title);
      this.imageView = itemView.findViewById(R.id.imageView_title);

      int pos1 = getAdapterPosition() ;
      if (pos1 != RecyclerView.NO_POSITION) {
        if (homeModelList.get(pos1).getCheckVisiable() == View.INVISIBLE) {
          this.imageView.setVisibility(View.INVISIBLE);
          this.textViewTitle.setVisibility(View.INVISIBLE);
        } else {
          this.imageView.setVisibility(View.VISIBLE);
          this.textViewTitle.setVisibility(View.VISIBLE);
        }
      }
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          int pos = getAdapterPosition() ;
//          getFilter().filter("축구");
          if (pos != RecyclerView.NO_POSITION) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("infraModel", homeModelList.get(pos));

            Navigation.findNavController(v).navigate(R.id.action_home_to_facilityDetail, bundle);

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
  public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
      .inflate(R.layout.row_news, parent, false);
    return new HomeViewHolder(layout);
  }

  @Override
  public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

    holder.getTextViewTitle().setText(homeModelList.get(position).getName());
    Uri uri = Uri.parse(homeModelList.get(position).getAttachFile());
    holder.getDraweeView().setImageURI(uri);
  }

  @Override
  public int getItemCount() {
    return homeModelList.size();
  }

  public List<InfraModel> getHomeModelList() {
    return homeModelList;
  }


  @Override
  public Filter getFilter() {
    return exampleFilter;
  }

  private Filter exampleFilter = new Filter() {
    //Automatic on background thread
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

      // 삭제할 할 경우 로직
      List<InfraModel> filteredList = new ArrayList<>();

      if (constraint == null || constraint.length() == 0 || constraint.toString() == "전체") {
        filteredList.addAll(homeModelList_orig);
      } else {
        String filterPattern = constraint.toString().toLowerCase().trim();
        for (InfraModel item : homeModelList_orig) {
          //TODO filter 대상 setting
          if (item.getInfraCategoryModel().getName().toLowerCase().contains(filterPattern)) {
            filteredList.add(item);
          }
        }
      }
      FilterResults results = new FilterResults();
      results.values = filteredList;
      return results;


      // visiable 할 경우 로직
//            List<InfraModel> filteredList = new ArrayList<>();
//
//      for (InfraModel item : homeModelList) {
//
//        if (constraint == null || constraint.length() == 0 || constraint.toString() == "전체") {
//          item.setCheckVisiable(View.VISIBLE);
//        } else {
//          String filterPattern = constraint.toString().toLowerCase().trim();
//          if (item.getInfraCategoryModel().getName().toLowerCase().contains(filterPattern)) {
//            item.setCheckVisiable(View.INVISIBLE);
//
//          } else {
//            item.setCheckVisiable(View.VISIBLE);
//          }
//          filteredList.add(item);
//        }
//
//      }
//
//      FilterResults results = new FilterResults();
//      results.values = filteredList;
//      return results;
    }

    //Automatic on UI thread
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      homeModelList.clear();
      homeModelList.addAll((List) results.values);
      notifyDataSetChanged();
    }
  };

  public List<InfraModel> getHomeModelList_orig() {
    return homeModelList_orig;
  }

  public void setHomeModelList_orig(List<InfraModel> homeModelList_orig) {
    this.homeModelList_orig = homeModelList_orig;
  }
}

//          Bundle bundle = new Bundle();
//        bundle.putParcelable("infraNo",);
//        bundle.putString("infraNo", "4387c0372e");
//          Navigation.findNavController(v).navigate(R.id.action_home_to_facilityDetail, bundle);
