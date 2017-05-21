package com.jetlore.model.decorators;

import com.jetlore.model.Element;

/**
 * Plain
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class Plain extends AbstractDecorator {

    public Plain(Element element) {
        super(element);
    }

    @Override
    public String expose() {
        return element.expose();
    }
}
