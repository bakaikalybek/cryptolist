package kg.bakai.cryptolist.ui

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kg.bakai.cryptolist.R
import kg.bakai.cryptolist.utils.NetworkManager

class MainActivity : AppCompatActivity(), NetworkManager.ConnectivityReceiverListener {
    private lateinit var receiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        receiver = NetworkManager()
        registerReceiver()
    }

    override fun onResume() {
        super.onResume()
        (receiver as NetworkManager).connectivityReceiverListener = this
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isConnected) {
            networkAlert()
        }
    }

    private fun registerReceiver() {
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun networkAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle(R.string.error_no_internet)
            setMessage(R.string.msg_connect_to_internet)
            setPositiveButton("OK", null)
        }
        dialog.show()
    }
}