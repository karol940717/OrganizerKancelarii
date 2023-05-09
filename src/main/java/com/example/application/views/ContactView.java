package com.example.application.views;

import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("J&R Kancelaria")
@PermitAll
public class ContactView extends VerticalLayout {
    private final CrmService service;

    public ContactView(CrmService service) {
        this.service = service;
        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

    }

    private Component getContactStats() {
        Span stats = new Span(service.countContacts() + " contacts");
        stats.addClassNames("text-xl", "mt-m");
        return stats;
    }


}