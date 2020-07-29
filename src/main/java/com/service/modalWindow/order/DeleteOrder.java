package com.service.modalWindow.order;


import com.model.Order;
import com.service.grids.OrderGrid;
import com.vaadin.ui.*;


public class DeleteOrder extends AbstractWindowOrder {

    private DateTimeField completion = new DateTimeField();
    private Order order;

    public DeleteOrder(Order order, OrderGrid orderGrid) {
        super(orderGrid);
        this.order = order;
        id.setEnabled(false);
        description.setEnabled(false);
        mechanicComboBox.setEnabled(false);
        clientComboBox.setEnabled(false);
        creature.setEnabled(false);
        completion.setEnabled(false);
        price.setEnabled(false);
        accept.setCaption("Удалить");
        id.setValue(String.valueOf(order.getId()));
        description.setValue(order.getDescription());
        clientComboBox.setValue(order.getClient());
        mechanicComboBox.setValue(order.getMechanic());
        creature.setValue(order.getCreature().toLocalDateTime());
        if (order.getCompletion() != null) creature.setValue(order.getCompletion().toLocalDateTime());
        price.setValue(String.valueOf(order.getPrice()));
        status.setValue(order.getStatus());
        setContent(getComponents());
    }

    @Override
    protected VerticalLayout getComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Подтвердите удаление"), id, description, mechanicComboBox, clientComboBox,
                creature, completion, price, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }

    @Override
    protected void accept() {
        boolean bool = orderRepository.deleteById(order.getId());
        if(bool)  Notification.show("Заказ удален.", Notification.Type.TRAY_NOTIFICATION);
        else
            Notification.show("Не удалось удалить заказ.", Notification.Type.WARNING_MESSAGE);
        grid.refresh();
        close();
    }

}
