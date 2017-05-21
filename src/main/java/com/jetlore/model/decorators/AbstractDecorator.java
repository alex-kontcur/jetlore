package com.jetlore.model.decorators;

import com.jetlore.model.Element;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * AbstractDecorator
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public abstract class AbstractDecorator implements Element {

    protected Element element;

    protected AbstractDecorator(Element element) {
        this.element = checkNotNull(element);
    }
}
