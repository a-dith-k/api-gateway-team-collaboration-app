package site.adithk.apigatewayservice.configuraitions.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import site.adithk.apigatewayservice.configuraitions.RouteValidator;
import site.adithk.apigatewayservice.exceptions.AuthorizationHeaderNotFoundException;
import site.adithk.apigatewayservice.exceptions.UnauthorizedException;
import site.adithk.apigatewayservice.services.jwt.JwtService;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>{

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private JwtService jwtService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config{

    }

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange,chain)->{
            System.out.println(exchange.getRequest().getPath());
            if(routeValidator.isSecured.test(exchange.getRequest())) {

                //header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new AuthorizationHeaderNotFoundException("missing authorization header");
                }

                String authHeader=exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader=authHeader.substring(7);
                }

                try {
                    jwtService.validateToken(authHeader);
                }catch(Exception e){
                    throw new UnauthorizedException("Not authorized to access the resource");

                }
            }
            return chain.filter(exchange);
        });
    }

}
