package com.WTFGdzieJestem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WTFGdzieJestemActivity extends Activity implements
		LocationListener {

	private static String PREFS_NAME = "WTF";
	private static String LONGITUDE = "Longitude";
	private static String LATTITUDE = "Lattitude";
	private static String NAME = "Name";
	private static String TAG = "WTF_Gdzie_Jestem_Location_Listener";
	private LocationManager locationManager;
	private Button homeLocationButton;
	private EditText locationText;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.main);
		
		homeLocationButton = (Button)findViewById(R.id.homeLocationButton);
		homeLocationButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				initializeLocationProvider();

			}
		});

		locationText = (EditText)findViewById(R.id.locationText);
		
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			locationManager.removeUpdates(this);
			double longitude = location.getLongitude();
			double lattitude = location.getLatitude();

			
			String locationName = getHumanReadableLocation(longitude, lattitude);
			SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(LONGITUDE, Double.toString(longitude));
			editor.putString(LATTITUDE, Double.toString(lattitude));
			editor.putString(NAME, locationName);
			editor.commit();
			locationText.setText(locationName);
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
			List<Address> addresses = geocoder.getFromLocation(lattitude,
					longitude,1);

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
		try{
		locationManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				60000, 0, this);
		}catch (Exception e) {
			Log.d(TAG, "error");
		}
	}
}