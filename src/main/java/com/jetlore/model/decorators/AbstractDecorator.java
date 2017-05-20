package com.jetlore.model.decorators;

import com.jetlore.model.Element;
import com.jetlore.model.Item;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * AbstractDecorator
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public abstract class AbstractDecorator implements Element {

    protected Item item;

    protected AbstractDecorator(Item item) {
        this.item = checkNotNull(item);
    }
}
