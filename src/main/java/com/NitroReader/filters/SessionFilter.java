package com.NitroReader.filters;

import com.NitroReader.services.ServiceMethods;
import com.NitroReader.utilities.PropertiesReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Manga;
import models.Response;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "SessionFilter")
public class SessionFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ObjectMapper objM = new ObjectMapper();
        Response<Manga> res = new Response<>();
        PropertiesReader props = PropertiesReader.getInstance();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        if (request.getMethod().equalsIgnoreCase("GET")){
            if (session != null && session.getAttribute("name") != null && session.getAttribute("id") != null){
                req.setAttribute("logged", true);
                System.out.println(props.getValue("userLogged"));
            } else{
                req.setAttribute("logged", false);
                System.out.println(props.getValue("notLogged"));
            }
            chain.doFilter(req, resp);
        }

        if (request.getMethod().equalsIgnoreCase("POST") || request.getMethod().equalsIgnoreCase("PUT")
         || request.getMethod().equalsIgnoreCase("DELETE")){
            if (session != null && session.getAttribute("name") != null && session.getAttribute("id") != null){
                req.setAttribute("logged", true);
                System.out.println(props.getValue("userLogged") + "YOU CAN PASS");
                chain.doFilter(req, resp);
            }else{
                ServiceMethods.setResponse(res, 423, props.getValue("Locked"), null);
                System.out.println(props.getValue("notLogged") + "YOU CAN'T DO THIS");
                response.getWriter().print(objM.writeValueAsString(res));
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
