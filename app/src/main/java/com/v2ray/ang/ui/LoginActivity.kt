package com.v2ray.ang.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.v2ray.ang.R

class LoginActivity : AppCompatActivity() {
    
    companion object {
        private const val PREFS_NAME = "shadeline_login"
        private const val KEY_ACCOUNT = "account"
        private const val KEY_PASSWORD = "password"
        private const val KEY_LOGGED_IN = "logged_in"
        
        fun isLoggedIn(context: Context): Boolean {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_LOGGED_IN, false)
        }
        
        fun getAccount(context: Context): String {
            return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_ACCOUNT, "") ?: ""
        }
        
        fun logout(context: Context) {
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                .putBoolean(KEY_LOGGED_IN, false)
                .apply()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        val etAccount = findViewById<EditText>(R.id.et_account)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)
        val tvSwitch = findViewById<TextView>(R.id.tv_switch_mode)
        
        var isRegisterMode = false
        
        btnLogin.setOnClickListener {
            val account = etAccount.text.toString().trim()
            val password = etPassword.text.toString().trim()
            
            if (account.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "请输入账号和密码", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            
            if (isRegisterMode) {
                // Simple "registration" - just save
                prefs.edit()
                    .putString(KEY_ACCOUNT, account)
                    .putString(KEY_PASSWORD, password)
                    .putBoolean(KEY_LOGGED_IN, true)
                    .apply()
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                val savedPwd = prefs.getString(KEY_PASSWORD, "")
                if (prefs.contains(KEY_ACCOUNT) && password == savedPwd) {
                    prefs.edit().putBoolean(KEY_LOGGED_IN, true).apply()
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        btnRegister.setOnClickListener {
            isRegisterMode = true
            btnLogin.text = "注册"
            btnRegister.visibility = android.view.View.GONE
            tvSwitch.visibility = android.view.View.VISIBLE
        }
        
        tvSwitch.setOnClickListener {
            isRegisterMode = false
            btnLogin.text = "登录"
            btnRegister.visibility = android.view.View.VISIBLE
            tvSwitch.visibility = android.view.View.GONE
        }
    }
}
