package com.service.grids;


import com.model.Mechanic;
import com.project.MechanicUI;
import com.repository.MechanicDAO;
import com.repository.MechanicDAOImpl;
import com.service.modalWindow.mechanic.DeleteMechanic;
import com.service.modalWindow.mechanic.NewMechanic;
import com.service.modalWindow.mechanic.StatisticsMechanic;
import com.service.modalWindow.mechanic.UpdateMechanic;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.ItemClickListener;

public class MechanicGrid {
    private Mechanic mechanic;
    private MechanicUI mechanicUI;
    private MechanicDAO mechanicRepository = new MechanicDAOImpl();
    private Grid<Mechanic> grid = new Grid<>(Mechanic.class);
    private TextField filterText = new TextField();
    private Button clearFilterText = new Button(VaadinIcons.CLOSE);
    private CssLayout filter = new CssLayout();
    private Button insert = new Button("Добавить");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Изменить");
    private Button stat = new Button("Показать статистику");

    public MechanicGrid(MechanicUI mechanicUI) {

        this.mechanicUI = mechanicUI;
        gridInit();
        filterTextInit();
        addClientButtonInit();
        updateClientButtonInit();
        deleteClientButtonInit();
        clearFilterTextInit();
        filterTextInit();
        statButtonInit();
        refresh();
    }

    private void gridInit() {
        grid.setColumns("id", "name", "surname", "middleName", "hourPrice");
        grid.getColumn("name").setCaption("Имя");
        grid.getColumn("surname").setCaption("Фамилия");
        grid.getColumn("middleName").setCaption("Отчество");
        grid.getColumn("hourPrice").setCaption("Почасовая оплата");
        grid.setSizeFull();
            filter.addComponents(filterText, clearFilterText, insert, update, delete, stat);
            grid.addItemClickListener((ItemClickListener<Mechanic>) itemClick -> {
            mechanic = itemClick.getItem();
            delete.setEnabled(true);
            update.setEnabled(true);
            stat.setEnabled(true);

        });
    }

    public final void refresh() {
        this.stat.setEnabled(false);
        this.delete.setEnabled(false);
        this.update.setEnabled(false);
        mechanic = null;
       grid.setItems(mechanicRepository.findAllByFragments(filterText.getValue()));
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
        this.insert.addClickListener(clickEvent -> {
            mechanicUI.addWindow(new NewMechanic(this));
        });
    }

    private void updateClientButtonInit() {
        this.update.addClickListener(clickEvent -> mechanicUI.addWindow(new UpdateMechanic(this.mechanic, this)));
    }

    private void deleteClientButtonInit() {
        this.delete.addClickListener(clickEvent -> mechanicUI.addWindow(new DeleteMechanic(this.mechanic, this)));
    }
    private  void statButtonInit(){
        this.stat.addClickListener(
                clickEvent -> mechanicUI.addWindow(new StatisticsMechanic(this.mechanic)));
    }

}
