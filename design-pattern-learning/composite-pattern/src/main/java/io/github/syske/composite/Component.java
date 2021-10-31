/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.composite;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-19 8:54
 */
public interface Component {
    void add(Component component);
    void remove(Component component);
    Component getChild(int i);
}
