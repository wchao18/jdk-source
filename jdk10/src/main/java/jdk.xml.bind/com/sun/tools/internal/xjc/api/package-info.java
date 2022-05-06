/*
 * Copyright (c) 2017, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/**
 * API for programmatic invocation of XJC and schemagen.
 *
 * <p>
 * This package provides a way to invoke XJC from within another program. The primary target of this API is the JAX-WS
 * RI, but we hope that this API would be useful for other integration purposes as well.
 *
 * <h2>Getting Started: Using XJC</h2>
 * <p>
 * To invoke XJC, a typical client would do something like this:
 * <pre>
 *    SchemaCompiler sc = XJC.createSchemaCompiler();
 *    sc.parseSchema(new InputSource(schema1Url.toExternalForm()));
 *    sc.parseSchema(new InputSource(schema2Url.toExternalForm()));
 *    ...
 *    S2JModel model = sc.bind();
 * </pre>
 * <p>
 * The bind operation causes XJC to do the bulk of the work, such as figuring out what classes to generate, what
 * methods/fields to generate, etc. The obtained model contains useful introspective information about how the binding
 * was performed (such as the mapping between XML types and generated Java classes)
 *
 * <p>
 * Once the model is obtained, generate the code into the file system as follows:
 * <pre>
 *   JCodeModel cm = model.generateCode( null, ... );
 *   cm.build(new FileCodeWriter(outputDir));
 * </pre>
 *
 * <h2>Implementation Note</h2>
 * <p>
 * This package shouldn't contain any implementation code.
 */
package com.sun.tools.internal.xjc.api;
