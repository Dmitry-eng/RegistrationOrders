package com.service.modalWindow.order;


import com.model.Order;
import com.service.grids.OrderGrid;
import com.vaadin.ui.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;


public class UpdateOrder extends AbstractWindowOrder {

    private Order order;


    public UpdateOrder(Order order, OrderGrid orderGrid) {
        super(orderGrid);
        this.order = order;
        creature.setEnabled(false);
        id.setEnabled(false);
        center();
        creature.setValue(order.getCreature().toLocalDateTime());
        id.setValue(String.valueOf(order.getId()));
        description.setValue(order.getDescription());
        creature.setValue(order.getCreature().toLocalDateTime());
        //if (order.getCompletion() != null) creature.setValue(order.getCreature().toLocalDateTime());
        if (order.getCompletion() != null) completion.setValue(order.getCompletion().toLocalDateTime());
        clientComboBox.setItems(clientRepository.findAll());
        clientComboBox.setValue(order.getClient());
        mechanicComboBox.setItems(mechanicRepository.findAll());
        mechanicComboBox.setValue(order.getMechanic());
        price.setValue(String.valueOf(order.getPrice()));
        status.setEmptySelectionAllowed(false);
        status.setItems(statusRepository.findAll());
        status.setValue(order.getStatus());
        completionDateInit();
        setContent(getComponents());
    }


    private void completionDateInit() {
        completion.addValueChangeListener(listener ->
        {
            if (creature.getValue().isAfter(completion.getValue())) completion.setValue(null);
        });
    }


    @Override
    protected VerticalLayout getComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Редактирование заказа"), id, description, clientComboBox, mechanicComboBox, creature, completion,
                price, status, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }

    @Override
    protected void accept() {
        Order order;
        if(completion.getValue()!=null) order = new Order(this.order.getId(), description.getValue(), clientComboBox.getValue(), mechanicComboBox.getValue(), this.order.getCreature(), getDate(completion), Integer.parseInt(price.getValue().replace(" ", "")), status.getValue());
        else
            order = new Order(this.order.getId(), description.getValue(), clientComboBox.getValue(), mechanicComboBox.getValue(), this.order.getCreature(), Integer.parseInt(price.getValue().replace(" ", "")), status.getValue());
        orderRepository.update(order);
        grid.refresh();
        setVisible(false);
    }

    private Timestamp getDate(DateTimeField dateTimeField) {
        if (dateTimeField.getValue() == null) dateTimeField = new DateTimeField();
        LocalDateTime localDateTime = dateTimeField.getValue();
        return Timestamp.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
