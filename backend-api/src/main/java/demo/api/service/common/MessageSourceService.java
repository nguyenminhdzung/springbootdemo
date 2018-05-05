package demo.api.service.common;

import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class MessageSourceService {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageSourceService.class);
	
	@Autowired
	private MessageSource errorMessageSource;
	
	public String getMessage(String language, String messageCode, String defaultMessage) {
		return getMessage(language, messageCode, null, defaultMessage);
	}
	public String getMessage(String language, String messageCode, Object[] params, String defaultMessage) {
		
		
		Locale locale = getLocale(language);
		
		return errorMessageSource.getMessage(messageCode, params, defaultMessage, locale);
	}
	private Locale getLocale(String language) {
		try {
			return StringUtils.isBlank(language) ? Locale.getDefault() : Locale.forLanguageTag(language);
		} catch (Exception e) {
			return Locale.getDefault();
		}
	}
}
