package io.github.syske.common.util;

/**
 * @program: dpf-sign-test
 * @description:
 * @author: syske
 * @create: 2019-12-04 09:00
 */
public class Extension {
    private String oid;

    private boolean critical;

    private byte[] value;

    public String getOid() {
        return oid;
    }

    public byte[] getValue() {
        return value;
    }

    public boolean isCritical() {
        return critical;
    }
}
