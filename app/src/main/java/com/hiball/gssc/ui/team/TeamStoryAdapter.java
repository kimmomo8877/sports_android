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
import com.hiball.gssc.ui.common.BoardModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class TeamStoryAdapter extends RecyclerView.Adapter<TeamStoryAdapter.TeamStoryViewHolder> implements Filterable {
    private List<BoardModel> boardModelList;
    private List<BoardModel> boardModelList_orig;

    public TeamStoryAdapter(List<BoardModel> boardModelList) {
        this.boardModelList = boardModelList;
    }

    public class TeamStoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewUser;
        private final TextView textViewRegisteDate;
        private final TextView textViewContent;

        private final SimpleDraweeView imageUser;
        private final SimpleDraweeView imageMain;

        public TeamStoryViewHolder(@NonNull final View itemView) {
            super(itemView);

            this.textViewUser = itemView.findViewById(R.id.textView_teamDetail_teamStoryName);
            this.textViewRegisteDate = itemView.findViewById(R.id.textView_teamDetail_teamStoryRegisteDate);
            this.textViewContent = itemView.findViewById(R.id.textView_teamDetail_teamStoryContent);

            this.imageUser = itemView.findViewById(R.id.imageView_teamDetail_teamStoryUser);
            this.imageMain = itemView.findViewById(R.id.imageView_teamDetail_teamStoryImage);


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

        public TextView getTextViewUser() {
            return textViewUser;
        }

        public TextView getTextViewRegisteDate() {
            return textViewRegisteDate;
        }

        public TextView getTextViewContent() {
            return textViewContent;
        }

        public SimpleDraweeView getImageUser() {
            return imageUser;
        }

        public SimpleDraweeView getImageMain() {
            return imageMain;
        }
    }

    @NonNull
    @Override
    public TeamStoryAdapter.TeamStoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_detailteam_teamstory, parent, false);
        return new TeamStoryAdapter.TeamStoryViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamStoryAdapter.TeamStoryViewHolder holder, int position) {

        if (boardModelList.get(position).getAttachFile() != null ) {
            Uri uri = Uri.parse(boardModelList.get(position).getAttachFile());
            holder.getImageMain().setImageURI(uri);
        }

        if (boardModelList.get(position).getWriter().getName() != null ) {
            holder.getTextViewUser().setText(boardModelList.get(position).getWriter().getName());
        }

        if (boardModelList.get(position).getWriter().getRegisteDate()!= null ) {
            holder.getTextViewRegisteDate().setText(boardModelList.get(position).getWriter().getRegisteDate());
        }

        if (boardModelList.get(position).getContent()!= null ) {
            holder.getTextViewContent().setText(boardModelList.get(position).getContent());
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
