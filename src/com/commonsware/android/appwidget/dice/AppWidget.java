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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider {
  private static final int[] IMAGES={R.drawable.die_1,R.drawable.die_2,
                                      R.drawable.die_3,R.drawable.die_4,
                                      R.drawable.die_5,R.drawable.die_6};
  
  @Override
  public void onReceive(Context context, Intent intent) {
	  AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
      ComponentName thisAppWidget = new ComponentName(context, AppWidget.class);
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
    /*
    updateViews.setImageViewResource(R.id.left_die,
                                     IMAGES[(int)(Math.random()*6)]); 
    updateViews.setOnClickPendingIntent(R.id.left_die, pi);
    updateViews.setImageViewResource(R.id.right_die,
                                     IMAGES[(int)(Math.random()*6)]); 
    updateViews.setOnClickPendingIntent(R.id.right_die, pi);
    updateViews.setOnClickPendingIntent(R.id.background, pi);
     */
    return(updateViews);    
  }
    
}