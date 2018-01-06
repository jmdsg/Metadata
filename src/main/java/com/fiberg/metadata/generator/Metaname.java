package com.fiberg.metadata.generator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface Metaname {

	String value();

}
