package com.jetlore;

import com.google.common.util.concurrent.Striped;
import com.jetlore.api.ItemMeta;
import com.jetlore.correction.Corrector;
import com.jetlore.correction.PlainCorrector;
import com.jetlore.factory.ElementFactory;
import com.jetlore.model.Element;
import com.jetlore.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * SourceAcceptor - main entry for second module.
 * We don't use any IoC container so creating everything manually.
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class SourceAcceptor {

    private static final Logger logger = LoggerFactory.getLogger(SourceAcceptor.class);

    private Validator validator;
    private Corrector corrector;
    private ElementFactory factory;
    private Map<String, List<Element>> map;

    private Striped<Lock> locks = Striped.lock(64);

    public SourceAcceptor() {
        validator = new Validator();
        factory = new ElementFactory();
        corrector = new PlainCorrector();

        map = new ConcurrentHashMap<>();
    }

    public void accept(String session, String source, List<ItemMeta> items) {
        MDC.put("session", session);
        MDC.put("action", "put");
        Lock lock = locks.get(session);
        lock.lock();
        try {
            validator.validate(source, items);
            Collection<ItemMeta> corrected = corrector.correct(source, items);
            List<Element> elements = new ArrayList<>();
            corrected.stream().sorted().forEach(itemMeta -> {
                try {
                    elements.add(factory.buildElement(source, itemMeta));
                } catch (Exception e) {
                    logger.error("Error while building Element for " + itemMeta + "->", e);
                }
            });
            map.put(session, elements);
        } finally {
            lock.unlock();
        }
    }

    public String getOutput(String session) {
        MDC.put("session", session);
        MDC.put("action", "get");
        Lock lock = locks.get(session);
        lock.lock();
        try {
            List<Element> elements = map.remove(session);
            if (elements == null) {
                throw new IllegalStateException("No output found for session " + session);
            }
            return elements.stream().map(Element::expose).reduce((s1, s2) -> s1 + " " + s2).orElse(null);
        } finally {
            lock.unlock();
        }
    }

}
