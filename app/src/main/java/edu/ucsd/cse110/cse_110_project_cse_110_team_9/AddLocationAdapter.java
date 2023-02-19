package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class AddLocationAdapter extends RecyclerView.Adapter<AddLocationAdapter.ViewHolder> {
    private List<AddLocation> addLocations = Collections.emptyList();

    public void setLocations(List<AddLocation> addLocations){
        this.addLocations.clear();
        this.addLocations = addLocations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private AddLocation addLocation;

        public ViewHolder(@NonNull View itemView, TextView textView) {
            super(itemView);
            this.textView = textView;
        }

        public AddLocation getAddLocation(){
            return addLocation;
        }

        public void setAddLocation(AddLocation addLocation){
            this.addLocation = addLocation;
            this.textView.setText(addLocation.toString());
        }
    }
}
