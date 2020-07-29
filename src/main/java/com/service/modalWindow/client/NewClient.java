package com.service.modalWindow.client;


import com.model.Client;
import com.service.grids.ClientGrig;
import com.vaadin.ui.*;


public class NewClient extends AbstractWindowClient {

    public NewClient(ClientGrig clientGrig) {
        super(clientGrig);
        setContent(initComponents());
    }

    @Override
    protected VerticalLayout initComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Создание нового клиента"), name, surname, middleName,
                phoneNumber, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }

    @Override
    protected void accept() {
        Client client = new Client(name.getValue(), surname.getValue(), middleName.getValue(), Long.parseLong(phoneNumber.getValue()));
        clientRepository.insert(client);
        clientGrig.refresh();
        setVisible(false);
    }
}
