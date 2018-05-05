package demo.api.util;

public final class DataUtility {
	
	public static String getEntityInfo(Object entity) {
        if (entity != null){
            return entity.toString();
        }

        return "";
    }
}