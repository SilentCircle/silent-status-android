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

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.silentcircle.status.R;

public class SilentText extends TrackedApplication {

	public static final int STATUS_UNKNOWN = -1;
	public static final int STATUS_INSTALLED = 0;
	public static final int STATUS_UNLOCKED = 1;
	public static final int STATUS_AUTHENTICATED = 2;
	public static final int STATUS_ONLINE = 3;

	public static final String PACKAGE_NAME = "com.silentcircle.silenttext";
	public static final Uri CONTENT_URI = Uri.parse( "content://" + PACKAGE_NAME + "/status" );

	private static CharSequence labelFor( Context context, int status ) {
		switch( status ) {
			case STATUS_UNKNOWN:
				return context.getString( R.string.status_not_installed );
			case STATUS_INSTALLED:
				return context.getString( R.string.status_installed );
			case STATUS_UNLOCKED:
				return context.getString( R.string.status_unlocked );
			case STATUS_AUTHENTICATED:
				return context.getString( R.string.status_authenticated );
			case STATUS_ONLINE:
				return context.getString( R.string.status_online );
		}
		return context.getString( R.string.status_unknown );
	}

	public SilentText() {
		super( PACKAGE_NAME );
	}

	@Override
	public CharSequence getStatus( Context context ) {

		if( !isInstalled( context ) ) {
			return labelFor( context, STATUS_UNKNOWN );
		}

		try {

			ContentResolver resolver = context.getContentResolver();
			Cursor cursor = resolver.query( CONTENT_URI, null, null, null, null );

			if( cursor == null ) {
				throw new NullPointerException( "The Silent Text status query returned a null cursor. Silent Text may not be installed." );
			}

			int status = -1;

			try {

				if( !cursor.moveToNext() ) {
					throw new IllegalStateException( "The Silent Text status query returned an empty cursor. This violates the status API contract and should be considered a bug." );
				}

				int column = cursor.getColumnIndex( "status" );

				if( column < 0 ) {
					throw new IllegalStateException( "The Silent Text status query returned a cursor without a 'status' column. This violates the status API contract and should be considered a bug." );
				}

				status = cursor.getInt( column );

			} finally {
				cursor.close();
			}

			return labelFor( context, status );

		} catch( Throwable exception ) {
			return labelFor( context, STATUS_UNKNOWN );
		}

	}

}
