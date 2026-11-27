
package com.example.ad_block

import android.content.Intent
import android.net.VpnService
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "ad_block/vpn"
    private var pendingResult: MethodChannel.Result? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->

            when (call.method) {
                "startVpn" -> {
                    val intent = VpnService.prepare(this)

                    if (intent != null) {
                        pendingResult = result
                        startActivityForResult(intent, 100)
                    } else {
                        result.success(false)
                    }
                }

                "stopVpn" -> {
                    val serviceIntent = Intent(this, AdBlockVpnService::class.java)
                    serviceIntent.action = "STOP"
                    startService(serviceIntent)
                    result.success(true)
                }

                else -> result.notImplemented()
            }
        }
    }

    @Deprecated("Deprecated in Android")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            pendingResult?.success(false)
            pendingResult = null
        }
    }
}
