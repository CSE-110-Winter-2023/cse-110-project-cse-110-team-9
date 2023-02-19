package edu.ucsd.cse110.cse_110_project_cse_110_team_9;

import android.view.LayoutInflater;
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
         View view = LayoutInflater.from(parent.getContext())
                 .inflate(R.layout.location_entry, parent, false);
         return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setAddLocation(addLocations.get(position));
    }

    @Override
    public int getItemCount() {
        return addLocations.size();
    }

//    @Override
//    public long getItemId(int position)
//    {
//        return addLocations.get(position).id;
//    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private AddLocation addLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.location_item_text);
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
