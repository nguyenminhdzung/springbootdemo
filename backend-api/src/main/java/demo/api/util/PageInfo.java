package demo.api.util;

import java.util.ArrayList;
import java.util.List;

public class PageInfo {

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    private List<Object> data;
    private int size;
    private long totalElements;
    private int totalPages;
    private int number;
    
    public static final PageInfo empty() {
    	PageInfo emptyPage = new PageInfo();
    	emptyPage.setData(new ArrayList<Object>());
    	emptyPage.setNumber(0);
    	emptyPage.setSize(0);
    	emptyPage.setTotalElements(0);
    	emptyPage.setTotalPages(0);
    	
    	return emptyPage;
    }
}
