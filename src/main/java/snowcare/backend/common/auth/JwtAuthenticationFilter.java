//package snowcare.backend.common.auth;
//
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import snowcare.backend.common.jwt.JwtUtils;
//import snowcare.backend.domain.User;
//import snowcare.backend.repository.UserRepository;
//import com.auth0.jwt.exceptions.TokenExpiredException;
//import com.auth0.jwt.interfaces.Claim;
//
//import java.io.IOException;
//import java.util.Map;
//
//
//@Component
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    private final JwtUtils jwtUtils;
//    private final UserDetailsService userDetailsService;
//    private final UserRepository userRepository;
//
//    // 실제 필터링 로직은 doFilterInternal 에 들어감
//    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain
//    ) throws ServletException, IOException {
//
//        if (request.getServletPath().contains("/auth") || request.getServletPath().contains("/swagger-ui")
//            || request.getServletPath().contains("/swagger-resources") || request.getServletPath().contains("v3/api-docs")
//            || request.getServletPath().contains("/users/nickname") || request.getServletPath().contains("/volunteers/recent")
//            /*|| request.getServletPath().contains("/volunteers") || request.getServletPath().contains("/community")*/) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//
//        final String authHeader = request.getHeader("Authorization");
//        final String jwt;
//        final String userEmail;
//        Long userId;
//        Map<String, Claim> claims;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//        jwt = authHeader.substring(7);
//        try {
//            userId = jwtUtils.getUserIdFromToken(jwt);
//            claims = jwtUtils.getClaimsFromToken(jwt);
//        } catch (TokenExpiredException e){
//            logger.warn("Token is expired and not valid anymore", e);
//            System.out.println(">> JwtException: " + "TokenExpiredException");
//            throw new JwtException("토큰 기한 만료");
//        }
//
//        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(String.valueOf(userId));
//
//            Boolean userMatch = false;
//            User user = userRepository.findById(userId).orElse(null);
//            if(user!=null && user.getEmail().equals(claims.get("email").asString()) ) {
//                userMatch = true;
//            }
//
//            if (jwtUtils.validateAccessToken(jwt) && userDetails!=null && userMatch) {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        null,
//                        userDetails.getAuthorities()
//                );
//                authToken.setDetails(
//                        new WebAuthenticationDetailsSource().buildDetails(request)
//                );
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//        filterChain.doFilter(request, response);
//        }
//}
