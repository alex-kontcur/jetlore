package com.jetlore.model;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Item
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class Item implements Element {

    private String content;

    public Item(String content) {
        this.content = checkNotNull(content);
    }

    @Override
    public String expose() {
        return content;
    }
}
