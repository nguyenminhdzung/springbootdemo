package demo.api.exception;

public class DemoException extends RuntimeException {

	private static final long serialVersionUID = -2807564902466350645L;
	
	private String errorCode;

	public DemoException(String errorCode){
		super();
		
		this.errorCode = errorCode;
	}
	
	public DemoException(String message, String errorCode){
		super(message);
		
		this.errorCode = errorCode;
	}
	
	public DemoException(Exception innerException, String errorCode){
		super(innerException);
		
		this.errorCode = errorCode;
	}
	
	public DemoException(String message, Exception innerException, String errorCode){
		super(message, innerException);
		
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}	
	
}
