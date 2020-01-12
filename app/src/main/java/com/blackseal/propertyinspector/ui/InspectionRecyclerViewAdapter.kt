package com.blackseal.propertyinspector.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blackseal.propertyinspector.R
import com.blackseal.propertyinspector.model.Inspection


import com.blackseal.propertyinspector.ui.InspectionListFragment.OnListFragmentInteractionListener
import com.blackseal.propertyinspector.ui.dummy.DummyContent.DummyItem

import kotlinx.android.synthetic.main.item_inspection.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class InspectionRecyclerViewAdapter(
    private val mValues: List<Inspection>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<InspectionRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Inspection
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inspection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = "${item.address}\n${item.houseShape.bedroomNumber} bedrooms, ${item.houseShape.landSize} m2"
        holder.mContentView.text = item.calculateRank().toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }



    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
