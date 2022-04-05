package run.bequick.dreamccc.pats.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        var openAPI = new OpenAPI();
        var securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION)
                .scheme("bearer")
                .bearerFormat("JWT");
        var info = new Info()
                .description("停车场管理系统-后端实现")
                .title("Parking Area Toll System");
        return openAPI
                .info(info)
                .components(new Components().addSecuritySchemes("token", securityScheme));
    }
}
