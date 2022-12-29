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

public class ChatPictureHolder extends RecyclerView.ViewHolder {
    private Unbinder unbinder;

    @BindView(R.id.text_time)
    public TextView text_time;

    @BindView(R.id.text_chat_message)
    public TextView text_chat_message;

    @BindView(R.id.img_preview)
    public ImageView img_preview;

    public ChatPictureHolder(@NonNull View itemView) {
        super(itemView);
        unbinder = ButterKnife.bind(this, itemView);

    }

}
