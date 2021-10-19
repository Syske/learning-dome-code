/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.composite;

import java.util.ArrayList;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-19 9:02
 */
public class Menu extends MenuComponent {
    ArrayList<Component> menuComponents = new ArrayList<>();
    String name;
    String description;

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public void add(Component component) {
        menuComponents.add(component);
    }

    @Override
    public void remove(Component component) {
        menuComponents.remove(component);
    }

    @Override
    public Component getChild(int i) {
        return menuComponents.get(i);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void print() {
        super.print();
    }
}
