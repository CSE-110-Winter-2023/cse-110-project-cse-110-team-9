package edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.model.MarkerOptions;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<MapUIState> uiState = new MutableLiveData<>();

    private MutableLiveData<MarkerOptions> newMarker = new MutableLiveData<>();

    //private MutableLiveData<Point>

    public LiveData<MapUIState> getUiState(){
        return  uiState;
    }

    public void setItem(MapUIState item){
        this.uiState.setValue(item);
    }

    public  LiveData<MarkerOptions> getNewMarker(){
        return newMarker;
    }

    public void addNewMarker(MarkerOptions marker)
    {
        this.newMarker.setValue(marker);
    }
}
