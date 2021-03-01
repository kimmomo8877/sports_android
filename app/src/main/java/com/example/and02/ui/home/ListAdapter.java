package com.example.and02.ui.home;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.and02.R;
import com.example.and02.ui.common.ListModel;
import com.ornach.nobobutton.NoboButton;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }
    private final List<ListModel> listModelList;

    private OnItemClickListener mListener = null ;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }


    public ListAdapter(List<ListModel> listModelList) {
        this.listModelList = listModelList;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        private final Button button_menu;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.button_menu = itemView.findViewById(R.id.button_menu);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                            Log.i("ListAdapter Click Event", String.valueOf(pos));
                        }
                    }
                }
            });


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int pos = getAdapterPosition() ;
//                    if (pos != RecyclerView.NO_POSITION) {
//                        Log.i("ListAdapter Click Event", String.valueOf(pos));
//                    }
//                }
//            });

        }

        public Button getButton_menu() {
            return button_menu;
        }
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_home_row, parent, false);
        return new ListViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {

        holder.getButton_menu().setText(listModelList.get(position).getMenu());

        final ListModel item = listModelList.get(position);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(position);
////                int pos = getAdapterPosition() ;
////                if (pos != RecyclerView.NO_POSITION) {
////                    // 리스너 객체의 메서드 호출.
////                    if (mListener != null) {
////                        mListener.onItemClick(v, pos) ;
////                    }
////                }
//            }
//        });


//        holder.setText("" + item);


//        holder.getButton_menu().setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                System.out.println(position);
////                Toast.makeText(mContext,item,Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return listModelList.size();
    }

    public List<ListModel> getListModelList() {
        return listModelList;
    }
}

