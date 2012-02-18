package com.WTFGdzieJestem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class WTFGdzieJestemActivity extends Activity implements
		LocationListener {

	private static String TAG = "WTF_Gdzie_Jestem_Location_Listener";
	private LocationManager locationManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			locationManager.removeUpdates(this);
			double longitude = location.getLongitude();
			double lattitude = location.getLatitude();

			DataDbAdapter dataDbAdapter = new DataDbAdapter(
					getApplicationContext());
			dataDbAdapter.open();
			String locationName = getHumanReadableLocation(longitude, lattitude);
			dataDbAdapter.saveData(locationName, longitude, lattitude);
			dataDbAdapter.close();
		}

	}

	@Override
	public void onProviderDisabled(String provider) {
		Log.d(TAG, "Provider Disabled");

	}

	@Override
	public void onProviderEnabled(String provider) {
		Log.d(TAG, "Provider Enabled");

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d(TAG, "Status Changed");

	}

	private String getHumanReadableLocation(double longitude, double lattitude) {
		Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        
		try {
			List<Address> addresses = geocoder.getFromLocation(longitude,
					lattitude, 1);

			if (addresses != null) {
				Address returnedAddress = addresses.get(0);
				StringBuilder strReturnedAddress = new StringBuilder(
						"Address:\n");
				for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
					strReturnedAddress
							.append(returnedAddress.getAddressLine(i)).append(
									"\n");
				}
				return strReturnedAddress.toString();
			} else {
				return  "Lon:" + longitude + " " + "Lat:" + lattitude;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return  "Lon:" + longitude + " " + "Lat:" + lattitude;
		}
	}

	private void initializeLocationProvider() {
		locationManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				60000, 0, this);
	}
}