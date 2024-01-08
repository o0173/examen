package com.app.examen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.app.examen.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    var URIData: Uri? =null
    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val imgUri = data?.data
                URIData= data?.data
                Log.d("dssadfdas", URIData!!.toString())
                binding.imageView.setImageURI(imgUri)
            }
        }
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        setContentView(binding.root)
        loadData()
            binding.button.setOnClickListener {
                Toast.makeText(applicationContext, "Повідомлення", Toast.LENGTH_SHORT).show()
            }
        binding.imageView.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }

    }
    private fun loadData() {
        val pib = sharedPreferences.getString("ПІБ", "")
        val ipn = sharedPreferences.getString("ІПН", "")
        val accountId = sharedPreferences.getString("Номер рахунку", "")
        val openDate = sharedPreferences.getString("Дата відкриття", "")
        val telephoneNumber = sharedPreferences.getString("Номер телефону", "")

        // Update your UI with the loaded data
        binding.textViewPib.setText(pib)
        binding.textViewIpn.setText(ipn)
        binding.textViewAccountId.setText(accountId)
        binding.textViewopenDate.setText(openDate)
        binding.textViewopentelephoneNumber.setText(telephoneNumber)
    }
    override fun onPause() {
        super.onPause()

        saveDataToSharedPreferences()
    }

    private fun saveDataToSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putString("ПІБ", binding.textViewPib.text.toString())
        editor.putString("ІПН", binding.textViewIpn.text.toString())
        editor.putString("Номер рахунку", binding.textViewAccountId.text.toString())
        editor.putString("Дата відкриття", binding.textViewopenDate.text.toString())
        editor.putString("Номер телефону", binding.textViewopentelephoneNumber.text.toString())
        editor.apply()
        showToast()
    }


    fun showToast() {
        // Set the toast and duration
        val toastDurationInMilliSeconds = 10000
      val mToastToShow = Toast.makeText(this, "Дані було збережено", Toast.LENGTH_LONG)

        // Set the countdown to display the toast
        val toastCountDown: CountDownTimer
        toastCountDown =
            object : CountDownTimer(toastDurationInMilliSeconds.toLong(), 1000 /*Tick duration*/) {
                override fun onTick(millisUntilFinished: Long) {
                    mToastToShow.show()
                }

                override fun onFinish() {
                    mToastToShow.cancel()
                }
            }

        // Show the toast and starts the countdown
        mToastToShow.show()
        toastCountDown.start()
    }
}