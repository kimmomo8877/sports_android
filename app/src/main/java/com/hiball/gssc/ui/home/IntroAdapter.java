package com.hiball.gssc.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hiball.gssc.R;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder> {
    private final List<IntroModel> introModelList;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    private ListAdapter.OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(ListAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public IntroAdapter(List<IntroModel> introModelList) {
        this.introModelList = introModelList;
    }

    public class IntroViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewSubTitle;

        public IntroViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.itextView_intro_title);
            this.textViewSubTitle = itemView.findViewById(R.id.textView_intro_subTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
//                            button_menu.setBackgroundColor(Color.BLACK);
                            Log.i("Intro Click Event", String.valueOf(pos));
                        }
                    }
                }
            });

        }

        public TextView getTextViewTitle() {
            return textViewTitle;
        }

        public TextView getTextViewSubTitle() {
            return textViewSubTitle;
        }
    }

    @NonNull
    @Override
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.intro_home_row, parent, false);
        return new IntroViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull IntroViewHolder holder, int position) {

        holder.getTextViewTitle().setText(introModelList.get(position).getTitleText());
        holder.getTextViewSubTitle().setText(introModelList.get(position).getTitleSubText());
//        Uri uri = Uri.parse(hotelRModelList.get(position).getAttachFile());
//        holder.getDraweeView().setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return introModelList.size();
    }
}
