package com.jetlore.validation;

import com.google.common.primitives.Chars;
import com.jetlore.api.ItemMeta;

import java.util.List;

/**
 * SeparationRule
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class SeparationRule extends ValidationRule {

    private static final char SPACE = 32;
    private static final char CARET = 10;
    private static final char TAB = 9;

    private char[] delimeters;

    public SeparationRule(ValidationRule validationRule) {
        super(validationRule);

        delimeters = new char[] {SPACE, CARET, TAB};
    }

    @Override
    protected void check(String source, List<ItemMeta> items) {
        boolean malformed = items.parallelStream()
            .filter(itemMeta -> {
                Integer end = itemMeta.getEnd();
                return end < source.length() && !Chars.contains(delimeters, source.charAt(end));
            }).findAny().isPresent();
        if (malformed) {
            throw new IllegalArgumentException("Items defined with errors -> endings wrong");
        }
    }
}
