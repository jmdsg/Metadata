package com.fiberg.metadata.generator.internal;

import org.seasar.aptina.unit.AptinaTestCase;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.Locale;

public class MetadataProcessorTest extends AptinaTestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		setLocale(Locale.JAPANESE);
		setCharset(Charset.forName("UTF-8"));

		addSourcePath("src/test/java");
	}

	public void testProcess() throws Exception {

		final DefineNamesProcessor processor = new DefineNamesProcessor();
		addProcessor(processor);

		addCompilationUnit(TestSource1Parent.class);
		addCompilationUnit(TestSource1.class);
		addCompilationUnit(TestSource2.class);

		compile();

		assertEqualsGeneratedSourceWithResource(getClass().getResource("ExpectedTestSource1_.java"),
				"com.fiberg.metadata.generator.internal.TestSource1_");
		assertEqualsGeneratedSourceWithResource(getClass().getResource("ExpectedTestSource2_.java"),
				"com.fiberg.metadata.generator.internal.TestSource2_");
	}

	private void testProcess(Class<?> target, URL expectedResourceUrl, String exportFilename) throws Exception {

		final DefineNamesProcessor processor = new DefineNamesProcessor();
		addProcessor(processor);

		addCompilationUnit(target);

		compile();

		assertEqualsGeneratedSourceWithResource(expectedResourceUrl, exportFilename);
	}

}
