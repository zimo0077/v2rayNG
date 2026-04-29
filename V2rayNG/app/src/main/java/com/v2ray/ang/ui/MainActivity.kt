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
    private var selectedNodeIndex = 0
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78")
    )

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
    private val requestVpnPermission = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            startV2Ray()
      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewWithToolbar(binding.root, showHomeAsUp = false, title = getString(R.string.app_name))

        binding.btnConnect.setOnClickListener {
            if (MmkvManager.getSelectServer().isNullOrEmpty()) {
                toast(R.string.title_file_chooser)
                return@setOnClickListener
          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            if (SettingsManager.isVpnMode()) {
                val intent = VpnService.prepare(this)
                if (intent == null) {
                    startV2Ray()
                } else {
                    requestVpnPermission.launch(intent)
              
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            } else {
                startV2Ray()
          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
        binding.btnStop.setOnClickListener {
            V2RayServiceManager.stopVService(this)
            updateConnectionStatus(false)
      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

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
                              
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}                          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}                      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}                  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}              
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            input.setNegativeButton("Cancel", null)
            input.show()
      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
        binding.btnPayment.setOnClickListener {
            Utils.openUri(this, "https://mindsparklearn.com/pricing")
      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
        binding.btnSpeedTest.setOnClickListener {
            val selectedGuid = MmkvManager.getSelectServer()
            if (selectedGuid.isNullOrEmpty()) {
                toast("No server selected")
                return@setOnClickListener
          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            lifecycleScope.launch(Dispatchers.IO) {
                val config = AngConfigManager.getProfileItem(selectedGuid)
                val host = config?.address ?: return@launch
                val port = config?.port ?: return@launch
                val ping = SpeedtestManager.tcping(host, port)
                withContext(Dispatchers.Main) {
                    if (ping >= 0) {
                        toast("Ping: ${ping}ms")
                    } else {
                        toast("Ping timeout")
                  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}              
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
        updateConnectionStatus(false)
  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
    private fun startV2Ray() {
        if (MmkvManager.getSelectServer().isNullOrEmpty()) {
            toast(R.string.title_file_chooser)
            return
      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}        V2RayServiceManager.startVService(this)
        updateConnectionStatus(true)
  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

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
      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_account_info -> {
                toast("账号信息")
                true
          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            R.id.action_version -> {
                toast("版本号: 2.0.0")
                true
          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            R.id.action_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                true
          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            R.id.action_subscription_pay -> {
                Utils.openUri(this, "https://mindsparklearn.com/pricing")
                true
          
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}            else -> super.onOptionsItemSelected(item)
      
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}  
    // ShadeLine three-node server selection
    private data class ServerNode(val name: String, val host: String, val port: Int, val id: String)
    
    private val SERVER_NODES = listOf(
        ServerNode("英国节点", "77.68.49.98", 443, ""),
        ServerNode("美国一号节点", "23.95.226.190", 443, ""),
        ServerNode("美国二号节点", "23.94.236.233", 19105, "84f6ceb1-4c97-49ae-b1f7-661dd5147e78"),
    )
    private var selectedNodeIndex = 0
    
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
    
    private fun speedTestNode(index: Int) {
        val node = SERVER_NODES[index]
        lifecycleScope.launch(Dispatchers.IO) {
            val ping = try {
                val host = java.net.InetSocketAddress(node.host, node.port)
                val sock = java.net.Socket()
                sock.connect(host, 3000)
                sock.close()
                (3000 - (sock.connectTimeout ?: 0)) 
            } catch (e: Exception) { -1 }
            withContext(Dispatchers.Main) {
                val pingText = if (ping < 0) "超时" else "${ping}ms"
                when (index) {
                    0 -> binding.tvPingUk.text = "延迟: $pingText"
                    1 -> binding.tvPingUs1.text = "延迟: $pingText"
                    2 -> binding.tvPingUs2.text = "延迟: $pingText"
                }
            }
        }
    }

}}

    // ShadeLine three-node server selection
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

}