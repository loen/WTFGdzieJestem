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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.WTFGdzieJestem.util.client.GoogleMapsClient;

public class WTFGdzieJestemActivity extends Activity{

	private static String PREFS_NAME = "WTF";
	private static String LONGITUDE = "Longitude";
	private static String LATTITUDE = "Lattitude";
	private static String NAME = "Name";
	private static String TAG = "WTF_Gdzie_Jestem_Location_Listener";
	private LocationManager locationManager;
	private Button homeLocationButton;
	private Button navButton;
	private EditText locationText;
	private LocationObject locationObject;
	

	@Override
	protected void onResume() {
		super.onResume();
		locationObject = getLocation();
		locationText.setText(locationObject.getLocationName());
	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		setContentView(R.layout.main);
		
		homeLocationButton = (Button)findViewById(R.id.homeLocationButton);
		homeLocationButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				initializeLocationProvider(new HomeLocationListener());
			}
		});
        navButton = (Button)findViewById(R.id.goHomeButton);
        navButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeLocationProvider(new GoHomeListener());
            }
        });
		locationText = (EditText)findViewById(R.id.locationText);
		locationObject = getLocation();
		locationText.setText(locationObject.getLocationName());
		locationText.addTextChangedListener(new TextWatcher() {
			
			
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(locationText.getText() == null || locationText.getText().length() ==0){
					navButton.setEnabled(false);
				}else{
					navButton.setEnabled(true);
				}
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});
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

	private void saveLocation(double longitude, double lattitude, String locationName){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(LONGITUDE, Double.toString(longitude));
		editor.putString(LATTITUDE, Double.toString(lattitude));
		editor.putString(NAME, locationName);
		editor.commit();
	}
	
	private LocationObject getLocation(){
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		String name = settings.getString(NAME, "");
		double longitude = Double.valueOf(settings.getString(LONGITUDE,"0"));
		double lattitude = Double.valueOf(settings.getString(LATTITUDE,"0"));
		LocationObject location = new LocationObject(longitude, lattitude, name);
		return location;
		
	}
	
	private void initializeLocationProvider(LocationListener locationListener) {
		try{
		locationManager = (LocationManager) getApplicationContext()
				.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				60000, 0, locationListener);
		}catch (Exception e) {
			Log.d(TAG, "error");
		}
	}

    private class GoHomeListener implements LocationListener {


        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                locationManager.removeUpdates(this);
                SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
                String destLatitude = preferences.getString(LATTITUDE, "0");
                String destLongitude = preferences.getString(LONGITUDE, "0");
                double srcLatitude = location.getLatitude();
                double srcLongitude = location.getLongitude();
                setIntent(GoogleMapsClient.getGoogleMapsIntent(valueOf(destLatitude), valueOf(destLongitude), srcLatitude, srcLongitude));
            }
        }
        
        private double valueOf(String string){
            return Double.valueOf(string);
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
    }

    private class HomeLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                locationManager.removeUpdates(this);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                String locationName = getHumanReadableLocation(longitude, latitude);
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(LONGITUDE, Double.toString(longitude));
                editor.putString(LATTITUDE, Double.toString(latitude));
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
    }
    
}