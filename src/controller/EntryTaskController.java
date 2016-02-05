package controller;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker.State;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javafx.collections.ObservableList;

import javafx.fxml.*;
import javafx.scene.web.*;
import javafx.stage.Stage;
import mainArea.Main;
import org.jsoup.nodes.Node;

public class EntryTaskController extends AnchorPane implements Initializable {
	WebView webView;
	@FXML Button addOption;
	@FXML VBox optionArea;
	@FXML Button addTask;

	private List<ComboBox> cmb;
	
	public EntryTaskController(WebView webView){
		this.webView = webView;
		loadFXML();
		initPane();
	}
	
	private void initPane(){
//		WebEngine engine = optionView.getEngine();
		try {
			Document document = Jsoup.connect(webView.getEngine().getLocation()).get();
			Element table = document.select("#normal_basket_" + document.select("[name=item_id]").val()).first();
			Element td = table.select("td").first();
			Elements spans = td.select("span");
			Elements selects = td.select("select");
//			System.out.println(spans.size());
			cmb = new ArrayList<ComboBox>();
			for(int i = 0; i < spans.size(); i++){
				
				ObservableList<ValuePair> obs = FXCollections.observableArrayList();
				Elements options = selects.get(i).select("option");
				for(int k = 0; k < options.size(); k++){
					Element option = options.get(k);
					obs.add(new ValuePair("choice", option.text(), option.val()));
				}

				
				cmb.add(new ComboBox<ValuePair>(obs));
				optionArea.getChildren().addAll(new Text(spans.get(i).text()), cmb.get(i));
				
			}		
			
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}	
	}

	private void loadFXML() {
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("EntryTask.fxml"));
		fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO 自動生成されたメソッド・スタブ
		addOption.setOnAction(event -> {
			
			for(int i = 0; i < cmb.size(); i++){
//				System.out.println(cmb.get(i).getValue());
				if (cmb.get(i).getValue() == null){
					break;
				} else if (i == cmb.size() - 1){
					addTask.setDisable(false);
				}
			}
		});
		
		addTask.setOnAction(event -> {
			try {
				String url = webView.getEngine().getLocation();
				System.out.println(url);
				Document document = Jsoup.connect(url).get();
				
				Elements input = document.select("input");
				
				Map params = new HashMap<String, String>();
				for(ComboBox cmbx : cmb){
					ValuePair vp = (ValuePair)cmbx.getValue();
					params.put(vp.getName(), vp.getvalue());
				}
//				System.out.println(input.select("[name=shop_bid]").first());
//				System.out.println(input.select("[name=shop_bid]").first().val());
				params.put("shop_bid", input.select("[name=shop_bid]").first().val());
				params.put("item_id", input.select("[name=item_id]").first().val());
				params.put("__event", input.select("[name=__event]").first().val());
				params.put("units", "1");
				
				Map map = new HashMap<String, Long>();
				
//				System.out.println(document.select("#stime").size());
				if (document.select("#stime").size() != 0){
					System.out.println(document.select("#stime"));
					map.put("stime", Long.parseLong((input.select("#stime").first().val())));
					map.put("etime", Long.parseLong((input.select("#etime").first().val())));
				} else {
					map = null;
				}
				
				BuyTask task = new BuyTask(url, params, map);
				task.call();
				this.getScene().getWindow().hide();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		});
	}

}
