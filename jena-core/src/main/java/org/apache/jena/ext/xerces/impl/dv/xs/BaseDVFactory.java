/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jena.ext.xerces.impl.dv.xs;

import org.apache.jena.ext.xerces.impl.dv.SchemaDVFactory;
import org.apache.jena.ext.xerces.impl.dv.XSFacets;
import org.apache.jena.ext.xerces.impl.dv.XSSimpleType;
import org.apache.jena.ext.xerces.util.SymbolHash;
import org.apache.jena.ext.xerces.xs.XSConstants;

/**
 * the factory to create/return built-in schema DVs and create user-defined DVs
 *
 * {@literal @xerces.internal} 
 *
 * @author Neeraj Bajaj, Sun Microsystems, inc.
 * @author Sandy Gao, IBM
 *
 * @version $Id: BaseDVFactory.java 699892 2008-09-28 21:08:27Z mrglavas $
 */
@SuppressWarnings("all")
public class BaseDVFactory extends SchemaDVFactory {

    static final String URI_SCHEMAFORSCHEMA = "http://www.w3.org/2001/XMLSchema";

    // there are 27 types. 53 is the closest prime number to 27*2=54.
    static SymbolHash fBaseTypes = new SymbolHash(53);
    static {
        createBuiltInTypes(fBaseTypes);
    }

    /**
     * Get a built-in simple type of the given name
     * REVISIT: its still not decided within the Schema WG how to define the
     *          ur-types and if all simple types should be derived from a
     *          complex type, so as of now we ignore the fact that anySimpleType
     *          is derived from anyType, and pass 'null' as the base of
     *          anySimpleType. It needs to be changed as per the decision taken.
     *
     * @param name  the name of the datatype
     * @return      the datatype validator of the given name
     */
    public XSSimpleType getBuiltInType(String name) {
        return (XSSimpleType)fBaseTypes.get(name);
    }

    /**
     * get all built-in simple types, which are stored in a hashtable keyed by
     * the name
     *
     * @return      a hashtable which contains all built-in simple types
     */
    public SymbolHash getBuiltInTypes() {
        return fBaseTypes.makeClone();
    }

    /**
     * Create a new simple type which is derived by restriction from another
     * simple type.
     *
     * @param name              name of the new type, could be null
     * @param targetNamespace   target namespace of the new type, could be null
     * @param base              base type of the new type
     * @return                  the newly created simple type
     */
    public XSSimpleType createTypeRestriction(String name, String targetNamespace, XSSimpleType base) {
        return new XSSimpleTypeDecl((XSSimpleTypeDecl)base, name, targetNamespace, false);
    }

    // create all built-in types
    static void createBuiltInTypes(SymbolHash types) {
        // base schema simple type names
        final String ANYSIMPLETYPE     = "anySimpleType";
        final String ANYURI            = "anyURI";
        final String BASE64BINARY      = "base64Binary";
        final String BOOLEAN           = "boolean";
        final String BYTE              = "byte";
        final String DATE              = "date";
        final String DATETIME          = "dateTime";
        final String DAY               = "gDay";
        final String DECIMAL           = "decimal";
        final String INT               = "int";
        final String INTEGER           = "integer";
        final String LONG              = "long";
        final String NEGATIVEINTEGER   = "negativeInteger";
        final String MONTH             = "gMonth";
        final String MONTHDAY          = "gMonthDay";
        final String NONNEGATIVEINTEGER= "nonNegativeInteger";
        final String NONPOSITIVEINTEGER= "nonPositiveInteger";
        final String POSITIVEINTEGER   = "positiveInteger";
        final String SHORT             = "short";
        final String STRING            = "string";
        final String TIME              = "time";
        final String UNSIGNEDBYTE      = "unsignedByte";
        final String UNSIGNEDINT       = "unsignedInt";
        final String UNSIGNEDLONG      = "unsignedLong";
        final String UNSIGNEDSHORT     = "unsignedShort";
        final String YEAR              = "gYear";
        final String YEARMONTH         = "gYearMonth";

        final XSFacets facets = new XSFacets();

        XSSimpleTypeDecl anySimpleType = XSSimpleTypeDecl.fAnySimpleType;
        types.put(ANYSIMPLETYPE, anySimpleType);
        XSSimpleTypeDecl stringDV = new XSSimpleTypeDecl(anySimpleType, STRING, XSSimpleTypeDecl.DV_STRING, XSSimpleType.ORDERED_FALSE, false, false, true, XSConstants.STRING_DT);
        types.put(STRING, stringDV);
        types.put(BOOLEAN, new XSSimpleTypeDecl(anySimpleType, BOOLEAN, XSSimpleTypeDecl.DV_BOOLEAN, XSSimpleType.ORDERED_FALSE, true, false, true, XSConstants.BOOLEAN_DT));
        XSSimpleTypeDecl decimalDV = new XSSimpleTypeDecl(anySimpleType, DECIMAL, XSSimpleTypeDecl.DV_DECIMAL, XSSimpleType.ORDERED_TOTAL, false, true, true, XSConstants.DECIMAL_DT);
        types.put(DECIMAL, decimalDV);

        types.put(ANYURI, new XSSimpleTypeDecl(anySimpleType, ANYURI, XSSimpleTypeDecl.DV_ANYURI, XSSimpleType.ORDERED_FALSE, false, false, true, XSConstants.ANYURI_DT));
        types.put(BASE64BINARY, new XSSimpleTypeDecl(anySimpleType, BASE64BINARY, XSSimpleTypeDecl.DV_BASE64BINARY, XSSimpleType.ORDERED_FALSE, false, false, true, XSConstants.BASE64BINARY_DT));
        types.put(DATETIME, new XSSimpleTypeDecl(anySimpleType, DATETIME, XSSimpleTypeDecl.DV_DATETIME, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.DATETIME_DT));
        types.put(TIME, new XSSimpleTypeDecl(anySimpleType, TIME, XSSimpleTypeDecl.DV_TIME, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.TIME_DT));
        types.put(DATE, new XSSimpleTypeDecl(anySimpleType, DATE, XSSimpleTypeDecl.DV_DATE, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.DATE_DT));
        types.put(YEARMONTH, new XSSimpleTypeDecl(anySimpleType, YEARMONTH, XSSimpleTypeDecl.DV_GYEARMONTH, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.GYEARMONTH_DT));
        types.put(YEAR, new XSSimpleTypeDecl(anySimpleType, YEAR, XSSimpleTypeDecl.DV_GYEAR, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.GYEAR_DT));
        types.put(MONTHDAY, new XSSimpleTypeDecl(anySimpleType, MONTHDAY, XSSimpleTypeDecl.DV_GMONTHDAY, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.GMONTHDAY_DT));
        types.put(DAY, new XSSimpleTypeDecl(anySimpleType, DAY, XSSimpleTypeDecl.DV_GDAY, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.GDAY_DT));
        types.put(MONTH, new XSSimpleTypeDecl(anySimpleType, MONTH, XSSimpleTypeDecl.DV_GMONTH, XSSimpleType.ORDERED_PARTIAL, false, false, true, XSConstants.GMONTH_DT));

        XSSimpleTypeDecl integerDV = new XSSimpleTypeDecl(decimalDV, INTEGER, XSSimpleTypeDecl.DV_INTEGER, XSSimpleType.ORDERED_TOTAL, false, true, true, XSConstants.INTEGER_DT);
        types.put(INTEGER, integerDV);

        facets.maxInclusive = "0";
        XSSimpleTypeDecl nonPositiveDV = new XSSimpleTypeDecl(integerDV, NONPOSITIVEINTEGER, URI_SCHEMAFORSCHEMA, false, XSConstants.NONPOSITIVEINTEGER_DT);
        nonPositiveDV.applyFacets1(facets , XSSimpleType.FACET_MAXINCLUSIVE);
        types.put(NONPOSITIVEINTEGER, nonPositiveDV);

        facets.maxInclusive = "-1";
        XSSimpleTypeDecl negativeDV = new XSSimpleTypeDecl(nonPositiveDV, NEGATIVEINTEGER, URI_SCHEMAFORSCHEMA, false, XSConstants.NEGATIVEINTEGER_DT);
        negativeDV.applyFacets1(facets , XSSimpleType.FACET_MAXINCLUSIVE);
        types.put(NEGATIVEINTEGER, negativeDV);

        facets.maxInclusive = "9223372036854775807";
        facets.minInclusive = "-9223372036854775808";
        XSSimpleTypeDecl longDV = new XSSimpleTypeDecl(integerDV, LONG, URI_SCHEMAFORSCHEMA, false, XSConstants.LONG_DT);
        longDV.applyFacets1(facets , (short)(XSSimpleType.FACET_MAXINCLUSIVE | XSSimpleType.FACET_MININCLUSIVE));
        types.put(LONG, longDV);


        facets.maxInclusive = "2147483647";
        facets.minInclusive =  "-2147483648";
        XSSimpleTypeDecl intDV = new XSSimpleTypeDecl(longDV, INT, URI_SCHEMAFORSCHEMA, false, XSConstants.INT_DT);
        intDV.applyFacets1(facets, (short)(XSSimpleType.FACET_MAXINCLUSIVE | XSSimpleType.FACET_MININCLUSIVE));
        types.put(INT, intDV);

        facets.maxInclusive = "32767";
        facets.minInclusive = "-32768";
        XSSimpleTypeDecl shortDV = new XSSimpleTypeDecl(intDV, SHORT , URI_SCHEMAFORSCHEMA, false, XSConstants.SHORT_DT);
        shortDV.applyFacets1(facets, (short)(XSSimpleType.FACET_MAXINCLUSIVE | XSSimpleType.FACET_MININCLUSIVE));
        types.put(SHORT, shortDV);

        facets.maxInclusive = "127";
        facets.minInclusive = "-128";
        XSSimpleTypeDecl byteDV = new XSSimpleTypeDecl(shortDV, BYTE , URI_SCHEMAFORSCHEMA, false, XSConstants.BYTE_DT);
        byteDV.applyFacets1(facets, (short)(XSSimpleType.FACET_MAXINCLUSIVE | XSSimpleType.FACET_MININCLUSIVE));
        types.put(BYTE, byteDV);

        facets.minInclusive =  "0" ;
        XSSimpleTypeDecl nonNegativeDV = new XSSimpleTypeDecl(integerDV, NONNEGATIVEINTEGER , URI_SCHEMAFORSCHEMA, false, XSConstants.NONNEGATIVEINTEGER_DT);
        nonNegativeDV.applyFacets1(facets, XSSimpleType.FACET_MININCLUSIVE);
        types.put(NONNEGATIVEINTEGER, nonNegativeDV);

        facets.maxInclusive = "18446744073709551615" ;
        XSSimpleTypeDecl unsignedLongDV = new XSSimpleTypeDecl(nonNegativeDV, UNSIGNEDLONG , URI_SCHEMAFORSCHEMA, false, XSConstants.UNSIGNEDLONG_DT);
        unsignedLongDV.applyFacets1(facets, XSSimpleType.FACET_MAXINCLUSIVE);
        types.put(UNSIGNEDLONG, unsignedLongDV);

        facets.maxInclusive = "4294967295" ;
        XSSimpleTypeDecl unsignedIntDV = new XSSimpleTypeDecl(unsignedLongDV, UNSIGNEDINT , URI_SCHEMAFORSCHEMA, false, XSConstants.UNSIGNEDINT_DT);
        unsignedIntDV.applyFacets1(facets, XSSimpleType.FACET_MAXINCLUSIVE);
        types.put(UNSIGNEDINT, unsignedIntDV);

        facets.maxInclusive = "65535" ;
        XSSimpleTypeDecl unsignedShortDV = new XSSimpleTypeDecl(unsignedIntDV, UNSIGNEDSHORT , URI_SCHEMAFORSCHEMA, false, XSConstants.UNSIGNEDSHORT_DT);
        unsignedShortDV.applyFacets1(facets, XSSimpleType.FACET_MAXINCLUSIVE);
        types.put(UNSIGNEDSHORT, unsignedShortDV);

        facets.maxInclusive = "255" ;
        XSSimpleTypeDecl unsignedByteDV = new XSSimpleTypeDecl(unsignedShortDV, UNSIGNEDBYTE , URI_SCHEMAFORSCHEMA, false, XSConstants.UNSIGNEDBYTE_DT);
        unsignedByteDV.applyFacets1(facets, XSSimpleType.FACET_MAXINCLUSIVE);
        types.put(UNSIGNEDBYTE, unsignedByteDV);

        facets.minInclusive = "1" ;
        XSSimpleTypeDecl positiveIntegerDV = new XSSimpleTypeDecl(nonNegativeDV, POSITIVEINTEGER , URI_SCHEMAFORSCHEMA, false, XSConstants.POSITIVEINTEGER_DT);
        positiveIntegerDV.applyFacets1(facets, XSSimpleType.FACET_MININCLUSIVE);
        types.put(POSITIVEINTEGER, positiveIntegerDV);
    }//createBuiltInTypes(SymbolHash)

}//BaseDVFactory
