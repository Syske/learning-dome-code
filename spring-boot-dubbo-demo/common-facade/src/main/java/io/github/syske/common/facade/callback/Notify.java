/* Copyright © 2021 syske. All rights reserved. */
package io.github.syske.common.facade.callback;

/**
 * @author syske
 * @version 1.0
 * @date 2021-08-17 8:11
 */
public interface Notify {
    void onreturn(String msg);

    void onthrow(Throwable ex);

    void oninvoke(String msg);
}
