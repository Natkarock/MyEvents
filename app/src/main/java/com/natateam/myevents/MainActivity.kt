package com.natateam.myevents

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.natateam.myevents.adapter.MainPagerAdapter
import com.natateam.myevents.auth.AuthModule
import com.natateam.myevents.auth.AuthPresenterImpl
import com.natateam.myevents.extension.app
import com.natateam.myevents.mainlist.MainCotractor
import com.natateam.myevents.mainlist.fragments.BirthdaysFragment
import com.natateam.myevents.model.ContactModel
import com.natateam.myevents.model.EventModel
import org.jetbrains.anko.find
import com.firebase.ui.auth.AuthUI
import java.util.*
import java.util.Arrays.asList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.R.attr.data
import com.firebase.ui.auth.IdpResponse
import javax.inject.Inject


const  val SELECT_PHONE_NUMBER = 123
const  val RC_SIGN_IN = 500

class MainActivity : AppCompatActivity(),MainCotractor.AuthView {


    var viewPager:ViewPager?= null
    var pagerAdapter:MainPagerAdapter?=null
    @Inject
    lateinit var authPresenter:AuthPresenterImpl

    val component by lazy { app.applicationComponent.plus(AuthModule(this))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = find<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        component.injectTo(this)
        viewPager = find<ViewPager>(R.id.viewPager);
        pagerAdapter = MainPagerAdapter(supportFragmentManager,context = this)
        viewPager?.setAdapter(pagerAdapter)
        val tabLayout:TabLayout? = find<TabLayout>(R.id.tablayout);
        tabLayout?.setupWithViewPager(viewPager);
        val fab = find<FloatingActionButton>(R.id.fab);
        fab.setOnClickListener {
            if (!viewPager!!.currentItem.equals(MainPagerAdapter.Types.BIRTHDAYS.type)) {
                if (viewPager!!.currentItem==MainPagerAdapter.Types.REEAT.type){
                    showEventActivity(Consts.REPEAT_TYPE)
                }else {
                    showEventActivity()
                }
            }else{
                if(PermissionHelper.isPermissionGranted(this,  Manifest.permission.READ_CONTACTS)) {
                    showContactPicker()
                }else{
                    PermissionHelper.showContactsPermission(this)
                }
            }}
        checkIsSignIn()

    }


    override fun onResume() {
        super.onResume()
    }


    fun showEventActivity(type:String = Consts.TODO_TYPE) {
        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra(EventActivity.EVENT_TYPE,type)
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
    }

    fun showEventActivityWithId(id: Long,type:String = Consts.TODO_TYPE) {
        val intent = Intent(this, EventActivity::class.java)
        intent.putExtra(EventActivity.EVENT_ID, id)
        intent.putExtra(EventActivity.EVENT_TYPE,type)
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }

    fun showContactPicker(){
        val i=Intent(Intent.ACTION_PICK);
        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(i, SELECT_PHONE_NUMBER);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            val contactUri = data!!.getData();
            val cursor = applicationContext.getContentResolver().query(contactUri, null,
                    null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                val  numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                val number = cursor.getString(numberIndex);
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                val gogle_id = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts.SOURCE_ID))
                val group = cursor.getString(cursor.getColumnIndex(ContactsContract.Groups.ACCOUNT_NAME))
                val contactModel = ContactModel(0,gogle_id,email,name,group,number)
                saveCurrentContactFromPicker(contactModel)
            }
            cursor.close();
        }
        if (requestCode == PermissionHelper.MY_PERMISSIONS_CONTACTS_CODE&& resultCode == Activity.RESULT_OK){
            showContactPicker()
        }
        if (requestCode === RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode === Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser
                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
       }

    fun saveCurrentContactFromPicker(contactModel: ContactModel){
        val birthdaysFragment= supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + MainPagerAdapter.Types.BIRTHDAYS.type ) as BirthdaysFragment
        if (birthdaysFragment!=null) {
            birthdaysFragment.clearCurrentContact()
            birthdaysFragment.saveContact(contactModel)
        }
    }

    fun saveContactBirthdayFromDialog(birthday: String){
        val birthdaysFragment= supportFragmentManager.findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + MainPagerAdapter.Types.BIRTHDAYS.type ) as BirthdaysFragment
        if (birthdaysFragment!=null){
           birthdaysFragment.saveContactBirthday(birthday)
        }
    }

    fun showBirthdayDialog(){
        DialogHelper.showBirthdayDialog(fragmentManager)
    }


    override fun checkIsSignIn() {
        authPresenter.checkIsSign()
    }

    override fun loadDataForUser() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startAuthActivity() {
        val providers = Arrays.asList(
                AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),

                AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())

// Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)    }

}


