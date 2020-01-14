package com.blackseal.propertyinspector.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.blackseal.propertyinspector.R
import com.blackseal.propertyinspector.model.Inspection
import com.blackseal.propertyinspector.ui.EditFragment.Companion.KEY_ID
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), InspectionListFragment.OnListFragmentInteractionListener {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var view: EditText
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        view = EditText(this)
        view.hint = getString(R.string.email)
        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        viewModel.inspectionListLiveData.observe(this, Observer {
            sendEmail(view.text.toString(), gson.toJson(it))
        })
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
            R.id.action_export -> exportProperty()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun exportProperty(): Boolean {

        AlertDialog.Builder(this).apply {
            title?.let { setTitle(it) }
            setCancelable(false)
            setView(view)
            setPositiveButton(R.string.ok) { _, _ ->
                viewModel.export(view.text.toString())
            }

            setNegativeButton(R.string.cancel) { _, _ ->

            }

        }.show()
        return true
    }

    fun sendEmail(email: String, content: String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        i.putExtra(Intent.EXTRA_SUBJECT, "Property Backup")
        i.putExtra(Intent.EXTRA_TEXT, content)
        try {
            startActivity(Intent.createChooser(i, "Send report mail..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(
                this,
                "There are no email clients installed.",
                Toast.LENGTH_SHORT
            ).show()
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

fun Context.showNormalConfirmDialog(
    title: String?, msg: String, callback: () -> Unit,
    cancelCallback: (() -> Unit)? = null,
    okTextRes: Int = R.string.ok,
    cancelTextRes: Int = R.string.cancel
) {
    AlertDialog.Builder(this).apply {
        title?.let { setTitle(it) }
        setCancelable(false)
        setMessage(msg)
        setPositiveButton(okTextRes) { _, _ ->
            callback()
        }
        if (cancelCallback != null) {
            setNegativeButton(cancelTextRes) { _, _ ->
                cancelCallback()
            }
        }
    }.let {
        it.show()
    }
}
