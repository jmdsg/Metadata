package com.fiberg.metadata.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Metadata {

	enum Case {
		SnakeCase, Original
	}

	Case[] value() default Case.Original;

    boolean withStaticField() default false;

}
