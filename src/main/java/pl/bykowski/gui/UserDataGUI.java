package pl.bykowski.gui;


import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.bykowski.domains.Person;
import pl.bykowski.manager.PersonManager;

@SpringUI(path = "/user-data")
public class UserDataGUI extends UI {

    private PersonManager personManager;

    @Autowired
    public UserDataGUI(PersonManager personManager) {
        this.personManager = personManager;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Long userId = Long.parseLong(vaadinRequest.getParameter("userId"));
        Person personById = personManager.getPersonById(userId);

        VerticalLayout verticalLayout = new VerticalLayout();

        TextField textFieldName = new TextField();
        textFieldName.setPlaceholder("Name");
        textFieldName.setValue(personById.getName());

        TextField textFieldSurname = new TextField();
        textFieldSurname.setPlaceholder("Surname");
        textFieldSurname.setValue(personById.getSurname());

        TextField textFieldAge = new TextField();
        textFieldAge.setPlaceholder("Age");

        Button buttonAddPerson = new Button("Save");
        buttonAddPerson.addClickListener(event ->
                {
                    boolean updateComplete = personManager.update(userId, textFieldName.getValue(), textFieldSurname.getValue(), textFieldAge.getValue());
                    if (updateComplete) {
                        Notification.show("Update complete", Notification.Type.TRAY_NOTIFICATION);
                    } else {
                        Notification.show("Problem :(", Notification.Type.ERROR_MESSAGE);
                    }
                }
        );

        verticalLayout.addComponent(textFieldName);
        verticalLayout.addComponent(textFieldSurname);
        verticalLayout.addComponent(textFieldAge);
        verticalLayout.addComponent(buttonAddPerson);
        setContent(verticalLayout);
    }
}
