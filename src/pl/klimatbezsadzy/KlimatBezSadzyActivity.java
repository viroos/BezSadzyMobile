package pl.klimatbezsadzy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class KlimatBezSadzyActivity extends Activity {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    Toast.makeText(this, "Widget zainstalowany! Możesz teraz dodać go do ekranu.",
                   Toast.LENGTH_LONG).show();
    startService(new Intent(this, AllertService.class));
    finish();
  }
}
