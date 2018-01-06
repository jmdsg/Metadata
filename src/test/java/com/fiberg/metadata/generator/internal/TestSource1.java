package com.fiberg.metadata.generator.internal;

import com.fiberg.metadata.generator.Metadata;

@Metadata({ Metadata.Case.ORIGINAL, Metadata.Case.SNAKE_CASE })
@SuppressWarnings("unused")
public class TestSource1 extends TestSource1Parent {
	private String test;
	private String testCase;
	private String TestCase;
    private static final String STATIC_FIELD = "static_field";
}
