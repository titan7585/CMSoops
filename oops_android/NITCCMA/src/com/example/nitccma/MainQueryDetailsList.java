package com.example.nitccma;

import com.example.nitccma.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainQueryDetailsList extends ListActivity {

	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String url = "http://allstuffcodes.info/classmanagement/all_student.php";

	// JSON Node names
	//private static final String TAG_CONTACTS = "contacts";
	private static final String TAG_STUDENTS = "students";
	private static final String TAG_ROLL = "Roll";
	private static final String TAG_FNAME = "Firstname";
	private static final String TAG_LNAME = "Lastname";
	private static final String TAG_NAME = "Name";
	private static final String TAG_EMAIL = "Email";
	private static final String TAG_BRANCH = "Branch";
	private static final String TAG_YEAR = "Year";	
	private static final String TAG_MOBILE = "Mobile";
	//private static final String TAG_INDEX = "1";
	//private static final String TAG_PHONE_OFFICE = "office";
	
	// contacts JSONArray
	JSONArray students = null;

	// Hashmap for ListView
	JSONParser jsonParser = new JSONParser();
	ArrayList<HashMap<String, String>> studentList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_query_details_list);
		 Intent intent=getIntent();
		 //String choice=intent.getStringExtra("next");
		studentList = new ArrayList<HashMap<String, String>>();

		ListView lv = getListView();

		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String Name = ((TextView) view.findViewById(R.id.name)).getText().toString();
				String Roll = ((TextView) view.findViewById(R.id.roll)).getText().toString();
				for(int i=0;i<studentList.size();i++)
				{
					HashMap<String, String> student1 = new HashMap<String, String>();
					student1=studentList.get(i);
					String Index=student1.get(TAG_ROLL);
					if(Roll.compareTo(Index)==0)
					{
						Intent in = new Intent(getApplicationContext(),
								SingleContactActivity.class);
						in.putExtra(TAG_NAME, student1.get(TAG_NAME));
						in.putExtra(TAG_ROLL, student1.get(TAG_ROLL));
						in.putExtra(TAG_EMAIL, student1.get(TAG_EMAIL));
						in.putExtra(TAG_BRANCH, student1.get(TAG_BRANCH));
						in.putExtra(TAG_YEAR, student1.get(TAG_YEAR));
						in.putExtra(TAG_MOBILE, student1.get(TAG_MOBILE));
						startActivity(in);
						break;
					}
					
					
				}
				//String description = ((TextView) view.findViewById(R.id.mobile)).getText().toString();
				// Starting single contact activity
				/*Intent in = new Intent(getApplicationContext(),
						SingleContactActivity.class);
				in.putExtra(TAG_NAME, Name);
				in.putExtra(TAG_ROLL, Roll);
				//in.putExtra(TAG_PHONE_MOBILE, description);
				startActivity(in);*/

			}
		});

		// Calling async task to get json
		new GetStudents().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetStudents extends AsyncTask<String,String,String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainQueryDetailsList.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected String doInBackground(String... arg) {
			// Creating service handler class instance
			// Making a request to url and getting response
			Intent intent=getIntent();
			 final String Sub_Code=intent.getStringExtra("Sub_Code");//getting the prof name
			 List<NameValuePair> params = new ArrayList<NameValuePair>();
	            params.add(new BasicNameValuePair("Sub_Code",Sub_Code));

			// Making a request to url and getting response
			JSONObject jsonStr = jsonParser.makeHttpRequest(url,"GET", params);

			Log.d("Response: ", "> " + jsonStr.toString());

			if (jsonStr.toString() != null) {
				try {
					//JSONObject jsonObj = new JSONObject();
					
					// Getting JSON Array node
					students = jsonStr.getJSONArray(TAG_STUDENTS);

					// looping through All Contacts
					for (int i = 0; i < students.length(); i++) {
						JSONObject c = students.getJSONObject(i);
						
						String Roll = c.getString(TAG_ROLL);
						String Fname = c.getString(TAG_FNAME);
						String Lname = c.getString(TAG_LNAME);
						String name=Fname+" "+Lname;
						String Email = c.getString(TAG_EMAIL);
						String Mobile = c.getString(TAG_MOBILE);
						String Branch = c.getString(TAG_BRANCH);
						String Year = c.getString(TAG_YEAR);
						//String Index ="";
						//Index+=i;
						//String address = c.getString(TAG_ADDRESS);
						//String gender = c.getString(TAG_GENDER);

						// Phone node is JSON Object
						/*JSONObject phone = c.getJSONObject(TAG_PHONE);
						String mobile = phone.getString(TAG_PHONE_MOBILE);
						String home = phone.getString(TAG_PHONE_HOME);
						String office = phone.getString(TAG_PHONE_OFFICE);
						*/
						// tmp hashmap for single contact
						HashMap<String, String> students = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						students.put(TAG_ROLL, Roll);
						students.put(TAG_FNAME, Fname);
						students.put(TAG_FNAME, Fname);
						students.put(TAG_NAME, name);
						students.put(TAG_EMAIL, Email);
						students.put(TAG_MOBILE, Mobile);
						students.put(TAG_BRANCH, Branch);
						students.put(TAG_YEAR, Year);
						//students.put(TAG_INDEX,Index);
						// adding contact to contact list
						studentList.add(students);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(
					MainQueryDetailsList.this, studentList,
					R.layout.list_item, new String[] { TAG_NAME,TAG_ROLL }, new int[] { R.id.name,R.id.roll });

			setListAdapter(adapter);
		}

	}

}