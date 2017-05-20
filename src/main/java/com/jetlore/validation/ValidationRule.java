package com.jetlore.validation;

import com.jetlore.api.ItemMeta;

import java.util.List;

/**
 * ValidationRule
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public abstract class ValidationRule {

    private ValidationRule next;

    protected ValidationRule(ValidationRule next) {
        this.next = next;
    }

    public void validate(String source, List<ItemMeta> items) {
        check(source, items);
        if (next != null) {
            next.validate(source, items);
        }
    }

    protected abstract void check(String source, List<ItemMeta> items);
}
