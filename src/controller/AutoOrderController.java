package controller;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.concurrent.Worker.State;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.web.WebEngine;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.fxml.*;
import javafx.scene.web.*;
import javafx.stage.Stage;
import mainArea.Main;
import java.util.*;
public class AutoOrderController implements Initializable {
	@FXML WebView webView;
	@FXML TextField urlField;
	@FXML Button addButton;
	@FXML Button entryButton;
	@FXML HBox main;
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
        urlField.setOnAction(event -> {
        	String text = urlField.getText();
        	urlField.setText("tetetetetetetete");
        	webView.getEngine().load(text);
        });
        
        webView.getEngine().getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
        	
            if (newState == State.SUCCEEDED) {
                String url = webView.getEngine().getLocation();
                urlField.setText(url);
                if (Pattern.compile("http://item.rakuten.co.jp/.*").matcher(url).find()) {
                	try {
                		Elements tmp;
						Document document = Jsoup.connect(url).get();
						tmp = document.select("input");
						tmp = tmp.select("#etime");
						if (tmp.size() != 0){
							if (!(Long.parseLong(tmp.first().val()) < new Date().getTime())){
								entryButton.setDisable(false);
							}
						} else {
							entryButton.setDisable(false);
						}
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}
                }
            };
        });
        
        entryButton.setOnAction(event -> {
        	urlField.setText("webView disable");
        	sendEntryTaskController();
        });
	}
	
	public void sendEntryTaskController(){
		Stage stage = new Stage();
		
//		EntryTaskController controller = new EntryTaskController();
			EntryTaskController controller = new EntryTaskController(webView);
			Scene scene = new Scene(controller);
			stage.setScene(scene);

		stage.show();
//		this.replaceSceneContent(controller);
	}
}
