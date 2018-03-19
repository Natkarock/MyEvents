package com.natateam.myevents.mainlist.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.natateam.myevents.Consts
import com.natateam.myevents.MainActivity
import com.natateam.myevents.R
import com.natateam.myevents.adapter.BirthdayRecyclerViewAdapter
import com.natateam.myevents.adapter.MyRecyclerViewAdapter
import com.natateam.myevents.db.Contact
import com.natateam.myevents.db.Event
import com.natateam.myevents.extension.app
import com.natateam.myevents.mainlist.*
import com.natateam.myevents.model.ContactModel
import com.natateam.myevents.model.EventModel
import io.realm.OrderedRealmCollection
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

/**
 * Created by macbook on 05/03/ 15.
 */
class BirthdaysFragment: BaseListFragment(),MainCotractor.BirthdayView{


    var recyclerView: RecyclerView? = null
    @Inject
    lateinit var presenter: BirthdaysPresenter
    val component by lazy { app.applicationComponent.plus(BirthdayModule(this))}

    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_main,null);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = find(R.id.recyclerview) as RecyclerView
        component.injectTo(this);
        presenter?.isViewAttached()
        presenter?.loadData()
    }

    override fun loadData(list: OrderedRealmCollection<Contact>) {
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        recyclerView?.adapter = BirthdayRecyclerViewAdapter(context!!, list){contact -> showEventActivity(id = contact!!.id!!) }
        recyclerView?.setHasFixedSize(true)
        val verticalDecoration = DividerItemDecoration(recyclerView!!.context,
                DividerItemDecoration.VERTICAL)
        val verticalDivider = ContextCompat.getDrawable(context!!, R.drawable.horizontal_divider)
        verticalDecoration.setDrawable(verticalDivider!!)
        recyclerView!!.addItemDecoration(verticalDecoration)    }

    fun saveContact(contactModel: ContactModel)=
        presenter.saveContact(contactModel)


    fun saveContactBirthday(birthday:String)=
        presenter.saveContactBirthday(birthday)

    fun clearCurrentContact() = presenter.clearCurrenContact()

    override fun showBirthdayDialog()= (activity as MainActivity).showBirthdayDialog()

    override fun showEventActivity(id:Long,type: String) = (activity as MainActivity).showEventActivityWithId(id,type)

    override fun onDestroyView() {
        super.onDestroyView()
    }




}