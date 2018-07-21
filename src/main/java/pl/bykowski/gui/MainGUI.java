package pl.bykowski.gui;


import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.bykowski.manager.PersonManager;

@SpringUI(path = "/main")
public class MainGUI extends UI {

    private PersonManager personManager;

    @Autowired
    public MainGUI(PersonManager personManager) {
        this.personManager = personManager;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        VerticalLayout verticalLayout = new VerticalLayout();

        TextField textFieldName = new TextField();
        textFieldName.setPlaceholder("Name");

        TextField textFieldSurname = new TextField();
        textFieldSurname.setPlaceholder("Surname");

        Button buttonAddPerson = new Button("Add");
        buttonAddPerson.addClickListener(event ->
                {
                    Long personId = personManager.addPerson(textFieldName.getValue(), textFieldSurname.getValue());
                    if (personId > 0) {
                        Page.getCurrent().open("/user-data?userId=" + personId, null);

                    } else {
                        Notification.show("Problem :(", Notification.Type.ERROR_MESSAGE);
                    }
                }
        );

        verticalLayout.addComponent(textFieldName);
        verticalLayout.addComponent(textFieldSurname);
        verticalLayout.addComponent(buttonAddPerson);
        setContent(verticalLayout);
    }
}
