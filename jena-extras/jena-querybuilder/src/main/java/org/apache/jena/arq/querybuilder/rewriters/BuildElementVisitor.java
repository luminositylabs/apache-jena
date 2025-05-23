/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.jena.arq.querybuilder.rewriters;

import java.util.List;

import org.apache.jena.arq.querybuilder.handlers.WhereHandler;
import org.apache.jena.sparql.syntax.*;

/**
 * An element visitor that does an in-place modification of the elements to fix
 * union-of-one and similar issues.
 */
public class BuildElementVisitor implements ElementVisitor {
    private Element result;

    public Element getResult() {
        return result;
    }

    public void setResult(Element result) {
        this.result = result;
    }

    @Override
    public void visit(ElementTriplesBlock el) {
        // no changes
        result = el;
    }

    @Override
    public void visit(ElementPathBlock el) {
        // no changes
        result = el;
    }

    @Override
    public void visit(ElementFilter el) {
        // no changes
        result = el;
    }

    @Override
    public void visit(ElementAssign el) {
        // no change
        result = el;
    }

    @Override
    public void visit(ElementBind el) {
        // no change
        result = el;
    }

    @Override
    public void visit(ElementUnfold el) {
        // no change
        result = el;
    }

    @Override
    public void visit(ElementData el) {
        // no change
        result = el;
    }

    private void updateList(List<Element> lst) {
        for (int i = 0; i < lst.size(); i++) {
            lst.get(i).visit(this);
            lst.set(i, result);
        }
    }

    @Override
    public void visit(ElementUnion el) {
        List<Element> lst = el.getElements();
        if (lst.size() <= 1) {
            ElementGroup eg = new ElementGroup();
            if (lst.size() == 1) {
                el.getElements().get(0).visit(this);
                eg.addElement(result);
            }
            result = eg;
        } else {
            updateList(lst);
            result = el;
        }
    }

    @Override
    public void visit(ElementLateral el) {
        el.getLateralElement().visit(this);
        if (result == el.getLateralElement()) {
            result = el;
        } else {
            result = new ElementOptional(result);
        }
    }

    @Override
    public void visit(ElementSemiJoin el) {
        el.getSubElement().visit(this);
        if (result == el.getSubElement()) {
            result = el;
        } else {
            result = new ElementSemiJoin(result);
        }
    }

    @Override
    public void visit(ElementAntiJoin el) {
        el.getSubElement().visit(this);
        if (result == el.getSubElement()) {
            result = el;
        } else {
            result = new ElementAntiJoin(result);
        }
    }

    @Override
    public void visit(ElementOptional el) {
        el.getOptionalElement().visit(this);
        if (result == el.getOptionalElement()) {
            result = el;
        } else {
            result = new ElementOptional(result);
        }
    }

    @Override
    public void visit(ElementGroup el) {
        List<Element> lst = el.getElements();
        if (lst.isEmpty()) {
            // noting to do
            result = el;
        } else if (lst.size() == 1) {
            lst.get(0).visit(this);
            // result is now set properly
        } else {
            updateList(lst);
            result = el;
        }
    }

    @Override
    public void visit(ElementDataset el) {
        // noting to do
        result = el;
    }

    @Override
    public void visit(ElementNamedGraph el) {
        el.getElement().visit(this);
        if (result == el.getElement()) {
            // nothing to do
            result = el;
        } else {
            result = new ElementNamedGraph(el.getGraphNameNode(), result);
        }
    }

    @Override
    public void visit(ElementExists el) {
        el.getElement().visit(this);
        if (result == el.getElement()) {
            // nothing to do
            result = el;
        } else {
            result = new ElementExists(result);
        }
    }

    @Override
    public void visit(ElementNotExists el) {
        el.getElement().visit(this);
        if (result == el.getElement()) {
            // nothing to do
            result = el;
        } else {
            result = new ElementNotExists(result);
        }
    }

    @Override
    public void visit(ElementMinus el) {
        el.getMinusElement().visit(this);
        if (result == el.getMinusElement()) {
            // nothing to do
            result = el;
        } else {
            result = new ElementMinus(result);
        }
    }

    @Override
    public void visit(ElementService el) {
        el.getElement().visit(this);
        if (result == el.getElement()) {
            // nothing to do
            result = el;
        } else {
            result = new ElementService(el.getServiceNode(), result, el.getSilent());
        }

    }

    @Override
    public void visit(ElementSubQuery el) {
        WhereHandler other = new WhereHandler(el.getQuery());
        other.build();
        if (other.getElement() != el.getQuery().getQueryPattern()) {
            el.getQuery().setQueryPattern(other.getQuery().getQueryPattern());
        }
        result = el;
    }

}
