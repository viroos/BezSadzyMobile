package pl.klimatbezsadzy;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Info extends Activity {
	 private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
				String[] values = (String[]) intent.getExtras().get("values");
	        	updateUI(values);   

	        }
	    };    
	
	  @Override
	  public void onCreate(Bundle savedInstanceState) {

		  
		  
	    super.onCreate(savedInstanceState);
	    
	    String[] values = AllertService.values;

	    setContentView(R.layout.info);
	    updateUI(values);
	    
	  }	
	  
	  public void onNewIntent(Bundle savedInstanceState) {
	  }
	  

	  
		@Override
		public void onResume() {
			super.onResume();
			Log.d("resume", "resume");
			registerReceiver(broadcastReceiver, new IntentFilter(AllertService.UPDATE));
		}
		@Override
		public void onPause() {
			super.onPause();
			unregisterReceiver(broadcastReceiver);

		}
		
		
		private void updateUI(String[] values){
			String mokotow = values[0];
			String targowek = values[1];
			String ursynow = values[2];
			
			TextView mokotowTextView = (TextView) findViewById(R.id.textViewKomunikacyjnaValue);
			TextView targowekTextView = (TextView) findViewById(R.id.textViewTargowekValue);
			TextView ursynowTextView = (TextView) findViewById(R.id.textViewUrsynowValue);
			
			mokotowTextView.setText(""+mokotow);
			ursynowTextView.setText(""+ursynow);
			targowekTextView.setText(""+targowek);
		}
	  

}
