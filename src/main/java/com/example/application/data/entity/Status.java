package com.example.application.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.example.application.data.AbstractEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Status extends AbstractEntity{
    private String name;

    public Status() {
    }

    public Status(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
