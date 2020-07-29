package com.service.modalWindow.client;

import com.model.Client;
import com.repository.ClientDAO;
import com.repository.ClientDAOImpl;
import com.service.grids.ClientGrig;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public abstract class AbstractWindowClient extends Window {
    protected ClientDAO clientRepository = new ClientDAOImpl();
    protected TextField id = new TextField("Идентификатор");
    protected TextField name = new TextField("Имя");
    protected TextField surname = new TextField("Фамилия");
    protected TextField middleName = new TextField("Отчество");
    protected TextField phoneNumber = new TextField("Номер телефона");
    protected Button accept = new Button("Сохранить");
    protected Button cancel = new Button("Отменить");
    protected ClientGrig clientGrig;
    protected AbstractWindowClient(ClientGrig clientGrig){
        this.clientGrig=clientGrig;
        accept.setEnabled(false);
        center();
        setModal(true);
        setClosable(false);
        setResizable(false);
        cancelButtonInit();
        acceptButtonInit();
        validationInit();
    }
    private   void validationInit() {

        Binder<Client> binder = new Binder<>(Client.class);
        binder.forField(name).
                asRequired("Поле не может быть пустое").
                bind("name");
        binder.forField(surname).
                asRequired("Поле не может быть пустое").
                bind("surname");
        binder.forField(middleName).
                asRequired("Поле не может быть пустое").
                bind("middleName");
        binder.forField(phoneNumber).
                withConverter(new StringToLongConverter(
                        "Проверьте правильность заполнения номера")).asRequired("Проверьте правильность заполнения номера")
                .withValidator(number -> number > 0, "Проверьте правильность заполнения номера")
                .bind("phoneNumber");
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


