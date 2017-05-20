package com.jetlore.test;

import com.jetlore.SourceAcceptor;
import com.jetlore.api.ItemMeta;
import com.jetlore.api.ItemType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * FormattedTest
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class FormattedTest {

    private SourceAcceptor sourceAcceptor;
    private ExecutorService executorService;

    @Before
    public void init() {
        sourceAcceptor = new SourceAcceptor();
        executorService = Executors.newFixedThreadPool(2);
    }

    @Test
    public void wrongEntityBoundsProcessingFailed() throws Exception {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile the end";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 14, 22));
        items.add(new ItemMeta(ItemType.TwitterUser, 48, 56));
        items.add(new ItemMeta(ItemType.Link, 37, 47));

        String session = UUID.randomUUID().toString();
        Assert.assertFalse(acceptInput(session, input, items));
    }

    @Test
    public void itemsMetaCorrectedOkay() throws Exception {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile the end";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 14, 22));
        items.add(new ItemMeta(ItemType.TwitterUser, 55, 67)); //48, 56
        items.add(new ItemMeta(ItemType.Link, 37, 54)); //37, 47

        String session = UUID.randomUUID().toString();
        Assert.assertTrue(acceptInput(session, input, items));

        String expectation = "<strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a> the end";
        Assert.assertEquals(expectation, getOutput(session));
    }

    @Test
    public void intersectionMetaFailure() throws Exception {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 5, 13));

        String session = UUID.randomUUID().toString();
        Assert.assertFalse(acceptInput(session, input, items));
    }

    @Test
    public void sourceStringFormattedCorectly() throws Exception {
        String input = "Obama visited Facebook headquarters: http://bit.ly/xyz @elversatile";

        List<ItemMeta> items = new ArrayList<>();
        items.add(new ItemMeta(ItemType.Entity, 0, 5));
        items.add(new ItemMeta(ItemType.Entity, 14, 22));
        items.add(new ItemMeta(ItemType.TwitterUser, 55, 67));
        items.add(new ItemMeta(ItemType.Link, 37, 54));

        String session = UUID.randomUUID().toString();

        Assert.assertTrue(acceptInput(session, input, items));

        String expectation = "<strong>Obama</strong> visited <strong>Facebook</strong> headquarters: <a href=\"http://bit.ly/xyz\">http://bit.ly/xyz</a> @<a href=\"http://twitter.com/elversatile\">elversatile</a>";
        Assert.assertEquals(expectation, getOutput(session));
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private boolean acceptInput(String session, String input, List<ItemMeta> items) throws Exception{
        Exception exception = executorService.submit(() -> {
            Exception ex = null;
            try {
                sourceAcceptor.accept(session, input, items);
            } catch (Exception e) {
                ex = e;
            }
            return ex;
        }).get();
        return exception == null;
    }

    private String getOutput(String session) throws Exception {
        TimeUnit.MILLISECONDS.sleep(500);

        CountDownLatch latch = new CountDownLatch(1);
        String result = executorService.submit(() -> {
            String output = sourceAcceptor.getOutput(session);
            latch.countDown();
            return output;
        }).get();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (Exception ignored) {
        }
        return result;
    }

}
