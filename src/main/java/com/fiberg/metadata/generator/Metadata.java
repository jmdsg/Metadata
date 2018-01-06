package com.fiberg.metadata.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Metadata {

	enum Case {
		SNAKE_CASE, ORIGINAL, UPPERCASE
	}

	Case[] value() default Case.UPPERCASE;

    boolean withStaticField() default false;

}
