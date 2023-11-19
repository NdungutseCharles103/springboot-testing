package rw.ac.rca.gradesclassb.utils;

public class APIResponseB {

	private boolean status;
	
	private String message;

	
	public APIResponseB() {
		super();
	}
	public APIResponseB(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}


	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
