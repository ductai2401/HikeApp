package fpt.gw.hikerapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ObservationAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Observation> observationList;

    public ObservationAdapter(Context context, int layout, ArrayList<Observation> observationList) {
        this.context = context;
        this.layout = layout;
        this.observationList = observationList;
    }

    @Override
    public int getCount() {
        return observationList.size();
    }

    private class ViewHolderObservation {
        TextView txtIdHike, txtObservationType, txtObservationDescription, txtObDate, txtObComment;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderObservation holderObservation;

        if(view == null) {
            holderObservation = new ViewHolderObservation();
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            holderObservation.txtIdHike = (TextView) view.findViewById(R.id.textviewObservationIdHike);
            holderObservation.txtObservationType = (TextView) view.findViewById(R.id.textviewObservationType);
            holderObservation.txtObservationDescription = (TextView) view.findViewById(R.id.textviewObservationDescription);
            holderObservation.txtObDate = (TextView) view.findViewById(R.id.textviewObservationDate);
            holderObservation.txtObComment = (TextView) view.findViewById(R.id.textviewObservationComment);
            view.setTag(holderObservation);
        }else {
            holderObservation = (ViewHolderObservation) view.getTag();
        }

        Observation observation = observationList.get(i);

        holderObservation.txtIdHike.setText(String.valueOf(observation.getHikeId()));
        holderObservation.txtObservationType.setText("Type: " + observation.getType());
        holderObservation.txtObservationDescription.setText("Description: " + observation.getDescription());
        holderObservation.txtObDate.setText("Date: " + observation.getDate());
        holderObservation.txtObComment.setText("Comment: " + observation.getComment());

        return view;
    }
}
