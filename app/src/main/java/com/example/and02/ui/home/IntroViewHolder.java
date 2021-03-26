package com.example.and02.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.and02.R;
import com.facebook.drawee.view.SimpleDraweeView;

//public class IntroViewHolder extends RecyclerView.ViewHolder {
//    private final TextView textViewTitle;
//    private final Button buttonViewSubTitle;
//
//    public IntroViewHolder(@NonNull View itemView) {
//        super(itemView);
//        this.textViewTitle = itemView.findViewById(R.id.itextView_intro_title);
//        this.buttonViewSubTitle = itemView.findViewById(R.id.button_intro_subTitle);
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int pos = getAdapterPosition() ;
//                if (pos != RecyclerView.NO_POSITION) {
//
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="));
//
//
//                }
//            }
//        });
//
//    }
//
//    public TextView getTextViewTitle() {
//        return textViewTitle;
//    }
//
//    public Button getButtonViewSubTitle() {
//        return buttonViewSubTitle;
//    }
//}