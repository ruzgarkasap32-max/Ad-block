
package com.example.ad_block

object DnsPacket {

    fun getDomain(data: ByteArray): String? {
        if (data.size < 17) return null

        var pos = 12
        val parts = mutableListOf<String>()

        while (pos < data.size) {
            val length = data[pos].toInt() and 0xff
            pos++

            if (length == 0) break
            if (length > 63 || pos + length > data.size) return null

            parts.add(
                String(data, pos, length, Charsets.US_ASCII)
            )
            pos += length
        }

        if (parts.isEmpty()) return null
        return parts.joinToString(".").lowercase()
    }

    fun blockedResponse(query: ByteArray): ByteArray {
        if (query.size < 12) return byteArrayOf()

        val response = query.copyOf()
        response[2] = 0x81.toByte()
        response[3] = 0x83.toByte()

        for (i in 6..11) {
            response[i] = 0
        }

        return response
    }
}
