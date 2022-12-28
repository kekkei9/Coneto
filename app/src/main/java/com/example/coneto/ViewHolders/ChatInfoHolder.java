package com.example.coneto.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.coneto.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.rxjava3.annotations.NonNull;

public class ChatInfoHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.img_avatar)
    public ImageView img_avatar;

    @BindView(R.id.text_name)
    public TextView text_name;

    @BindView(R.id.last_message)
    public TextView last_message;

    @BindView(R.id.text_time)
    public TextView text_time;
    Unbinder unbinder;
    public ChatInfoHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);
    }
}
