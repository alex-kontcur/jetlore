package com.jetlore;

import com.jetlore.api.ItemMeta;
import com.jetlore.correction.Corrector;
import com.jetlore.correction.PlainCorrector;
import com.jetlore.factory.ElementFactory;
import com.jetlore.model.Element;
import com.jetlore.validation.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SourceAcceptor - main entry for second module.
 * We don't use any IoC container so creating everything manually.
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public class SourceAcceptor {

    private Validator validator;
    private Corrector corrector;
    private ElementFactory factory;

    public SourceAcceptor() {
        validator = new Validator();
        factory = new ElementFactory();
        corrector = new PlainCorrector();
    }

    public String processInput(String source, List<ItemMeta> items) throws Exception {
        validator.validate(source, items);
        List<ItemMeta> corrected = corrector.correct(source, items);
        Collections.sort(corrected);
        List<Element> elements = new ArrayList<>();
        for (ItemMeta itemMeta : corrected) {
            elements.add(factory.buildElement(source, itemMeta));
        }
        return elements.stream().map(Element::expose).reduce((s1, s2) -> s1 + " " + s2).orElse(null);
    }

}
