package com.natateam.myevents;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.natateam.myevents.adapter.MyRecyclerViewAdapter;
import com.natateam.myevents.app.EventApp;
import com.natateam.myevents.db.Event;
import com.natateam.myevents.db.RealmHelper;
import com.natateam.myevents.mainlist.MainCotractor;
import com.natateam.myevents.mainlist.MainPresenterImpl;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.realm.OrderedRealmCollection;

public class MainActivity extends AppCompatActivity implements MainCotractor.MainView{
    @BindView(R.id.recyclerview)RecyclerView recyclerView;
    @Inject
    MainPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
               setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEventActivity();
            }
        });
        presenter.isViewAttached();
        presenter.loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loadData(OrderedRealmCollection<Event> list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerViewAdapter(this,list));
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration verticalDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable verticalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_divider);
        verticalDecoration.setDrawable(verticalDivider);
        recyclerView.addItemDecoration(verticalDecoration);
    }

    public void showEventActivity(){
        Intent intent = new Intent(this,EventActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    public void showEventActivityWithId(long id){
        Intent intent = new Intent(this,EventActivity.class);
        intent.putExtra(EventActivity.EVENT_ID,id);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
