package com.example.ad_block

class DnsBlocker {

    private val blockedDomains = setOf(
        "doubleclick.net",
        "googlesyndication.com",
        "googleadservices.com",
        "adservice.google.com",
        "adnxs.com",
        "popads.net",
        "popcash.net"
    )

    fun shouldBlock(host: String): Boolean {
        val domain = host.lowercase().trimEnd('.')

        return blockedDomains.any {
            domain == it || domain.endsWith(".$it")
        }
    }
}