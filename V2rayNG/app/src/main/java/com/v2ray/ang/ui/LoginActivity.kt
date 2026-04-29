package com.v2ray.ang.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.v2ray.ang.R

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val PREFS_NAME = "shadeline_login"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Login"

        val etUsername = findViewById<EditText>(R.id.et_username)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnLogout = findViewById<Button>(R.id.btn_logout)
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        if (prefs.getBoolean(KEY_IS_LOGGED_IN, false)) {
            etUsername.setText(prefs.getString(KEY_USERNAME, ""))
            etPassword.setText(prefs.getString(KEY_PASSWORD, ""))
            btnLogin.isEnabled = false
            btnLogout.isEnabled = true
            Toast.makeText(this, "Already logged in as ${prefs.getString(KEY_USERNAME, "")}", Toast.LENGTH_SHORT).show()
        } else {
            btnLogout.isEnabled = false
        }

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()) {
                prefs.edit().apply {
                    putString(KEY_USERNAME, username)
                    putString(KEY_PASSWORD, password)
                    putBoolean(KEY_IS_LOGGED_IN, true)
                    apply()
                }
                btnLogin.isEnabled = false
                btnLogout.isEnabled = true
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        btnLogout.setOnClickListener {
            prefs.edit().apply {
                remove(KEY_USERNAME)
                remove(KEY_PASSWORD)
                putBoolean(KEY_IS_LOGGED_IN, false)
                apply()
            }
            etUsername.text.clear()
            etPassword.text.clear()
            btnLogin.isEnabled = true
            btnLogout.isEnabled = false
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
        }
    }
}
