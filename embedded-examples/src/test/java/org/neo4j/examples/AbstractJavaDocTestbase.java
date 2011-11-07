/**
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.neo4j.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.pql.javacompat.ExecutionEngine;
import org.neo4j.pql.javacompat.PqlParser;
import org.neo4j.test.*;

import java.util.Map;

public class AbstractJavaDocTestbase implements GraphHolder {

public @Rule
TestData<JavaTestDocsGenerator> gen = TestData.producedThrough( JavaTestDocsGenerator.PRODUCER );
public @Rule
TestData<Map<String, Node>> data = TestData.producedThrough( GraphDescription.createGraphFor(
        this, true ) );
protected static ImpermanentGraphDatabase db;
protected PqlParser parser;
protected ExecutionEngine engine;


@BeforeClass
public static void init()
{
    db = new ImpermanentGraphDatabase("target/"+ System.currentTimeMillis());
}

@Before
public void setUp() {
    db.cleanContent();
    gen.get().setGraph( db );
    parser = new PqlParser();
    engine = new ExecutionEngine(db);
}
@After
public void doc() {
    gen.get().document("target/docs","examples");
}
@Override
public GraphDatabaseService graphdb()
{
    return db;
}

}
