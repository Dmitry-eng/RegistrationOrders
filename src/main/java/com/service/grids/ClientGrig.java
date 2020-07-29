package com.service.grids;

import com.model.Client;
import com.project.ClientUI;
import com.repository.ClientDAO;
import com.repository.ClientDAOImpl;
import com.service.modalWindow.client.DeleteClient;
import com.service.modalWindow.client.NewClient;
import com.service.modalWindow.client.UpdateClient;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;


public class ClientGrig {
    private Client client;
    private Button addClient = new Button("Новый клиент");
    private Button updateClient = new Button("Редактировать");
    private Button deleteClient = new Button("Удалить");
    private Button clearFilterText = new Button(VaadinIcons.CLOSE);
    private TextField filterText = new TextField();
    private CssLayout filter = new CssLayout();
    private ClientDAO clientRepository = new ClientDAOImpl();
    private Grid<Client> grid = new Grid<>(Client.class);
    private ClientUI clientUI;


    public ClientGrig(ClientUI clientUI) {
        gridInit();
        refresh();
        clearFilterTextInit();
        addClientButtonInit();
        updateClientButtonInit();
        deleteClientButtonInit();
        filterTextInit();
        this.clientUI = clientUI;
    }

    private void gridInit() {
        grid.setColumns("id", "name", "surname", "middleName", "phoneNumber");
        grid.getColumn("name").setCaption("Имя");
        grid.getColumn("surname").setCaption("Фамилия");
        grid.getColumn("middleName").setCaption("Отчество");
        grid.getColumn("phoneNumber").setCaption("Номер телефона");
        grid.setSizeFull();
       filter.addComponents(filterText, clearFilterText, addClient, updateClient, deleteClient);
        grid.addItemClickListener((ItemClickListener<Client>) itemClick -> {
            client = itemClick.getItem();
            deleteClient.setEnabled(true);
            updateClient.setEnabled(true);
        });
    }

    private void clearFilterTextInit() {
        clearFilterText.addClickListener(e -> filterText.clear());
    }


    private void filterTextInit() {
        filterText.addValueChangeListener(valueChangeEvent -> refresh());
    }

    public VerticalLayout getVerticalLayout() {
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(filterText, clearFilterText);
        verticalLayout.addComponents(horizontalLayout, filter, grid);
        return verticalLayout;
    }

    private void addClientButtonInit() {
        this.addClient.addClickListener(clickEvent -> {
            clientUI.addWindow(new NewClient(this));
        });
    }

    private void updateClientButtonInit() {
        this.updateClient.addClickListener(clickEvent -> clientUI.addWindow(new UpdateClient(this.client, this)));
    }

    private void deleteClientButtonInit() {
        this.deleteClient.addClickListener(clickEvent -> clientUI.addWindow(new DeleteClient(this.client, this)));
    }

    public final void refresh() {
        this.deleteClient.setEnabled(false);
        this.updateClient.setEnabled(false);
        this.client = null;
        grid.setItems(clientRepository.findAllByFragments(filterText.getValue()));
    }
}
