/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.iterator.impl;

import io.github.syske.iterator.Container;
import io.github.syske.iterator.Iterator;

/**
 * @author syske
 * @version 1.0
 * @date 2021-10-18 21:17
 */
public class SyskeContainer implements Container {
    public Object[] values = {"云中志" , "java" ,"志哥" , "syske"};

    @Override
    public Iterator getIterator() {
        return new SyskeIterator();
    }

    private class SyskeIterator implements Iterator {

        int index;

        @Override
        public boolean hasNext() {
            if(index < values.length){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            if(this.hasNext()){
                return values[index++];
            }
            return null;
        }
    }
}
