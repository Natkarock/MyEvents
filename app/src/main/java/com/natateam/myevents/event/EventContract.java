package com.natateam.myevents.event;

import com.natateam.myevents.BasePresenter;
import com.natateam.myevents.BaseView;
import com.natateam.myevents.db.Event;

/**
 * Created by macbook on 30/11/ 15.
 */

public interface EventContract {
    public interface EventView  extends BaseView {
        public void setData(Event event);
    }

    public interface EventPresenter extends BasePresenter<EventView>{
        public void createOrUpdateEvent(final Event event, final String title, final String desc, final String date, final String time, final long dateMS);
        public void setData(long id);

    }

}
