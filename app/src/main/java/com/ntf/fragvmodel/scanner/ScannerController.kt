package com.ntf.fragvmodel.scanner

import android.content.Context
import android.os.Build
import android.util.Log
import com.ntf.fragvmodel.scanner.ScannerBrand.*


internal enum class ScannerBrand {
    HONEYWELL, ZEBRA, UNKNOW
}

class ScannerController(context: Context, listener: ScannerListener) {
    val TAG: String = ScannerController::class.java.simpleName

    private var scannerListener: ScannerListener? = null
    private var zebraScanner: ZebraScanner? = null
    private var honeywellScanner: HoneywellScanner? = null

    private var scannerBrand: ScannerBrand? = null

    init {
        Log.d(TAG, "CREATE SCANNING SERVICE")
        setManuFacturer()
        scannerListener = listener
        if (scannerBrand === HONEYWELL) {
            honeywellScanner = HoneywellScanner(context, scannerListener!!)
        } else if (scannerBrand === ZEBRA) {
            zebraScanner = ZebraScanner(context, scannerListener!!)
        }
    }


    fun resume() {
        Log.d(TAG, "REMOVE BARCODE LISTENER")
        if (scannerBrand === HONEYWELL) {
            honeywellScanner?.resume()
        } else if (scannerBrand === ZEBRA) {
            zebraScanner?.resume()
        }
    }

    fun pause() {
        Log.d(TAG, "REGISTER BARCODE LISTENER")
        if (scannerBrand === HONEYWELL) {
            honeywellScanner?.pause()
        } else if (scannerBrand === ZEBRA) {
            zebraScanner?.pause()
        }
    }

    fun close() {
        Log.d(TAG, "CLOSE BARCODE LISTENER")
        if (scannerBrand === HONEYWELL) {
            honeywellScanner?.close()
        } else if (scannerBrand === ZEBRA) {
            zebraScanner?.close()
        }
        honeywellScanner = null
        zebraScanner = null
        scannerListener = null
    }

    private fun setManuFacturer() {
        var brand = getDeviceName()
        brand = brand?.uppercase() ?: ""
        if (brand.contains("ZEBRA")) {
            scannerBrand = ZEBRA
        } else if (brand.contains("HONEYWELL")) {
            scannerBrand = HONEYWELL
        } else {
            scannerBrand = UNKNOW
        }
    }

    private fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.startsWith(manufacturer)) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }
}