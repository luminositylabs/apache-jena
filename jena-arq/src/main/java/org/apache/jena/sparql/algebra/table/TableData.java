/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.sparql.algebra.table ;

import java.util.Collections;
import java.util.List ;

import org.apache.jena.sparql.ARQException ;
import org.apache.jena.sparql.core.Var ;
import org.apache.jena.sparql.engine.binding.Binding ;

/** Immutable table. */
public class TableData extends TableN {
    public TableData(List<Var> variables, List<Binding> rows) {
        super(Collections.unmodifiableList(variables), Collections.unmodifiableList(rows)) ;
    }

    @Override
    public void addBinding(Binding binding) {
        throw new ARQException("Can't add bindings to an existing data table") ;
    }
}
