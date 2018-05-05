package demo.api.web;

public class APIOutputInfo<T> {
	private boolean success;
    private String resultCode;
    private String devMessage;
    private String message;
    private T data;
    
    public APIOutputInfo() { } 

    public APIOutputInfo(boolean isSuccess, String resultCode, String message) {
    	this.success = isSuccess;
        this.resultCode = resultCode;
        this.message = message;
    }
    
    public APIOutputInfo(boolean isSuccess, String resultCode, String message, T data) {
        this(isSuccess, resultCode, message);
        this.data = data;
    }
    
    public <T2> APIOutputInfo<T2> duplicate(T2 data) {
    	return new APIOutputInfo<T2>(this.success, this.resultCode, this.message, data);
    }
    
//    public static <T> ResultInfo<T> succezz(T data) {
//    	return succezz(Constants.ErrorCode.SUCCESS, data);
//    }
//    
//    public static <T> ResultInfo<T> succezz(String resultCode, T data) {
//    	return new ResultInfo<T>(true, resultCode, "", data);
//    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getDevMessage() {
		return devMessage;
	}

	public void setDevMessage(String devMessage) {
		this.devMessage = devMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
