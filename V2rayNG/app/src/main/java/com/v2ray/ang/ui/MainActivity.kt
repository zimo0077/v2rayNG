package com.v2ray.ang.ui

import android.content.Intent
import android.net.VpnService
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.v2ray.ang.R
import com.v2ray.ang.databinding.ActivityMainBinding
import com.v2ray.ang.handler.AngConfigManager
import com.v2ray.ang.handler.MmkvManager
import com.v2ray.ang.handler.SettingsManager
import com.v2ray.ang.handler.V2RayServiceManager
import com.v2ray.ang.handler.SpeedtestManager
import com.v2ray.ang.util.Utils
import com.v2ray.ang.extension.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val requestVpnPermission = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            startV2Ray()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithToolbar(binding.root, showHomeAsUp = false, title = getString(R.string.app_name))

        binding.btnConnect.setOnClickListener {
            if (MmkvManager.getSelectServer().isNullOrEmpty()) {
                toast(R.string.title_file_chooser)
                return@setOnClickListener
            }
            if (SettingsManager.isVpnMode()) {
                val intent = VpnService.prepare(this)
                if (intent == null) {
                    startV2Ray()
                } else {
                    requestVpnPermission.launch(intent)
                }
            } else {
                startV2Ray()
            }
        }

        binding.btnStop.setOnClickListener {
            V2RayServiceManager.stopVService(this)
            updateConnectionStatus(false)
        }

        binding.btnSubscription.setOnClickListener {
            val input = androidx.appcompat.app.AlertDialog.Builder(this)
            input.setTitle("Subscription URL")
            input.setMessage("Enter subscription URL:")
            val editText = android.widget.EditText(this)
            input.setView(editText)
            input.setPositiveButton("OK") { _, _ ->
                val url = editText.text.toString().trim()
                if (url.isNotEmpty()) {
                    val subId = MmkvManager.createSubscription("Subscription", url, "", false)
                    if (subId != null) {
                        lifecycleScope.launch(Dispatchers.IO) {
                            val result = AngConfigManager.updateSubscription(subId)
                            launch(Dispatchers.Main) {
                                if (result > 0) {
                                    toast("Imported $result servers")
                                } else {
                                    toast("Import failed")
                                }
                            }
                        }
                    }
                }
            }
            input.setNegativeButton("Cancel", null)
            input.show()
        }

        binding.btnPayment.setOnClickListener {
            Utils.openUri(this, "https://mindsparklearn.com/pricing")
        }

        binding.btnSpeedTest.setOnClickListener {
            val selectedGuid = MmkvManager.getSelectServer()
            if (selectedGuid.isNullOrEmpty()) {
                toast("No server selected")
                return@setOnClickListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                val config = AngConfigManager.getProfileItem(selectedGuid)
                val host = config?.address ?: return@launch
                val port = config?.port ?: return@launch
                val ping = SpeedtestManager.tcping(host, port)
                withContext(Dispatchers.Main) {
                    if (ping >= 0) {
                        toast("Ping: ${ping}ms")
                    } else {
                        toast("Ping timeout")
                    }
                }
            }
        }

        updateConnectionStatus(false)
    }

    private fun startV2Ray() {
        if (MmkvManager.getSelectServer().isNullOrEmpty()) {
            toast(R.string.title_file_chooser)
            return
        }
        V2RayServiceManager.startVService(this)
        updateConnectionStatus(true)
    }

    private fun updateConnectionStatus(isRunning: Boolean) {
        if (isRunning) {
            binding.btnConnect.isEnabled = false
            binding.btnStop.isEnabled = true
            binding.tvStatus.text = getString(R.string.connection_connected)
        } else {
            binding.btnConnect.isEnabled = true
            binding.btnStop.isEnabled = false
            binding.tvStatus.text = getString(R.string.connection_not_connected)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_account_info -> {
                toast("账号信息")
                true
            }
            R.id.action_version -> {
                toast("版本号: 2.0.0")
                true
            }
            R.id.action_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
            }
            R.id.action_subscription_pay -> {
                Utils.openUri(this, "https://mindsparklearn.com/pricing")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
