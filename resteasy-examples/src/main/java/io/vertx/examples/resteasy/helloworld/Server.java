package io.vertx.examples.resteasy.helloworld;

import org.jboss.resteasy.plugins.server.vertx.VertxRequestHandler;
import org.jboss.resteasy.plugins.server.vertx.VertxResteasyDeployment;
import org.jboss.weld.vertx.WeldVerticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.examples.resteasy.util.Runner;

/*
 * @author <a href="mailto:julien@julienviet.com">Julien Viet</a>
 */
public class Server extends AbstractVerticle {

    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Runner.runExample(Server.class);
    }

    @Override
    public void start() throws Exception {

        // Start the CDI container first
        WeldVerticle weldVerticle = new WeldVerticle();

        vertx.deployVerticle(weldVerticle, (r) -> {
            if (r.succeeded()) {
                // Build the Jax-RS hello world deployment
                VertxResteasyDeployment deployment = new VertxResteasyDeployment();
                // CdiInjectorFactory comes from org.jboss.resteasy:resteasy-cdi
                deployment.setInjectorFactoryClass("org.jboss.resteasy.cdi.CdiInjectorFactory");
                deployment.start();
                deployment.getRegistry().addPerInstanceResource(HelloWorldService.class);

                // TODO We need to wrap/intercept the body handler set by VertxRequestHandler to activate request context
                // Start the front end server using the Jax-RS controller
                vertx.createHttpServer().requestHandler(new VertxRequestHandler(vertx, deployment)).listen(8080, ar -> {
                    System.out.println("Server started on port " + ar.result().actualPort());
                });
            } else {
                r.cause().printStackTrace();
            }
        });
    }

}
