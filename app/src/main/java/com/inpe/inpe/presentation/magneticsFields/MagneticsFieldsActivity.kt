package com.inpe.inpe.presentation.magneticsFields

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.inpe.inpe.R
import kotlinx.android.synthetic.main.activity_magnetics_fields.*
import kotlinx.android.synthetic.main.view_jsoc_dates.*
import java.time.Month
import java.util.*

class MagneticsFieldsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magnetics_fields)

        text_input_field.setOnEditorActionListener { v, actionId, event ->
            if ( (actionId == EditorInfo.IME_NULL) && (event.action == KeyEvent.ACTION_DOWN) && (v.text.isNotEmpty()) ) {
                addChipToGroup(v.text.toString())
                v.text = ""
            }
            true
        }

        rg_date.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                rb_actual.id -> layout_dates.visibility = View.GONE
                rb_select_date.id -> layout_dates.visibility = View.VISIBLE
            }
        }

        tv_first_date.setOnClickListener {
            setDatePickerDialog(tv_first_date)
        }

        tv_last_date.setOnClickListener {
            setDatePickerDialog(tv_last_date)
        }

        ib_first_date.setOnClickListener {
            setDatePickerDialog(tv_first_date)
        }

        ib_last_date.setOnClickListener {
            setDatePickerDialog(tv_last_date)
        }
    }

    private fun setDatePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            if (month < 10 && dayOfMonth < 10) {
                textView.text = "0$dayOfMonth/0${month + 1}/$year"
            } else if (dayOfMonth < 10) {
                textView.text = "0$dayOfMonth/${month + 1}/$year"
            } else if (month < 10) {
                textView.text = "$dayOfMonth/0${month + 1}/$year"
            } else {
                textView.text = "$dayOfMonth/${month + 1}/$year"
            }
        }, year, month, day)

        picker.show()

    }

    private fun addChipToGroup(type: String) {
        val chip = Chip(this)
        chip.text = type
        chip.chipIcon = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background)
        chip.isChipIconVisible = false
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chip.isCheckable = false
        chip_group.addView(chip as View)

        chip.setOnCloseIconClickListener {
            chip_group.removeView(chip as View)
        }
    }
}