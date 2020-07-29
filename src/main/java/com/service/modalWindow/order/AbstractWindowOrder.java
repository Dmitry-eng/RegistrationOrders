package com.service.modalWindow.order;

import com.model.Client;
import com.model.Mechanic;
import com.model.Order;
import com.model.Status;
import com.repository.*;
import com.service.grids.OrderGrid;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public abstract class AbstractWindowOrder extends Window {
    protected OrderDAO orderRepository = new OrderDAOImpl();
    protected ClientDAO clientRepository = new ClientDAOImpl();
    protected MechanicDAO mechanicRepository = new MechanicDAOImpl();
    protected StatusDAO statusRepository = new StatusDAOImpl();
    protected TextField id = new TextField("Идентификатор");
    protected TextArea description = new TextArea("Описание");
    protected ComboBox<Client> clientComboBox = new ComboBox<>("Клиент");
    protected ComboBox<Mechanic> mechanicComboBox = new ComboBox<>("Механик");
    protected DateTimeField creature = new DateTimeField("Дата регистрации");
    protected DateTimeField completion = new DateTimeField("Дата завершения");
    protected TextField price = new TextField("Сумма заказа");
    protected Button accept = new Button("Сохранить");
    protected Button cancel = new Button("Отменить");
    protected ComboBox<Status> status = new ComboBox<>("Статус заказа");
    protected OrderGrid grid;

    protected AbstractWindowOrder(OrderGrid orderGrid) {
        this.grid = orderGrid;
        clientComboBoxInit();
        mechanicComboBoxInit();
        validationInit();
        acceptButtonInit();
        cancelButtonInit();
        setModal(true);
        setClosable(false);
        setResizable(false);
        creatureDateFieldInit();
    }


    private void clientComboBoxInit() {
        clientComboBox.setItems(clientRepository.findAll());
    }

    private void mechanicComboBoxInit() {
        mechanicComboBox.setItems(mechanicRepository.findAll());
    }

    private void validationInit() {
        Binder<Order> binder = new Binder<>(Order.class);
        binder.forField(description).
                asRequired("Поле не может быть пустое").
                bind("description");
        binder.forField(clientComboBox).
                asRequired("Необходимо указать клиента").
                bind("client");
        binder.forField(mechanicComboBox).
                asRequired("Необходимо указать механика").
                bind("mechanic");
        binder.forField(price).
                withConverter(new StringToIntegerConverter(
                        "Проверьте правильность заполнения суммы заказа")).asRequired("ППроверьте правильность заполнения суммы заказа")
                .withValidator(number -> number > 0 && number < 10000000, "Проверьте правильность заполнения суммы заказа")
                .bind("price");
        binder.addStatusChangeListener(statusChangeEvent -> accept.setEnabled(binder.isValid()));
    }
    private void acceptButtonInit() {
        accept.setEnabled(false);
        accept.addClickListener(clickEvent -> accept());
    }
    protected abstract void accept();
    protected  abstract VerticalLayout getComponents();
    private void cancelButtonInit() {
        cancel.addClickListener(clickEvent -> setVisible(false));
    }

    private void creatureDateFieldInit() {
        creature.setEnabled(false);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        creature.setValue(LocalDateTime.now());
        creature.setDateFormat(simpleDateFormat.toPattern());
    }

    protected Timestamp getDate() {
        return Timestamp.valueOf(creature.getValue());
    }

}
