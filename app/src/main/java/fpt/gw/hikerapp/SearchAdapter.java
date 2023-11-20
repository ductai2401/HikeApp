package fpt.gw.hikerapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.TripViewHolder> implements Filterable {

    private List<Hike> tripList;
    private final List<Hike> tripListSearch;

    public SearchAdapter(List<Hike> tripList) {
        this.tripList = tripList;
        this.tripListSearch = tripList;
    }


    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new TripViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Hike trip = tripList.get(position);
        if(trip == null) {
            return;
        }

        holder.imgSearch.setImageResource(R.drawable.search);
        holder.txtNameSearch.setText(trip.getHikeName());
        holder.txtDateStartSearch.setText("Date Start: " + trip.getDateStart());
        holder.txtDescriptionSearch.setText("Description: " + trip.getDescription());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    //Search
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String valueSearch = charSequence.toString();
                if (valueSearch.isEmpty()){
                    tripList = tripListSearch;
                }else {
                    List<Hike> list = new ArrayList<>();
                    for (Hike trip : tripListSearch) {
                        if (trip.getHikeName().toLowerCase().contains(valueSearch.toLowerCase())){
                            list.add(trip);
                        }
                    }

                    tripList = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = tripList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                tripList = (List<Hike>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    //

    public static class TripViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView imgSearch;
        private TextView txtNameSearch;
        private TextView txtDateStartSearch;
        private TextView txtDescriptionSearch;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSearch = itemView.findViewById(R.id.imageSearch);
            txtNameSearch = (TextView) itemView.findViewById(R.id.textviewNameSearch);
            txtDateStartSearch = (TextView) itemView.findViewById(R.id.textviewDateStartSearch);
            txtDescriptionSearch = (TextView) itemView.findViewById(R.id.textviewDescriptionSearch);
        }
    }
}
