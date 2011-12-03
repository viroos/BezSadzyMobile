/***
  Copyright (c) 2008-2011 CommonsWare, LLC
  Licensed under the Apache License, Version 2.0 (the "License"); you may not
  use this file except in compliance with the License. You may obtain a copy
  of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
  by applicable law or agreed to in writing, software distributed under the
  License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
  OF ANY KIND, either express or implied. See the License for the specific
  language governing permissions and limitations under the License.
  
  From _The Busy Coder's Guide to Advanced Android Development_
    http://commonsware.com/AdvAndroid
*/

   
package com.commonsware.android.appwidget.dice;

import java.util.HashMap;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider {
	
  private static String []states;

  private static final HashMap<String,Integer> IMAGES = new HashMap<String,Integer>();

  public AppWidget() {
	  IMAGES.put("good", R.drawable.good);
	  IMAGES.put("notbad", R.drawable.notbad);
	  IMAGES.put("bad", R.drawable.bad);
	  IMAGES.put("verybad", R.	drawable.verybad);
	  IMAGES.put("extremelybad", R.drawable.extremelybad);

}
  
  @Override
  public void onReceive(Context context, Intent intent) {
	  AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
      ComponentName thisAppWidget = new ComponentName(context, AppWidget.class);
      if(intent.getAction().equals(AllertService.UPDATE)){
      Bundle extras = intent.getExtras();
     
      states = extras.getStringArray("states");
      
    
      Log.d("1",states[0]);
      Log.d("2",states[1]);
      Log.d("3",states[2]);
      }

      
      
      
      int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
      try {
          onUpdate(context, appWidgetManager, appWidgetIds);
      } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }	  
  }
  
  @Override
  public void onUpdate(Context ctxt, AppWidgetManager mgr,
                        int[] appWidgetIds) {
	    ComponentName me=new ComponentName(ctxt, AppWidget.class);
	    mgr.updateAppWidget(me, buildUpdate(ctxt, appWidgetIds));  
  }
  
  private RemoteViews buildUpdate(Context ctxt, int[] appWidgetIds) {
    RemoteViews updateViews=new RemoteViews(ctxt.getPackageName(),
                                            R.layout.widget);
  
    Intent i=new Intent(ctxt, AppWidget.class);
    
    i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
    i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
    
    PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0 , i,
                                                PendingIntent.FLAG_UPDATE_CURRENT);
    
    updateViews.setImageViewResource(R.id.station1,
                                     IMAGES.get(states[0])); 
    //updateViews.setOnClickPendingIntent(R.id.left_die, pi);
   updateViews.setImageViewResource(R.id.station2,
                                   IMAGES.get(states[1]));
   updateViews.setImageViewResource(R.id.station3,
           IMAGES.get(states[2]));
    //pdateViews.setOnClickPendingIntent(R.id.right_die, pi);
    //updateViews.setOnClickPendingIntent(R.id.background, pi);
    
    return(updateViews);    
  }
    
}