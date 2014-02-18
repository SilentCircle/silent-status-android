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

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.silentcircle.status.R;
import com.silentcircle.status.content.TrackedApplication;
import com.silentcircle.status.model.LabeledValue;

public class LabeledValueView extends LinearLayout {

	static class Views {

		public final ImageView icon;
		public final TextView label;
		public final TextView value;

		public Views( LabeledValueView parent ) {
			icon = (ImageView) parent.findViewById( R.id.icon );
			label = (TextView) parent.findViewById( R.id.label );
			value = (TextView) parent.findViewById( R.id.value );
		}

	}

	private Views views;

	protected Views getViews() {
		if( views == null ) {
			views = new Views( this );
		}
		return views;
	}

	public static LabeledValueView create( Context context ) {
		LayoutInflater inflater = LayoutInflater.from( context );
		return (LabeledValueView) inflater.inflate( R.layout.labeled_value, null, false );
	}

	public LabeledValueView( Context context ) {
		super( context );
	}

	public LabeledValueView( Context context, AttributeSet attrs ) {
		super( context, attrs );
	}

	@TargetApi( Build.VERSION_CODES.HONEYCOMB )
	public LabeledValueView( Context context, AttributeSet attrs, int defStyle ) {
		super( context, attrs, defStyle );
	}

	public void setLabel( CharSequence label ) {
		Views v = getViews();
		v.label.setText( label );
		v.icon.setContentDescription( label );
	}

	public void setValue( CharSequence value ) {
		getViews().value.setText( value );
	}

	public void setIcon( Drawable icon ) {
		getViews().icon.setImageDrawable( icon );
	}

	@Override
	public void setTag( Object tag ) {
		super.setTag( tag );
		if( tag instanceof LabeledValue ) {
			setLabeledValue( (LabeledValue) tag );
		}
		if( tag instanceof TrackedApplication ) {
			setTrackedApplication( (TrackedApplication) tag );
		}
	}

	public void setTrackedApplication( TrackedApplication trackedApplication ) {
		if( trackedApplication != null ) {
			setLabeledValue( trackedApplication.getLabeledValue( getContext() ) );
		}
	}

	public void setLabeledValue( LabeledValue labeledValue ) {
		if( labeledValue != null ) {
			setIcon( labeledValue.icon );
			setLabel( labeledValue.label );
			setValue( labeledValue.value );
		}
	}

}
