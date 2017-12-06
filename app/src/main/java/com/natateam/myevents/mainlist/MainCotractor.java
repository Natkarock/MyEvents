package com.natateam.myevents.mainlist;

import com.natateam.myevents.BasePresenter;
import com.natateam.myevents.BaseView;
import com.natateam.myevents.db.Event;

import io.realm.OrderedRealmCollection;

/**
 * Created by macbook on 29/11/ 15.
 */

public class MainCotractor {
    public interface MainView extends BaseView{
        public void loadData(OrderedRealmCollection<Event> list);

    }
    public interface MainPresenter extends BasePresenter<MainView>{
        public void loadData();
        public void isViewAtached();
    }

}
