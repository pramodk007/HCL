package com.example.heartcarelite.ui.userDetails

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ActivityCvdRiskBinding
import com.example.heartcarelite.databinding.ActivityUserDetailsBinding
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import com.example.heartcarelite.ui.cvdRisk.CvdRiskActivity
import com.example.heartcarelite.ui.cvdRisk.CvdRiskViewModel
import com.example.heartcarelite.ui.cvdRisk.saveRecord.SaveRecordActivity
import com.example.heartcarelite.ui.cvdRisk.saveRecord.SaveRecordViewModel
import com.example.heartcarelite.ui.userList.UserListActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailsBinding
    private val saveRecordViewModel: SaveRecordViewModel by viewModels()
    var alertDialog: AlertDialog? = null

    private lateinit var lss: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        creteMaterialDialog()


        requireNotNull(intent).run {
            lss = getParcelableExtra("userDetails")!!
        }
        lss.let {
            supportActionBar?.title = "${lss.userFirstName} ${lss.userMiddleName?.trim()} ${lss.userLastName}"
            binding.tvPatientId.text = lss.userPatientId
            binding.tvScreeningDate.text = Date(lss.screeningDate.toLong()).toSimpleString()
            binding.tvSex.text = lss.userSex
            binding.tvAge.text = lss.userAge
            binding.tvDiabetes.text = lss.userDiabetes
            binding.tvTabacco.text = lss.userTobaccoUser
            binding.tvCardiovascularEvent.text = lss.cardiovascularEvent
            binding.tvBloodPressure.text = lss.userBloodPressure
            binding.tvTotalCholesterol.text = "${lss.totalCholesterol} mg/dL"
            binding.tvHeight.text = lss.userHeight
            binding.tvWeight.text = lss.userWeight
            binding.tvBMI.text = lss.userBMI
            binding.tvAddressLineOne.text = lss.userAddressLineOne
            binding.tvAddressLineTwo.text = lss.userPinCode
            binding.tvAddressLineThree.text = lss.userCountry
            binding.tvMobileNumber.text = lss.userMobileNumber
        }

        binding.bUpdate.setOnClickListener {
            val intent = Intent(applicationContext, SaveRecordActivity::class.java)
            intent.putExtra("update", lss)
            startActivity(intent)
        }
        binding.bDelete.setOnClickListener {
            alertDialog?.show()
        }

    }
    private fun Date.toSimpleString() : String {
        val format = SimpleDateFormat("dd/MM/yyy")
        return format.format(this)
    }

    private fun creteMaterialDialog(){
        val builder = MaterialAlertDialogBuilder(this,R.style.AlertDialogTheme)
        with(builder) {
            setTitle("Delete record?")
            setMessage("The record will no longer be recoverable.")
            setPositiveButton("NO, G0 BACK") { _, _ ->
            }
            setNegativeButton("YES, DELETE" ){ _, _ ->
                saveRecordViewModel.delete(lss)
                val intent = Intent(applicationContext, UserListActivity::class.java)
                startActivity(intent)
            }
        }
        alertDialog = builder.create()

        val button = alertDialog?.getButton(DialogInterface.BUTTON_POSITIVE)
        with(button) {
            this?.setTextColor(ContextCompat.getColor(this@UserDetailsActivity, R.color.bmi_red))
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}