package fpt.gw.hikerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    ListView lvTrip;
    ArrayList<Hike> hikeArrayList;
    HikeAdapter adapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("TAG", "Here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTrip = (ListView) findViewById(R.id.listviewHike);
        hikeArrayList = new ArrayList<>();

        adapter = new HikeAdapter(this, R.layout.view_all_hike, hikeArrayList);
        lvTrip.setAdapter(adapter);

        //Tao database cho hike app
        databaseHelper = new DatabaseHelper(this, "hikeApp.sqlite", null, 1);
        //Tao bang Hike
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS Hike(Id INTEGER PRIMARY KEY AUTOINCREMENT, HikeName TEXT, " +
                "Location TEXT, Length TEXT, Difficulty TEXT, DateStart TEXT, DateEnd TEXT , Park TEXT,  Description TEXT)");

        //Tao bang ob
        databaseHelper.QueryData("CREATE TABLE IF NOT EXISTS Observation(Id INTEGER PRIMARY KEY AUTOINCREMENT, HikeId INTEGER, " +
                "Type TEXT, Description TEXT, Date TEXT, Comment TEXT)");

//        databaseHelper.QueryData("INSERT INTO Cost VALUES(null, 25, 'Food', '2.45$', '31/11/2022', 'I felling good!!')");

        lvTrip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView txtHikeName = (TextView) view.findViewById(R.id.textviewHikeName);
                TextView txtIdHike = (TextView) view.findViewById(R.id.textviewIdHike);
                TextView txtLocation = (TextView) view.findViewById(R.id.textviewLocation);
                TextView txtLength = (TextView) view.findViewById(R.id.textviewLength);
                TextView txtDifficulty = (TextView) view.findViewById(R.id.textviewDifficulty);
                TextView txtDateStart = (TextView) view.findViewById(R.id.textviewDateStart);
                TextView txtDateEnd = (TextView) view.findViewById(R.id.textviewDateEnd);
                TextView txtPark = (TextView) view.findViewById(R.id.textviewPark);
                TextView txtDescription = (TextView) view.findViewById(R.id.textviewDescription);

                Intent tripDetail = new Intent(MainActivity.this, HikeDetail.class);

                Bundle bundle = new Bundle();
                bundle.putString("hikeName", String.valueOf(txtHikeName.getText()));
                Log.e("hikeid", String.valueOf(txtIdHike.getText()));
                bundle.putString("idHike", String.valueOf(txtIdHike.getText()));
                bundle.putString("location", String.valueOf(txtLocation.getText()));
                bundle.putString("length", String.valueOf(txtLength.getText()));
                bundle.putString("difficulty", String.valueOf(txtDifficulty.getText()));
                bundle.putString("dateStart", String.valueOf(txtDateStart.getText()));
                bundle.putString("dateEnd", String.valueOf(txtDateEnd.getText()));
                bundle.putString("park", String.valueOf(txtPark.getText()));
                bundle.putString("description", String.valueOf(txtDescription.getText()));
                tripDetail.putExtra("hikeDetail", bundle);
                startActivity(tripDetail);
            }

        });
        Log.d("TAG", "Here");
        //Insert data
        //databaseHelper.QueryData("INSERT INTO Hike VALUES(null, 'Hike1')");
        //databaseHelper.QueryData("INSERT INTO Hike VALUES(null, 'Hike2')");
        //Select data
        GetDataHike();
    }

    public void DialogDeleteTrip(String hikeName, int id){
        AlertDialog.Builder dialogDeleteTrip = new AlertDialog.Builder(this);
        dialogDeleteTrip.setMessage("DO YOU WANT TO DELETE " + hikeName + "?");
        dialogDeleteTrip.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseHelper.QueryData("DELETE FROM Hike WHERE Id = '" + id + "'");
                databaseHelper.QueryData("DELETE FROM Observation WHERE HikeId = '" + id + "'");
                Toast.makeText(MainActivity.this, "DELETE " + hikeName + " SUCCESSFULLY!!" , Toast.LENGTH_SHORT).show();
                GetDataHike();
            }
        });
        dialogDeleteTrip.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogDeleteTrip.show();
    }

    public void DialogEditTrip(String tripName, String location, String length, String difficulty, String dateStart, String dateEnd, String park, String description, int id){
        Dialog dialog = new Dialog((this));
        dialog.setContentView(R.layout.dialog_edit_hike);

        RadioGroup rdGroupEdit = (RadioGroup) dialog.findViewById(R.id.radioGroupParkAvailableEdit);
        RadioButton rdYesEdit = (RadioButton) dialog.findViewById(R.id.radioButtonYesEdit);
        RadioButton rdNoEdit = (RadioButton) dialog.findViewById(R.id.radioButtonNoEdit);
        RadioGroup rdGroupEdit1 = (RadioGroup) dialog.findViewById(R.id.radioGroupDifficultyEdit);
        RadioButton rdHardEdit = (RadioButton) dialog.findViewById(R.id.radioButtonHardEdit);
        RadioButton rdMediumEdit = (RadioButton) dialog.findViewById(R.id.radioButtonMediumEdit);
        RadioButton rdEasyEdit = (RadioButton) dialog.findViewById(R.id.radioButtonEasyEdit);
        EditText edtTextDescriptionEdit = (EditText) dialog.findViewById(R.id.editTextDescriptionEdit);
        EditText edtTextLocationEdit = (EditText) dialog.findViewById(R.id.editTextLocationEdit);
        EditText edtTextLengthEdit = (EditText) dialog.findViewById(R.id.editTextLengthEdit);
        EditText edtTextDateEndEdit = (EditText) dialog.findViewById(R.id.editTextTripDateEndEdit);
        EditText edtTextDateStartEdit = (EditText) dialog.findViewById(R.id.editTextTripDateStartEdit);
        EditText edtTextHikeNameEdit = (EditText) dialog.findViewById(R.id.editTextHikeNameEdit);

//        int checkedButtonId = rdGroupEdit.getCheckedRadioButtonId();
//        RadioButton checkedButton = dialog.findViewById(checkedButtonId);

        Button btnEdit = (Button) dialog.findViewById((R.id.buttonEdit));
        Button btnCancelEdit = (Button) dialog.findViewById((R.id.buttonCancelEdit));

        edtTextHikeNameEdit.setText(tripName);
        edtTextLocationEdit.setText(location);
        edtTextLengthEdit.setText(length);
        edtTextDateStartEdit.setText(dateStart);
        edtTextDateEndEdit.setText(dateEnd);

//        if(checkedButton.getText() == "YES"){
//            rdYesEdit.setChecked(true);
//        } else {
//            rdNoEdit.setChecked(true);
//        }
//        Log.d("TAG", "Here");

        edtTextDescriptionEdit.setText(description);

        //Edit xac nhan edit
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hikeNameEdit = edtTextHikeNameEdit.getText().toString().trim();
                String locationEdit = edtTextLocationEdit.getText().toString().trim();
                String lengthEdit = edtTextLengthEdit.getText().toString().trim();
                String dateStartEdit = edtTextDateStartEdit.getText().toString().trim();
                String dateEndEdit = edtTextDateEndEdit.getText().toString().trim();

                int checkedButtonId = rdGroupEdit.getCheckedRadioButtonId();
                RadioButton checkedButton = dialog.findViewById(checkedButtonId);
                String parkEdit = checkedButton.getText().toString().trim();

                int checkedButtonId1 = rdGroupEdit1.getCheckedRadioButtonId();
                RadioButton checkedButton1 = dialog.findViewById(checkedButtonId1);
                String difficultyEdit = checkedButton1.getText().toString().trim();

                String descriptionEdit = edtTextDescriptionEdit.getText().toString().trim();

                if(hikeNameEdit.equals("") || locationEdit.equals("") || lengthEdit.equals("") || dateStartEdit.equals("") || dateEndEdit. equals("") || descriptionEdit.equals("")){
                    Toast.makeText(MainActivity.this, "Please fill all information!!", Toast.LENGTH_SHORT).show();
                }else {
                    databaseHelper.QueryData("UPDATE Hike SET HikeName = '" + hikeNameEdit + "', Location = '" + locationEdit + "', " +
                            "DateStart = '" + lengthEdit + "', DateStart = '" + dateStartEdit + "', " +
                            "DateEnd = '" + dateEndEdit + "', Park = '" + parkEdit +  "', Difficulty = '" + difficultyEdit + "', Description = '" + descriptionEdit + "' " +
                            "WHERE Id = '" + id + "'");
                    Toast.makeText(MainActivity.this, "UPDATE SUCCESSFULLY!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataHike();
                }
                Log.d("TAG", "Here");
            }
        });

        edtTextDateEndEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day =calendar.get(Calendar.DATE);
                int month =calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);//Ko gan calendar thi he thong se tu dong lay ngay thang hien tai
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtTextDateEndEdit.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        edtTextDateStartEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day =calendar.get(Calendar.DATE);
                int month =calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);//Ko gan calendar thi he thong se tu dong lay ngay thang hien tai
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtTextDateStartEdit.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //Cancel ko edit nua
        btnCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void GetDataHike(){
        //Select data
        Cursor dataTrip = databaseHelper.GetData("SELECT * FROM Hike");
        hikeArrayList.clear(); //Clear array de khong bi lap lai trip
        while (dataTrip.moveToNext()){
            String hikeName = dataTrip.getString(1);
            String location = dataTrip.getString(2);
            String length = dataTrip.getString(3);
            String difficulty = dataTrip.getString(4);
            String dateStart = dataTrip.getString(5);
            String dateEnd = dataTrip.getString(6);
            String park = dataTrip.getString(7);
            String description = dataTrip.getString(8);
            int id = dataTrip.getInt(0);
            hikeArrayList.add(new Hike(id, hikeName, location, length, difficulty, dateStart, dateEnd, park, description));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_trip, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.addTrip){
            DialogAdd();
        }
        if(item.getItemId() == R.id.searchTrip){
            Intent searchTrip = new Intent(MainActivity.this, SearchHike.class);
            startActivity(searchTrip);
        }

        return true;
    }

    private void DialogAdd(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_hike);

        RadioGroup rdGroup = (RadioGroup) dialog.findViewById(R.id.radioGroupParkAvailable);
        RadioButton rdYes = (RadioButton) dialog.findViewById(R.id.radioButtonYes);
        RadioButton rdNo = (RadioButton) dialog.findViewById(R.id.radioButtonNo);
        RadioGroup rdGroup1 = (RadioGroup) dialog.findViewById(R.id.radioGroupDifficulty);
        RadioButton rdHard = (RadioButton) dialog.findViewById(R.id.radioButtonHard);
        RadioButton rdMedium = (RadioButton) dialog.findViewById(R.id.radioButtonMedium);
        RadioButton rdEasy = (RadioButton) dialog.findViewById(R.id.radioButtonEasy);
        EditText edtDescription = (EditText) dialog.findViewById(R.id.editTextDescription);
        EditText edtLocation = (EditText) dialog.findViewById(R.id.editTextLocation);
        EditText edtLength = (EditText) dialog.findViewById(R.id.editTextLength);
        EditText edtDateEnd = (EditText) dialog.findViewById(R.id.editTextHikeDateEnd);
        EditText edtDateStart = (EditText) dialog.findViewById(R.id.editTextHikeDateStart);
        EditText edtHikeName = (EditText) dialog.findViewById(R.id.editTextHikeName);
        Button btnSave = (Button) dialog.findViewById(R.id.buttonSave);
        Button btnCancel = (Button) dialog.findViewById(R.id.buttonCancel);
        //Radio Park Available
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //i: onCheckedChanged tra ve i -> radiobutton dang dc check
                switch (i){
                    case R.id.radioButtonYes:
                        Toast.makeText(MainActivity.this, "YES", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButtonNo:
                        Toast.makeText(MainActivity.this, "NO", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        rdGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //i: onCheckedChanged tra ve i -> radiobutton dang dc check
                switch (i){
                    case R.id.radioButtonHard:
                        Toast.makeText(MainActivity.this, "HARD", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButtonMedium:
                        Toast.makeText(MainActivity.this, "MEDIUM", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButtonEasy:
                        Toast.makeText(MainActivity.this, "EASY", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //Save add new trip
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Insert data
                String HikeName = edtHikeName.getText().toString();
                String Location = edtLocation.getText().toString();
                String Length = edtLength.getText().toString();
                String DateStart = edtDateStart.getText().toString();
                String DateEnd = edtDateEnd.getText().toString();

                int checkedButtonId = rdGroup.getCheckedRadioButtonId();
                RadioButton checkedButton = dialog.findViewById(checkedButtonId);
                String Park = checkedButton.getText().toString();

                int checkedButtonId1 = rdGroup1.getCheckedRadioButtonId();
                RadioButton checkedButton1 = dialog.findViewById(checkedButtonId1);
                String Difficulty = checkedButton1.getText().toString();

                //String Park = ((RadioButton)findViewById(rdGroup.getCheckedRadioButtonId())).getText().toString();
                String Description = edtDescription.getText().toString();
                if(HikeName.equals("") || Location.equals("") || Length.equals("") || DateStart.equals("") || DateEnd.equals("") || Description.equals("")){
                    Toast.makeText(MainActivity.this, "Please fill all information!!", Toast.LENGTH_SHORT).show();
                }else {
                    databaseHelper.QueryData("INSERT INTO Hike VALUES(null, '" + HikeName + "','" + Location + "','" + Length + "','" + Difficulty + "','" + DateStart + "','" + DateEnd + "','" + Park + "','" + Description + "')");
                    Log.d("TAG", "Here");
                    Toast.makeText(MainActivity.this, "SUCCESSFULLY!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataHike();
                }
            }
        });

        //Date End
        edtDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day =calendar.get(Calendar.DATE);
                int month =calendar.get(Calendar.MONTH);
                int year =calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);//Ko gan calendar thi he thong se tu dong lay ngay thang hien tai
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtDateEnd.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        //Date Start
        edtDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        edtDateStart.setText(simpleDateFormat.format(calendar.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        //Cancel khong them nua
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}