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

        return parts.joinToString(".").lowercase()
    }

    fun blockedResponse(query: ByteArray): ByteArray {
        val end = query.copyOfRange(0, query.size)

        end[2] = 0x81.toByte()
        end[3] = 0x83.toByte()

        end[6] = 0
        end[7] = 0
        end[8] = 0
        end[9] = 0
        end[10] = 0
        end[11] = 0

        return end
    }
}