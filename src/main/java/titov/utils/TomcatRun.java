package titov.utils;

import lombok.experimental.UtilityClass;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;
import titov.controllers.CardsController;
import titov.controllers.LikesController;
import titov.controllers.UsersController;

@UtilityClass
public class TomcatRun {
    private static final int PORT = 8079;
    public void start() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setPort(PORT);
        Context tomcatContext = tomcat.addContext("", null);

        Wrapper usersServletWrapper =
                Tomcat.addServlet(tomcatContext, "usersServlet", new UsersController());
        usersServletWrapper.addMapping("/users");

        Wrapper cardsServletWrapper =
                Tomcat.addServlet(tomcatContext, "cardsServlet", new CardsController());
        cardsServletWrapper.addMapping("/cards");

        Wrapper likesServletWrapper =
                Tomcat.addServlet(tomcatContext, "likesServlet", new LikesController());
        likesServletWrapper.addMapping("/cards/likes");

        tomcat.start();
    }

}