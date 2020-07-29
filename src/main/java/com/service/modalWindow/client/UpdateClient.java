package com.service.modalWindow.client;

import com.model.Client;
import com.service.grids.ClientGrig;
import com.vaadin.ui.*;


public class UpdateClient extends AbstractWindowClient {
    private Client client;

    public UpdateClient(Client client, ClientGrig clientGrig) {
        super(clientGrig);
        this.clientGrig = clientGrig;
        this.client = client;
        this.id.setValue(String.valueOf(client.getId()));
        this.name.setValue(client.getName());
        this.surname.setValue(client.getSurname());
        this.middleName.setValue(client.getMiddleName());
        this.phoneNumber.setValue(String.valueOf(client.getPhoneNumber()));
        this.id.setEnabled(false);
        setContent(initComponents());
    }


    @Override
    protected VerticalLayout initComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Редактирование клиента"), id, name, surname, middleName,
                phoneNumber, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }

    @Override
    protected void accept() {
        Client client = new Client(this.client.getId(), name.getValue(), surname.getValue(), middleName.getValue(), Long.parseLong(phoneNumber.getValue()));
        clientRepository.update(client);
        clientGrig.refresh();
        setVisible(false);
    }

}