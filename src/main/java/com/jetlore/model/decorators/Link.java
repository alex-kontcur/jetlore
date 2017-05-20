package com.jetlore.model.decorators;

import com.jetlore.model.Item;

/**
 * Link
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class Link extends AbstractDecorator {

    public Link(Item item) {
        super(item);
    }

    @Override
    public String expose() {
        String value = item.expose();
        return "<a href=\"" + value + "\">" + value + "</a>";
    }
}
