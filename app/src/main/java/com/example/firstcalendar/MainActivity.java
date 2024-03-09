package com.example.firstcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button q1 = findViewById(R.id.quarter1);
        Button q2 = findViewById(R.id.quarter2);
        Button q3 = findViewById(R.id.quarter3);
        Button q4 = findViewById(R.id.quarter4);

        Drawable buttonPressBackground = AppCompatResources.getDrawable(this, R.drawable.button_press_background);
        Drawable buttonBackground = AppCompatResources.getDrawable(this, R.drawable.button_background);

        int whiteColor = ContextCompat.getColor(this, R.color.white);
        int darkGreenColor = ContextCompat.getColor(this, R.color.dark_green);

        int currentYear;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentYear = LocalDate.now().getYear();
        } else {
            currentYear = 2024;
        }

        initWidgets();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now();
        }

        ArrayList<DayItem> greenDays = new ArrayList<>();

        greenDays.add(new DayItem(23, 1, 2024));
        greenDays.add(new DayItem(7, 3, 2024, 3));
        greenDays.add(new DayItem(14, 3, 2024, 2));
        greenDays.add(new DayItem(12, 3, 2024, 4));
        greenDays.add(new DayItem(12, 2, 2024, 5));

        setMonthView(greenDays);


        q1.setOnClickListener(v -> {
            q1.setBackground(buttonBackground);
            q2.setBackground(buttonPressBackground);
            q3.setBackground(buttonPressBackground);
            q4.setBackground(buttonPressBackground);

            q1.setTextColor(darkGreenColor);
            q2.setTextColor(whiteColor);
            q3.setTextColor(whiteColor);
            q4.setTextColor(whiteColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                selectedDate = LocalDate.of(currentYear, 1, 1);
            }
            setMonthView(greenDays);
        });

        q2.setOnClickListener(v -> {
            q1.setBackground(buttonPressBackground);
            q2.setBackground(buttonBackground);
            q3.setBackground(buttonPressBackground);
            q4.setBackground(buttonPressBackground);

            q1.setTextColor(whiteColor);
            q2.setTextColor(darkGreenColor);
            q3.setTextColor(whiteColor);
            q4.setTextColor(whiteColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                selectedDate = LocalDate.of(currentYear, 4, 1);
            }
            setMonthView(greenDays);
        });

        q3.setOnClickListener(v -> {
            q1.setBackground(buttonPressBackground);
            q2.setBackground(buttonPressBackground);
            q3.setBackground(buttonBackground);
            q4.setBackground(buttonPressBackground);

            q1.setTextColor(whiteColor);
            q2.setTextColor(whiteColor);
            q3.setTextColor(darkGreenColor);
            q4.setTextColor(whiteColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                selectedDate = LocalDate.of(currentYear, 7, 1);
            }
            setMonthView(greenDays);
        });

        q4.setOnClickListener(v -> {
            q1.setBackground(buttonPressBackground);
            q2.setBackground(buttonPressBackground);
            q3.setBackground(buttonPressBackground);
            q4.setBackground(buttonBackground);

            q1.setTextColor(whiteColor);
            q2.setTextColor(whiteColor);
            q3.setTextColor(whiteColor);
            q4.setTextColor(darkGreenColor);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                selectedDate = LocalDate.of(currentYear, 10, 1);
            }
            setMonthView(greenDays);
        });



    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }


    private void setMonthView(ArrayList<DayItem> greenDays) {
        monthYearText.setText(monthYearFromDate(selectedDate));

        ArrayList<DayItem> daysInMonth = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Calculate the first day of the selected month
            LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);

            // Iterate over the previous 2 months and add their days to the list
            for (int i = -2; i <= 0; i++) {
                LocalDate month = firstOfMonth.plusMonths(i);
                int monthValue = month.getMonthValue();
                int yearValue = month.getYear();
                for (int day = 1; day <= month.lengthOfMonth(); day++) {
                    daysInMonth.add(new DayItem(day, monthValue, yearValue));
                    Log.i("Day", "Day: " + day + " Month: " + monthValue + " Year: " + yearValue);
                }
            }
        }



        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, greenDays, this);

        // Set up GridLayoutManager with horizontal orientation and 7 items per row
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7, GridLayoutManager.HORIZONTAL, false);

        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }


    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date.format(formatter);
        }
        return null;
    }

    public void previousMonthAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.minusMonths(1);
        }
        setMonthView(null);
    }

    public void nextMonthAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.plusMonths(1);
        }
        setMonthView(null);
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.isEmpty())
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }


}







