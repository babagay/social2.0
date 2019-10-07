package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

// [!] НЕ работает
@WebListener
public class MyListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        ServletContext sc = sce.getServletContext();
        String path = sc.getInitParameter("path");
        String mode = sc.getInitParameter("mode");
        sc.setAttribute("filePath", path);
        sc.setAttribute("fileMode", mode);
        System.out.println("context Initialized. Value saved in context.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        ServletContext sc = sce.getServletContext();
        sc.removeAttribute("path");
        sc.removeAttribute("mode");
        System.out.println("context Destroyed. Value deleted from context.");

    }
}
