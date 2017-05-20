package com.jetlore.api;

/**
 * ItemType
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public enum ItemType {

    Plain,
    Entity,
    Link,
    TwitterUser;

    public Class getTargetClass() {
        switch (this) {
            case Plain: return com.jetlore.model.decorators.Plain.class;
            case Entity: return com.jetlore.model.decorators.Entity.class;
            case Link: return com.jetlore.model.decorators.Link.class;
            case TwitterUser:return com.jetlore.model.decorators.TwitterUser.class;
            default: throw new IllegalArgumentException("Unknown Item Type");
        }
    }

}
