package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import androidx.lifecycle.MutableLiveData;

import java.sql.Time;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimeService {


    private MutableLiveData<Long> timeValue;

    private ScheduledFuture<?> clockFuture;


    //Singeton instance of TimeService

    private static TimeService instance;

    public static TimeService singleton(){
        if (instance == null)
        {
            instance = new TimeService();
        }
        return instance;
    }

    protected TimeService() {
        this.timeValue = new MutableLiveData<>();
        this.registerTimeListener();

    }

    public void registerTimeListener() {
        ScheduledExecutorService executer = Executors.newSingleThreadScheduledExecutor();
        this.clockFuture = executer.scheduleAtFixedRate(() -> {
            this.timeValue.postValue(System.currentTimeMillis());
        }, 0, 1000, TimeUnit.MILLISECONDS);

    }

    public void unregisterTimeListener(){
        this.clockFuture.cancel(true);
    }


    public void setMockTimeSource(MutableLiveData<Long> mockTimeSource){
        unregisterTimeListener();
        this.timeValue = mockTimeSource;
    }

    public MutableLiveData<Long> getTime()
    {
        return this.timeValue;
    }

}
