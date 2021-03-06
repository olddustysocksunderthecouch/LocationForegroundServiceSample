package com.gdg.foreground.services.sample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationResult
import timber.log.Timber


/**
 * Created by Adrian
 */
class LocationUpdateIntentService : Service() {


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        NotificationUtil.createNotificationForForegroundLocationService(this@LocationUpdateIntentService)

        if (intent != null) {
            if (LocationAvailability.hasLocationAvailability(intent)) {
                Timber.d("isLocationAvailable = ${LocationAvailability.extractLocationAvailability(intent).isLocationAvailable}")
            }

            if (LocationResult.hasResult(intent)) {
                val result: LocationResult = LocationResult.extractResult(intent)
                Log.e("LocationUpdatService", "lastLocation = ${result.lastLocation}, locations = ${result.locations}")
                CloudFunctionsService.updateLocation(result.lastLocation)

            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}