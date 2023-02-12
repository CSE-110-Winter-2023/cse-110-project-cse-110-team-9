package edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Map;

public class MapViewModel extends ViewModel
{
    private MutableLiveData<MapUIState>
            uiState = new MutableLiveData(new MapUIState(10.0,10.0));

    public LiveData<MapUIState> getUiState(){
        return  uiState;
    }


    public void updateMap()
    {
        uiState.setValue(new MapUIState(20.0,20.0));
    }


}
