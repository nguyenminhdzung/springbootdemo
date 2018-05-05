package demo.api.repository.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import demo.api.model.User;
import demo.api.repository.CommonRepository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

public class UserRepositoryImpl extends CommonRepository implements UserRepositoryCustom {
	@Override
    public Page<User> findAll(Pageable pageable, UserSearchInfo searchInfo) {
        CriteriaBuilder queryBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = queryBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        
        criteriaQuery.distinct(true).where(calculatePredicates(searchInfo, criteriaQuery, queryBuilder, root));

        Sort sort = pageable.getSort();
        if (sort != null) {
            criteriaQuery.orderBy(toOrders(sort, root, queryBuilder));
        }

        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<User> list = query.getResultList();

        if (CollectionUtils.isEmpty(list)&& pageable.getOffset()>0){
            query.setFirstResult(0);
            list = query.getResultList();
        }

        CriteriaQuery<Long> countCriteriaQuery = queryBuilder.createQuery(Long.class);
        Root<User> countRoot = countCriteriaQuery.from(User.class);
        countCriteriaQuery.select(queryBuilder.countDistinct(countRoot));
        countCriteriaQuery.where(calculatePredicates(searchInfo, countCriteriaQuery, queryBuilder, countRoot));
        TypedQuery<Long> countQuery = entityManager.createQuery(countCriteriaQuery);
        Long total = countQuery.getSingleResult();

        Page<User> page = new PageImpl<>(list, pageable, total);
        return page;
    }

    private <T> Predicate[] calculatePredicates(UserSearchInfo searchInfo, CriteriaQuery<T> criteriaQuery, CriteriaBuilder queryBuilder, Root<User> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (searchInfo != null) {
        	
            if (!StringUtils.isBlank(searchInfo.getKeyword())) {
            	Expression<String> nameExp = queryBuilder.concat(root.<String>get("firstname"), " ");
            	nameExp = queryBuilder.concat(nameExp, root.<String>get("lastname"));
                predicates.add(queryBuilder.or(
                				containIgnoreCase(queryBuilder, nameExp, searchInfo.getKeyword()),
                				containIgnoreCase(queryBuilder, root.<String>get("username"), searchInfo.getKeyword()),
                				containIgnoreCase(queryBuilder, root.<String>get("email"), searchInfo.getKeyword()),
                				containIgnoreCase(queryBuilder, root.<String>get("address1"), searchInfo.getKeyword()),
                				containIgnoreCase(queryBuilder, root.<String>get("address2"), searchInfo.getKeyword()),
                				containIgnoreCase(queryBuilder, root.<String>get("address3"), searchInfo.getKeyword())));            	
            }
           
        }

        return predicates.toArray(new Predicate[0]);
    }

}
