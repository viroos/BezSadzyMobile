package pl.klimatbezsadzy;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Info extends Activity {
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.info);
	  }	
	  
	  public void onNewIntent(Bundle savedInstanceState) {
	  }
	  
	  public void onResume() {
		  super.onResume();		  
	  }
}
