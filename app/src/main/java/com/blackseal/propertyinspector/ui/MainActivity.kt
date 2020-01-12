package com.blackseal.propertyinspector.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.blackseal.propertyinspector.R
import com.blackseal.propertyinspector.model.Inspection
import com.blackseal.propertyinspector.ui.EditFragment.Companion.KEY_ID
import com.blackseal.propertyinspector.ui.dummy.DummyContent

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), InspectionListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
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
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onListFragmentInteraction(item: Inspection?) {
        //do nothing
        item?.let {
            val bundle = bundleOf(KEY_ID to item.id.toString())
            getNavigationController().navigate(R.id.editFragment, bundle)
        }
    }

    private fun getNavigationController(): NavController {
        return findNavController(R.id.my_nav_host_fragment)
    }
}
