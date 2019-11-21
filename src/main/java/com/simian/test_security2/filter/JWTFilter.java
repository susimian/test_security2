package com.simian.test_security2.filter;

import com.simian.test_security2.dao.UserDao;
import com.simian.test_security2.pojo.User;
import com.simian.test_security2.service.RedisService;
import com.simian.test_security2.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    UserDao userDao;
    @Autowired
    RedisService redisService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 如果已经通过认证
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }
        // 获取 header 解析出 jwt 并进行认证 无token 直接进入下一个过滤器  因为  SecurityContext 的缘故 如果无权限并不会放行
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (JWTUtil.verifyToken(jwtToken)){
            Integer userId = JWTUtil.getUserId(jwtToken);

            if (redisService.get(userId).equals(jwtToken)){
                User user = userDao.findById(userId).get();
                Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
                // 构建用户认证token
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 放入安全上下文中
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        chain.doFilter(request, response);
    }
}
