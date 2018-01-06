package com.fiberg.metadata.generator.internal;

import com.fiberg.metadata.generator.Metadata;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Modifier;

class DefineNameClassWriter {

	private static final String GENERATE_CLASS_PREFIX = "";
	private static final String GENERATE_CLASS_SUFFIX = "_";

	private final String packageName;
	private final String targetClassSimpleName;
	private final Metadata.Case[] cases;
	private final Map<String, String> fields = new HashMap<>();

	DefineNameClassWriter(final String packageName,
	                      final String targetClassSimpleName, final Metadata.Case[] cases) {
		this.packageName = packageName;
		this.targetClassSimpleName = targetClassSimpleName;
		this.cases = cases;
	}

	void addFields(final String field) {
		for (final Metadata.Case c : cases) {
			switch (c) {
				case ORIGINAL:
					fields.put(field, field);
					break;
				case SNAKE_CASE:
					fields.put(convertToSnakeCase(field), field);
					break;
				case UPPERCASE:
					fields.put(convertToSnakeCase(field).toUpperCase(), field);
					break;
				default:
					break;
			}
		}
	}

	String getClassFQDN() {
		return packageName + "." + GENERATE_CLASS_PREFIX
				+ targetClassSimpleName + GENERATE_CLASS_SUFFIX;
	}

	void write(final Writer writer) throws IOException {
		JavaWriter javaWriter = null;
		try {
			javaWriter = new JavaWriter(writer);
			javaWriter.emitPackage(packageName).beginType(getClassFQDN(),
					"class", EnumSet.of(Modifier.PUBLIC));

			final List<Map.Entry<String, String>> fields = new ArrayList<>(this.fields.entrySet());
			fields.sort(Comparator.comparing(Map.Entry::getValue));

			for (final Map.Entry<String, String> entry : fields) {
				javaWriter.emitField("String", entry.getKey(), EnumSet.of(
						Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL), "\""
						+ entry.getValue() + "\"");
			}
			javaWriter.endType();
		} finally {
			if (javaWriter != null) {
				try {
					javaWriter.close();
				} catch (IOException e) {

				}
			}
		}
	}

	private static final Pattern UPPERCASE = Pattern.compile("[A-Z]");

	static final String convertToSnakeCase(final String original) {
		final StringBuffer sb = new StringBuffer();
		final Matcher m = UPPERCASE.matcher(original);
		while (m.find()) {
			if (m.start() != 0) {
				m.appendReplacement(sb, "_");
			} else {
				m.appendReplacement(sb, "");
			}
			sb.append(m.group().toLowerCase());
		}
		m.appendTail(sb);
		return sb.toString();
	}
}