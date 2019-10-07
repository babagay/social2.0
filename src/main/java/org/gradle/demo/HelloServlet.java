package org.gradle.demo;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.function.Predicate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"}, loadOnStartup = 1) 
public class HelloServlet extends HttpServlet {

    public static final Logger LOGGER = LoggerFactory.getLogger(HelloServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

//        response.getWriter().print("Hello, World!"); // OK

        request.setAttribute("user", "USR");
        request.setAttribute("invalid", !isReqDataValid());
        System.out.println(!isReqDataValid());
        // При использовании такого пути "../WEB-INF/JSP/response.jsp" выскакивает NPE
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }

    /**
     * For testing use content-type: x-www-form-urlencoded
     * При использовании контент-тайп raw ("name": "Foo")
     * на серверной стороне печатается объект {"name": "Fvoo "=[]}, а поле name игнорится.
     * При использовании x-www-form-urlencoded объект request.getParameterMap() выглядит так:
     * {name=[Foo-]}
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        LOGGER.info("doPost is {}", request.getParameterMap()); // not working

        String name = request.getParameter("name");

        if (!isReqDataValid()){ // Request data is invalid
            request.setAttribute("user", "");
            request.setAttribute("invalid", true);
            request.getRequestDispatcher("response.jsp").forward(request, response);
            return;
        }

        // ...
        // Здесь идет обращени к базе. Данные подтягиваются через сервис

        request.setAttribute("invalid", false);
        if (name == null) name = "World";
        request.setAttribute("user", name);
        request.getRequestDispatcher("response.jsp").forward(request, response); 
    }

    // Для валидации можно подключать отдельный сервис - передавать туда мапу с входыми параметрами
    private boolean isReqDataValid(){
        return true;
    }
}
