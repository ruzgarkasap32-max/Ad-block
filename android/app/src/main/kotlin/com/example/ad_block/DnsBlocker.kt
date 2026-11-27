
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
        " تبلیغات.ir"
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
