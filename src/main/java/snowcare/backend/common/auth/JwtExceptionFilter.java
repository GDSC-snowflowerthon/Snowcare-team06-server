//package snowcare.backend.common.auth;
//
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import snowcare.backend.common.exception.ErrorResponse;
//
//import java.io.IOException;
//
//@Component
//public class JwtExceptionFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
//        try {
//            chain.doFilter(req, res); // go to 'JwtAuthFilter'
//        } catch (JwtException ex) {
//            System.out.println(">> JwtException: " + ex);
//            setErrorResponse(HttpStatus.UNAUTHORIZED, res, ex);
//        } catch (UsernameNotFoundException ex){
//            System.out.println(">> JwtException: " + ex);
//            setErrorResponse(HttpStatus.UNAUTHORIZED, res, ex);
//        }
//    }
//
//    public void setErrorResponse(HttpStatus status, HttpServletResponse res, Throwable ex) throws IOException {
//        res.setStatus(status.value());
//        res.setContentType("application/json; charset=UTF-8");
//
//        ErrorResponse jwtExceptionResponse = ErrorResponse.builder()
//                .status(HttpStatus.UNAUTHORIZED.value())
//                .error(HttpStatus.UNAUTHORIZED.name())
//                .code("TOKEN")
//                .detail(ex.getMessage())
//                .build();
//        jwtExceptionResponse.setDateNull();
//        res.getWriter().write(jwtExceptionResponse.convertToJson());
//    }
//}
