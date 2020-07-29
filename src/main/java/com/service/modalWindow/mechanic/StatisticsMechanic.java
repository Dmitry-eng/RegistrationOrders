package com.service.modalWindow.mechanic;

import com.model.Mechanic;
import com.model.Order;
import com.model.Status;
import com.repository.OrderDAO;
import com.repository.OrderDAOImpl;
import com.vaadin.ui.*;


public class StatisticsMechanic extends Window {
    private Mechanic mechanic;
    private TextField mechanicTextField = new TextField("Механик");
    private OrderDAO orderRepository = new OrderDAOImpl();
    private Grid<Order> shedule = new Grid<>(Order.class);
    private Grid<Order> completed = new Grid<>(Order.class);
    private Grid<Order> accepted = new Grid<>(Order.class);
    private TextField totalQuantilyOrder = new TextField("Общее количество заказов");
    private TextField totalNumberOrder = new TextField("Всего заказов на сумму");
    private Button cancel = new Button("Закрыть");

    public StatisticsMechanic( Mechanic mechanic) {
        this.mechanic = mechanic;
        cancelButtonInit();
        mechanicTextField.setValue(mechanic.toString());
        setModal(true);
        setClosable(false);
        setResizable(false);
        center();
        setMechanicTextFieldInit();
        sheduleInit();
        completedInit();
        acceptedInit();
        totalNumberOrderInit();
        totalQuantityOrderInit();
        setContent(initComponents());

    }

    private VerticalLayout initComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Статистика"), mechanicTextField, shedule, completed, accepted,
                totalQuantilyOrder, totalNumberOrder, cancel);
        return verticalLayout;
    }

    private void cancelButtonInit() {
        cancel.addClickListener(clickEvent -> setVisible(false));
    }

    private void setMechanicTextFieldInit() {
        mechanicTextField.setValue(mechanic.toString());
    }

    private void sheduleInit() {
        gridInit(shedule, "Запланированные заказы");
        shedule.setItems(orderRepository.findAllByMechanicAndStatus(mechanic, new Status(1)));
    }

    private void completedInit() {
        gridInit(completed, "Выполненные заказы");
        completed.setItems(orderRepository.findAllByMechanicAndStatus(mechanic, new Status(2)));
    }

    private void acceptedInit() {
        gridInit(accepted, "Заказы принятые клиентом");
        accepted.setItems(orderRepository.findAllByMechanicAndStatus(mechanic, new Status(3)));
    }

    private void totalQuantityOrderInit() {
        totalQuantilyOrder.setEnabled(false);
        totalQuantilyOrder.setValue(String.valueOf(orderRepository.getCountByMechanic(mechanic)));
    }

    private void totalNumberOrderInit() {
        totalNumberOrder.setEnabled(false);
        totalNumberOrder.setValue(String.valueOf(orderRepository.getSumByMechanic(mechanic)));
    }

    private void gridInit(Grid<Order> grid, String caption){
        grid.setCaption(caption);
        grid.setColumns("id", "description", "client", "creature", "completion", "price", "status");
        grid.getColumn("description").setCaption("Описание");
        grid.getColumn("client").setCaption("Клиент");
        grid.getColumn("creature").setCaption("Создание");
        grid.getColumn("completion").setCaption("Завершение");
        grid.getColumn("price").setCaption("Сумма заказа");
        grid.getColumn("status").setCaption("Статус заказа");
    }
}
