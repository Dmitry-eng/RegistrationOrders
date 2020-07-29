package com.service.modalWindow.mechanic;

import com.model.Mechanic;
import com.service.grids.MechanicGrid;
import com.vaadin.ui.*;

public class UpdateMechanic extends AbstractWindowMechanic {

    private Mechanic mechanic;

    public UpdateMechanic(Mechanic mechanic, MechanicGrid mechanicGrid) {
        super(mechanicGrid);
        this.mechanic = mechanic;
        id.setValue(String.valueOf(mechanic.getId()));
        name.setValue(mechanic.getName());
        surname.setValue(mechanic.getSurname());
        middleName.setValue(mechanic.getMiddleName());
        hourPrice.setValue(String.valueOf(mechanic.getHourPrice()));
        accept.setCaption("Сохранить");
        id.setEnabled(false);
        setContent(initComponents());
    }

    @Override
    protected VerticalLayout initComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Редактирование сотрудника"), id, name, surname, middleName,
                hourPrice, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }

    @Override
    protected void accept() {
        Mechanic mechanic = new Mechanic(this.mechanic.getId(), name.getValue(), surname.getValue(), middleName.getValue(), Integer.parseInt(hourPrice.getValue()));
        mechanicRepository.update(mechanic);
        grid.refresh();
        setVisible(false);
    }
}
