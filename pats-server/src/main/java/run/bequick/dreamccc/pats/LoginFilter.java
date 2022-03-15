//package run.bequick.dreamccc.pats;
//
//import com.google.common.base.Strings;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpHeaders;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
//public class LoginFilter extends OncePerRequestFilter implements Ordered {
//
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String requestHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (Strings.isNullOrEmpty(requestHeader)) {
//            //401
//            return;
//        }
//
//
//
//        filterChain.doFilter(request,response);
//    }
//
//    public int getOrder() {
//        return HIGHEST_PRECEDENCE;
//    }
//}
