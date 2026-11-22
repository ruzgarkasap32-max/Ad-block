import 'package:flutter/services.dart';

class VpnService {
  static const MethodChannel _channel =
      MethodChannel('ad_block/vpn');

  static Future<bool> start() async {
    try {
      final result = await _channel.invokeMethod<bool>('startVpn');
      return result ?? false;
    } on PlatformException {
      return false;
    }
  }

  static Future<bool> stop() async {
    try {
      final result = await _channel.invokeMethod<bool>('stopVpn');
      return result ?? false;
    } on PlatformException {
      return false;
    }
  }
}
