/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.composite;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-19 8:59
 */
public class MenuItem extends MenuComponent {
    private String name;
    private String description;
    private boolean vegetarian;
    private double price;

    public MenuItem(String name, String description, boolean vegetarian, double price) {
        this.name = name;
        this.description = description;
        this.vegetarian = vegetarian;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isVegetarian() {
        return vegetarian;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void print() {
        System.out.println("==========start=============");
        System.out.printf("name: %s  price: ￥%s%n", this.getName(), this.getPrice());
        System.out.printf("description: %s  isVegetarian: %s%n", this.getDescription(), this.isVegetarian());
        System.out.println("==========end=============");
    }
}
