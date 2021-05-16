//package com.example.and02.ui.home;
//
//import android.content.Intent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.and02.R;
//import com.facebook.drawee.view.SimpleDraweeView;
//
//public class ListViewHolder extends RecyclerView.ViewHolder {
//    private final Button button_menu;
//
//    public ListViewHolder(@NonNull View itemView) {
//        super(itemView);
//        this.button_menu = itemView.findViewById(R.id.button_menu);
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = getAdapterPosition() ;
//                if (pos != RecyclerView.NO_POSITION) {
//
//                }
//            }
//        });
//
//    }
//
//    public Button getButton_menu() {
//        return button_menu;
//    }
//}