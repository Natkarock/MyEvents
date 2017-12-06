package com.natateam.myevents.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.natateam.myevents.Consts;
import com.natateam.myevents.MainActivity;
import com.natateam.myevents.R;
import com.natateam.myevents.TimeUtils;
import com.natateam.myevents.db.Event;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;

/**
 * Created by macbook on 07/07/ 15.
 */
public class MyRecyclerViewAdapter extends RealmRecyclerViewAdapter<Event, MyRecyclerViewAdapter.MyViewHolder> {

private final MainActivity activity;

public MyRecyclerViewAdapter(MainActivity activity, OrderedRealmCollection<Event> data) {
        super(activity ,data, true);
        this.activity = activity;
        }

@Override
public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.event_item, parent, false);
        return new MyViewHolder(itemView);
        }

@Override
public void onBindViewHolder(MyViewHolder holder, int position) {
        Event obj = getData().get(position);
        boolean isTop = true;
        if (position>0) {
         Event prevEvent = getData().get(position-1);
         long currentBeginOfDay = TimeUtils.getBeginOfDaInMillis(obj.getDateMS());
         long prevBeginOfDay = TimeUtils.getBeginOfDaInMillis(prevEvent.getDateMS());
         if (currentBeginOfDay==prevBeginOfDay){
             isTop=false;
         }
        }
        holder.setItem(obj,isTop);
        }

class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {
    @BindView(R.id.txt_name)
    public TextView title;
    @BindView(R.id.txt_type)
    public TextView txtType;
    @BindView(R.id.txt_description)
    public TextView txtDescription;
    @BindView(R.id.layout_top_date)
    ConstraintLayout layoutTopDate;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_date_string)
    TextView txtDateString;
    public Event data;


    public MyViewHolder(View view) {
        super(view);
        ButterKnife.bind(this,view);
        view.setOnLongClickListener(this);
        view.setOnClickListener(this);
    }

    public void setItem(Event obj, boolean isTop){
        data = obj;
        title.setText(obj.getTitle());
        txtDescription.setText(obj.getDesc());
        if  (isTop){
            layoutTopDate.setVisibility(View.VISIBLE);
            txtDate.setText(TimeUtils.getFormatDateFromLong(obj.getDateMS(), Consts.DATE_FORMAT));
            txtDateString.setText(TimeUtils.getFormatDateFromLong(obj.getDateMS(), Consts.DATE_STRING_FORMAT));
        }else {
            layoutTopDate.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        //activity.deleteItem(data);
        return true;
    }


    @Override
    public void onClick(View v) {
        activity.showEventActivityWithId(data.getId());
    }
}
}
