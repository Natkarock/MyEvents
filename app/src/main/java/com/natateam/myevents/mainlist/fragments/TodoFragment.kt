package com.natateam.myevents.mainlist.fragments

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.natateam.myevents.Consts
import com.natateam.myevents.Consts.TODO_TYPE
import com.natateam.myevents.FirebaseHelper
import com.natateam.myevents.MainActivity
import com.natateam.myevents.R
import com.natateam.myevents.adapter.FirestoreEventAdapter
import com.natateam.myevents.db.Event
import com.natateam.myevents.extension.app
import com.natateam.myevents.mainlist.EventListModule
import com.natateam.myevents.mainlist.MainCotractor
import com.natateam.myevents.mainlist.MainPresenterImpl
import com.natateam.myevents.model.EventModel
import io.realm.OrderedRealmCollection
import org.jetbrains.anko.support.v4.find
import javax.inject.Inject

/**
 * Created by macbook on 05/03/ 15.
 */
class TodoFragment: BaseListFragment(),MainCotractor.MainView{


    var recyclerView: RecyclerView? = null
    @Inject
    lateinit var presenter: MainPresenterImpl
    val component by lazy { app.applicationComponent.mainBuilder().eventListModule(EventListModule(this,Consts.TODO_TYPE)).build() }
    var lifecycleRegistry:LifecycleRegistry? = null
    var adapter:FirestoreEventAdapter? =null
    @Override
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.content_main,null);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = find(R.id.recyclerview) as RecyclerView
        component.injectTo(this);
        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry!!.markState(Lifecycle.State.CREATED);
        presenter.isViewAttached()
        presenter.loadData()
    }


    override fun loadData(list: OrderedRealmCollection<Event>) {
        val  query = FirebaseHelper.getEventTypeQuery(TODO_TYPE)
        if (query!=null) {
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            val options = FirestoreRecyclerOptions.Builder<EventModel>()
                    .setQuery(query, EventModel::class.java).setLifecycleOwner(this)
                    .build()
            recyclerView?.layoutManager = LinearLayoutManager(activity)
            adapter =  FirestoreEventAdapter(options) { (activity as MainActivity).showEventActivityWithId(it.event_id) }
            adapter!!.startListening()
            adapter!!.notifyDataSetChanged()
            recyclerView?.adapter =adapter
            recyclerView?.setHasFixedSize(true)
            val verticalDecoration = DividerItemDecoration(recyclerView!!.context,
                    DividerItemDecoration.VERTICAL)
            val verticalDivider = ContextCompat.getDrawable(context!!, R.drawable.horizontal_divider)
            verticalDecoration.setDrawable(verticalDivider!!)
            recyclerView!!.addItemDecoration(verticalDecoration)
        }
    }


    override fun onStart() {
        super.onStart()
        /*if (adapter!=null) {
            adapter!!.startListening()
        }*/
        /*if (lifecycleRegistry!=null) {
            lifecycleRegistry!!.markState(Lifecycle.State.STARTED)
        }*/
    }

    override fun onStop() {
        super.onStop()
        /*if (adapter!=null){
            adapter!!.stopListening()
        }*/
    }

}