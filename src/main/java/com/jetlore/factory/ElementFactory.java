package com.jetlore.factory;

import com.jetlore.api.ItemMeta;
import com.jetlore.model.Element;
import com.jetlore.model.Item;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ElementFactory
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class ElementFactory {

    @SuppressWarnings("CollectionDeclaredAsConcreteClass")
    private ConcurrentHashMap<Class, Constructor> cache = new ConcurrentHashMap<>();

    public Element buildElement(String source, ItemMeta itemMeta) throws Exception {
        Constructor constructor = getConstructor(itemMeta.getItemType().getTargetClass());
        Item item = new Item(source.substring(itemMeta.getStart(), itemMeta.getEnd()));
        return (Element) constructor.newInstance(item);
    }

    private Constructor getConstructor(Class clazz) throws Exception {
        Constructor constructor = cache.get(clazz);
        if (constructor == null) {
            constructor = clazz.getConstructor(Element.class);
            Constructor old = cache.putIfAbsent(clazz, constructor);
            if (old != null) {
                constructor = old;
            }
        }
        return constructor;
    }

}
