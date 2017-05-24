package com.jetlore.factory;

import com.jetlore.api.ItemMeta;
import com.jetlore.model.Element;
import com.jetlore.model.Item;

import java.lang.reflect.Constructor;

/**
 * ElementFactory
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class ElementFactory {

    public Element buildElement(String source, ItemMeta itemMeta) throws Exception {
        Constructor constructor = itemMeta.getItemType().getTargetClass().getConstructor(Element.class);
        Item item = new Item(source.substring(itemMeta.getStart(), itemMeta.getEnd()));
        return (Element) constructor.newInstance(item);
    }

}
