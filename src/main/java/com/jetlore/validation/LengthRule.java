package com.jetlore.validation;

import com.jetlore.api.ItemMeta;

import java.util.List;

/**
 * LengthRule
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class LengthRule extends ValidationRule {

    public LengthRule(ValidationRule next) {
        super(next);
    }

    @Override
    protected void check(String source, List<ItemMeta> items) {
        int length = source.length();
        boolean malformed = items.stream().filter(itemMeta -> itemMeta.getEnd() > length).findAny().isPresent();
        if (malformed) {
            throw new IllegalArgumentException("Meta information is invalid");
        }
    }
}
