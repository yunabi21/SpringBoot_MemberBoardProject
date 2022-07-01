package com.its.project.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                           Object Handler) throws IOException {
    String requestURI = request.getRequestURI();
    HttpSession session = request.getSession();

    if (session.getAttribute("loginId") == null) {
      response.sendRedirect("/member/login-form?redirectURL=" + requestURI);
      return false;
    }
    return true;
  }
}
