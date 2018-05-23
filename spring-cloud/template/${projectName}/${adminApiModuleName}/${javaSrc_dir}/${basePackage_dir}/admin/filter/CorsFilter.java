package ${basePackage}.admin.filter;

import ${basePackage}.admin.properties.CorsProperties;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@WebFilter(filterName = "corsFilter", urlPatterns = "/*")
public class CorsFilter implements Filter {

    @Autowired
    CorsProperties corsProperties;

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse rep = (HttpServletResponse) response;
        rep.setHeader("Access-Control-Allow-Origin", corsProperties.getAllowOrigin());
        rep.setHeader("Access-Control-Allow-Methods", corsProperties.getAllowMethods());
        rep.setHeader("Access-Control-Max-Age", corsProperties.getMaxAge());
        rep.setHeader("Access-Control-Allow-Headers", corsProperties.getAllowHeaders());
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
