package demo.api.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.api.util.Constants;
import demo.api.web.APIOutputInfo;

@Service
public class UtilService {
	
	private static final Logger logger = LoggerFactory.getLogger(UtilService.class);
	
	public static final String USLanguage = "en-US";
		
	@Autowired
	private MessageSourceService messageSourceService;
	
	public <T> APIOutputInfo<T> successResult(String resultCode, T data) {
		return new APIOutputInfo<T>(true, resultCode, getMessage(resultCode, "Success"), data);
	}
	
	public <T> APIOutputInfo<T> successResult(String resultCode) {
		return successResult(resultCode, null);
	}
	
	public <T> APIOutputInfo<T> successResult(T data) {
		return successResult(Constants.ErrorCode.SUCCESS, data);
	}
	
	public <T> APIOutputInfo<T> failureResult(String resultCode, T data) {
		return new APIOutputInfo<T>(false, resultCode, messageSourceService.getMessage(getCurrentUserLanguage(), resultCode, "Not Success"), data);
	}
	
	public String getMessage(String messageCode, String defaultText) {
		return messageSourceService.getMessage(getCurrentUserLanguage(), messageCode, defaultText);
	}
	
	public String getMessage(String messageCode) {
		return getMessage(messageCode, "");
	}

	public String getCurrentUserLanguage() {
		return USLanguage;
	}
}
