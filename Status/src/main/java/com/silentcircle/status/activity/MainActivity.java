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

package com.silentcircle.status.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.silentcircle.status.R;
import com.silentcircle.status.content.SilentContacts;
import com.silentcircle.status.content.SilentPhone;
import com.silentcircle.status.content.SilentText;
import com.silentcircle.status.content.TrackedApplication;
import com.silentcircle.status.widget.ListAdapter;

public class MainActivity extends Activity implements OnItemClickListener {

	@Override
	protected void onCreate( Bundle savedInstanceState ) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
	}

	@Override
	public void setContentView( int layoutResID ) {
		setContentView( LayoutInflater.from( this ).inflate( layoutResID, null, false ) );
	}

	@Override
	public void setContentView( View view ) {
		super.setContentView( view );
		onInflateContentView( view );
	}

	@Override
	public void setContentView( View view, LayoutParams params ) {
		super.setContentView( view, params );
		onInflateContentView( view );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu ) {
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

	private ListView contentView;

	public void onInflateContentView( View view ) {
		if( view instanceof ListView ) {
			contentView = (ListView) view;
			contentView.setOnItemClickListener( this );
		}
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ) {
		int id = item.getItemId();
		if( R.id.action_refresh == id ) {
			refresh();
			return true;
		}
		return super.onOptionsItemSelected( item );
	}

	@Override
	protected void onResume() {
		super.onResume();
		refresh();
	}

	public void refresh() {
		if( contentView != null ) {
			contentView.setAdapter( new ListAdapter<TrackedApplication>( new TrackedApplication [] {
				new SilentContacts(),
				new SilentPhone(),
				new SilentText()
			}, R.layout.labeled_value ) );
		}
	}

	@Override
	public void onItemClick( AdapterView<?> parent, View view, int position, long itemID ) {
		refresh();
		( (TrackedApplication) parent.getAdapter().getItem( position ) ).launch( this );
	}

}
