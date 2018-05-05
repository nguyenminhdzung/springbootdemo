package demo.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class CommonRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    protected Predicate containIgnoreCase(CriteriaBuilder queryBuilder, Expression<String> path, String searchText) {
    	return ilike(queryBuilder, path, "%" + searchText + "%");
    }
    
    protected Predicate startWidthIgnoreCase(CriteriaBuilder queryBuilder, Expression<String> path, String searchText) {
    	return ilike(queryBuilder, path, searchText + "%");
    }
    
    protected Predicate endWidthIgnoreCase(CriteriaBuilder queryBuilder, Expression<String> path, String searchText) {
    	return ilike(queryBuilder, path, "%" + searchText);
    }
    
    protected Predicate ilike(CriteriaBuilder queryBuilder, Expression<String> path, String searchText) {
    	return queryBuilder.like(queryBuilder.upper(path), searchText.toUpperCase());
    }

    protected List<Integer> parseSearchOptions(Map<Integer, Boolean> searchOptions) {
        if (searchOptions != null) {
            List<Integer> options = new ArrayList<>();

            for (Map.Entry<Integer, Boolean> optionMapEntry : searchOptions.entrySet()) {
                if (optionMapEntry.getValue()) {
                	options.add(optionMapEntry.getKey());
                }
            }

            return options;
        }

        return null;
    }
    
    protected List<String> parseIds(String ids) {
    	if (StringUtils.isBlank(ids))
    		return null;
    	
    	try {
    		// Don't use Splitter.splitToList here because it return a immutable list
    		List<String> splitedIds = Lists.newArrayList(Splitter.on(",").split(ids));
    		splitedIds.removeIf(id -> StringUtils.isBlank(id));
    		
    		return splitedIds;
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    protected List<Integer> parseSearchOptions(String options) {
    	if (StringUtils.isBlank(options))
    		return null;
    	
    	try {
    		Iterable<String> splitedOptions = Splitter.on(",").split(options);
    		
    		List<Integer> parsedOptions = new ArrayList<>();
    		for(String splitedOption : splitedOptions)
    			if (!StringUtils.isBlank(splitedOption))
    				parsedOptions.add(Integer.parseInt(splitedOption));
    		
    		return parsedOptions;
    	} catch (Exception e) {
    		return null;
    	}
    }
}