package com.service.modalWindow.mechanic;


import com.model.Mechanic;
import com.service.grids.MechanicGrid;
import com.vaadin.ui.*;

public class NewMechanic extends AbstractWindowMechanic {

    public NewMechanic(MechanicGrid mechanicGrid) {
        super(mechanicGrid);
        this.grid = mechanicGrid;
        setContent(initComponents());
    }

    @Override
    protected VerticalLayout initComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Добавление сотрудника"), name, surname, middleName,
                hourPrice, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }

    @Override
    protected void accept() {
        Mechanic mechanic = new Mechanic(name.getValue(), surname.getValue(), middleName.getValue(), Integer.parseInt(hourPrice.getValue()));
        mechanicRepository.insert(mechanic);
        grid.refresh();
        setVisible(false);
    }
}
