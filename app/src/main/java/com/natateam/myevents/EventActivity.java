package com.natateam.myevents;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.natateam.myevents.app.EventApp;
import com.natateam.myevents.db.Event;
import com.natateam.myevents.db.RealmHelper;
import com.natateam.myevents.event.EventContract;
import com.natateam.myevents.event.EventPresenerImpl;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;

/**
 * Created by macbook on 07/07/ 15.
 */
public class EventActivity extends AppCompatActivity implements EventContract.EventView, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public static final String EVENT_ID = "EVENT_ID=";
    private static final int DEFAULT_HOUR = 8;
    @BindView(R.id.title)
    EditText txtTitle;
    @BindView(R.id.desc)
    EditText txtDesc;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindString(R.string.enter_title)
    String enter_title;
    @BindString(R.string.enter_right_date)
    String enter_right_date;
    @BindString(R.string.enter_date)
    String enter_date;
    @BindString(R.string.date_mask)
    String date_mask;
    @BindString(R.string.time_mask)
    String time_mask;
    private Calendar date;
    private Event event;


    @Inject
    EventPresenerImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupEnterAnimation();
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);
        date = Calendar.getInstance();
        if (getIntent() != null && getIntent().getLongExtra(EVENT_ID, 0) != 0) {
            long id = getIntent().getLongExtra(EVENT_ID, 0);
            presenter.setData(id);
        }

    }

    private void setupEnterAnimation(){

        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setExitTransition(new Explode());
        getWindow().setEnterTransition(new Explode());

    }



    @Override
    public void setData(Event event) {
        this.event = event;
        txtTitle.setText(event.getTitle());
        txtDate.setText(event.getDate());
        txtTime.setText(event.getTime());
        txtDesc.setText(event.getDesc());
    }

    @OnClick(R.id.txtDate)
    public void dateClick() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setMinDate(now);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    @OnClick(R.id.txtTime)
    public void timeClick() {
        Calendar now = Calendar.getInstance();
        com.wdullaer.materialdatetimepicker.time.TimePickerDialog timePickerDialog =
                com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(this,
                        now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true);
        timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
        timePickerDialog.show(getFragmentManager(), "TimepickerDialog");

    }

    @OnClick(R.id.btn_add)
    public void addEvent() {
        if (txtTitle.getText().toString().equals("")) {
            EventApp.getApp().showToast(enter_title);
            return;
        }
        if (txtDate.getText().toString().equals(date_mask)) {
            EventApp.getApp().showToast(enter_date);
            return;
        }
        long dateMs = TimeUtils.getDateFromString(txtDate.getText() + " " + txtTime.getText()).getTime();
        if (dateMs <= System.currentTimeMillis()) {
            EventApp.getApp().showToast(enter_right_date);
            return;
        }
        presenter.createOrUpdateEvent(event, txtTitle.getText().toString(), txtDesc.getText().toString(),
                txtDate.getText().toString(), txtTime.getText().toString(), dateMs
        );
        finish();
    }

    @OnClick(R.id.btn_back)
    public void btnBackClick(){
        onBackPressed();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, monthOfYear);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        txtDate.setText(TimeUtils.getDateString(date.getTimeInMillis()));

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
        date.set(Calendar.MINUTE, minute);
        txtTime.setText(TimeUtils.getTimeString(date.getTimeInMillis()));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
