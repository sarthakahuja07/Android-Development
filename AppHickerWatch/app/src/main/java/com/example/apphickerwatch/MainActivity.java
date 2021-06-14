package com.example.apphickerwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;
    String address;
    TextView longitude;
    TextView latitude;
    TextView altitude;
    TextView addressText;



    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);
                }
            }
        }

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        longitude=findViewById(R.id.editText1);
        latitude=findViewById(R.id.editText2);
        altitude=findViewById(R.id.editText3);
        addressText=findViewById(R.id.editText4);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

                try {
                    List<Address> addressList=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    if(addressList!=null && addressList.size()>0) {
                        Log.i("i",addressList.get(0).toString());
                        String address="";
                        if(addressList.get(0).getFeatureName()!=null){
                            address+=addressList.get(0).getFeatureName()+" ";
                        }
                        if(addressList.get(0).getLocality()!=null){
                            address+=addressList.get(0).getLocality()+" ";
                        }
                        if(addressList.get(0).getPostalCode()!=null){
                            address+=addressList.get(0).getPostalCode()+" ";
                        }if(addressList.get(0).getAdminArea()!=null){
                            address+=addressList.get(0).getAdminArea()+" ";
                        }
                        addressText.setText("Address : "+address);

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                latitude.setText("Latitude : "+String.valueOf(location.getLatitude()));
                longitude.setText("Longitude : "+String.valueOf(location.getLongitude()));
                altitude.setText("Altitude : "+String.valueOf(location.getAltitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (Build.VERSION.SDK_INT < 29) {


            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 10, locationListener);

        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

            }else{
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,10,10,locationListener);

            }
        }
    }
}
