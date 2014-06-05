package com.example.petfiles;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class CareShareMainFragment extends Fragment implements ConnectionCallbacks, OnConnectionFailedListener{

	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;
	// Logcat tag
	private static final String TAG = "MainActivity";
	
	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/* A flag indicating that a PendingIntent is in progress and prevents
	* us from starting further intents.
	*/
	private boolean mIntentInProgress;
	
	private ConnectionResult mConnectionResult;
	
	private SignInButton btnSignIn;
	
	public CareShareMainFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_main_care, container, false);
        Activity now = getActivity();
        mGoogleApiClient = new GoogleApiClient.Builder(now)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(Plus.API)
        .addScope(Plus.SCOPE_PLUS_LOGIN)
        .build();
        
        return rootView;
    }
	
	public void onConnected(Bundle connectionHint) {
		
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), getActivity(),
					0).show();
			return;
		}
	}
	
	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}
}
