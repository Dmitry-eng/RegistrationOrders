package com.service.modalWindow.mechanic;


import com.model.Mechanic;
import com.repository.MechanicDAO;
import com.repository.MechanicDAOImpl;
import com.service.grids.MechanicGrid;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class AbstractWindowMechanic extends Window {
    protected MechanicDAO mechanicRepository = new MechanicDAOImpl();
    protected TextField id = new TextField();
    protected TextField name = new TextField("Имя");
    protected TextField surname = new TextField("Фамилия");
    protected TextField middleName = new TextField("Отчество");
    protected TextField hourPrice = new TextField("Почасовая оплата");
    protected Button accept = new Button("Создать");
    protected Button cancel = new Button("Отменить");
    protected MechanicGrid grid;

    protected AbstractWindowMechanic(MechanicGrid mechanicGrid) {
        this.grid = mechanicGrid;
        setModal(true);
        setClosable(false);
        setResizable(false);
        accept.setEnabled(false);
        validationInit();
        center();
        acceptButtonInit();
        cancelButtonInit();
        setContent(initComponents());
    }

    private void validationInit() {
        Binder<Mechanic> binder = new Binder<>(Mechanic.class);
        binder.forField(name).
                asRequired("Поле не может быть пустое").
                bind("name");
        binder.forField(surname).
                asRequired("Поле не может быть пустое").
                bind("surname");
        binder.forField(middleName).
                asRequired("Поле не может быть пустое").
                bind("middleName");
        binder.forField(hourPrice).
                withConverter(new StringToIntegerConverter(
                        "Проверьте правильность заполнения поля")).asRequired("Проверьте правильность заполнения поля")
                .withValidator(number -> number > 0, "Проверьте правильность заполнения поля")
                .bind("hourPrice");
        binder.addStatusChangeListener(statusChangeEvent -> accept.setEnabled(binder.isValid()));
    }

    private void cancelButtonInit() {
        cancel.addClickListener(clickEvent -> setVisible(false));
    }

    private void acceptButtonInit() {
        accept.addClickListener(clickEvent -> accept());
    }

    protected abstract void accept();

    protected abstract VerticalLayout initComponents();
}
