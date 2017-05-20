package com.jetlore.correction;

import com.jetlore.api.ItemMeta;
import com.jetlore.api.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * PlainCorrector
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class PlainCorrector implements Corrector {

    @Override
    public List<ItemMeta> correct(String source, List<ItemMeta> items) {
        List<ItemMeta> corrected = new ArrayList<>();

        List<ItemMeta> sortedItems = items.stream().sorted().collect(Collectors.toList());
        IntStream.range(0, sortedItems.size() - 1)
            .filter(i -> sortedItems.get(i).getEnd() + 1 < sortedItems.get(i + 1).getStart())
            .forEach(i -> {
                int start = sortedItems.get(i).getEnd() + 1;
                int end = sortedItems.get(i + 1).getStart() - 1;
                ItemMeta itemMeta = new ItemMeta(ItemType.Plain, start, end);
                corrected.add(itemMeta);
            });
        corrected.addAll(sortedItems);

        ItemMeta lastItem = sortedItems.get(sortedItems.size() - 1);
        Integer lastFormattedEnd = lastItem.getEnd();
        if (lastFormattedEnd < source.length()) {
            corrected.add(new ItemMeta(ItemType.Plain, lastItem.getEnd() + 1, source.length()));
        }

        return corrected.stream().sorted().collect(Collectors.toList());
    }
}
