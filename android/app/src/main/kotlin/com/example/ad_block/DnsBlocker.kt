
package com.example.ad_block

class DnsBlocker {

    private val blockedDomains = setOf(
        "doubleclick.net",
        "googlesyndication.com",
        "googleadservices.com",
        "adservice.google.com",
        "adnxs.com",
        "popads.net",
        "popcash.net",
        "adcolony.com",
        "admob.com",
        "applovin.com",
        "unity3d.com",
        "unityads.unity3d.com",
        "imasdk.googleapis.com",
        "ads.yahoo.com",
        "taboola.com",
        "outbrain.com"
    )

    fun shouldBlock(host: String): Boolean {
        val domain = host
            .lowercase()
            .trim()
            .trimEnd('.')

        if (domain.isEmpty()) return false

        return blockedDomains.any { blocked ->
            domain == blocked || domain.endsWith(".$blocked")
        }
    }
}
