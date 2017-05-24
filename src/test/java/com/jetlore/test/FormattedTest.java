package com.jetlore.test;

import com.jetlore.SourceAcceptor;
import com.jetlore.api.ItemMeta;
import com.jetlore.api.ItemType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * FormattedTest
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class FormattedTest {

    private SourceAcceptor sourceAcceptor;

    @Before
    public void init() {
        sourceAcceptor = new SourceAcceptor();
    }

    @Test
    public void wrongEntityBoundsProcessingFailed() {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile the end";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 14, 22));
        items.add(new ItemMeta(ItemType.TwitterUser, 48, 56));
        items.add(new ItemMeta(ItemType.Link, 37, 47));

        Exception ex = null;
        try {
            sourceAcceptor.processInput(input, items);
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull(ex);
    }

    @Test
    public void itemsMetaCorrectedOkay() throws Exception {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile the end";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 14, 22));
        items.add(new ItemMeta(ItemType.TwitterUser, 55, 67)); //48, 56
        items.add(new ItemMeta(ItemType.Link, 37, 54)); //37, 47

        String expectation = "<strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a> the end";
        Assert.assertEquals(expectation, sourceAcceptor.processInput(input, items));
    }

    @Test
    public void intersectionMetaFailure() throws Exception {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 5, 13));

        Exception ex = null;
        try {
            sourceAcceptor.processInput(input, items);
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull(ex);
    }

    @Test
    public void sourceStringFormattedCorectly() throws Exception {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 14, 22));
        items.add(new ItemMeta(ItemType.TwitterUser, 55, 67));
        items.add(new ItemMeta(ItemType.Link, 37, 54));

        String expectation = "<strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a>";
        Assert.assertEquals(expectation, sourceAcceptor.processInput(input, items));
    }

}
