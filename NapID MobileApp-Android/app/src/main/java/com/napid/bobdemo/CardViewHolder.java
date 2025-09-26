package com.napid.bobdemo;


import android.content.Context;

import android.util.Log;
import android.view.View;


import androidx.recyclerview.widget.RecyclerView;


import com.napid.bobdemo.databinding.DigitalidListitemCardBinding;


public class CardViewHolder extends RecyclerView.ViewHolder{
    private final DigitalidListitemCardBinding binding;

    public CardViewHolder(DigitalidListitemCardBinding binding) {
        super(binding.getRoot());
        this.binding = binding;


    }

    public void bind(UserIDListResponseModel sleepId, Context context, OnIDTouchListener listener){
        if (binding.progressLnr.getVisibility()==View.VISIBLE){
            binding.progressLnr.setVisibility(View.GONE);
        }
        binding.cardTxt.setText(sleepId.getAppName());
        binding.cardTxt.setTextColor(context.getColor(R.color.black));

        if (sleepId.getAppName().equalsIgnoreCase("Debit Card")||sleepId.getAppName().equalsIgnoreCase("Credit Card")){
            Log.e("card",""+sleepId.getAppName());
            binding.card.setBackground(context.getDrawable(R.drawable.card));
            binding.securedTxt.setTextColor(context.getColor(R.color.white));
            binding.digitalIdName.setText("XX ".concat(sleepId.getAlias().substring(sleepId.getAlias().length()-4,sleepId.getAlias().length())));
            binding.digitalIdName.setTextColor(context.getColor(R.color.red_napid));


            binding.securedLogo.setImageDrawable(context.getDrawable(R.drawable.verified2));
        }else {
            Log.e("others",""+sleepId.getAppName());
            binding.card.setBackground(context.getDrawable(R.drawable.id_card_background));
            binding.securedTxt.setTextColor(context.getColor(R.color.napid_white));
            binding.digitalIdName.setTextColor(context.getColor(R.color.red_napid));
            binding.securedLogo.setImageDrawable(context.getDrawable(R.drawable.verified2));

        }
        binding.getRoot().setOnClickListener(v->{
            binding.progressLnr.setVisibility(View.VISIBLE);
            int pos = getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION){
                listener.onIDTouch(sleepId.getDigitalId(),pos);

            }
        });

    }


}
