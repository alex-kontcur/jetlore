package com.jetlore.model.decorators;

import com.jetlore.model.Element;

/**
 * Link
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class Link extends AbstractDecorator {

    public Link(Element element) {
        super(element);
    }

    @Override
    public String expose() {
        String value = element.expose();
        return "<a href=\"" + value + "\">" + value + "</a>";
    }
}
