package com.napid.bobdemo;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


import com.napid.bobdemo.databinding.DigitalidListitemAwakeBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


public class AwakeViewHolder extends RecyclerView.ViewHolder{
    private final DigitalidListitemAwakeBinding binding;

    private SparseArray<CountDownTimer> timers = new SparseArray<>();
    public AwakeViewHolder(DigitalidListitemAwakeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(UserIDListResponseModel AwakeId, Context context, OnIDTouchListener listener, IDListAdapterNew adapter){
        if (binding.progressLnr.getVisibility()==View.VISIBLE){
            binding.progressLnr.setVisibility(View.GONE);
        }
        binding.cardTxt.setText(AwakeId.getAppName());
        if (AwakeId.getAppName().contains("card")){
            Log.e("cardawake",""+AwakeId.getAppName());
            binding.digitalIdName.setText("XX ".concat(AwakeId.getAlias().substring(AwakeId.getAlias().length()-4,AwakeId.getAlias().length())));
            binding.securedTxt.setText(context.getString(R.string.card_use_it_now));
        }else {
            Log.e("other",""+AwakeId.getAppName());
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
