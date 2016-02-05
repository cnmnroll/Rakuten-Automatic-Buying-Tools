package controller;
import javafx.scene.web.WebEngine;

public class OrderEngine {
	private WebEngine engine = new WebEngine();
	private int transition;
	
	public OrderEngine(){
		engine = new WebEngine();
		engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
			if (newState == newState.SUCCEEDED){
				System.out.println(engine.getLocation());
			}
		});
	}
	
	public void load(String url){
		this.engine.load(url);
	}
	
	
	
	
}
