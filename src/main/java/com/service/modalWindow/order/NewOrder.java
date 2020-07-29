package com.service.modalWindow.order;


import com.model.Order;
import com.model.Status;
import com.service.grids.OrderGrid;
import com.vaadin.ui.*;


public class NewOrder extends AbstractWindowOrder {

    public NewOrder(OrderGrid orderGrid) {
        super(orderGrid);
        this.grid = orderGrid;
        setContent(getComponents());
    }

    @Override
    protected void accept() {
        Order order = new Order(description.getValue(), clientComboBox.getValue(), mechanicComboBox.getValue(), getDate(), Integer.parseInt(price.getValue().replace(" ", "")), new Status(1));
        orderRepository.insert(order);
        grid.refresh();
        setVisible(false);
    }

    @Override
    protected VerticalLayout getComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Заказ"), description, clientComboBox, mechanicComboBox,
                creature, price, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }
}
