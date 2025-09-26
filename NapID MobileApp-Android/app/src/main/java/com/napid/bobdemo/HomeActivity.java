package com.napid.bobdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.napid.bobdemo.Retrofit.Resource;
import com.napid.bobdemo.databinding.HomeActivityBinding;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements OnIDTouchListener {
    HomeActivityBinding homeActivityBinding;
    HomeViewModel homeViewModel;
    IDListAdapterNew idListAdapter;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Map<String, Integer> customerIds = new HashMap<>();
    private final long POLL_INTERVAL = 1000;
    private boolean isPolling = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivityBinding = DataBindingUtil.setContentView(this, R.layout.home_activity);
        HomeRepository homeRepository = new HomeRepository();
        HomeViewModelFactory homeViewModelFactory = new HomeViewModelFactory(this.getApplication(), homeRepository);
        homeViewModel = new ViewModelProvider(this, homeViewModelFactory).get(HomeViewModel.class);
        idListAdapter = new IDListAdapterNew(HomeActivity.this,HomeActivity.this);
        homeActivityBinding.digitalIdRecyclerView.setAdapter(idListAdapter);
        homeActivityBinding.digitalIdRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getID();
        homeActivityBinding.toolbarTitle.setText("89******83");
        homeActivityBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                idListAdapter.setData(Collections.emptyList());
                idListAdapter.notifyDataSetChanged();
                //homeViewModel.syncUpWithServerTime();
                getID();
            }
        });
    }

    private void getID() {


        homeViewModel.getID(HomeActivity.this).observe(HomeActivity.this, new Observer<Resource<List<UserIDListResponseModel>>>() {
            @Override
            public void onChanged(Resource<List<UserIDListResponseModel>> idList) {
                if (idList != null) {
                    switch (idList.status) {
                        case SUCCESS:
                            Log.e("IDLIst", "" + idList.data.size());
                            if (homeActivityBinding.progressBar.getVisibility() == View.VISIBLE) {
                                homeActivityBinding.progressBar.setVisibility(View.GONE);
                            }

                            if (!idList.data.isEmpty()) {
                                Log.e("idList.data",""+idList.data);
                                idListAdapter.setData(idList.data);
                                idListAdapter.notifyDataSetChanged();
                                homeActivityBinding.digitalIdRecyclerView.setVisibility(View.VISIBLE);
                                homeActivityBinding.swipeRefreshLayout.setRefreshing(false);
                            } else {
                                Log.e("Move to", "Registration");
                                //homeViewModel.invalidateUser();
                                //showAlertDialog("No Ids", "There is no ids associated with this number");
                            }
                            break;
                        case ERROR:
                            //showError(idList.message);
                            break;
                        case MAINTENANCE:
                            //showMaintenanceScreen();
                            break;
                    }
                }


            }
        });




}


    @Override
    public void onIDTouch(String customerID, int position) {
        homeViewModel.OnIDClicked(customerID);

        homeViewModel.checkOncliecked().observe(this, new Observer<Resource<AwakeResponseModel>>() {
            @Override
            public void onChanged(Resource<AwakeResponseModel> awakeResponseModel) {

                if (awakeResponseModel != null) {
                    switch (awakeResponseModel.status) {
                        case SUCCESS:

                            if (awakeResponseModel.data.getStatusCode().equalsIgnoreCase("SUCCESS")){
                                Log.e("ID","AwakeSuccessfull");
                                //homeViewModel.addTimer(customerID,30000,position);
                                addCustomerId(customerID,position);
                                homeActivityBinding.digitalIdRecyclerView.post(()->{
                                    if(idListAdapter!=null){
                                        Log.e("idchange position",""+position);
                                        idListAdapter.dataChanged(position, true);

                                    }
                                });
                                //idListAdapter.dataChanged(position, awakeResponseModel.getCreatedAt(), awakeResponseModel.getCurrentServerTime());

                                homeViewModel.checkOncliecked().removeObservers(HomeActivity.this);
                            }else {

                            }
                            break;
                        case ERROR:
                            //showError(awakeResponseModel.message);
                            break;
                        case MAINTENANCE:
                            //showMaintenanceScreen();
                            break;
                    }
                }


            }
        });
    }

    @Override
    public void onCancel(String customerID, int position) {


        homeViewModel.OnIDsleep(customerID).observe(this, new Observer<Resource<SleepResponseModel>>() {
            @Override
            public void onChanged(Resource<SleepResponseModel> sleepResponseModel) {

                if (sleepResponseModel != null) {
                    switch (sleepResponseModel.status) {
                        case SUCCESS:
                            //updateUI(sleepResponseModel.data);
                            if (sleepResponseModel.data.getStatusCode().equalsIgnoreCase("SUCCESS")){
                                Log.e("ID","SleepSuccessfull");

                                homeActivityBinding.digitalIdRecyclerView.post(()->{
                                    if(idListAdapter!=null){
                                        idListAdapter.dataChanged(position,false);
                                    }
                                });

                            }
                            break;
                        case ERROR:
                            //showError(sleepResponseModel.message);
                            break;
                        case MAINTENANCE:
                            //showMaintenanceScreen();
                            break;
                    }
                }

            }
        });
    }

    public void addCustomerId(String customerId, int position) {
        customerIds.put(customerId, position);
        if (!isPolling) {
            isPolling = true;
            startPolling();
        }
    }

    public void removeCustomerId(String customerId) {
        customerIds.remove(customerId);
        if (customerIds.isEmpty()) {
            stopPolling();
        }
    }

    private void startPolling() {
        handler.post(pollRunnable);
    }

    private void stopPolling() {
        handler.removeCallbacks(pollRunnable);
        isPolling = false;
    }

    private final Runnable pollRunnable = new Runnable() {
        @Override
        public void run() {
            if (customerIds.isEmpty()) {
                stopPolling();
                return;
            }

            for (Map.Entry<String, Integer> entry : new HashMap<>(customerIds).entrySet()) {
                String customerId = entry.getKey();
                int position = entry.getValue();
                homeViewModel.status(customerId).observe(HomeActivity.this, new Observer<Resource<StatusResponseModel>>() {
                    @Override
                    public void onChanged(Resource<StatusResponseModel> statusResponseModelResource) {
                        if (statusResponseModelResource.data.getIsEnabled()){

                        }else {
                            homeActivityBinding.digitalIdRecyclerView.post(()->{
                                if(idListAdapter!=null){
                                    idListAdapter.dataChanged(position,false);
                                }
                            });
                            removeCustomerId(customerId);
                        }
                    }
                });
            }

            handler.postDelayed(this, POLL_INTERVAL);
        }
    };


}

