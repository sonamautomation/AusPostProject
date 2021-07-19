package base;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TestBase {

	public static RequestSpecification httprequest;
	public static RequestSpecification req;
	public static ResponseSpecification response;
	public static Response responsebody;
	
	public static String baseUri="https://api.rainviewer.com";
	public String getResource="/public/weather-maps.json";
	public String exphost="https://tilecache.rainviewer.com";
	public float expversion=(float) 2.0;
	public int expcode=200;
	public String headerserver="Server";
	public String expserver="nginx";
	public String expcontenttype="application/json";
	public String expmsg ="HTTP/1.1 200 OK";
	public String actencoding="Content-Encoding";
	public String expencoding="gzip";
	public String actpath;
	public int acttime;
	public String getRadarResource="/v2/radar/nowcast_f0e5468eb357/512/2/2/1/1/1_1.png";
	public String expheaderradarcontenttype="image/png";
	public String xcache="X-Cache";
	public String expcache="rv_server";
	public int expradarstatus=200;
	public String getSatelliteResource="v2/satellite/3747daadb50f/256/2/35.71/-70.87/0/0_0.png";
	public String getradarpastdataresource="/v2/radar/1626571800/8000/2/0_1.png";
	public pages.WeatherDetails weatherdetail;

}
