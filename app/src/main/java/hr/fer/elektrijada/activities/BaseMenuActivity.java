package hr.fer.elektrijada.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import hr.fer.elektrijada.MenuHandler;
import hr.fer.elektrijada.R;

/**
 * Created by b on 17.11.2015..
 */
public abstract class BaseMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Svaka aktivnost koja naslijedi ovu aktivnost mora dati id elementa koji služi za sadržaj
     * Za primjer vidi StartActivity ili SettingsActivity.
     * Sadržaj nasljeđene je sve što je definirano ovom apstraktnom aktivnošću + R.layout.naziv_sadržaja
     * npr. R.layout.content_settings_activity (layout/content_settings_activity.xml)
     * @return Should return custom layout id (e.g. R.layout.content_settings_activity)
     */
    protected abstract int getContentLayoutId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_menu_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(getContentLayoutId());
        View inflated = stub.inflate();

//        ViewGroup inclusionViewGroup = (ViewGroup)findViewById(R.id.custom_content);
//
//        View child = LayoutInflater.from(this).inflate(
//                getContentLayoutId(), null);
//        inclusionViewGroup.addView(child);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        MenuHandler.handle(this, id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
