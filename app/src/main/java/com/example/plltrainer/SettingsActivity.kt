package com.example.plltrainer

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import com.example.plltrainer.database.SolveDBService
import com.example.plltrainer.databinding.ConfirmPopupBinding
import com.example.plltrainer.databinding.SettingsActivityBinding

class SettingsActivity: AppCompatActivity() {

    private lateinit var binding: SettingsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SettingsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.clearAllDataButton.setOnClickListener {
            val popupBinding = ConfirmPopupBinding.inflate(layoutInflater)
            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true

            val popupWindow = PopupWindow(popupBinding.root, width, height, focusable)
            popupWindow.contentView = popupBinding.root

            popupWindow.showAtLocation(binding.clearAllDataButton, Gravity.CENTER, 0, 0)

            popupBinding.confirmButton.setOnClickListener{
                SolveDBService(this).clearSolveHistory()
                popupWindow.dismiss()
            }

            popupBinding.cancelButton.setOnClickListener{
                popupWindow.dismiss()
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }

}
