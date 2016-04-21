package com.demo.api.rest.config.jersey;

import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorGrammarsSupport;

import java.util.List;

public class SchemaGenConfig extends WadlGeneratorConfig {

	@Override
	public List<WadlGeneratorDescription> configure() {
//		 return generator(WadlGeneratorJAXBGrammarGenerator.class).descriptions();
		return generator(WadlGeneratorGrammarsSupport.class)
				.prop("grammarsStream", "wadl-grammar.xml")
				.prop("overrideGrammars", true)
				.descriptions();
	}

}