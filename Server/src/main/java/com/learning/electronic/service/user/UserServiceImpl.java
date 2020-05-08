package com.learning.electronic.service.user;

import com.learning.electronic.bean.user.Role;
import com.learning.electronic.bean.user.UserInfo;
import com.learning.electronic.dao.user.UserDao;
import com.learning.electronic.dao.user.UserRoleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 程龙[chenglonghy]
 * @date 2020/5/7 - 11:14
 * @history 2020/5/7 - 11:14 chenglonghy  create.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    UserDao userDao;
    @Resource
    UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = null;
        try {
            user = userDao.selectUserInfo(username);
            if (user == null) {
                throw new UsernameNotFoundException("not found");
            }
            List<Role> roles = userRoleDao.selectUserRoles(user.getId());
            user.setRoles(roles);
        } catch (SQLException e) {
            throw new UsernameNotFoundException("数据查询异常");
        }
        return user;
    }

}
