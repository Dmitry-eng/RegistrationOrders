package com.service.modalWindow.mechanic;

import com.model.Mechanic;
import com.service.grids.MechanicGrid;
import com.vaadin.ui.*;

public class DeleteMechanic extends AbstractWindowMechanic {

    private Mechanic mechanic;

    public DeleteMechanic(Mechanic mechanic, MechanicGrid mechanicGrid) {
        super(mechanicGrid);
        this.mechanic = mechanic;
        id.setEnabled(false);
        name.setEnabled(false);
        surname.setEnabled(false);
        middleName.setEnabled(false);
        hourPrice.setEnabled(false);
        id.setValue(String.valueOf(mechanic.getId()));
        name.setValue(mechanic.getName());
        surname.setValue(mechanic.getSurname());
        middleName.setValue(mechanic.getMiddleName());
        hourPrice.setValue(String.valueOf(mechanic.getHourPrice()));
        accept.setCaption("Удалить");
        setContent(initComponents());
    }

    @Override
    protected VerticalLayout initComponents() {
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(new Label("Подтвердите удаление"), id, name, surname, middleName,
                hourPrice, new HorizontalLayout(accept, cancel));
        return verticalLayout;
    }

    @Override
    protected void accept() {
        boolean bool = mechanicRepository.deleteById(mechanic.getId());
        if (bool) Notification.show("Механик удален.", Notification.Type.TRAY_NOTIFICATION);
        else
            Notification.show("Не удалось удалить механика.", Notification.Type.WARNING_MESSAGE);
        grid.refresh();
        close();
    }
}
