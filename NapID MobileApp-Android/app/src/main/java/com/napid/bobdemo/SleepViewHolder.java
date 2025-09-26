package com.napid.bobdemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;


import com.napid.bobdemo.databinding.DigitalidListitemSleepBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SleepViewHolder extends RecyclerView.ViewHolder{
    private final DigitalidListitemSleepBinding binding;
    ConvertToImage convertToImage = new ConvertToImage();
    EmailAliasConverter emailAliasConverter = new EmailAliasConverter();
    public SleepViewHolder(DigitalidListitemSleepBinding binding) {
        super(binding.getRoot());
        this.binding = binding;


    }

    public void bind(UserIDListResponseModel sleepId, Context context, OnIDTouchListener listener){
        if (binding.progressLnr.getVisibility()==View.VISIBLE){
            binding.progressLnr.setVisibility(View.GONE);
        }
        binding.cardTxt.setText(sleepId.getAppName());
        binding.cardTxt.setTextColor(context.getColor(R.color.black));
        if (sleepId.getAppName().equalsIgnoreCase("Credit Card")||sleepId.getAppName().equalsIgnoreCase("Debit Card")){
            /*Bitmap bitmap = convertToImage.setImageFromBase64(context.getDrawable(R.drawable.card));
            RoundedBitmapDrawable drawable = new RoundedBitmapDrawable(bitmap, 50);
            binding.card.setBackground(drawable);*/

            Log.e("cardsleep",""+sleepId.getAppName());
            binding.securedTxt.setTextColor(context.getColor(R.color.white));
            binding.digitalIdName.setText("XX ".concat(sleepId.getAlias().substring(sleepId.getAlias().length()-4,sleepId.getAlias().length())));
            binding.digitalIdName.setTextColor(context.getColor(R.color.red_napid));

            binding.securedLogo.setImageDrawable(context.getDrawable(R.drawable.verified2));
        }else {
            Log.e("other",""+sleepId.getAppName());
            binding.digitalIdName.setText(emailAliasConverter.Converter(sleepId.getAlias()));
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
