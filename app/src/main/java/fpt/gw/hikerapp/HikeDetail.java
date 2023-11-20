package fpt.gw.hikerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class HikeDetail extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView lvObservation;
    ArrayList<Observation> arrayObservation;
    ArrayList<Hike> arrayHike;
    ObservationAdapter adapter;

    TextView txtHikeName, txtIdHike, txtLocation, txtLength, txtDifficulty, txtDateStart,txtDateEnd, txtPark,txtDescription;
    Button btnAddObservation, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_detail);
        //Log.d("TAG", "Here");

        lvObservation = (ListView) findViewById(R.id.listviewObservation);
        btnAddObservation = (Button) findViewById(R.id.buttonAddObservation);
        btnDelete = (Button) findViewById(R.id.buttonDelete);
        arrayObservation = new ArrayList<>();
        arrayHike = new ArrayList<>();

        adapter = new ObservationAdapter(this, R.layout.view_observation, arrayObservation);
        lvObservation.setAdapter(adapter);

        //Tao database cho hikeApp
        databaseHelper = new DatabaseHelper(this, "hikeApp.sqlite", null, 1);

        //Add Ob click
        btnAddObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddObservation();
            }
        });


        txtHikeName = (TextView) findViewById(R.id.textviewHikeNameDetail);
        txtIdHike = (TextView) findViewById(R.id.textviewIdHikeDetail);
        txtLocation = (TextView) findViewById(R.id.textviewLocationDetail);
        txtLength = (TextView) findViewById(R.id.textviewLengthDetail);
        txtDifficulty = (TextView) findViewById(R.id.textViewDifficultyDetail);
        txtDateStart = (TextView) findViewById(R.id.textviewDateStartDetail);
        txtDateEnd = (TextView) findViewById(R.id.textviewDateEndDetail);
        txtPark = (TextView) findViewById(R.id.textviewParkAvailable);
        txtDescription = (TextView) findViewById(R.id.textviewDescriptionDetail);

        Intent intent = getIntent();
        Bundle bd = intent.getBundleExtra("hikeDetail");
        if(bd != null){
            String hikeName = bd.getString("hikeName");
            String idHike = bd.getString("idHike");
            String location = bd.getString("location");
            String length = bd.getString("length");
            String difficulty = bd.getString("difficulty");
            String dateStart = bd.getString("dateStart");
            String dateEnd = bd.getString("dateEnd");
            String park = bd.getString("park");
            String description = bd.getString("description");

            txtHikeName.setText(hikeName);
            txtIdHike.setText(idHike);
            txtLocation.setText(location);
            txtLength.setText(length);
            txtDifficulty.setText(difficulty);
            txtDateStart.setText(dateStart);
            txtDateEnd.setText(dateEnd);
            txtPark.setText(park);
            txtDescription.setText(description);

            GetDataObservation(Integer.parseInt(idHike));

        }

        //Delete ob and trip
        btnDelete.setOnClickListener(new View.OnClickListener() {
            String name = txtHikeName.getText().toString();
            int id = Integer.parseInt(txtIdHike.getText().toString());
            @Override
            public void onClick(View view) {
                DeleteAll(name, id);
            }
        });
    }

    private void GetDataObservation(int HikeId){
        //Select data
        Cursor dataObservation = databaseHelper.GetData("SELECT * FROM Observation WHERE HikeId = '" + HikeId + "'");
        arrayObservation.clear();
        Log.d("TAG", "Here");
        while (dataObservation.moveToNext()){
            int id = dataObservation.getInt(0);
            int idHike = dataObservation.getInt(1);
            String obType = dataObservation.getString(2);
            String obDescription = dataObservation.getString(3);
            String obDate = dataObservation.getString(4);
            String obComment = dataObservation.getString(5);
            arrayObservation.add(new Observation(id, idHike, obType, obDescription, obDate, obComment));
            Log.d("TAG", "Here");
        }
        adapter.notifyDataSetChanged();
    }

    private void GetDataHike(){
        //Select data
        Cursor dataHike = databaseHelper.GetData("SELECT * FROM Hike");
        arrayHike.clear(); //Clear array de khong bi lap lai
        while (dataHike.moveToNext()){
            String hikeName = dataHike.getString(1);
            String location = dataHike.getString(2);
            String length = dataHike.getString(3);
            String difficulty = dataHike.getString(4);
            String dateStart = dataHike.getString(5);
            String dateEnd = dataHike.getString(6);
            String park = dataHike.getString(7);
            String description = dataHike.getString(8);
            //Toast.makeText(this, hikeName, Toast.LENGTH_SHORT).show();
            int id = dataHike.getInt(0);
            arrayHike.add(new Hike(id, hikeName, location, length, difficulty, dateStart, dateEnd, park, description));
        }

        adapter.notifyDataSetChanged();
    }


    private  void DeleteAll(String hikeName, int id){
        AlertDialog.Builder dialogDeleteHike = new AlertDialog.Builder(this);
        dialogDeleteHike.setMessage("DO YOU WANT TO DELETE " + hikeName + "?");
        dialogDeleteHike.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelper.QueryData("DELETE FROM Hike WHERE Id = '" + id + "'");
                databaseHelper.QueryData("DELETE FROM Observation WHERE HikeId = '" + id + "'");
                Toast.makeText(HikeDetail.this, "DELETE " + hikeName + " SUCCESSFULLY!!" , Toast.LENGTH_SHORT).show();
                GetDataObservation(id);
                GetDataHike();
                Intent back = new Intent(HikeDetail.this, MainActivity.class);
                startActivity(back);
            }
        });
        dialogDeleteHike.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDeleteHike.show();
    }

    //Add Observation dialog
    private void DialogAddObservation(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_observation);

        EditText edtHikeId = (EditText) dialog.findViewById(R.id.editTextHikeIdOb);
        //Log.e("edtHikeId: ",String.valueOf(edtHikeId));
        EditText edtObType = (EditText) dialog.findViewById(R.id.editTextObservationType);
        EditText edtObDescription = (EditText) dialog.findViewById(R.id.editTextObservationDescription);
        EditText edtObDate = (EditText) dialog.findViewById(R.id.editTextObservationDate);
        EditText edtObComment = (EditText) dialog.findViewById(R.id.editTextObservationComment);
        Button btnSaveObservation = (Button) dialog.findViewById(R.id.buttonSaveObservation);
        Button btnCancelObservation = (Button) dialog.findViewById(R.id.buttonCancelObservation);

        btnSaveObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idHike = edtHikeId.getText().toString();
                String obType = edtObType.getText().toString();
                String obDescription = edtObDescription.getText().toString();
                String obDate = edtObDate.getText().toString();
                String obComment = edtObComment.getText().toString();
                if (obType.isEmpty() || obDescription.isEmpty() || obDate.isEmpty() || obComment.isEmpty()) {
                    Toast.makeText(HikeDetail.this, "Please enter all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int hikeId = Integer.parseInt(idHike);

                        databaseHelper.QueryData("INSERT INTO Observation VALUES(null, '" + idHike + "','" + obType + "','" + obDescription + "','" + obDate + "','" + obComment + "')");

                        Toast.makeText(HikeDetail.this, "SUCCESSFULLY!!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        GetDataObservation(hikeId);

                    } catch (NumberFormatException e) {
                        Log.e("idhike: ", String.valueOf(edtHikeId));
                        Toast.makeText(HikeDetail.this, "Invalid Hike ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        edtObDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day =calendar.get(Calendar.DATE);
                int month =calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(HikeDetail.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);//Ko gan calendar thi he thong se tu dong lay ngay thang hien tai
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtObDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnCancelObservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}