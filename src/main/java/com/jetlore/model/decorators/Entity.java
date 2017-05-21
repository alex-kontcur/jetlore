package com.jetlore.model.decorators;

import com.jetlore.model.Element;

/**
 * Entity
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class Entity extends AbstractDecorator {

    public Entity(Element element) {
        super(element);
    }

    @Override
    public String expose() {
        return "<strong>" + element.expose() + "</strong>";
    }
}
