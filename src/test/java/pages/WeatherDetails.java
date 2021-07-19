package pages;

import java.util.List;

public class WeatherDetails {
	private float version;
	private int generated;
	private String host;
	private radar radar;
	private satellite satellite;
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}
	public int getGenerated() {
		return generated;
	}
	public void setGenerated(int generated) {
		this.generated = generated;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public pages.radar getRadar() {
		return radar;
	}
	public void setRadar(pages.radar radar) {
		this.radar = radar;
	}
	public pages.satellite getSatellite() {
		return satellite;
	}
	public void setSatellite(pages.satellite satellite) {
		this.satellite = satellite;
	}
	
	

}
