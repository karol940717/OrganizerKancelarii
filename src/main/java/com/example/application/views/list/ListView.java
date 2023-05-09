package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;


@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("J&R Kancelaria")
@PermitAll
public class ListView extends VerticalLayout {
    Grid<Contact> grid = new Grid<>(Contact.class,false);
    TextField filterText = new TextField();
    ContactForm form = new ContactForm();
    CrmService service;

    public ListView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        setWidthFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

private void configureForm() {

    form.setWidth("25em");
    form.addListener(ContactForm.SaveEvent.class, this::saveContact);
    form.addListener(ContactForm.DeleteEvent.class, this::deleteContact);
    form.addListener(ContactForm.CloseEvent.class, e -> closeEditor());
}

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();

        grid.addColumn(Contact::getDate).setHeader("Data").setSortable(true);
        grid.addColumn(Contact::getClaimant).setHeader("Powód");
        grid.addColumn(Contact::getDefendant).setHeader("Pozwany");
        grid.addColumn(Contact::getSignature).setHeader("SYGN. AKT");
        grid.addColumn(Contact::getPersonWhichGo).setHeader("Kto idzie");
        grid.addColumn(Contact::getComments).setHeader("Uwagi");
        grid.addColumn(Contact::getVictim).setHeader("Poszkodowany");
        grid.addColumn(Contact::getRoom).setHeader("Sala");
        grid.addColumn(Contact::getTime).setHeader("Godzina");
        grid.addColumn(Contact::getCourt).setHeader("Sąd");
        grid.addColumn(Contact::getStatus).setHeader("Osoba");

        grid.asSingleSelect().addValueChangeListener(event ->
            editContact(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Sortuj po imieniu...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Dodaj");
        addContactButton.addClickListener(click -> addContact());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveContact(ContactForm.SaveEvent event) {
        service.saveContact(event.getContact());
        updateList();
        closeEditor();
    }

    private void deleteContact(ContactForm.DeleteEvent event) {
        service.deleteContact(event.getContact());
        updateList();
        closeEditor();
    }

    public void editContact(Contact contact) {
        if (contact == null) {
            closeEditor();
        } else {
            form.setContact(contact);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void closeEditor() {
        form.setContact(null);
        form.setVisible(false);
        removeClassName("editing");
    }
    private void updateList() {
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }


}
