package com.fiberg.metadata.generator.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

import com.fiberg.metadata.generator.Metadata.Case;

public class MetanameClassWriterTest {

	@Test
	public void testConvertToSnakeCase() {
		Assert.assertEquals("test",
				DefineNameClassWriter.convertToSnakeCase("test"));
		Assert.assertEquals("test_case",
				DefineNameClassWriter.convertToSnakeCase("testCase"));
		Assert.assertEquals("test_case",
				DefineNameClassWriter.convertToSnakeCase("TestCase"));
		Assert.assertEquals("test_case_for_class_builder",
				DefineNameClassWriter
						.convertToSnakeCase("testCaseForClassBuilder"));
	}

	@Test
	public void testWrite() throws IOException {
		final DefineNameClassWriter classWriter = new DefineNameClassWriter(
				"com.fiberg.metadata.generator.internal", "TestSource1", new Case[]{Case.ORIGINAL, Case.SNAKE_CASE});
		classWriter.addFields("test");
		classWriter.addFields("testCase");
		classWriter.addFields("testCaseForClassBuilder");
		final StringWriter writer = new StringWriter();
		classWriter.write(writer);
		writer.flush();
		writer.close();

		Assert.assertEquals(getTestSource("ExpectedTestSource1_.java"),
				writer.toString());
	}

	private String getTestSource(final String className) throws IOException {
		final InputStream in = getClass().getResourceAsStream(className);
		final Reader reader = new InputStreamReader(in);
		final BufferedReader bReader = new BufferedReader(reader);
		try {
			int size;
			final char[] buffer = new char[1024];
			final StringBuilder sb = new StringBuilder();
			while ((size = bReader.read(buffer)) != -1) {
				sb.append(buffer, 0, size);
			}
			return sb.toString();
		} finally {
			bReader.close();
			reader.close();
			in.close();
		}
	}

}
