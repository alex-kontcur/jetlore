package com.jetlore.model.decorators;

import com.jetlore.model.Element;

/**
 * TwitterUser
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class TwitterUser extends AbstractDecorator {

    public TwitterUser(Element element) {
        super(element);
    }

    @Override
    public String expose() {
        String value = element.expose();
        value = value.contains("@") ? value.split("@")[1] : value;
        return "@<a href=\"http://twitter.com/" + value + "\">" + value + "</a>";
    }

}
