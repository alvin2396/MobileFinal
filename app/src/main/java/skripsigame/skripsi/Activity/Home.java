package skripsigame.skripsi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import skripsigame.skripsi.Adapter.AdapterViewPager;
import skripsigame.skripsi.Fragment.NewGame;
import skripsigame.skripsi.Fragment.Popular;
import skripsigame.skripsi.Fragment.Rekomendasi;
import skripsigame.skripsi.R;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String token,email,wallet;
    Popular popFragr = new Popular();
    NewGame newGame = new NewGame();
    Rekomendasi rekomFrag = new Rekomendasi();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Intent ints = getIntent();
        token = ints.getStringExtra("token");
        email = ints.getStringExtra("email");
        wallet = ints.getStringExtra("wallet");
        AntarDataKePopular();
        AntarDataKeNew();
        AntarDataKeRekomen();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(Home.this,Home.class);
            intent.putExtra("token",token);
            intent.putExtra("email",email);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_profil) {
            Intent intent = new Intent(Home.this,Profile.class);
            intent.putExtra("token", token);
            intent.putExtra("email", email);
            startActivity(intent);

        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(Home.this,ActivityCarts.class);
            intent.putExtra("token",token);
            intent.putExtra("email",email);

            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addTabs(ViewPager viewPager) {
        AdapterViewPager adapter = new AdapterViewPager(getSupportFragmentManager());
        adapter.addFrag(rekomFrag, "Rekomendasi");
        adapter.addFrag(popFragr, "Popular");
        adapter.addFrag(newGame, "NewGame");
        viewPager.setAdapter(adapter);
    }
    private void AntarDataKePopular()
    {
        Bundle bundle = new Bundle();
        bundle.putString("tokens",token);
        bundle.putString("email",email);
        popFragr.setArguments(bundle);
    }

    private void AntarDataKeNew()
    {
        Bundle bundle = new Bundle();
        bundle.putString("tokens",token);
        bundle.putString("email",email);
        newGame.setArguments(bundle);
    }

    private void AntarDataKeRekomen()
    {
        Bundle bundle = new Bundle();
        bundle.putString("tokens",token);
        bundle.putString("email",email);
        rekomFrag.setArguments(bundle);
    }




}
