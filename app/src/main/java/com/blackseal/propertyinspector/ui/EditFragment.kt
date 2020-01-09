package com.blackseal.propertyinspector.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.blackseal.propertyinspector.R
import com.blackseal.propertyinspector.model.*
import kotlinx.android.synthetic.main.edit_fragment.*

class EditFragment : Fragment() {

    companion object {
        fun newInstance() = EditFragment()
    }

    private lateinit var viewModel: EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)
        // TODO: Use the ViewModel

        save_btn.setOnClickListener {
            val roadCondition = when (roadConditionRadioGroup.checkedRadioButtonId) {
                R.id.roadConditionRadio1 -> ROAD_CONDITION.MAIN_ROAD
                R.id.roadConditionRadio2 -> ROAD_CONDITION.NORMAL_ROAD
                R.id.roadConditionRadio3 -> ROAD_CONDITION.QUIET_ROAD
                else -> ROAD_CONDITION.NO_THROUGH_ROAD
            }

            val commute = when (commuteRadioGroup.checkedRadioButtonId) {
                R.id.commuteRadio1 -> COMMUTE.TRAIN
                R.id.commuteRadio2 -> COMMUTE.EXPRESS_BUS
                R.id.commuteRadio3 -> COMMUTE.BUS
                else -> COMMUTE.DRIVE_ONLY
            }

            val shopping = when (shoppingRadioGroup.checkedRadioButtonId) {
                R.id.shoppingRadio1 -> SHOPPING.MALL
                R.id.shoppingRadio2 -> SHOPPING.VILLAGE
                R.id.shoppingRadio3 -> SHOPPING.BUS
                else -> SHOPPING.DRIVE_ONLY
            }

            val catchment = when (catchmentRadioGroup.checkedRadioButtonId) {
                R.id.catchmentRadio1 -> CATCHMENT.WEAK
                R.id.catchmentRadio2 -> CATCHMENT.OK
                R.id.catchmentRadio3 -> CATCHMENT.GOOD
                else -> CATCHMENT.GREAT
            }

            val inspection = Inspection(
                addressEditText.text.toString(), "",
                false,
                HouseShape(),
                Decoration(),
                HouseLocation(
                    roadCondition,
                    commute,
                    shopping,
                    catchment,
                    outBowCheckBox.isChecked,
                    roadOnFaceCheckBox.isChecked,
                    lowerThanRoadCheckBox.isChecked,
                    electricalTowerCheckBox.isChecked
                )
            )
            rankTextView.text = inspection.calculateRank().toString()
            scrollView.scrollTo(0,0)
            viewModel.saveInspection(inspection)
        }
    }

}
