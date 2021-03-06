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

package com.silentcircle.status.widget;

import java.util.Arrays;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListAdapter<T> extends BaseAdapter {

	private final List<T> items;
	private final int layoutResourceID;

	public ListAdapter( List<T> items, int layoutResourceID ) {
		this.items = items;
		this.layoutResourceID = layoutResourceID;
	}

	public ListAdapter( T [] items, int layoutResourceID ) {
		this( Arrays.asList( items ), layoutResourceID );
	}

	@Override
	public int getCount() {
		return items != null ? items.size() : 0;
	}

	@Override
	public Object getItem( int position ) {
		return items != null ? items.get( position % items.size() ) : null;
	}

	@Override
	public long getItemId( int position ) {
		Object item = getItem( position );
		return item != null ? item.hashCode() : 0;
	}

	/**
	 * By default, always returns the value of the {@link ListAdapter#layoutResourceID} field.
	 * Override this method to provide alternate layout resourceIDs.
	 * 
	 * @param position
	 * @param item
	 * @return the layout resource ID to use for the object at this position.
	 */
	protected int getLayoutResourceID( int position, Object item ) {
		return layoutResourceID;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		Object item = getItem( position );
		View view = convertView;
		if( view == null ) {
			view = LayoutInflater.from( parent.getContext() ).inflate( getLayoutResourceID( position, item ), parent, false );
		}
		view.setTag( item );
		return view;
	}

}
