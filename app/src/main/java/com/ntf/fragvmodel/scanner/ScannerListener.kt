package com.ntf.fragvmodel.scanner

import java.util.*


interface ScannerListener : EventListener {
    fun onDataEvent(data: String?)
}
