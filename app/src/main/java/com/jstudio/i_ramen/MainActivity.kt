package com.jstudio.i_ramen

import android.arch.persistence.room.Room
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawer: DrawerLayout
    lateinit var fragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        //ActionBar(ツールバー）をセットする
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)
        val navigationview : NavigationView = findViewById(R.id.nav_view)
        navigationview.setNavigationItemSelectedListener (this)

        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        fragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        //navigationview.setCheckedItem(R.id.nav_view)
    }

    //fragmentが完成次第追加
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.fragment_main -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment()).commit()
            }
            R.id.fragment_quest -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, QuestFragment()).commit()
            }
            R.id.fragment_memory -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MemoryFragment()).commit()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}
