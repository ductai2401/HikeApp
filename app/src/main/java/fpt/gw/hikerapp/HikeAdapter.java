package fpt.gw.hikerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class HikeAdapter extends BaseAdapter /*implements Filterable*/ {

    private final MainActivity context;
    private int Layout;
    private List<Hike> hikeList;

    public HikeAdapter(MainActivity context, int layout, List<Hike> tripList) {
        this.context = context;
        Layout = layout;
        this.hikeList = tripList;
    }

    @Override
    public int getCount() {
        return hikeList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    static class ViewHolder{
        TextView txtIdHike, txtHikeName, txtLocation, txtLength, txtDifficulty, txtDateStart, txtDateEnd, txtPark, txtDescription;
        ImageView imgDelete, imgEdit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(Layout, null);
            holder.txtIdHike = (TextView) view.findViewById(R.id.textviewIdHike);
            holder.txtHikeName = (TextView) view.findViewById(R.id.textviewHikeName);
            holder.txtLocation = (TextView) view.findViewById(R.id.textviewLocation);
            holder.txtLength = (TextView) view.findViewById(R.id.textviewLength);
            holder.txtDifficulty = (TextView) view.findViewById(R.id.textviewDifficulty);
            holder.txtDateStart = (TextView) view.findViewById(R.id.textviewDateStart);
            holder.txtDateEnd = (TextView) view.findViewById(R.id.textviewDateEnd);
            holder.txtPark = (TextView) view.findViewById(R.id.textviewPark);
            holder.txtDescription = (TextView) view.findViewById(R.id.textviewDescription);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imageviewDelete);
            holder.imgEdit = (ImageView) view.findViewById(R.id.imageviewEdit);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        Hike Hike = hikeList.get(i);

        holder.txtHikeName.setText(Hike.getHikeName());
        holder.txtIdHike.setText(String.valueOf(Hike.getIdHike()));
        holder.txtLocation.setText("Location: " + Hike.getLocation());
        holder.txtLength.setText("Length: " + Hike.getLengthHike());
        holder.txtDifficulty.setText("Level of difficulty:" + Hike.getDifficulty());
        holder.txtDateStart.setText("Start Date: " + Hike.getDateStart());
        holder.txtDateEnd.setText("End Date: " + Hike.getDateEnd());
        holder.txtPark.setText("Park Available: " + Hike.getPark());
        holder.txtDescription.setText("Description: " + Hike.getDescription());

        //Bat su kien edit
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogEditTrip(Hike.getHikeName(), Hike.getLocation(), Hike.getLengthHike(), Hike.getDifficulty(),
                        Hike.getDateStart(), Hike.getDateEnd(), Hike.getPark(), Hike.getDescription(), Hike.getIdHike());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDeleteTrip(Hike.getHikeName(), Hike.getIdHike());
            }
        });

        return view;
    }
}
