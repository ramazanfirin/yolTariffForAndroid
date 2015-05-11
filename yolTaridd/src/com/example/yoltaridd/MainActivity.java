package com.example.yoltaridd;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnItemSelectedListener{

	KeyValueDTO[] ilceList =new  KeyValueDTO[0] ;
	KeyValueDTO[] mahalleList =new  KeyValueDTO[0] ;
	KeyValueDTO[] sokakList =new  KeyValueDTO[0] ;
	KeyValueDTO[] binaList =new  KeyValueDTO[0] ;
	
	
	Spinner ilceSpinner;
	Spinner mahalleSpinner;
	Spinner sokakSpinner;
	Spinner binaSpinner;
	Button showCoordinate;
	
	String destinationLat;
	String destinationLng;
	String binaId="";
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new IlceTask().execute();
		
		ilceSpinner = (Spinner) findViewById(R.id.ilceSpinner);
		mahalleSpinner = (Spinner) findViewById(R.id.mahalleSpinner);
		sokakSpinner = (Spinner) findViewById(R.id.sokakSpinner);
		binaSpinner = (Spinner) findViewById(R.id.binaSpinner);
		showCoordinate = (Button)findViewById(R.id.buttonGoster);
//		ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), android.R.layout.simple_spinner_item,ilceList);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		ilceSpinner.setAdapter(adapter);
		
		ilceSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {
			 System.out.println("geldi");
			 if (pos==0)
				 return ;
			 KeyValueDTO dto = (KeyValueDTO)parent.getItemAtPosition(pos);
			 new MahalleTask().execute(dto.getKey());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			});
		
		mahalleSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {
			 System.out.println("geldi");
			 if (pos==0)
				 return ;
			 KeyValueDTO dto = (KeyValueDTO)parent.getItemAtPosition(pos);
			 new SokakTask().execute(dto.getKey());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			});
		
		sokakSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {
			 System.out.println("geldi");
			 if (pos==0)
				 return ;
			 KeyValueDTO dto = (KeyValueDTO)parent.getItemAtPosition(pos);
			 new BinaTask().execute(dto.getKey());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			});
		
		binaSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view,
			int pos, long id) {
			 System.out.println("geldi");
			 if (pos==0)
				 return ;
			 KeyValueDTO dto = (KeyValueDTO)parent.getItemAtPosition(pos);
			 //new SokakTask().execute(dto.getKey());
			 binaId = dto.getKey();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
			});
		
		
		
		showCoordinate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if("Seciniz".equals(binaId) || binaId == null || binaId.equals(""))
					return;
				new GetCoordinateTask().execute(binaId.toString());
				
				
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
	      return Util.getIlceListAsString();
	   }

	    @Override
	    protected void onPostExecute(String result) {
	     // textView.setText(result);
	    	List<KeyValueDTO> resultList = Util.convert(result);
	    	ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), R.layout.spinner_item,Util.convertToArray(resultList));
			//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
			
			ilceSpinner.setAdapter(adapter);
	    }
	}
	
	private class MahalleTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	    	 return Util.getMahalleListAsString(urls[0]);
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	List<KeyValueDTO> resultList = Util.convert(result);
	    	ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), R.layout.spinner_item,Util.convertToArray(resultList));
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			mahalleSpinner.setAdapter(adapter);
	    }
	}
	
	private class SokakTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	    	return Util.getSokakListAsString(urls[0]);
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	List<KeyValueDTO> resultList = Util.convert(result);
	    	ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), R.layout.spinner_item,Util.convertToArray(resultList));
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			sokakSpinner.setAdapter(adapter);
	    	
	    }
	}
	
	private class BinaTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	    	return Util.getBinaListAsString(urls[0]);
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	List<KeyValueDTO> resultList = Util.convert(result);
	    	ArrayAdapter<KeyValueDTO> adapter = new ArrayAdapter<KeyValueDTO>(getApplicationContext(), R.layout.spinner_item,Util.convertToArray(resultList));
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			binaSpinner.setAdapter(adapter);
	    	
	    }
	}
	
	private class GetCoordinateTask extends AsyncTask<String, Void, String> {
	    @Override
	    protected String doInBackground(String... urls) {
	    	return Util.getCoordinatesAsString(urls[0]);
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	destinationLat ="";
	    	destinationLng="";
	    	
	    	JSONArray reader;
			try {
				reader = new JSONArray(result);
				for (int i = 0; i < reader.length(); i++) {
					JSONObject jsonObject = (JSONObject)reader.get(i);jsonObject.get("key");
					if("lat".equals(jsonObject.get("key")))
							destinationLat = jsonObject.getString("value");
					
					if("lng".equals(jsonObject.get("key")))
						destinationLng = jsonObject.getString("value");

					//resultList.add(new KeyValueDTO((String)jsonObject.get("value"), (String)jsonObject.get("key")));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	
	    	String uri2 ="route66://?daddr="+destinationLat+","+destinationLng;
		   	   Toast.makeText(getApplicationContext(),uri2, Toast.LENGTH_LONG).show();
		   	
		   		Intent newIntent = new Intent(Intent.ACTION_VIEW);
		   		newIntent.setData(Uri.parse(uri2));
		   		newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   		getApplicationContext().startActivity(newIntent);
	    	
	    }
	}
	
}
