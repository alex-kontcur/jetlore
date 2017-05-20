package com.jetlore.validation;

import com.jetlore.api.ItemMeta;

import java.util.List;

/**
 * Validator
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class Validator {

    private ValidationRule rule;

    public Validator() {
        rule = new LengthRule(new SeparationRule(new IntersectionRule(null)));
    }

    public void validate(String source, List<ItemMeta> items) {
        rule.validate(source, items);
    }
}
