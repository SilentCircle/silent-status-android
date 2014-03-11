/*
Copyright © 2014, Silent Circle, LLC.  All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Any redistribution, use, or modification is done solely for personal 
      benefit and not for any commercial purpose or for monetary gain
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name Silent Circle nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL SILENT CIRCLE, LLC BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.silentcircle.status.content;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.silentcircle.status.R;
import com.silentcircle.status.model.LabeledValue;

public class TrackedApplication {

	private final String defaultLabel;
	private final String packageName;

	public TrackedApplication( String packageName ) {
		this( packageName, packageName );
	}

	public TrackedApplication( String packageName, String defaultLabel ) {
		this.packageName = packageName;
		this.defaultLabel = defaultLabel;
	}

	public Drawable getIcon( Context context ) {
		try {
			return context.getPackageManager().getApplicationIcon( packageName );
		} catch( NameNotFoundException exception ) {
			return context.getPackageManager().getDefaultActivityIcon();
		}
	}

	public CharSequence getLabel( Context context ) {
		try {
			PackageManager pm = context.getPackageManager();
			return pm.getApplicationLabel( pm.getApplicationInfo( packageName, 0 ) );
		} catch( NameNotFoundException exception ) {
			return defaultLabel;
		}
	}

	public LabeledValue getLabeledValue( Context context ) {
		return new LabeledValue( getIcon( context ), getLabel( context ), getStatus( context ) );
	}

	public CharSequence getStatus( Context context ) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getApplicationInfo( packageName, 0 );
			return context.getString( R.string.status_installed );
		} catch( NameNotFoundException exception ) {
			return context.getString( R.string.status_not_installed );
		}
	}

	public boolean isInstalled( Context context ) {
		try {
			PackageManager pm = context.getPackageManager();
			return pm.getApplicationInfo( packageName, 0 ) != null;
		} catch( NameNotFoundException exception ) {
			return false;
		}
	}

	public void launch( Context context ) {
		Intent intent = context.getPackageManager().getLaunchIntentForPackage( packageName );
		if( intent == null ) {
			intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=" + packageName ) );
			intent.addFlags( Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET );
		}
		try {
			context.startActivity( intent );
		} catch( ActivityNotFoundException exception ) {
			// Ignore.
		}
	}

}
