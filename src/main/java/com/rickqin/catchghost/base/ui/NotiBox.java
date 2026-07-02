package com.rickqin.catchghost.base.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

/**
 *
 * @author Rick Qin <rickqinj@gmail.com>
 */
public class NotiBox extends Notification {

    public enum NotiType {
        SUCCESSFUL, WARNING, ERROR;
    }
    
    private HorizontalLayout hl;
    private Span msgbox;
    private Button closeButton;
    private String msg;

    public NotiBox() {
        super();
        msgbox = new Span(); 
        closeButton = new Button(new Icon(VaadinIcon.CLOSE_SMALL));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> {
            close();
        });
        
        hl = new HorizontalLayout();
        hl.setWidthFull();
        hl.setAlignItems(Alignment.CENTER);
        hl.addToStart(msgbox);
        hl.addToEnd(closeButton);
        
        setPosition(Position.MIDDLE);
        
        add(hl);
    }
    
    public void setType(NotiType type) {
        switch (type) {
            case WARNING -> {
                //The noti will disappear in 3 sec.
                setDuration(3000);
                removeThemeVariants(NotificationVariant.LUMO_SUCCESS, NotificationVariant.LUMO_ERROR);
                addThemeVariants(NotificationVariant.LUMO_WARNING);
            }
            case ERROR -> {
                //The error notification won't disappear automatically.
                setDuration(0);
                removeThemeVariants(NotificationVariant.LUMO_SUCCESS, NotificationVariant.LUMO_WARNING);
                addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            default -> {  //successful or others.
                //The noti will disappear in 3 sec.
                setDuration(3000);
                removeThemeVariants(NotificationVariant.LUMO_ERROR, NotificationVariant.LUMO_WARNING);
                addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            }
        }
    }
    
    public void open(String text, NotiType type) {
        setType(type);
        setText(text);
        open();
    }

    @Override
    public void setText(String text) {
        setMessage(text);
    }

    public void setMessage(String msg) {
        this.msg = msg;
        msgbox.setText(msg);
    }
    
}
