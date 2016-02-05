package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.scene.web.WebEngine;
import mainArea.Login;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.fxml.Initializable;

public class BuyTask extends Task {
	private Map<String, String> params;
	private Map<String, String> params2;
	private Map<String, Long> time;
	private int interval;
	private String url;
	private int state;
	private Login login;
	private int transition;
	private WebEngine engine;
	
	
	public BuyTask(String url, Map<String, String> params, Map<String, Long> time){
//		new OrderEngine();
		this.url = url; this.params = params;
		this.time = time; this.state = 1;
		this.params2 = new HashMap<String, String>();
		this.transition = 0; this.engine = new WebEngine();
		initialize();
		System.out.println(params);
	}
	
	@Override
	protected Object call() throws Exception {
		Date now = new Date();
//		while(true){
//			if (this.transition == 4) break; 
			if (time != null && time.get("etime") < now.getTime()){
				System.out.println(time.get("etime") + "|" + now.getTime());
				System.out.println("時間外"); setTimeOver(); 
//				break; 
			}
			addCart();
//		}
		return null;
	}

	public void addCart(){
//		login = new Login("id", "pwd");
		System.out.println("addCart start");
		Connection conn = Jsoup.connect("https://basket.step.rakuten.co.jp/rms/mall/bs/cartadd/set").data(params);
		try {
			conn.post();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.engine.load("https://basket.step.rakuten.co.jp/rms/mall/bs/cartall/");
		System.out.println("addCart end");
	}
	
	public String getName(String text){
		System.out.println(text);
//		Matcher tmp = Pattern.compile("^.*(name=\")([^\"]*)\"\\s(.*)$").matcher(text);
		Matcher tmp = Pattern.compile("^.*name=\"([^\"]*)\".*$").matcher(text);
		if (tmp.find()){
			return tmp.group(1);
		}
		return "";
	}
	
	private void setElementsMap(Map map, Elements elm){
		for(Element tmp : elm){
			map.put(getName(tmp.outerHtml()), tmp.val());
		}	
	}
	private Elements getPatternElements(Elements input, Pattern pattern){
		Elements tmp = new Elements();
		for(Element t : input){
			if (pattern.matcher(t.outerHtml()).find()){
				tmp.add(t);
			}
		}
		return tmp;
	}
//	public void startBuy() {
//		System.out.println("startBuy start");
//		Connection conn = Jsoup.connect("https://basket.step.rakuten.co.jp/rms/mall/bs/cart/set").data(params2);
//		try {
//			Document document = conn.post();
////			System.out.println(document);
//		} catch (IOException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		System.out.println("startBuy end");
//	}
	public String getStateText(){
		String text;
		switch (state) {
			case 1:
				text = "監視中"; break;
			case 2:
				text = "購入済み"; break;
			case 3 :
				text = "時間外"; break;
			default:
				text = "その他"; break;
		}
		return text;
	}
	
	private void setTimeOver(){
		this.state = 3;
	}

	public void initialize() {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println("initialize start");
		engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
            if (newState == State.SUCCEEDED) {
            	String url = engine.getLocation();
            	System.out.println(url);
            	if(Pattern.compile("https://basket.step.rakuten.co.jp/rms/mall/bs/cartall/.*").matcher(url).find() && this.transition == 0){
            		System.out.println("カート確認処理 開始");
            		engine.executeScript("document.querySelector('form[name=orderForm263453] input[name=submit]').click();");
            		this.transition++;
            		System.out.println("カート確認処理 終了");
            	} else if(url.equals("https://basket.step.rakuten.co.jp/rms/mall/bs/orderfrom/") && this.transition == 1){
            		System.out.println("ログイン処理 開始");
            		engine.executeScript(
            				"var fields = document.getElementsByClassName('itemInput');"
            				+ "fields[0].getElementsByTagName('input')[0].value = 'id';"
            				+ "fields[1].getElementsByTagName('input')[0].value = 'passwd';"
            				+ "var sbId = document.getElementById('sbId');"
            				+ "sbId.parentNode.removeChild(sbId);"
            				+ "document.getElementById('login_submit').click();"
            		);
            		this.transition++;
            		System.out.println("ログイン処理 終了");
            	} else if(url.equals("https://basket.step.rakuten.co.jp/rms/mall/bs/confirmorderquicknormalize/") && this.transition == 2){
            		System.out.println("購入確認処理 開始");
            		//buy
//            		engine.executeScript("document.getElementsByName('commit')[0].click();");
            		this.transition++;
            		System.out.println("購入確認処理 終了");
            	} else if(url.equals("https://basket.step.rakuten.co.jp/rms/mall/bs/confirmorderquicknormalize/set")) {
            		System.out.println("購入終了処理 start");
            		this.transition++;
            		System.out.println("購入終了処理 end");
            	} else {
            		System.out.println("other link start");
            		this.transition = -1;
            		System.out.println("other link end");
            	}
            } else {
            	  System.out.println("state missing");
            }
		});
		System.out.println("initialize end");
	}
}
