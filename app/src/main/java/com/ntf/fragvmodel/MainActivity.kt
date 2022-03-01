package com.ntf.fragvmodel

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ntf.fragvmodel.scanner.ScannerController
import com.ntf.fragvmodel.scanner.ScannerListener
import com.ntf.fragvmodel.ui.main.MainFragment


class MainActivity : AppCompatActivity(), ScannerListener {

    private val TAG = MainActivity::class.java.name

    private var mScanController: ScannerController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        mScanController = ScannerController(this@MainActivity, this)

    }

    override fun onDataEvent(data: String?) {
        Log.d("BARCODE", "onBarcodeEvent: data = $data")
    }

    override fun onResume() {
        super.onResume()
        mScanController?.resume()
    }

    override fun onPause() {
        super.onPause()
        mScanController?.pause()
    }

    override fun onDestroy() {
        mScanController?.close()
        super.onDestroy()
    }
}