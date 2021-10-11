package com.example.heartcarelite.ui.cvdRisk.saveRecord

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import com.example.heartcarelite.R
import com.example.heartcarelite.databinding.ActivityCvdRiskBinding
import com.example.heartcarelite.databinding.ActivitySaveRecordBinding
import com.example.heartcarelite.model.User
import com.example.heartcarelite.model.UserInfo
import com.example.heartcarelite.ui.cvdRisk.CvdRiskViewModel
import com.example.heartcarelite.utils.HelperFunction.toAgeDob
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveRecordBinding
    private val saveRecordViewModel: SaveRecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Save Record"

        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        binding.lifecycleOwner = this
        binding.myViewModel = saveRecordViewModel

        saveRecordViewModel.message.observe(this,{
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })


        val lss2: UserInfo? = intent.getParcelableExtra("userData")
        lss2?.let {
            saveRecordViewModel.inputAge.value = lss2.userAge
            saveRecordViewModel.inputSex.value = lss2.userSex
            saveRecordViewModel.inputDOB.value = lss2.userDOB
            saveRecordViewModel.screeningDate.value = lss2.screeningDate
            saveRecordViewModel.userDiabetes.value = lss2.userDiabetes
            saveRecordViewModel.userTobaccoUser.value = lss2.userTobaccoUser
            saveRecordViewModel.cardiovascularEvent.value = lss2.cardiovascularEvent
            saveRecordViewModel.userBloodPressure.value = lss2.userBloodPressure
            saveRecordViewModel.userHeight.value = lss2.userHeight
            saveRecordViewModel.userWeight.value = lss2.userWeight
            saveRecordViewModel.userBMI.value = lss2.userBMI
            saveRecordViewModel.totalCholesterol.value = lss2.totalCholesterol
            saveRecordViewModel.cvdScore.value = lss2.cvdScore
        }

        if (intent != null) {
            val lss1: User? = intent.getParcelableExtra("update")
            if (lss1 != null) {
                supportActionBar?.title = "Update Record"
                lss1.let {
                    saveRecordViewModel.initUpdateAndDelete(lss1)
                }

            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}