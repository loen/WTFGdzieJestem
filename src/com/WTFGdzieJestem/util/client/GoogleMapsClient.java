package com.WTFGdzieJestem.util.client;


import android.content.Intent;
import android.net.Uri;

public class GoogleMapsClient {

    public static Intent getGoogleMapsIntent(double srcLatitude, double srcLongitude,
                                             double dstLatitude, double dstLongitude) {
        /*Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                   Uri.parse("http://maps.google.com/maps?saddr="+srcLatitude+","+ srcLongitude +
                                           "&daddr="+dstLatitude+","+dstLongitude));
                                           */
        // google.navigation:q=
    	Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + dstLatitude + "," + dstLongitude));
    	
        return intent;
    }
}
