package com.natateam.myevents

import android.os.Build
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.Explode
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.dpro.widgets.WeekdaysPicker
import com.natateam.myevents.R.string.*
import com.natateam.myevents.adapter.MainPagerAdapter
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import com.natateam.myevents.event.EventContract
import com.natateam.myevents.event.EventModule
import com.natateam.myevents.event.EventPresenerImpl
import com.natateam.myevents.extension.app
import com.natateam.myevents.model.ContactModel
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout
import io.realm.RealmModel
import io.realm.RealmObject
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

/**
 * Created by macbook on 07/07/ 15.
 */
class EventActivity : AppCompatActivity(), EventContract.EventView, com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    internal var txtTitle: EditText? = null
    internal var txtDesc: EditText? = null
    internal var txtDate: TextView? = null
    internal var txtTime: TextView? = null
    internal var toolbar: Toolbar? = null
    private var btnAdd: TextView? = null
    private var btnBack: ImageView? = null
    private var btnDelete: TextView? = null
    private  var txtToolbarTitle: TextView? = null
    private  var textInputTitle:TextInputLayout? = null
    private var weekdaysPicker: WeekdaysPicker? = null
    private var date: Calendar? = null
    private var model: RealmObject? = null
    private var type: String = Consts.TODO_TYPE


    @Inject
    lateinit var presenter: EventPresenerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEnterAnimation()
        setContentView(R.layout.activity_add_event)
        app.applicationComponent.plus(EventModule(this)).injectTo(this)
        date = Calendar.getInstance()
        if (intent != null) {
            if (intent.hasExtra(EVENT_TYPE)) {
                type = intent.getStringExtra(EVENT_TYPE);
            }
            initFields()
            if (intent.hasExtra(EVENT_ID)) {
                val id = intent.getLongExtra(EVENT_ID, 0)
                presenter!!.setData(id, type)
            }

        }

    }

    private fun initFields() {
        btnAdd = find(R.id.btn_add)
        txtTitle = find(R.id.title)
        txtDesc = find(R.id.desc)
        txtDate = find(R.id.txtDate)
        txtTime = find(R.id.txtTime)
        toolbar = find(R.id.toolbar)
        btnAdd = find(R.id.btn_add)
        btnBack = find(R.id.btn_back)
        btnDelete = find(R.id.btn_delete)
        txtToolbarTitle = find(R.id.txt_toolbar_title)
        textInputTitle = find(R.id.text_input_title)
        weekdaysPicker = find(R.id.weekdays_picker)
        txtDate?.setOnClickListener { dateClick() }
        txtTime?.setOnClickListener { timeClick() }
        btnAdd?.setOnClickListener {
            addEvent()
        }
        btnBack?.setOnClickListener { btnBackClick() }
        btnDelete?.setOnClickListener { delete() }
        val currentTimeinMillis = date!!.timeInMillis
        txtTime?.setText(Consts.DEFAULT_TIME)
        if (type.equals(Consts.BIRTH_TYPE)){

        }else{
            txtDate?.setText(TimeUtils.getDateString(currentTimeinMillis))
        }
        val titles: Array<out String>? = resources.getStringArray(R.array.main_titles)
        var toolbar_title_string:String? = null
        if (titles!=null) {
            when (type) {
                Consts.TODO_TYPE -> {
                    toolbar_title_string = titles!![0]
                }
                Consts.REPEAT_TYPE -> {
                    txtDate!!.visibility= View.GONE
                    weekdaysPicker!!.visibility= View.VISIBLE
                    toolbar_title_string = titles!![1]
                }
                Consts.BIRTH_TYPE -> {
                    txtDesc!!.visibility= View.GONE
                    txtTitle!!.hint= getString(R.string.name_hint)
                    textInputTitle!!.hint =getString(R.string.name_hint)
                    toolbar_title_string = titles!![2]
                }

            }
            txtToolbarTitle!!.text = toolbar_title_string
        }


    }


    private fun setupEnterAnimation() {
        if (Build.VERSION.SDK_INT > +Build.VERSION_CODES.LOLLIPOP) {
            window.allowEnterTransitionOverlap = true
            window.allowReturnTransitionOverlap = true
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.exitTransition = Explode()
            window.enterTransition = Explode()
        }

    }


    override fun setData(model: RealmObject?) {
        this.model = model
        if (type != Consts.BIRTH_TYPE) {
            with(model as Event) {
                txtTitle!!.setText(title)
                txtDate!!.text = task_date
                txtTime!!.text = task_time
                txtDesc!!.setText(model.desc)
            }
        } else {
            with(model as Contact) {
                txtTitle!!.setText(contact_name)
                if (contact_birthday != null) {
                    txtDate!!.text = contact_birthday
                }
                txtTime!!.text = contact_time
            }
        }
        btnAdd?.text = getString(btn_add)
        btnDelete?.visibility = View.VISIBLE

    }

    fun dateClick() {
        val now = Calendar.getInstance()
        val dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        if(type!=Consts.BIRTH_TYPE){
            dpd.minDate = now
        }else{
            dpd.maxDate = now
        }
        dpd.accentColor = resources.getColor(R.color.colorPrimary)
        dpd.show(fragmentManager, "Datepickerdialog")

    }

    fun timeClick() {
        val now = Calendar.getInstance()
        val timePickerDialog = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true)
        timePickerDialog.accentColor = resources.getColor(R.color.colorPrimary)
        timePickerDialog.show(fragmentManager, "TimepickerDialog")

    }


    fun addEvent() {
        if (txtTitle!!.text.toString().equals("")) {
            toast(getString(enter_title))
            return
        }
        var dateMs = 0L;
        when(type){
            Consts.TODO_TYPE-> {
                if (txtDate!!.text.toString().equals(getString(date_mask))) {
                    toast(getString(enter_date))
                    return
                }

                dateMs= TimeUtils.getDateFromString(txtDate!!.text.toString() + " " + txtTime!!.text)!!.time
                if (dateMs <= System.currentTimeMillis()) {
                    toast(getString(enter_right_date))
                    return
                }
                var event: Event? = null
                if (model != null) {
                    event = model as Event
                }
                presenter!!.createOrUpdateEvent(event, txtTitle!!.text.toString(), txtDesc!!.text.toString(),
                        txtDate!!.text.toString(), txtTime!!.text.toString(), dateMs, type!!)
            }
            Consts.REPEAT_TYPE->{
                if (weekdaysPicker!!.noDaySelected()){
                    toast(getString(R.string.choose_just_one_weekday))
                    return
                }
                val weekdays = weekdaysPicker!!.getSelectedDaysText()
                val weekdaysString = weekdays.joinToString(separator = " ")
                var event: Event? = null
                if (model != null) {
                    event = model as Event
                }
                presenter!!.createOrUpdateEvent(event, txtTitle!!.text.toString(), txtDesc!!.text.toString(),
                        txtDate!!.text.toString(), txtTime!!.text.toString(), dateMs, type!!,task_repeat_days = weekdaysString)
            }
            Consts.BIRTH_TYPE ->{
                var contact: Contact? = null
                if (model != null) {
                    contact = model as Contact
                }
                presenter!!.createOrUpdateContact(contact, contactModel = ContactModel(contact_name = txtTitle!!.text.toString(), contact_birthday = txtDate!!.text.toString(),
                        contact_time = txtTime!!.text.toString(), contact_date_ms = dateMs))
            }

        }


        finish()
    }

    fun delete() = {
        if (model != null) {
            presenter.deleteObject(model!!)
        }
    }



    fun btnBackClick() {
        onBackPressed()
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        date!!.set(Calendar.YEAR, year)
        date!!.set(Calendar.MONTH, monthOfYear)
        date!!.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        txtDate!!.text = TimeUtils.getDateString(date!!.timeInMillis)

    }

    override fun onTimeSet(view: RadialPickerLayout, hourOfDay: Int, minute: Int, second: Int) {

        date!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
        date!!.set(Calendar.MINUTE, minute)
        txtTime!!.text = TimeUtils.getTimeString(date!!.timeInMillis)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val EVENT_ID = "EVENT_ID"
        val EVENT_TYPE = "EVENT_TYPE"
        private val DEFAULT_HOUR = 8
    }
}
