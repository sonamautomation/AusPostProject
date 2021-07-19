package pages;

import java.util.List;

public class radar {

	private List<past> past;
	private List<nowcast> nowcast;

	public List<pages.past> getPast() {
		return past;
	}

	public void setPast(List<pages.past> past) {
		this.past = past;
	}

	public List<pages.nowcast> getNowcast() {
		return nowcast;
	}

	public void setNowcast(List<pages.nowcast> nowcast) {
		this.nowcast = nowcast;
	}
	
	
}
