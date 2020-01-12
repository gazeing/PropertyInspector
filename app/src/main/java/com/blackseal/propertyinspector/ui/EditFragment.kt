package com.blackseal.propertyinspector.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.blackseal.propertyinspector.R
import com.blackseal.propertyinspector.model.*
import kotlinx.android.synthetic.main.edit_fragment.*

class EditFragment : Fragment() {

    companion object {
        fun newInstance() = EditFragment()
        const val KEY_ID = "key_id"
    }

    private lateinit var viewModel: EditViewModel
    private lateinit var viewList: List<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditViewModel::class.java)

        save_btn.setOnClickListener {
            onSave()
        }

        addViewToList()

        arguments?.getString(KEY_ID)?.let { id ->
            viewModel.getInspection(id)
            lock()
        }



        observerInspection()
    }

    fun onSave() {

        val inspection = Inspection(
            0,
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

    private fun addViewToList() {
        viewList = listOf<View>(
            addressEditText,
            landShapeRadio1,
            landShapeRadio2,
            landShapeRadio3,
            landShapeRadio4,
            MasterRoomLengthEditText,
            MasterRoomWidthEditText,
            masterRoomBathroomRadio1,
            masterRoomBathroomRadio2,
            masterRoomBathroomRadio3,
            masterRoomBathroomRadio4,
            masterRoomWardrobeRadio1,
            masterRoomWardrobeRadio2,
            masterRoomWardrobeRadio3,
            masterRoomWardrobeRadio4,
            rampusRadio1,
            rampusRadio2,
            rampusRadio3,
            grannyFlatRadio1,
            grannyFlatRadio2,
            grannyFlatRadio3,
            kitchenLengthEditText,
            kitchenWidthEditText,
            rampusLengthEditText,
            rampusWidthEditText,
            familyLengthEditText,
            familyWidthEditText,
            dinningLengthEditText,
            dinningWidthEditText,
            houseSizeEditText,
            bedroomNumbersEditText,
            MasterRoomDirectionCheckBox,
            coveredCarParkEditText,
            uncoveredCarParkEditText,
            kitchenDecorationRadio1,
            kitchenDecorationRadio2,
            kitchenDecorationRadio3,
            kitchenDecorationRadio4,
            backyardSizeRadio1,
            backyardSizeRadio2,
            backyardSizeRadio3,
            backyardSizeRadio4,
            backyardShapeRadio1,
            backyardShapeRadio2,
            backyardShapeRadio3,
            backyardShapeRadio4,
            swimmingPoolRadio1,
            swimmingPoolRadio2,
            swimmingPoolRadio3,
            swimmingPoolRadio4,
            hasGasCheckBox,
            airConCheckBox,
            timberWarmerCheckBox,
            firePlaceCheckBox,
            timberCheckBox,
            alfrescoCheckBox,
            roadConditionRadio1,
            roadConditionRadio2,
            roadConditionRadio3,
            roadConditionRadio4,
            commuteRadio1,
            commuteRadio2,
            commuteRadio3,
            commuteRadio4,
            shoppingRadio1,
            shoppingRadio2,
            shoppingRadio3,
            shoppingRadio4,
            catchmentRadio1,
            catchmentRadio2,
            catchmentRadio3,
            catchmentRadio4,
            outBowCheckBox,
            roadOnFaceCheckBox,
            lowerThanRoadCheckBox,
            electricalTowerCheckBox
        )

    }

    fun lock() {
        for (view in viewList) {
            view.isEnabled = false
        }
        buttonTextView.text = getString(R.string.edit)
        save_btn.setOnClickListener {
            unlock()
        }
    }

    fun unlock() {
        for (view in viewList) {
            view.isEnabled = true
        }
        buttonTextView.text = getString(R.string.calculate_and_save)
        save_btn.setOnClickListener {
            onSave()
        }
    }

    private fun observerInspection() {
        viewModel.inspectionLiveData.observe(this, Observer {
            addressEditText.setText(it.address)
            landShapeRadioGroup.check(
                when (it.houseShape.landShape) {
                    LAND_SHAPE.SQUARE -> R.id.landShapeRadio1
                    LAND_SHAPE.TRIANGLE -> R.id.landShapeRadio2
                    LAND_SHAPE.AXE -> R.id.landShapeRadio3
                    else -> R.id.landShapeRadio4
                }
            )
            MasterRoomSizeResultTextView.text =
                getString(R.string.square_meter_format, it.houseShape.masterRoomSize.toString())
            bedroomNumbersEditText.setText(it.houseShape.bedroomNumber.toString())
            masterRoomBathroomRadioGroup.check(
                when (it.houseShape.masterRoomBathRoom) {
                    DECORATION_CONDITION.BROKEN -> R.id.masterRoomBathroomRadio1
                    DECORATION_CONDITION.OLD -> R.id.masterRoomBathroomRadio2
                    DECORATION_CONDITION.USABLE -> R.id.masterRoomBathroomRadio3
                    else -> R.id.masterRoomBathroomRadio4
                }
            )
            masterRoomWardrobeRadioGroup.check(
                when (it.houseShape.masterRoomWardrobe) {
                    DECORATION_CONDITION.BROKEN -> R.id.masterRoomWardrobeRadio1
                    DECORATION_CONDITION.OLD -> R.id.masterRoomWardrobeRadio2
                    DECORATION_CONDITION.USABLE -> R.id.masterRoomWardrobeRadio3
                    else -> R.id.masterRoomWardrobeRadio4
                }
            )
            MasterRoomDirectionCheckBox.isChecked = it.houseShape.isMasterRoomFacingNorthWest

            rampusRadioGroup.check(
                when (it.houseShape.rampusSpace) {
                    RAMPUS_SPACE.ABSENT -> R.id.rampusRadio1
                    RAMPUS_SPACE.WITHOUT_DOOR -> R.id.rampusRadio2
                    else -> R.id.rampusRadio3
                }
            )

            grannyFlatRadioGroup.check(
                when (it.houseShape.inLawSpace) {
                    IN_LAW_SPACE.ABSENT -> R.id.grannyFlatRadio1
                    IN_LAW_SPACE.WITHOUT_KITCHEN -> R.id.grannyFlatRadio2
                    else -> R.id.grannyFlatRadio3
                }
            )

            kitchenSizeResultTextView.text =
                getString(R.string.square_meter_format, it.houseShape.kitchenSize.toString())
            livingAreaSizeSumTextView.text =
                getString(R.string.square_meter_format, it.houseShape.livingAreaSize.toString())

            kitchenDecorationRadioGroup.check(
                when (it.decoration.kitchenDecoration) {
                    DECORATION_CONDITION.BROKEN -> R.id.kitchenDecorationRadio1
                    DECORATION_CONDITION.OLD -> R.id.kitchenDecorationRadio2
                    DECORATION_CONDITION.USABLE -> R.id.kitchenDecorationRadio3
                    else -> R.id.kitchenDecorationRadio4
                }
            )

            backyardSizeRadioGroup.check(
                when (it.decoration.backYardSize) {
                    BACK_YARD_SIZE.ABSENT -> R.id.backyardSizeRadio1
                    BACK_YARD_SIZE.SMALL -> R.id.backyardSizeRadio2
                    BACK_YARD_SIZE.MIDIUM -> R.id.backyardSizeRadio3
                    else -> R.id.backyardSizeRadio4
                }
            )

            backyardShapeRadioGroup.check(
                when (it.decoration.backYardCondition) {
                    BACK_YARD_CONDITION.BUSHES -> R.id.backyardShapeRadio1
                    BACK_YARD_CONDITION.SLOPE -> R.id.backyardShapeRadio2
                    BACK_YARD_CONDITION.STEP -> R.id.backyardShapeRadio3
                    else -> R.id.backyardShapeRadio4
                }
            )

            swimmingPoolRadioGroup.check(
                when (it.decoration.swimmingPoolCondition) {
                    SWIMMING_POOL_CONDITION.ABSENT -> R.id.swimmingPoolRadio1
                    SWIMMING_POOL_CONDITION.BROKEN -> R.id.swimmingPoolRadio2
                    SWIMMING_POOL_CONDITION.USABLE -> R.id.swimmingPoolRadio3
                    else -> R.id.swimmingPoolRadio4
                }
            )

            coveredCarParkEditText.setText(it.houseShape.coverdCarSpace.toString())
            uncoveredCarParkEditText.setText(it.houseShape.driveWayCarSpace.toString())


            hasGasCheckBox.isChecked = it.decoration.hasGasSupply
            airConCheckBox.isChecked = it.decoration.hasAirCon
            timberWarmerCheckBox.isChecked = it.decoration.hasFloorWarmer
            firePlaceCheckBox.isChecked = it.decoration.hasFirePlace
            timberCheckBox.isChecked = it.decoration.hasTimberFloor
            alfrescoCheckBox.isChecked = it.decoration.hasAlfresco

            outBowCheckBox.isChecked = it.houseLocation.hasOutOfBow
            roadOnFaceCheckBox.isChecked = it.houseLocation.hasRoadOnFace
            lowerThanRoadCheckBox.isChecked = it.houseLocation.hasLowerThanRoad
            electricalTowerCheckBox.isChecked = it.houseLocation.hasElecTower

            roadConditionRadioGroup.check(
                when (it.houseLocation.roadCondition) {
                    ROAD_CONDITION.MAIN_ROAD -> R.id.roadConditionRadio1
                    ROAD_CONDITION.NORMAL_ROAD -> R.id.roadConditionRadio2
                    ROAD_CONDITION.QUIET_ROAD -> R.id.roadConditionRadio3
                    else -> R.id.roadConditionRadio4
                }
            )

            commuteRadioGroup.check(
                when (it.houseLocation.commute) {
                    COMMUTE.TRAIN -> R.id.commuteRadio1
                    COMMUTE.EXPRESS_BUS -> R.id.commuteRadio2
                    COMMUTE.BUS -> R.id.commuteRadio3
                    else -> R.id.commuteRadio4
                }
            )

            shoppingRadioGroup.check(
                when (it.houseLocation.shopping) {
                    SHOPPING.MALL -> R.id.shoppingRadio1
                    SHOPPING.VILLAGE -> R.id.shoppingRadio2
                    SHOPPING.BUS -> R.id.shoppingRadio3
                    else -> R.id.shoppingRadio4
                }
            )

            catchmentRadioGroup.check(
                when (it.houseLocation.catchment) {
                    CATCHMENT.WEAK -> R.id.catchmentRadio1
                    CATCHMENT.OK -> R.id.catchmentRadio2
                    CATCHMENT.GOOD -> R.id.catchmentRadio3
                    else -> R.id.catchmentRadio4
                }
            )
        })
    }


    private fun getHouseShape(): HouseShape {
        val landShape = when (landShapeRadioGroup.checkedRadioButtonId) {
            R.id.landShapeRadio1 -> LAND_SHAPE.SQUARE
            R.id.landShapeRadio2 -> LAND_SHAPE.TRIANGLE
            R.id.landShapeRadio3 -> LAND_SHAPE.AXE
            else -> LAND_SHAPE.LADDER
        }

        val masterRoomSize =
            MasterRoomLengthEditText.text.toString().toFloat() * MasterRoomWidthEditText.text.toString().toFloat()
        MasterRoomSizeResultTextView.text =
            getString(R.string.square_meter_format, masterRoomSize.toString())

        val masterRoomBathRoom = when (masterRoomBathroomRadioGroup.checkedRadioButtonId) {
            R.id.masterRoomBathroomRadio1 -> DECORATION_CONDITION.BROKEN
            R.id.masterRoomBathroomRadio2 -> DECORATION_CONDITION.OLD
            R.id.masterRoomBathroomRadio3 -> DECORATION_CONDITION.USABLE
            else -> DECORATION_CONDITION.NEW
        }

        val masterRoomWardrobe = when (masterRoomWardrobeRadioGroup.checkedRadioButtonId) {
            R.id.masterRoomWardrobeRadio1 -> DECORATION_CONDITION.BROKEN
            R.id.masterRoomWardrobeRadio2 -> DECORATION_CONDITION.OLD
            R.id.masterRoomWardrobeRadio3 -> DECORATION_CONDITION.USABLE
            else -> DECORATION_CONDITION.NEW
        }

        val rampusSpace = when (rampusRadioGroup.checkedRadioButtonId) {
            R.id.rampusRadio1 -> RAMPUS_SPACE.ABSENT
            R.id.rampusRadio2 -> RAMPUS_SPACE.WITHOUT_DOOR
            else -> RAMPUS_SPACE.WITH_DOOR
        }

        val inLawSpace = when (grannyFlatRadioGroup.checkedRadioButtonId) {
            R.id.grannyFlatRadio1 -> IN_LAW_SPACE.ABSENT
            R.id.grannyFlatRadio2 -> IN_LAW_SPACE.WITHOUT_KITCHEN
            else -> IN_LAW_SPACE.WITH_KITCHEN
        }

        val kitchenSize =
            kitchenLengthEditText.text.toString().toFloat() * kitchenWidthEditText.text.toString().toFloat()
        kitchenSizeResultTextView.text =
            getString(R.string.square_meter_format, kitchenSize.toString())

        val rampusSize =
            (rampusLengthEditText.text.toString().toFloatOrNull()
                ?: 0f) * (rampusWidthEditText.text.toString().toFloatOrNull() ?: 0f)
        rampusSizeResultTextView.text =
            getString(R.string.square_meter_format, rampusSize.toString())


        val dinningSize =
            (dinningLengthEditText.text.toString().toFloatOrNull()
                ?: 0f) * (dinningWidthEditText.text.toString().toFloatOrNull() ?: 0f)
        dinningSizeResultTextView.text =
            getString(R.string.square_meter_format, dinningSize.toString())


        val familySize =
            (familyLengthEditText.text.toString().toFloatOrNull()
                ?: 0f) * (familyWidthEditText.text.toString().toFloatOrNull() ?: 0f)
        familySizeResultTextView.text =
            getString(R.string.square_meter_format, familySize.toString())

        val livingSize = (familySize + dinningSize + rampusSize).toInt()
        livingAreaSizeSumTextView.text =
            getString(R.string.square_meter_format, livingSize.toString())

        return HouseShape(
            houseSizeEditText.text.toString().toInt(),
            landShape,
            bedroomNumbersEditText.text.toString().toInt(),
            masterRoomSize.toInt(),
            masterRoomBathRoom,
            masterRoomWardrobe,
            MasterRoomDirectionCheckBox.isChecked,
            rampusSpace,
            inLawSpace,
            kitchenSize.toInt(),
            livingSize,
            coveredCarParkEditText.text.toString().toInt(),
            uncoveredCarParkEditText.text.toString().toInt()

        )
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
