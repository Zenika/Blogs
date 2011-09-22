
package com.zenika.signature.signature;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.zenika.R;
import com.zenika.signature.db.Signature;


/**
 * Demonstrates the handling of touch screen and trackball events to
 * implement a simple painting app.
 */
public class SignatureActivity extends Activity {
	
	public static final String TAG = SignatureActivity.class.getSimpleName() + " - ";
	
	private SignatureView mSignatureView;
    
    @Override 
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signature);
        mSignatureView = (SignatureView) findViewById(R.id.signature_view);
    }
    
    public void clear(View view)
    {
    	mSignatureView.clear();
    }
    
    public void validate(View view)
    {
    	// get Bitmap from SignatureView 
    	Bitmap b = mSignatureView.save();
    	// calculate the number of pixels
    	int pixels = b.getHeight() * b.getRowBytes();
    	// instanciate an OutputStream to handle the pixels
    	ByteArrayOutputStream baos = new ByteArrayOutputStream(pixels);
    	// we record it as a PNG (lossless format)
    	b.compress(CompressFormat.PNG, 0, baos);
    	// we get the bytes array
    	byte[] bytes = baos.toByteArray();
    	// here we delete the previous recorded signature
    	getContentResolver().delete(Signature.CONTENT_URI, null, null);
    	// ContentValues is a wrapper class used to insert in db
    	ContentValues cv = new ContentValues(2);
    	cv.put(Signature.SIGNATURE, bytes);
    	cv.put(Signature.DATE, new Date().toLocaleString());
    	// here we insert in database, and we get the uri of the image
    	Uri uri = getContentResolver().insert(Signature.CONTENT_URI, cv);
    	setResult(RESULT_OK, new Intent().setData(uri));
    	finish();
    }
    
}
