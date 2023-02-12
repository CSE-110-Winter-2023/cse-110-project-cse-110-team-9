package edu.ucsd.cse110.cse_110_project_cse_110_team_9.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    public MutableLiveData<MapUIState> item = new MutableLiveData<>();

    public LiveData<MapUIState> getUiState(){
        return  item;
    }

    public void setItem(MapUIState item){
        this.item.setValue(item);
    }


}
