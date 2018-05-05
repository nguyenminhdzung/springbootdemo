package demo.api.service.common;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public abstract class BaseService<T> {
   
	@PersistenceContext
    private EntityManager entityManager;
	
	public static final int NUMBER_OF_ITEMS_PER_PAGE = 10;
	
	public Pageable constructPageSpecification(int pageIndex, int pageSize, String sortField, String sortDirection) {
        if (pageSize <= 0) {
            pageSize = NUMBER_OF_ITEMS_PER_PAGE;
        }
        Pageable pageSpecification = new PageRequest(pageIndex, pageSize, constructSortSpecification(sortField, sortDirection));
        return pageSpecification;
    }

    public Sort constructSortSpecification(String sortField, String sortDirection) {
        if (StringUtils.isEmpty(sortField)) {
            return sortByDefault();
        }
        Sort.Direction d = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort.Order order = new Sort.Order(d, sortField);
        Sort s = new Sort(order.ignoreCase());
        String defaultField = defaultSortField();
        if (!StringUtils.isEmpty(defaultField) && !defaultField.equalsIgnoreCase(sortField)) {
            Sort defaultSort = sortByDefault();
            if (defaultSort!=null)
                s = s.and(defaultSort);
        }
        return s;
    }
    
    public Sort sortByDefault() {
        String defaultField = defaultSortField();
        if (StringUtils.isEmpty(defaultField))
            return null;
        Sort.Order order = new Sort.Order(defaultSortDirection(), defaultField);
        return new Sort(order.ignoreCase());
    }

    public String defaultSortField(){
        return "id";
    }

    public Sort.Direction defaultSortDirection() {
        return Sort.Direction.ASC;
    }
}
