package com.service;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

public class ButtonObject {
    private ButtonObject(){}


    public static HorizontalLayout getLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        Link buttonClient = new Link("Клиент", new ExternalResource("/"));
        Link buttonMechanic = new Link("Механик", new ExternalResource("/mechanic"));
        Link buttonOrder = new Link("Заказы", new ExternalResource("/order"));
        layout.addComponent(buttonClient);
        layout.addComponent(buttonMechanic);
        layout.addComponent(buttonOrder);
        return layout;
    }
}
