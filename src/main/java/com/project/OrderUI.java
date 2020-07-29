package com.project;

import com.service.ButtonObject;
import com.service.grids.OrderGrid;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class OrderUI extends UI {
    OrderGrid orderGrid = new OrderGrid(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        setContent(new VerticalLayout(ButtonObject.getLayout(), orderGrid.getVerticalLayout()));
    }

    @WebServlet(urlPatterns = "/order/*", name = "OrderUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = OrderUI.class, productionMode = false)
    public static class OrderUIServlet extends VaadinServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            new OrderUI();
            super.doGet(req, resp);
        }
    }
}

