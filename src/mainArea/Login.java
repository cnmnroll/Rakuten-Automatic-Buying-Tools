package mainArea;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

public class Login {
	private Map<String, String> cookies;


	public Login(String userid, String passwd) {
		// TODO Auto-generated constructor stub
		try {
			this.cookies = Jsoup.connect("https://grp01.id.rakuten.co.jp/rms/nid/vc")
					.data("__event", "login")
					.data("p", passwd)
					.data("service=id", "top")
					.data("u", userid)
					.data("auto_logout", "false")
					.execute()
					.cookies();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	
	public Map<String, String> getCookies(){
		return this.cookies;
	}
	
//	public Connection getCookieLink() {
//		
//	}

}
