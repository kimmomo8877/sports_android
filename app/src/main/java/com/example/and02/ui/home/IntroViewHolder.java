package com.example.and02.ui.home;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.and02.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class IntroViewHolder extends RecyclerView.ViewHolder {
    private final TextView textViewTitle;
    private final TextView textViewSubTitle;

    public IntroViewHolder(@NonNull View itemView) {
        super(itemView);
        this.textViewTitle = itemView.findViewById(R.id.textView_title);
        this.textViewSubTitle = itemView.findViewById(R.id.textView_subTitle);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Intent intent = new Intent(v.getContext(), SecondActivity.class);
//        v.getContext().startActivity(intent);
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