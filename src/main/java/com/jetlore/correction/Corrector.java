/*
 * Copyright (c) 2016, CardsMobile. All Rights Reserved.
 * Use is subject to license terms.
 */

package com.jetlore.correction;

import com.jetlore.api.ItemMeta;

import java.util.List;

/**
 * Corrector
 *
 * @author Alex Kontsur
 * @since 20.05.17
 */
public interface Corrector {

    List<ItemMeta> correct(String source, List<ItemMeta> items);

}
