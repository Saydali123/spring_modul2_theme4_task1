package uz.sm.transfer.config.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.sm.transfer.config.jwt.JwtUtils;
import uz.sm.transfer.service.UserDetailService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {
    final
    JwtUtils jwtUtils;
    final UserDetailService userDetailService;

    public JwtFilter(JwtUtils jwtUtils, UserDetailService userDetailService) {
        this.jwtUtils = jwtUtils;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (Objects.nonNull(header) && header.startsWith("Bearer ")) {
            String substring = header.substring(7);
            boolean b = jwtUtils.validateToken(substring);

            if (b) {
                String username = jwtUtils.getUsernameFromToken(substring);

                //username orqali userdetails olindi
                UserDetails userDetails = userDetailService.loadUserByUsername(username);

                //userdetails orqali authentikation token yaratib oldik
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                userDetails.getAuthorities());

                //sistemaga kim kirganligini urnatdik
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
                System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());


            }

            filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request, response);
    }
}
