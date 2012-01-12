package pl.klimatbezsadzy;

import android.net.Uri;

public class AirQualityMonitor {
	private RawAirQualiyDataProvider rawAirQualityDataProvider;
	private AirQualityDataParser airQualityDataParser;
	
	private String rawData;
	
	
	AirQualityMonitor(RawAirQualiyDataProvider rawAirQualityDataProvider,
			AirQualityDataParser airQualityDataParser){
		this.rawAirQualityDataProvider = rawAirQualityDataProvider;
		this.airQualityDataParser = airQualityDataParser;
	}
	
	public void analyzeData(){
		rawData = rawAirQualityDataProvider.getData();
	}
}
