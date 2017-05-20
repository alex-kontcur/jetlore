package com.jetlore.model.decorators;

import com.jetlore.model.Item;

/**
 * Plain
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class Plain extends AbstractDecorator {

    public Plain(Item item) {
        super(item);
    }

    @Override
    public String expose() {
        return item.expose();
    }
}
