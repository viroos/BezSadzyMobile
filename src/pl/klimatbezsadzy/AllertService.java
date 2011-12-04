package pl.klimatbezsadzy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.JetPlayer;
import android.net.http.AndroidHttpClient;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;  

public class AllertService extends Service {
		public static String UPDATE = "updateAction";	
		private Timer timer = new Timer();
		private JSONObject jObject;
		
		NotificationManager nm;
		int icon = R.drawable.icon;
		CharSequence tickerText = "Zaktualizowano dane";

		AndroidHttpClient httpClient;
		HttpGet request;
		HttpResponse response;

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onCreate() {

			httpClient = AndroidHttpClient.newInstance("mobiledeveloper.pl");
			request = new HttpGet("http://www.hackerspace.pl/~viroos/sadza");

			notificationLoop();

		}

		private boolean doCheck() {
			//RemoteViews remoteView = new RemoteViews(getApplicationContext() .getPackageName(), R.layout.widget);
			Intent intent = new Intent(getApplicationContext(), AppWidget.class);
			intent.setAction(UPDATE);
			
			BufferedReader in = null;
			String page = null;
			try {
				response = httpClient.execute(request);
				in = new BufferedReader(new InputStreamReader(response.getEntity()
						.getContent()));
				StringBuffer sb = new StringBuffer("");
				String line = "";
				String NL = System.getProperty("line.separator");
				while ((line = in.readLine()) != null) {
					sb.append(line + NL);
				}
				in.close();

				page = sb.toString();
				String almost = page.split("jsonZMWklejka\\(")[1];
				String final_string = almost.substring(0, almost.length()-2); 				
				Log.d("page", final_string);
				try {
					jObject = new JSONObject(final_string);
					String time = jObject.getString("time");
					JSONObject results = jObject.getJSONObject("results");
					JSONObject pm10 = results.getJSONObject("pm_10");
					JSONObject s1 = pm10.getJSONObject("sonda1");
					JSONObject s2 = pm10.getJSONObject("sonda2");
					JSONObject s3 = pm10.getJSONObject("sonda3");
					
					
					String s1State = s1.getString("state");
					String s2State = s2.getString("state");
					String s3State = s3.getString("state");
					
					String []states = {s1State, s2State, s3State};
					
					intent.putExtra("states", states);
					this.sendBroadcast(intent);
  


					
					Log.d("time ",time);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//boolean notify = page.trim().equalsIgnoreCase("zdm");
			//return notify;
			return true;
		}

		private void notificationLoop() {
			
			String ns = Context.NOTIFICATION_SERVICE;

			nm = (NotificationManager) getSystemService(ns);

			// Create your notification

			timer.scheduleAtFixedRate(new TimerTask() {

				public void run() {

					long when = System.currentTimeMillis();

					Notification notification = new Notification(icon, tickerText,
							when);

					Context context = getApplicationContext();
					CharSequence contentTitle = "Klimat Bez Sadzy";
					CharSequence contentText = "Zaktualizowano dane.";
					Intent notificationIntent = new Intent(context,
							Info.class);
					PendingIntent contentIntent = PendingIntent.getActivity(
							context, 0, notificationIntent, 0);

					notification.setLatestEventInfo(context, contentTitle,
							contentText, contentIntent);
					
					notification.flags|=Notification.FLAG_AUTO_CANCEL;

					//
					// Send the notification
					Log.d("SERVICE", "dochec" + doCheck());

					if (doCheck()) {
						nm.notify(233124124, notification);
					}
				}

			}, 0, 5000);
		}

		@Override
		public void onDestroy() {
			// code to execute when the service is shutting down
		}

		@Override
		public void onStart(Intent intent, int startid) {
			// code to execute when the service is starting up

		}
	}  
