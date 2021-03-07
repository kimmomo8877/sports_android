package com.example.and02.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.and02.R;
import com.example.and02.ui.common.BoardModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> implements Filterable {
    private List<BoardModel> boardModelList;
    private List<BoardModel> boardModelList_orig;

    public BoardAdapter(List<BoardModel> boardModelList) {
        this.boardModelList = boardModelList;
    }

    public class BoardViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewContent;
        private final TextView textViewDate;

        public BoardViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.textViewTitle = itemView.findViewById(R.id.textView_teamDetail_noticeBoardTitle);
            this.textViewContent = itemView.findViewById(R.id.textView_teamDetail_noticeBoardContent);
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

        public TextView getTextViewContent() {
            return textViewContent;
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

        if (boardModelList.get(position).getTitle() != null) {
            holder.getTextViewTitle().setText("공지 " + boardModelList.get(position).getTitle());
        }

        if (boardModelList.get(position).getContent() != null) {
            holder.getTextViewContent().setText(boardModelList.get(position).getContent());
        }

        if (boardModelList.get(position).getRegisteDate() != null) {
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

    @Override
    public Filter getFilter() {
        return dataFilter;
    }

    private Filter dataFilter = new Filter() {
        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            // 삭제할 할 경우 로직
            List<BoardModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(boardModelList_orig);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (BoardModel item : boardModelList_orig) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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
                boardModelList.clear();
                boardModelList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }

    };

    public List<BoardModel> getBoardModelList_orig() {
        return boardModelList_orig;
    }

    public void setBoardModelList_orig(List<BoardModel> boardModelList_orig) {
        this.boardModelList_orig = boardModelList_orig;
    }


}
