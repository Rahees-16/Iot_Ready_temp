package com.example.iotrea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {
    private WifiManager mWifiManager;
    private NsdManager mNsdManager;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mNsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);
       t1= (TextView)findViewById(R.id.text1);

        WifiManager.MulticastLock lock = mWifiManager.createMulticastLock("lock");
        lock.acquire();

        mNsdManager.discoverServices(
                "_services._dns-sd._udp.", NsdManager.PROTOCOL_DNS_SD, mDiscoveryListener);



    }
    private NsdManager.DiscoveryListener mDiscoveryListener = new NsdManager.DiscoveryListener() {

        @Override
        public void onDiscoveryStarted(String regType) {
            // Discovery has started.
        }

        @Override
        public void onServiceFound(NsdServiceInfo service) {
            // A device has been found.
            mNsdManager.resolveService(service, mResolveListener);
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            // A device has been lost.
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            // Discovery has stopped.
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            // Discovery has failed.
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            // Discovery has failed.
        }
    };
    private NsdManager.ResolveListener mResolveListener = new NsdManager.ResolveListener() {

        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            // The resolve failed.
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            // The resolve succeeded.
            InetAddress deviceAddress = serviceInfo.getHost();
            Log.d("NSD", "Service lost: " + serviceInfo.getServiceName());
            // Do something with the device's IP address

        }
    };
}