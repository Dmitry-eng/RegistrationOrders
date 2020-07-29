package com.service.grids;

import com.model.Client;
import com.model.Order;
import com.model.Status;
import com.project.OrderUI;
import com.repository.*;
import com.service.modalWindow.order.DeleteOrder;
import com.service.modalWindow.order.NewOrder;
import com.service.modalWindow.order.UpdateOrder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;


public class OrderGrid {
    private Order order;
    private OrderUI orderUI;
    private NativeSelect<Status> status = new NativeSelect<>();
    private Button clear = new Button(VaadinIcons.CLOSE);
    private TextField description = new TextField();
    private ComboBox<Client> clientListBox = new ComboBox<>();
    private StatusDAO statusRepository = new StatusDAOImpl();
    private ClientDAO clientRepository = new ClientDAOImpl();
    private OrderDAO orderRepository = new OrderDAOImpl();
    private Grid<Order> grid = new Grid<>(Order.class);
    private Button apply = new Button("Применить");
    private CssLayout filter = new CssLayout();
    private Button insert = new Button("Добавить");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");

    public OrderGrid(OrderUI orderUI) {
        this.orderUI = orderUI;
        gridInit();
        addOrderButtonInit();
        updateOrderButtonInit();
        deleteOrderButtonInit();
        clientListBoxInit();
        applyButtonInit();
        clearInit();
        statusInit();
        refresh();
    }

    private void clearInit() {
        clear.addClickListener(clickEvent -> {
            clientListBox.clear();
            status.clear();
            description.clear();
            refresh();
        });
    }

    private void gridInit() {
        grid.setColumns("id", "description", "client", "mechanic", "creature", "completion", "price", "status");
        grid.getColumn("description").setCaption("Описание");
        grid.getColumn("client").setCaption("Клиент");
        grid.getColumn("mechanic").setCaption("Механик");
        grid.getColumn("creature").setCaption("Создание");
        grid.getColumn("completion").setCaption("Завершение");
        grid.getColumn("price").setCaption("Сумма заказа");
        grid.getColumn("status").setCaption("Статус заказа");
        grid.setSizeFull();
        filter.addComponents(insert, update, delete);
        grid.addItemClickListener((ItemClickListener<Order>) itemClick -> {
            order = itemClick.getItem();
            delete.setEnabled(true);
            update.setEnabled(true);
        });


    }

    public final void refresh() {
        this.delete.setEnabled(false);
        this.update.setEnabled(false);
        order = null;
        grid.setItems(orderRepository.findAll());
    }



    public VerticalLayout getVerticalLayout() {
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        horizontalLayout1.addComponents(clientListBox, status, description, apply, clear);
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        verticalLayout.addComponents(horizontalLayout, filter, horizontalLayout1, grid);
        return verticalLayout;
    }

    private void applyButtonInit() {
        apply.addClickListener(listener -> {
            String text = description.getValue();
            grid.setItems(orderRepository.findAllByClientOrStatusOrDescription(this.clientListBox.getValue(), this.status.getValue(), text));
        });
    }

    private void addOrderButtonInit() {
        this.insert.addClickListener(clickEvent ->
            orderUI.addWindow(new NewOrder(this))
        );
    }

    private void clientListBoxInit() {
        clientListBox.setWidth("400px");
        clientListBox.setItems(clientRepository.findAll());
    }

    private void statusInit() {
        status.setWidthUndefined();
        status.setItems(statusRepository.findAll());
    }

    private void updateOrderButtonInit() {
        this.update.addClickListener(clickEvent -> orderUI.addWindow(new UpdateOrder(this.order, this)));
    }

    private void deleteOrderButtonInit() {
        this.delete.addClickListener(clickEvent -> orderUI.addWindow(new DeleteOrder(this.order, this)));
    }


}
