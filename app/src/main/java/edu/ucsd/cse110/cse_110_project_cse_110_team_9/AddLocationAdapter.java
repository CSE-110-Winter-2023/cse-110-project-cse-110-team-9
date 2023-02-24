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
    private List<Location> locations = Collections.emptyList();

    public void setLocations(List<Location> locations){
        this.locations.clear();
        this.locations = locations;
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
        holder.setAddLocation(locations.get(position));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

//    @Override
//    public long getItemId(int position)
//    {
//        return addLocations.get(position).id;
//    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private Location location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.location_item_text);
        }

        public Location getAddLocation(){
            return location;
        }

        public void setAddLocation(Location location){
            this.location = location;
            this.textView.setText(location.toString());
        }
    }
}
