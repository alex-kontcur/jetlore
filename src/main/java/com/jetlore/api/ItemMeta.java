package com.jetlore.api;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * ItemMeta
 *
 * @author Alex Kontsur
 * @since 17.05.17
 */
public class ItemMeta implements Comparable<ItemMeta> {

    private ItemType itemType;
    private Integer start;
    private Integer end;

    public ItemMeta(ItemType itemType, int start, int end) {
        this.itemType = checkNotNull(itemType);
        if (start < 0 || end < 0 || start > end) {
            throw new IllegalArgumentException("Start or/and End parameter invalid");
        }
        this.start = start;
        this.end = end;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    @Override
    public int compareTo(ItemMeta o) {
        return start.compareTo(o.start);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemMeta itemMeta = (ItemMeta) o;
        return itemType == itemMeta.itemType &&
            Objects.equals(start, itemMeta.start) &&
            Objects.equals(end, itemMeta.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemType, start, end);
    }

    @Override
    public String toString() {
        return itemType.name() + ": " + start + " -> " + end;
    }
}
