package com.example.nitccma;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity implements OnGestureListener {
	private GestureDetector gDetector;
	EditText edit1,edit2;
	private ProgressDialog pDialog;
	 
    JSONParser jsonParser = new JSONParser();
    // url to create new product
    private static String url_create_product = "http://allstuffcodes.info/classmanagement/authenticate.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	
	
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Typeface font1 = Typeface.createFromAsset(getAssets(), "fonts/Yellow_Dog.ttf");
		Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/f2.ttf");
        //Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/FEASFBRG.TTF");
        TextView customText1 = (TextView)findViewById(R.id.textView1);
        TextView customText2 = (TextView)findViewById(R.id.textView2);
        TextView customText3 = (TextView)findViewById(R.id.textView3);
        TextView customText4 = (TextView)findViewById(R.id.textView4);
        TextView customText5 = (TextView)findViewById(R.id.textView5);
        TextView customText6 = (TextView)findViewById(R.id.textView6);
        TextView customText7 = (TextView)findViewById(R.id.textView7);
        edit1=(EditText)findViewById(R.id.editText1);
        edit2=(EditText)findViewById(R.id.editText2);
        Button b1=(Button)findViewById(R.id.button1);
        customText1.setTypeface(font1);
        customText2.setTypeface(font1);
        customText3.setTypeface(font1);
        customText4.setTypeface(font1);
        customText5.setTypeface(font2);
        customText6.setTypeface(font2);
        customText7.setTypeface(font2);
        edit1.setTypeface(font2);
        edit2.setTypeface(font2);
        b1.setTypeface(font2);
        b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				//Context context=this;
				/*EditText edit1=(EditText)findViewById(R.id.editText1); *****
				String s=edit1.getText().toString();
				Intent intent=new Intent(getApplicationContext(),WelcomeActivity.class);
				intent.putExtra("name",s);
				startActivity(intent);*/
				/*edit1=(EditText)findViewById(R.id.editText1);
		        edit2=(EditText)findViewById(R.id.editText2);*/
				new authenticate().execute();
			}
		});
        gDetector = new GestureDetector(this);
	}
	/**
     * Background Async Task to Create new product
     * */
    class authenticate extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Authenticating..");
            //pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String Teacher_Id = edit1.getText().toString();
            String Password = edit2.getText().toString();
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            if(Teacher_Id!="")
            params.add(new BasicNameValuePair("Teacher_Id", Teacher_Id));
            if(Password!="")
            params.add(new BasicNameValuePair("Password", Password));
            //params.add(new BasicNameValuePair("description", description));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,"POST",params);
 
            // check log cat fro response
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    // successfully created product
                    //Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
                    //startActivity(i);
                    Intent intent=new Intent(getApplicationContext(),WelcomeActivity.class);
    				intent.putExtra("Teacher_Id",Teacher_Id);
    				startActivity(intent);
 
                    // closing this screen
                    finish();
                } else {
                	//Toast.makeText(MainActivity.this, "error",5000).show();
                	Intent intent1=new Intent(getApplicationContext(),MainActivity.class);
                	startActivity(intent1);
                	finish();
                	//edit1.setText("Invalid name or ");
                	//edit2.setText("Invalid password");
                    // failed to create product
                	
                }
            } catch (JSONException e) {
            	//Toast.makeText(MainActivity.this, "error",5000).show();
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            pDialog.dismiss();
        }
 
    }
    @Override
    public boolean onTouchEvent(MotionEvent me) {
        return gDetector.onTouchEvent(me);
    }

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent start, MotionEvent finish, float xVelocity, float yVelocity) {
		/*if (start.getRawX() > finish.getRawX()) {
			//((ImageView)findViewById(R.id.image_place_holder)).setImageResource(R.drawable.down); 
			Intent intent = new Intent(MainActivity.this,.class);
	        startActivity(intent);
		} 
		else if (start.getRawX() > finish.getRawX()) {
			//((ImageView)findViewById(R.id.image_place_holder)).setImageResource(R.drawable.up); 
			Intent intent = new Intent(Main.this, Student_Login.class);
	        startActivity(intent);
		}
		else if (start.getRawY() > finish.getRawY()) {
			Intent intent = new Intent(Main.this, MainActivity.class);
	        startActivity(intent);
		}
		else
		{
			Intent intent = new Intent(MainActivity.this, Student_Login.class);
	        startActivity(intent);

		}*/
		
		Intent intent = new Intent(MainActivity.this, Main.class);
        startActivity(intent);

		
		return true;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/*@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}*/

}
