package com.example.and02.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and02.R;
import com.example.and02.ui.common.BoardModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder>  {
    private List<BoardModel> boardModelList;
    private List<BoardModel> boardModelList_orig;

    public BoardAdapter(List<BoardModel> boardModelList) {
        this.boardModelList = boardModelList;
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewDate;

        public BoardViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.textViewTitle = itemView.findViewById(R.id.textView_teamDetail_noticeBoardTitle);
            this.textViewDate = itemView.findViewById(R.id.textView_teamDetail_noticeBoardDate);

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
    public BoardAdapter.BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_detailteam_noticeboard, parent, false);
        return new BoardAdapter.BoardViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.BoardViewHolder holder, int position) {

        if (boardModelList.get(position).getTitle() != null ) {
            holder.getTextViewTitle().setText(boardModelList.get(position).getTitle());
        }

        if (boardModelList.get(position).getRegisteDate() != null ) {
            holder.getTextViewDate().setText(boardModelList.get(position).getRegisteDate());
        }
    }

    @Override
    public int getItemCount() {
        return boardModelList.size();
    }

    public List<BoardModel> getBoardModelList() {
        return boardModelList;
    }


}
