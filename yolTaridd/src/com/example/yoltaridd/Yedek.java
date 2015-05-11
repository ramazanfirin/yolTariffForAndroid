package com.example.yoltaridd;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Yedek extends ActionBarActivity implements OnItemSelectedListener{

	KeyValueDTO[] ilceList =new  KeyValueDTO[0] ;
	KeyValueDTO[] mahalleList =new  KeyValueDTO[0] ;
	KeyValueDTO[] sokakList =new  KeyValueDTO[0] ;
	KeyValueDTO[] binaList =new  KeyValueDTO[0] ;
	
	
	Spinner ilceSpinner;
	Spinner mahalleSpinner;
	Spinner sokakSpinner;
	Spinner binaSpinner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new IlceTask().execute();
		
		ilceSpinner = (Spinner) findViewById(R.id.ilceSpinner);
		mahalleSpinner = (Spinner) findViewById(R.id.mahalleSpinner);
		
		
		ilceSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {
			 System.out.println("geldi");
			 KeyValueDTO dto = ilceList[pos];
			 new MahalleTask().execute(dto.getKey());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			});
		
		mahalleSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {
			 System.out.println("geldi");
			 KeyValueDTO dto = ilceList[pos];
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			});
		
		
	}
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	private class IlceTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	      String response = "";
	      String url = "http://192.168.1.22:8080/yoltariffserver/rest/hello/getIlceList";
	       DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	        try {
	          HttpResponse execute = client.execute(httpGet);
	          InputStream content = execute.getEntity().getContent();

	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          String s = "";
	          while ((s = buffer.readLine()) != null) {
	            response += s;
	          }

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	     
	      return response;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	     // textView.setText(result);
	    	List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
	    	try {
				//JSONObject reader = new JSONObject(result);
				JSONArray reader = new JSONArray(result);
				for (int i = 0; i < reader.length(); i++) {
					JSONObject jsonObject = (JSONObject)reader.get(i);
					resultList.add(new KeyValueDTO((String)jsonObject.get("value"), (String)jsonObject.get("key")));
				}
				//ilceList  = (KeyValueDTO[])resultList.toArray();
				ilceList = resultList.toArray(new KeyValueDTO[resultList.size()]);
				System.out.println("bitti");
				ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), android.R.layout.simple_spinner_item,ilceList);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				ilceSpinner.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	private class MahalleTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	      String response = "";
	      String url = "http://192.168.1.22:8080/yoltariffserver/rest/hello/getMahalleList/"+urls[0];
	       DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	        try {
	          HttpResponse execute = client.execute(httpGet);
	          InputStream content = execute.getEntity().getContent();

	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          String s = "";
	          while ((s = buffer.readLine()) != null) {
	            response += s;
	          }

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	     
	      return response;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	     // textView.setText(result);
	    	List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
	    	try {
				//JSONObject reader = new JSONObject(result);
				JSONArray reader = new JSONArray(result);
				for (int i = 0; i < reader.length(); i++) {
					JSONObject jsonObject = (JSONObject)reader.get(i);
					resultList.add(new KeyValueDTO((String)jsonObject.get("value"), (String)jsonObject.get("key")));
				}
				//ilceList  = (KeyValueDTO[])resultList.toArray();
				mahalleList = resultList.toArray(new KeyValueDTO[resultList.size()]);
				System.out.println("bitti");
				ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), android.R.layout.simple_spinner_item,mahalleList);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mahalleSpinner.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	private class SokakTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	      String response = "";
	      String url = "http://192.168.1.22:8080/yoltariffserver/rest/hello/getSokakList/"+urls[0];
	       DefaultHttpClient client = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	        try {
	          HttpResponse execute = client.execute(httpGet);
	          InputStream content = execute.getEntity().getContent();

	          BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
	          String s = "";
	          while ((s = buffer.readLine()) != null) {
	            response += s;
	          }

	        } catch (Exception e) {
	          e.printStackTrace();
	        }
	     
	      return response;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	     // textView.setText(result);
	    	List<KeyValueDTO> resultList = new ArrayList<KeyValueDTO>();
	    	try {
				//JSONObject reader = new JSONObject(result);
				JSONArray reader = new JSONArray(result);
				for (int i = 0; i < reader.length(); i++) {
					JSONObject jsonObject = (JSONObject)reader.get(i);
					resultList.add(new KeyValueDTO((String)jsonObject.get("value"), (String)jsonObject.get("key")));
				}
				//ilceList  = (KeyValueDTO[])resultList.toArray();
				sokakList = resultList.toArray(new KeyValueDTO[resultList.size()]);
				System.out.println("bitti");
				ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), android.R.layout.simple_spinner_item,sokakList);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sokakSpinner.setAdapter(adapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}

