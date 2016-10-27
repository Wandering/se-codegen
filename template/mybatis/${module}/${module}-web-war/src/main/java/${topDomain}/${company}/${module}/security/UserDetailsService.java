package ${basepackage}.security;

import cn.starteasy.core.common.adminui.backend.dao.IAdminUserDAO;
import cn.starteasy.core.common.adminui.backend.domain.AdminUser;
import cn.starteasy.core.common.domain.persistent.SearchEnum;
import cn.starteasy.core.common.domain.persistent.utils.ConditionBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserDetailsService.class);

    @Inject
    private IAdminUserDAO adminUserDAO;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);

        Map<String, Object> condition = ConditionBuilder.condition("login", SearchEnum.eq, lowercaseLogin);
        AdminUser userFromDatabase = adminUserDAO.queryOne(null, condition, null);//.findOneByLogin(lowercaseLogin);
        Optional<AdminUser> adminUserOptional = Optional.of(userFromDatabase);
        return adminUserOptional.map(user -> {
//            if (!user.getActivated()) {
//                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
//            }
            //根据 权限体系获取  用户对应的角色
            List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
//                    user.getAuthorities().stream()
//                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
//                .collect(Collectors.toList());
            return userFromDatabase;
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
    }
}
