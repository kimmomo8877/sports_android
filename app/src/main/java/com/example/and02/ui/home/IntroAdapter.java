package com.example.and02.ui.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.and02.R;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroViewHolder> {
    private final List<IntroModel> introModelList;

    public IntroAdapter(List<IntroModel> introModelList) {
        this.introModelList = introModelList;
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
