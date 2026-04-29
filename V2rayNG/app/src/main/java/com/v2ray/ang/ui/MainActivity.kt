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
import com.v2ray.ang.util.Utils
import com.v2ray.ang.extension.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket

class MainActivity : BaseActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val requestVpnPermission = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            startV2Ray()
        }
    }

    private var selectedNodeIndex = 0
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithToolbar(binding.root, showHomeAsUp = false, title = getString(R.string.app_name))
        setupServerNodes()

        binding.btnConnect.setOnClickListener {
            if (SettingsManager.isVpnMode()) {
                val intent = VpnService.prepare(this)
                if (intent == null) startV2Ray()
                else requestVpnPermission.launch(intent)
            } else { startV2Ray() }
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
                    if (subId != null) lifecycleScope.launch(Dispatchers.IO) {
                        val result = AngConfigManager.updateSubscription(subId)
                        launch(Dispatchers.Main) {
                            if (result > 0) toast("Imported $result servers")
                            else toast("Import failed")
                        }
                    }
                }
            }
            input.setNegativeButton("Cancel", null)
            input.show()
        }

        binding.btnPayment.setOnClickListener { Utils.openUri(this, "https://mindsparklearn.com/pricing") }

        binding.btnSpeedTest.setOnClickListener {
            val node = SERVER_NODES[selectedNodeIndex]
            lifecycleScope.launch(Dispatchers.IO) {
                val ping = try {
                    val sock = Socket()
                    sock.connect(InetSocketAddress(node.host, node.port), 3000)
                    val start = System.currentTimeMillis()
                    sock.close()
                    (System.currentTimeMillis() - start).toInt()
                } catch (e: Exception) { -1 }
                withContext(Dispatchers.Main) {
                    updatePingForNode(selectedNodeIndex, if (ping < 0) "延迟: 超时" else "延迟: ${ping}ms")
                }
            }
        }
        updateConnectionStatus(false)
    }

    private fun setupServerNodes() {
        binding.layoutServerUk.setOnClickListener { selectNode(0) }
        binding.rbServerUk.setOnClickListener { selectNode(0) }
        binding.layoutServerUs1.setOnClickListener { selectNode(1) }
        binding.rbServerUs1.setOnClickListener { selectNode(1) }
        binding.layoutServerUs2.setOnClickListener { selectNode(2) }
        binding.rbServerUs2.setOnClickListener { selectNode(2) }
        selectNode(0)
    }

    private fun selectNode(index: Int) {
        selectedNodeIndex = index
        binding.rbServerUk.isChecked = index == 0
        binding.rbServerUs1.isChecked = index == 1
        binding.rbServerUs2.isChecked = index == 2
        listOf(binding.layoutServerUk, binding.layoutServerUs1, binding.layoutServerUs2).forEachIndexed { i, v ->
            v.alpha = if (i == index) 1.0f else 0.6f
        }
    }

    private fun updatePingForNode(index: Int, text: String) {
        when (index) { 0 -> binding.tvPingUk.text = text; 1 -> binding.tvPingUs1.text = text; 2 -> binding.tvPingUs2.text = text }
    }

    private fun startV2Ray() { V2RayServiceManager.startVService(this); updateConnectionStatus(true) }
    private fun updateConnectionStatus(isRunning: Boolean) {
        binding.btnConnect.isEnabled = !isRunning; binding.btnStop.isEnabled = isRunning
        binding.tvStatus.text = if (isRunning) "\uD83D\uDFE2 已连接" else "\uD83D\uDD34 已断开"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { menuInflater.inflate(R.menu.menu_main, menu); return true }
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_account_info -> { toast("账号: ${LoginActivity.getAccount(this)}"); true }
        R.id.action_version -> { toast("ShadeLine v2.0"); true }
        R.id.action_login -> {
            if (LoginActivity.isLoggedIn(this)) { LoginActivity.logout(this); toast("已退出登录") }
            else { startActivity(Intent(this, LoginActivity::class.java)) }
            true
        }
        R.id.action_subscription_pay -> { Utils.openUri(this, "https://mindsparklearn.com/pricing"); true }
        else -> super.onOptionsItemSelected(item)
    }
}
