package com.fiberg.metadata.generator.internal;

import com.fiberg.metadata.generator.Metadata;
import com.fiberg.metadata.generator.Metadata.Case;

@Metadata(value = Case.ORIGINAL, withStaticField = true)
public class TestSource2 {
    private static final String STATIC_FIELD = "static_field";
}
