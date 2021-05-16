package com.hiball.gssc.ui.team;

import android.net.Uri;
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
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> implements Filterable {
    private List<ReservationModel> reservationModelList;
    private List<ReservationModel> reservationModelList_orig;

    public ReservationAdapter(List<ReservationModel> reservationModelList) {
        this.reservationModelList =  reservationModelList;
    }

    public class ReservationViewHolder extends RecyclerView.ViewHolder {

//        private final ImageView imageViewMain;
        private final TextView textViewTitle;
        private final TextView textViewDate;
        private final SimpleDraweeView imageViewImage;

        public ReservationViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.imageViewImage = itemView.findViewById(R.id.imageView_teamDetail_reservationImage);
            this.textViewTitle = itemView.findViewById(R.id.textView_teamDetail_reservationName);
            this.textViewDate = itemView.findViewById(R.id.textView_teamDetail_reservationRegistDate);

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

        public SimpleDraweeView getImageViewImage() {
            return imageViewImage;
        }
    }

    @NonNull
    @Override
    public ReservationAdapter.ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_detailteam_reservation, parent, false);
        return new ReservationAdapter.ReservationViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.ReservationViewHolder holder, int position) {


        if (reservationModelList.get(position).getInfra().getAttachFile() != null ) {
            Uri uri = Uri.parse(reservationModelList.get(position).getInfra().getAttachFile());
            holder.getImageViewImage().setImageURI(uri);
        }

        if (reservationModelList.get(position).getInfra().getName() != null ) {
            holder.getTextViewTitle().setText(reservationModelList.get(position).getInfra().getName());
        }

        if (reservationModelList.get(position).getRegisteDate() != null ) {
            holder.getTextViewDate().setText(reservationModelList.get(position).getRegisteDate());
        }
    }

    @Override
    public int getItemCount() {
        return reservationModelList.size();
    }

    public List<ReservationModel> getReservationModelList() {
        return reservationModelList;
    }

    public void setReservationModelList(List<ReservationModel> reservationModelList) {
        this.reservationModelList = reservationModelList;
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
            List<ReservationModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(reservationModelList_orig);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ReservationModel item : reservationModelList_orig) {
                    if (item.getInfra().getName().toLowerCase().contains(filterPattern)) {
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
                reservationModelList.clear();
                reservationModelList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }

    };

    public List<ReservationModel> getReservationModelList_orig() {
        return reservationModelList_orig;
    }

    public void setReservationModelList_orig(List<ReservationModel> reservationModelList_orig) {
        this.reservationModelList_orig = reservationModelList_orig;
    }

}
