package controller;

import javafx.scene.control.ComboBox;

public class ValuePair {
	private String key;
	private String name;
	private String value;
	
	public ValuePair(String name, String key, String value){
		this.key = key;
		this.value = value;
		this.name = name;
	}
	
	public String getvalue(){
		return this.value;
	}
	
	 @Override
     public String toString() {
         return this.key.toString();
     }

	public String getName() {
		return name;
	}

}
