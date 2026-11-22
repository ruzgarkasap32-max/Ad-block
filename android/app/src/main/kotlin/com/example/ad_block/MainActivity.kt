package com.example.ad_block

import android.content.Intent
import android.net.VpnService
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "ad_block/vpn"

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
                        startActivityForResult(intent, 100)
                        result.success(false)
                    } else {
                        result.success(false)
                    }
                }

                "stopVpn" -> {
                    result.success(false)
                }

                else -> result.notImplemented()
            }
        }
    }
}
