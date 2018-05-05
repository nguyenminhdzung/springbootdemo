package demo.api.util;

public class Constants {
	
	public interface ErrorCode {
		public static final String SUCCESS = "Success";
        public static final String NOT_AVAILABLE_FUNCTION = "E_0001";
        public static final String UNKNOWN_EOR = "E_0002";
        public static final String NOT_FOUND = "E_0003";
        
        public static final String USERNAME_DUPLICATED = "E_0101";
        public static final String USER_ROLE_NOT_SET = "E_0102";
        public static final String USERNAME_NOT_EXIST = "E_0103";
        public static final String PASSWORD_INVALID = "E_0104";
		public static final String CANNOT_DELETE_CURRENT_PROFILE = "E_0105";
		
    }
}