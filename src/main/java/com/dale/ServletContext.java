/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
/**
 *
 * @author dmiller
 * NOTE: This class is called before and after the container (tomcat) is loaded
 */
@WebListener
public class ServletContext implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        //ServletContext ctx = arg0.getServletContext();
        WebDriver chromeWebDriver = null;
        event.getServletContext().setAttribute("chromeWebDriver", chromeWebDriver);
        System.out.println("---SeleniumTrial: chromeWebDriver loaded into memory");		

    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        event.getServletContext().removeAttribute("chromeWebDriver");
        System.out.println("---seleniumTrial: chromeWebDriver removed from memory");
    }
}