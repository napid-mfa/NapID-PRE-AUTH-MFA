package com.napid.bobdemo;




import static com.napid.bobdemo.ViewType.VIEW_TYPE_AWAKE;
import static com.napid.bobdemo.ViewType.VIEW_TYPE_CARD;
import static com.napid.bobdemo.ViewType.VIEW_TYPE_CARD_AWAKE;
import static com.napid.bobdemo.ViewType.VIEW_TYPE_CARD_OPTOUT;
import static com.napid.bobdemo.ViewType.VIEW_TYPE_OPTOUT;
import static com.napid.bobdemo.ViewType.VIEW_TYPE_SLEEP;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.napid.bobdemo.databinding.DigitalidListitemAwakeBinding;
import com.napid.bobdemo.databinding.DigitalidListitemCardAwakeBinding;
import com.napid.bobdemo.databinding.DigitalidListitemCardBinding;
import com.napid.bobdemo.databinding.DigitalidListitemSleepBinding;

import java.util.List;

public class IDListAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<UserIDListResponseModel> IDList;
    private OnIDTouchListener idTouchListener;
    private Context context;

    private SparseArray<CountDownTimer> timers = new SparseArray<>();
    CountDownTimer  countDownTimer;

    public IDListAdapterNew(OnIDTouchListener idTouchListener, Context context) {


        this.idTouchListener = idTouchListener;
        this.context = context;
    }

    public void setData(List<UserIDListResponseModel> IDList) {
        this.IDList = IDList;
    }

    @Override
    public int getItemViewType(int position) {
        Boolean createdAt = IDList.get(position).getIsEnabled();


        if (IDList.get(position).getAppName().equalsIgnoreCase("Debit Card")||IDList.get(position).getAppName().equalsIgnoreCase("Credit Card")){
            if (createdAt) {
                return VIEW_TYPE_CARD_AWAKE.ordinal(); // Show Cancelled View
            } else {
                return VIEW_TYPE_CARD.ordinal(); // Show Completed View
            }

        }else {
            if (createdAt) {
                return VIEW_TYPE_AWAKE.ordinal(); // Show Cancelled View
            } else{
                return VIEW_TYPE_SLEEP.ordinal(); // Show Completed View
            }
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewType type = ViewType.values()[viewType];
        switch (type) {
            case VIEW_TYPE_AWAKE:
                DigitalidListitemAwakeBinding awakeBinding = DigitalidListitemAwakeBinding.inflate(inflater, parent, false);
                return new AwakeViewHolder(awakeBinding);
            case VIEW_TYPE_SLEEP:
                DigitalidListitemSleepBinding sleepBinding = DigitalidListitemSleepBinding.inflate(inflater, parent, false);
                return new SleepViewHolder(sleepBinding);
            case VIEW_TYPE_CARD:
                DigitalidListitemCardBinding cardBinding = DigitalidListitemCardBinding.inflate(inflater, parent, false);
                return new CardViewHolder(cardBinding);
            case VIEW_TYPE_CARD_AWAKE:
                DigitalidListitemCardAwakeBinding cardAwakeBinding = DigitalidListitemCardAwakeBinding.inflate(inflater, parent, false);
                return new CardAwakeViewHolder(cardAwakeBinding);
            default:
                throw new IllegalArgumentException("Invalid ViewType: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserIDListResponseModel ID = IDList.get(position);
        if (holder instanceof AwakeViewHolder) {
            ((AwakeViewHolder) holder).bind(ID, context,idTouchListener,this);
        } else if (holder instanceof SleepViewHolder) {
            ((SleepViewHolder) holder).bind(ID, context,idTouchListener);
        } else if (holder instanceof CardViewHolder) {
            ((CardViewHolder) holder).bind(ID, context,idTouchListener);
        }else if (holder instanceof CardAwakeViewHolder) {
            ((CardAwakeViewHolder) holder).bind(ID, context,idTouchListener,this);
        }
    }

    public void dataChanged(int position, Boolean createdAt){
        if (!IDList.isEmpty()){

            IDList.get(position).setIsEnabled(createdAt);

            notifyItemChanged(position);
        }
    }





    public void loaderClose(int position){
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return IDList == null ? 0 : IDList.size();
    }

    public void startCountdown(TextView countdownTextView, long endTime, int position, IDListAdapterNew adapter,String CustomerID) {
        Log.e("TimerSize",""+timers.size());
        if (timers.size()>0){
            if (timers.get(position) != null) {
                timers.get(position).cancel();
            }
        }

           /* if (countDownTimer !=null){
                countDownTimer.cancel();
            }*/

        countDownTimer = new CountDownTimer(endTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished < 1000) {
                    onFinish();
                    //op the timer if the countdown has finished
                } else {
                    countdownTextView.setText(String.valueOf(millisUntilFinished / 1000));
                    String timerText = countdownTextView.getText().toString();
                    int textLength = timerText.length();

// Set different text sizes based on the length of the text
                    if (textLength <= 2) {
                        countdownTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80);  // Larger text size for 1-2 digits
                    } else if (textLength == 3) {
                        countdownTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);  // Medium text size for 3 digits
                    } else {
                        countdownTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);  // Smaller text size for 4 or more digits
                    }
                }
            }

            @Override
            public void onFinish() {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        adapter.dataChanged(position,false);
                        idTouchListener.onCancel(CustomerID,position);

                    }
                }, 100);

            }
        };
        //countDownTimer.start();
        timers.put(position, countDownTimer);
        timers.get(position).start();
        Log.e("posrion Timer",""+position);

    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof AwakeViewHolder){
            cleanup(holder.getAdapterPosition());
        }else if(holder instanceof CardAwakeViewHolder){
            cleanup(holder.getAdapterPosition());
        }
    }

    public void cleanup(int position) {
        // Cancel timer to prevent memory leaks
        /*if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }*/
        if (timers.get(position) != null) {
            timers.get(position).cancel();
        }
    }
}
