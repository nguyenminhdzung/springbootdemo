package demo.api.web.dto;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import demo.api.model.User;
import demo.api.util.PageInfo;
import demo.api.web.dto.user.UserViewInfo;
import java.util.List;

import org.springframework.data.domain.Page;

public abstract class DTOHelper {
	
	
	
    public static PageInfo convertToPageTransfer(final Page page){
        PageInfo pageInfo = new PageInfo();
        if (page==null)
            return pageInfo;
        
        pageInfo.setData(page.getContent());
        pageInfo.setNumber(page.getNumber());
        pageInfo.setSize(page.getSize());
        pageInfo.setTotalElements(page.getTotalElements());
        pageInfo.setTotalPages(page.getTotalPages());
        
        return pageInfo;
    }

    public static PageInfo convertToUserInfoPage(Page<User> userPage) {
        PageInfo page = convertToPageTransfer(userPage);

        List<Object> userInfos = Lists.newArrayList(
            Iterables.transform(userPage.getContent(), new Function<User, Object>() {
                @Override
                public Object apply(User user) {
                    UserViewInfo userViewInfo = new UserViewInfo(user);
                    return userViewInfo;
                }
            }));

        page.setData(userInfos);

        return page;
    }

}
