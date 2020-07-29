package com.project;

import com.service.ButtonObject;
import com.service.grids.ClientGrig;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ClientUI extends UI {
    private ClientGrig clientGrid = new ClientGrig(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setContent(new VerticalLayout(ButtonObject.getLayout(), clientGrid.getVerticalLayout()));

    }

    @WebServlet(urlPatterns = "/*", name = "ClientUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = ClientUI.class, productionMode = false)
    public static class ClientUIServlet extends VaadinServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            new ClientUIServlet();
            super.doGet(req, resp);
        }
    }
}
