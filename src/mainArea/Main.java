package mainArea;
	
import java.awt.TextField;
import java.io.IOException;

import controller.EntryTaskController;
import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;


public class Main extends Application {
	WebEngine mainEngine;
	WebEngine tmpEngine = new WebEngine();

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			ComboBox comboBox = new ComboBox();
			FlowPane root = (FlowPane)FXMLLoader.load(getClass().getResource("AutoOrder.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			mainEngine = ((WebView)scene.lookup("#webView")).getEngine();
			((ComboBox<String>)scene.lookup("#searchBox")).getItems().addAll("楽天", "amazon");
			mainEngine.load("http://item.rakuten.co.jp/es-ink/kfd-150202/");
				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
