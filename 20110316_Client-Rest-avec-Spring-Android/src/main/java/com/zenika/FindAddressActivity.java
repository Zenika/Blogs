package com.zenika;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FindAddressActivity extends Activity implements OnClickListener {

	private static final String URL = "http://maps.googleapis.com/maps/api/geocode/json?address={address}&sensor=false";
	private static final String TAG = "SpringAndroidPoc";
	
	private static final String OK = "OK";
	private static final String NO_RESULTS = "ZERO_RESULTS";

	private EditText address;
	private Button find;
	private ArrayAdapter<Address>	adapter;
	private RestTemplate restTemplate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		find = (Button) findViewById(R.id.find);
		address = (EditText) findViewById(R.id.adress);
		ListView listView = (ListView) findViewById(R.id.results);
		adapter = new ArrayAdapter<Address>(getApplicationContext(), android.R.layout.simple_list_item_1);
		listView.setAdapter(adapter);
		find.setOnClickListener(this);
	}

	public void onClick(View view) {
		if(view.getId() == R.id.find)
		{
			CharSequence csAddress = address.getText();
			if(!TextUtils.isEmpty(csAddress))
			{
				new GetRestTask().execute(csAddress);
			}
		}
	}
	
	private class GetRestTask extends AsyncTask<CharSequence, Void, Addresses>
	{
		
		@Override
		protected void onPreExecute() {
			find.setEnabled(false);
		}

		@Override
		protected Addresses doInBackground(CharSequence... address) {
			if(address != null && address.length > 0)
			{
				try
				{
					return getRestTemplate().getForObject(URL, Addresses.class, address[0]);
				}
				catch (RestClientException e) {
					return null;
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Addresses addresses) {
			adapter.clear();
			if(addresses == null || addresses.status.equals(NO_RESULTS))
			{
				Toast.makeText(getApplicationContext(), "Non trouvée ou erreur", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Log.d(TAG, "onPostExecute : "+addresses.status);
				for(Address addr : addresses.results)
				{
					adapter.add(addr);
				}
			}
			find.setEnabled(true);
		}
		
		private RestTemplate getRestTemplate() {
			if(restTemplate == null)
			{
				restTemplate = new RestTemplate();
				MappingJacksonHttpMessageConverter jsonConverter = new MappingJacksonHttpMessageConverter();
				List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
				supportedMediaTypes.add(MediaType.APPLICATION_JSON);
				jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
				List<HttpMessageConverter<?>> listHttpMessageConverters = restTemplate.getMessageConverters();
				listHttpMessageConverters.add(jsonConverter);
				restTemplate.setMessageConverters(listHttpMessageConverters);
			}
	        return restTemplate;
	    }
	}
	
	public static class Addresses
	{
		public String status;
		public Address[] results;
	}
	
	@JsonIgnoreProperties({"address_components", "types", "geometry", "partial_match"})
	public static class Address
	{
		public String formatted_address;
		
		@Override
		public String toString() {
			return formatted_address;
		}
	}
}

