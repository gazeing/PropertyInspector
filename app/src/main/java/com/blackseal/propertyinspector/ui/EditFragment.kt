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


            val inspection = Inspection(
                addressEditText.text.toString(), "",
                false,
                getHouseShape(),
                getDecoration(),
                getLocation()

            )
            rankTextView.text = inspection.calculateRank().toString()
            scrollView.smoothScrollTo(0, 0)
            viewModel.saveInspection(inspection)
        }
    }

    private fun getHouseShape(): HouseShape {

        return HouseShape(houseSizeEditText.text.toString().toInt())
    }

    private fun getDecoration(): Decoration {
        val kitchenDecoration = when (kitchenDecorationRadioGroup.checkedRadioButtonId) {
            R.id.kitchenDecorationRadio1 -> DECORATION_CONDITION.BROKEN
            R.id.kitchenDecorationRadio2 -> DECORATION_CONDITION.OLD
            R.id.kitchenDecorationRadio3 -> DECORATION_CONDITION.USABLE
            else -> DECORATION_CONDITION.NEW
        }
        val backYardSize =
            when (backyardSizeRadioGroup.checkedRadioButtonId) {
                R.id.backyardSizeRadio1 -> BACK_YARD_SIZE.ABSENT
                R.id.backyardSizeRadio2 -> BACK_YARD_SIZE.SMALL
                R.id.backyardSizeRadio3 -> BACK_YARD_SIZE.MIDIUM
                else -> BACK_YARD_SIZE.LARGE
            }

        val backYardShape = when (backyardShapeRadioGroup.checkedRadioButtonId) {
            R.id.backyardShapeRadio1 -> BACK_YARD_CONDITION.BUSHES
            R.id.backyardShapeRadio2 -> BACK_YARD_CONDITION.SLOPE
            R.id.backyardShapeRadio3 -> BACK_YARD_CONDITION.STEP
            else -> BACK_YARD_CONDITION.FLAT
        }

        val swimmingPoolCondition = when (swimmingPoolRadioGroup.checkedRadioButtonId) {
            R.id.swimmingPoolRadio1 -> SWIMMING_POOL_CONDITION.ABSENT
            R.id.swimmingPoolRadio2 -> SWIMMING_POOL_CONDITION.BROKEN
            R.id.swimmingPoolRadio3 -> SWIMMING_POOL_CONDITION.USABLE
            else -> SWIMMING_POOL_CONDITION.NEW
        }
        return Decoration(
            hasGasCheckBox.isChecked, airConCheckBox.isChecked, timberWarmerCheckBox.isChecked,
            firePlaceCheckBox.isChecked, timberCheckBox.isChecked, alfrescoCheckBox.isChecked,
            kitchenDecoration, backYardSize, backYardShape, swimmingPoolCondition
        )
    }

    private fun getLocation(): HouseLocation {
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

        return HouseLocation(
            roadCondition,
            commute,
            shopping,
            catchment,
            outBowCheckBox.isChecked,
            roadOnFaceCheckBox.isChecked,
            lowerThanRoadCheckBox.isChecked,
            electricalTowerCheckBox.isChecked
        )
    }

}
