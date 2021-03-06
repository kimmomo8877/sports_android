package com.example.and02.ui.team;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and02.R;
import com.example.and02.ui.common.BoardModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.Serializable;
import java.util.List;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>  {
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


}
