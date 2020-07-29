package com.model;

import javax.validation.constraints.*;


import java.sql.Timestamp;


public class Order {
    private long id;
    @NotNull
    @Size(min = 1)
    private String description;
    @NotNull
    private Client client;
    @NotNull
    private Mechanic mechanic;
    @NotNull
    @Future
    private Timestamp creature;

    @NotNull
    @Future
    private Timestamp completion;


    @NotNull
    @Min(0)
    private int price;

    @NotNull
    @Min(0)
    private Status status;

    public Order() {
    }

    public Order(long id, String description, Client client, Mechanic mechanic, Timestamp creature, Timestamp completion, int price, Status status) {
        this.id = id;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
        this.creature = creature;
        this.completion = completion;
        this.price = price;
        this.status = status;
    }

    public Order(String description, Client client, Mechanic mechanic, Timestamp creature, int price, Status status) {
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
        this.creature = creature;
        this.price = price;
        this.status = status;
    }

    public Order(long id, String description, Client client, Mechanic mechanic, Timestamp creature, int price, Status status) {
        this.id = id;
        this.description = description;
        this.client = client;
        this.mechanic = mechanic;
        this.creature = creature;
        this.price = price;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Timestamp getCreature() {
        return creature;
    }

    public void setCreature(Timestamp creature) {
        this.creature = creature;
    }

    public Timestamp getCompletion() {
        return completion;
    }

    public void setCompletion(Timestamp completion) {
        this.completion = completion;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", client=" + client +
                ", mechanic=" + mechanic +
                ", creature=" + creature +
                ", completion=" + completion +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
