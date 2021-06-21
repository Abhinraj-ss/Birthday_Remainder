package com.abhinraj.birthdayremainder

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    /*Array of permissions to be used in the app*/
    val permissionString =
        arrayOf(Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.INTERNET)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            openNewActivity()
        }, 2000)
    }
    fun openNewActivity() {

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

        /*else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }
   /* override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Handler().postDelayed({
                        openNewActivity()
                    }, 2000)
                } else {
                    Toast.makeText(
                        this,
                        "Please grant all permissions to continue",
                        Toast.LENGTH_SHORT
                    ).show()
                    this.finish()
                }
                return
            }
            else -> {
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
                this.finish()
                return
            }
        }
    }

    /*Lifecycle method. Here the finish() ensures that the activity does not open again when the user presses back button*/
    override fun onPause() {
        super.onPause()
        finish()
    }*/
}