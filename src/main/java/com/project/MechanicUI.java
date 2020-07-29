package com.project;

import com.service.ButtonObject;
import com.service.grids.MechanicGrid;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MechanicUI extends UI {
    MechanicGrid mechanicGrid = new MechanicGrid(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setContent(new VerticalLayout(ButtonObject.getLayout(), mechanicGrid.getVerticalLayout()));

    }

    @WebServlet(urlPatterns = "/mechanic/*", name = "MechanicUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MechanicUI.class, productionMode = false)
    public static class MechanicUIServlet extends VaadinServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            new MechanicUI();
            super.doGet(req, resp);
        }
    }
}
