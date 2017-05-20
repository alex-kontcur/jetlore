package com.jetlore.validation;

import com.jetlore.api.ItemMeta;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.IntStream;

/**
 * IntersectionRule
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class IntersectionRule extends ValidationRule {

    public IntersectionRule(ValidationRule next) {
        super(next);
    }

    @Override
    protected void check(String source, List<ItemMeta> items) {
        Set<ItemMeta> checked = new CopyOnWriteArraySet<>();
        boolean malformed = IntStream.range(0, items.size() - 1)
            .filter(i -> items.parallelStream()
                .filter(item -> !item.equals(items.get(i)) && !checked.contains(item))
                .filter(item -> {
                    Integer start = items.get(i).getStart();
                    Integer end = items.get(i).getEnd();
                    boolean startWithin = item.getStart() <= start && item.getEnd() >= start;
                    boolean endWithin = item.getStart() <= end && item.getEnd() >= end;
                    boolean within = startWithin || endWithin;
                    if (!within) {
                        checked.add(items.get(i));
                    }
                    return within;
                }).findAny().orElse(null) != null)
            .findAny().isPresent();

        if (malformed) {
            throw new IllegalArgumentException("Meta information is invalid - intersections detected");
        }
    }
}
