package com.miko.falco

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        register_Button.setOnClickListener{
            // go to register view
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        signIn_Button.setOnClickListener{
            // go to add produce activity
            val intent = Intent(this, AddProduceActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}