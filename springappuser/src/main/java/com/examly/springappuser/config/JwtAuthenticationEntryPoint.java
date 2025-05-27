package com.examly.springappuser.config;

import javax.naming.AuthenticationException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                         }

    
}
