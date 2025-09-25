package com.napid.bobdemo;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;


import com.napid.bobdemo.databinding.DigitalidListitemCardAwakeBinding;




public class CardAwakeViewHolder extends RecyclerView.ViewHolder{
    private final DigitalidListitemCardAwakeBinding binding;
    private SparseArray<CountDownTimer> timers = new SparseArray<>();
    public CardAwakeViewHolder(DigitalidListitemCardAwakeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(UserIDListResponseModel AwakeId, Context context, OnIDTouchListener listener, IDListAdapterNew adapter){
        if (binding.progressLnr.getVisibility()==View.VISIBLE){
            binding.progressLnr.setVisibility(View.GONE);
        }

        binding.cardTxt.setText(AwakeId.getAppName());


        if (AwakeId.getAppName().equalsIgnoreCase("Credit Card")||AwakeId.getAppName().equalsIgnoreCase("Debit Card")){

            binding.card.setBackground(context.getDrawable(R.drawable.card));
            binding.digitalIdName.setText("XX ".concat(AwakeId.getAlias().substring(AwakeId.getAlias().length()-4,AwakeId.getAlias().length())));
            binding.securedTxt.setText(context.getString(R.string.card_use_it_now));

        }else {

            binding.digitalIdName.setText(AwakeId.getAlias());
            binding.securedTxt.setText(context.getString(R.string.id_use_it_now));
        }

        if (AwakeId.getIsEnabled()){
            adapter.startCountdown(binding.timer,30000,getAdapterPosition(),adapter,AwakeId.getDigitalId());
        }

        binding.getRoot().setOnClickListener(v->{
            binding.progressLnr.setVisibility(View.VISIBLE);
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION){
                listener.onCancel(AwakeId.getDigitalId(),pos);
            }
        });
    }



}
