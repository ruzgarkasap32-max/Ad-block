
package com.example.ad_block

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import android.os.Build
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "ad_block/vpn"
    private val VPN_REQUEST_CODE = 100
    private var pendingResult: MethodChannel.Result? = null

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(
            flutterEngine.dartExecutor.binaryMessenger,
            CHANNEL
        ).setMethodCallHandler { call, result ->

            when (call.method) {
                "startVpn" -> {
                    val permissionIntent = VpnService.prepare(this)

                    if (permissionIntent != null) {
                        pendingResult = result
                        startActivityForResult(
                            permissionIntent,
                            VPN_REQUEST_CODE
                        )
                    } else {
                        startVpnService(result)
                    }
                }

                "stopVpn" -> {
                    val intent = Intent(
                        this,
                        AdBlockVpnService::class.java
                    )
                    intent.action = "STOP"
                    startService(intent)
                    result.success(true)
                }

                else -> result.notImplemented()
            }
        }
    }

    private fun startVpnService(result: MethodChannel.Result) {
        val intent = Intent(
            this,
            AdBlockVpnService::class.java
        )
        intent.action = "START"

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }

            result.success(true)
        } catch (e: Exception) {
            result.success(false)
        }
    }

    @Deprecated("Deprecated in Android")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == VPN_REQUEST_CODE) {
            val result = pendingResult
            pendingResult = null

            if (resultCode == Activity.RESULT_OK && result != null) {
                startVpnService(result)
            } else {
                result?.success(false)
            }
        }
    }
}
