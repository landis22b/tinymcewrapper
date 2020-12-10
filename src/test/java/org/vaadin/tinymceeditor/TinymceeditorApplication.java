package org.vaadin.tinymceeditor;

import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import elemental.json.JsonArray;
import org.apache.tools.ant.taskdefs.Java;

public class TinymceeditorApplication extends UI {

    private TinyMCETextField tinyMCETextField;

    @Override
    protected void init(VaadinRequest request) {

        setPollInterval(4000);

        VerticalLayout content = new VerticalLayout();
        content.setSpacing(true);
        content.setMargin(true);

        content.addComponent(new Button("Show Html in editor 1", new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                Notification notification = new Notification("Content in editor 1");
                notification.setHtmlContentAllowed(false);
                notification.setDescription(tinyMCETextField.getValue());
                notification.show(Page.getCurrent());
            }
        }));
        tinyMCETextField = new TinyMCETextField();
        content.addComponent(tinyMCETextField);
        tinyMCETextField.addListener(new Listener() {
            @Override
            public void componentEvent(Event event) {
                System.out.println(event);
            }
        });

        tinyMCETextField.setValue("Some test content<h1>Vaadin rocks!</h1>");

        tinyMCETextField.addValueChangeListener(event -> {
                    new Notification("Content now: " + event.getValue(), "", Type.HUMANIZED_MESSAGE, true).show(Page.getCurrent());
                }
        );

        JavaScript.getCurrent().addFunction("tinyMCESelectionChange", new JavaScriptFunction() {
            @Override
            public void call(JsonArray jsonArray) {
                System.out.println(jsonArray);
                System.out.println("got change");
            }
        });


        tinyMCETextField.setConfig(tinyMCETextField.getState().conf);

        setContent(content);

    }

}
