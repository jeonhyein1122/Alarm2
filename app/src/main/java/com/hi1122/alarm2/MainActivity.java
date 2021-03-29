package com.hi1122.alarm2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmManager= (AlarmManager)getSystemService(ALARM_SERVICE);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(" Alarm - 알람 ");

    }
    public void clickBtn(View view) {
        // 설정된 알람이 되었을때 동작하는 리시버가 있어야 함.
        Intent intent= new Intent(this, AlarmReceiver.class);
        
        //알람이 울릴때까지 보류되어야 하기에 보류중인 인텐트로 만들기
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //M버전부터 Doz(낮잠)모드가 생겨서 이를 깨우고 알람이 울리도록..
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
            Toast.makeText(this, "10초후 알람이 울립니다!", Toast.LENGTH_SHORT).show();
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
        }
    }

    public void clickBtn2(View view) {
        Intent intent= new Intent(this, AlarmRepeatReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, 20, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
            Toast.makeText(this, "10초후 알람이 울립니다! 이 알람은 15초 간격으로 울립니다!", Toast.LENGTH_SHORT).show();
        }else{

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
        }
    }

    public void clickBtn3(View view) {
        //반복 알람 종료하기
        Intent intent= new Intent(this, AlarmRepeatReceiver.class);
        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, 20, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Toast.makeText(this, "15초 간격 반복 알람이 종료되었습니다", Toast.LENGTH_SHORT).show();
        alarmManager.cancel(pendingIntent);
    }

    //멤버변수
    int Year, Month, Day;
    int Hour, Min;

    public void clickBtn4(View view) {
        //특정날짜에 알람 지정.. 날짜 선택 다이얼로그로 날짜 선택
        //DatePickerDialog
        GregorianCalendar calendar= new GregorianCalendar(Locale.KOREA);
        int y= calendar.get(Calendar.YEAR);
        int m= calendar.get(Calendar.MONTH);
        int d= calendar.get(Calendar.DATE);
        DatePickerDialog dialog= new DatePickerDialog(this, dateSetListener, y, m, d);
        dialog.show();
    }

    //날짜 선택 완료 리스너
    DatePickerDialog.OnDateSetListener dateSetListener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Year= year;
            Month= month;
            Day= dayOfMonth;

            //시간 선택 다이얼로그 보이기
            GregorianCalendar calendar= new GregorianCalendar(Locale.KOREA);
            int h= calendar.get(Calendar.HOUR_OF_DAY);
            int m= calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog= new TimePickerDialog(MainActivity.this, timeSetListener, h, m, true);
            timePickerDialog.show();
        }
    };

    //시간설정 완료 리스너
    TimePickerDialog.OnTimeSetListener timeSetListener= new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Hour= hourOfDay;
            Min= minute;

            //Year, Month, Day, Hour, Min 값으로 Calendar객체 생성- 알람설정위해
            GregorianCalendar calendar= new GregorianCalendar(Year, Month, Day, Hour, Min);

            Intent intent= new Intent(MainActivity.this, AlarmReceiver.class);
            PendingIntent pendingIntent= PendingIntent.getBroadcast(MainActivity.this, 30, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }else{
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

        }
    };

    public void clickBtn20(View view) {

        Intent intent= new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, pendingIntent);
            Toast.makeText(this, "20초후 알람이 울립니다!", Toast.LENGTH_SHORT).show();
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 20000, pendingIntent);
        }
    }

    public void clickBtn30(View view) {

        Intent intent= new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30000, pendingIntent);
            Toast.makeText(this, "30초후 알람이 울립니다!", Toast.LENGTH_SHORT).show();
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 30000, pendingIntent);
        }
    }

    public void clickBtn60(View view) {
        Intent intent= new Intent(this, AlarmReceiver.class);

        PendingIntent pendingIntent= PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pendingIntent);
            Toast.makeText(this, "1분후 알람이 울립니다!", Toast.LENGTH_SHORT).show();
        }else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 60000, pendingIntent);
        }
    }
}