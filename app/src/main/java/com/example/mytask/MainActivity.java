package com.example.mytask;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mytask.databinding.ActivityMainBinding;
import com.example.mytask.ui.dashboard.DashboardFragment;
import com.example.mytask.ui.home.HomeFragment;
import com.example.mytask.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;

private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMainBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        getCurrentDate();

    }

    public void navigate(MenuItem item){
        long date = System.currentTimeMillis();
        ZonedDateTime nowCurrent = LocalDateTime.now().atZone(ZoneId.of("Asia/Kuala_Lumpur"));

        String dateString = nowCurrent.format(DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy"));
        String timeString = nowCurrent.format(DateTimeFormatter.ofPattern("h:mm a"));
        String zoneString = nowCurrent.format(DateTimeFormatter.ofPattern("zzzz"));

        ((TextView)findViewById(R.id.currentDateTime)).setText(dateString);
        ((TextView)findViewById(R.id.time)).setText(timeString);
        ((TextView)findViewById(R.id.timezone)).setText(zoneString);

    }

    public void getCurrentDate (){
        long date = System.currentTimeMillis();
        ZonedDateTime nowCurrent = LocalDateTime.now().atZone(ZoneId.of("Asia/Kuala_Lumpur"));

        String dateString = nowCurrent.format(DateTimeFormatter.ofPattern("EEEE, d MMMM, yyyy"));
        String timeString = nowCurrent.format(DateTimeFormatter.ofPattern("h:mm a"));
        String zoneString = nowCurrent.format(DateTimeFormatter.ofPattern("zzzz"));

        ((TextView)findViewById(R.id.currentDateTime)).setText(dateString);
        ((TextView)findViewById(R.id.time)).setText(timeString);
        ((TextView)findViewById(R.id.timezone)).setText(zoneString);

    }



    public void getdatepicker(View v){
        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                // on below line we are passing context. 
                MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view. 
                        ((TextView)findViewById(R.id.Userdob_data)).setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                    }
                },
                // on below line we are passing year,  
                // month and day for selected date in our date picker. 
                year, month, day);
        // at last we are calling show to  
        // display our date picker dialog. 
        datePickerDialog.show();
    }

///////////////////////////////////////////////////////////////////////////////////////

    @SuppressLint("SetTextI18n")
    public void disable(View v){

//        findViewById(R.id.button).setEnabled(false);
//        ((Button)findViewById(R.id.button)).setText("New New Disable");

        /*
        View myView = findViewById(R.id.textView);
                myView.setEnabled(false);
        TextView t = (TextView) myView;
        t.setText("New Disable");
        */

        /*
        v.setEnabled(false);
        Button b = (Button) v;
        b.setText("disabled");
         */
    }

    public void handleText(View h){
//        EditText t = findViewById(R.id.NameSource);
//        String input = t.getText().toString();
//        ((TextView)findViewById(R.id.displayName)).setText(input);


//        Log.d("info", input);
    }

}