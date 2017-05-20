package com.jetlore.model.decorators;

import com.jetlore.model.Item;

/**
 * Entity
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class Entity extends AbstractDecorator {

    public Entity(Item item) {
        super(item);
    }

    @Override
    public String expose() {
        return "<strong>" + item.expose() + "</strong>";
    }
}
