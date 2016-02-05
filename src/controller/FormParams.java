package controller;

public class FormParams {
	private String name;
	private String val;
	
	public FormParams(String name, String val){
		this.name = name;
		this.val = val;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getVal() {
		return val;
	}


	public void setVal(String val) {
		this.val = val;
	}
	 @Override
     public String toString() {
         return this.name + ":" + this.val;
     }
}

