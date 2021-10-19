package io.github.syske.iterator.impl;

import io.github.syske.iterator.Container;
import io.github.syske.iterator.Iterator;

/**
 * @program: design-pattern-learning
 * @description:
 * @author: syske
 * @date: 2021-10-18 13:12
 */
public class SyskeContainer implements Container {
    Object[] container = {"syske", "云中志", "志哥"};

    @Override
    public Iterator iterator() {
        return new SyskeIterator();
    }
    private class SyskeIterator implements Iterator {
        int index;
        @Override
        public boolean hasNext() {
            if(index < container.length){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if (hasNext()) {
                return container[index++];
            }
            return null;
        }
    }
}
