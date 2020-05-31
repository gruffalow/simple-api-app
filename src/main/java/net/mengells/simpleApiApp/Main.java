package net.mengells.simpleApiApp;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import net.mengells.simpleApiApp.user.Controller;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {
    public static void main(String[] args) {
        Javalin.create(config -> {
            config.registerPlugin(getConfiguredOpenApiPlugin());
            config.addStaticFiles("/static");
            config.defaultContentType = "application/json";
        }).routes(() -> {
            path("users", () -> {
                get(Controller::getAll);
                post(Controller::create);
                path(":userId", () -> {
                    get(Controller::getOne);
                    patch(Controller::update);
                    delete(Controller::delete);
                });
            });
            path("echo", () -> {
                post((Context ctx) -> {
                    System.out.println("content Type : "+ctx.contentType());
                    System.out.println("body : "+ctx.body());
                    ctx.html(ctx.body());
                });
            });
        }).start(7000);

        System.out.println("Check out ReDoc docs at http://localhost:7000/redoc");
        System.out.println("Check out Swagger UI docs at http://localhost:7000/swagger-ui");
    }

    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
        Info info = new Info().version("1.0").description("User API");
        OpenApiOptions options = new OpenApiOptions(info)
                .activateAnnotationScanningFor("net.mengells.simpleApiApp.user")
                .path("/swagger-docs") // endpoint for OpenAPI json
                .swagger(new SwaggerOptions("/swagger-ui")) // endpoint for swagger-ui
                .reDoc(new ReDocOptions("/redoc")) // endpoint for redoc
                .defaultDocumentation(doc -> {
                    doc.json("500", ErrorResponse.class);
                    doc.json("503", ErrorResponse.class);
                });
        return new OpenApiPlugin(options);
    }
}
