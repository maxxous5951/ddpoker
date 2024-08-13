/*
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 * DD Poker - Source Code
 * Copyright (c) 2003-2024 Doug Donohoe
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * For the full License text, please see the LICENSE.txt file
 * in the root directory of this project.
 * 
 * The "DD Poker" and "Donohoe Digital" names and logos, as well as any images, 
 * graphics, text, and documentation found in this repository (including but not
 * limited to written documentation, website content, and marketing materials) 
 * are licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 
 * 4.0 International License (CC BY-NC-ND 4.0). You may not use these assets 
 * without explicit written permission for any uses not covered by this License.
 * For the full License text, please see the LICENSE-CREATIVE-COMMONS.txt file
 * in the root directory of this project.
 * 
 * For inquiries regarding commercial licensing of this source code or 
 * the use of names, logos, images, text, or other assets, please contact 
 * doug [at] donohoe [dot] info.
 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
 */
/*
 * TokenizedList.java
 *
 * Created on February 5, 2003, 2:37 PM
 */

package com.donohoedigital.comms;

import com.donohoedigital.base.*;
import org.apache.log4j.*;

import java.io.*;
import java.util.*;

/**
 *
 * @author  Doug Donohoe
 */
@DataCoder('t')
public class TokenizedList implements DataMarshal
{
    static Logger logger = Logger.getLogger(TokenizedList.class);
    
    // these must be escaped
    public static final char TOKEN_DELIM = ':';
    public static final char TOKEN_NULL = '~';
    
    // reading
    public static final int TOKEN_READ_ALL = Integer.MAX_VALUE;
    
    // data
    protected List<DataMarshal> tokens_ = new ArrayList<DataMarshal>();
    protected EscapeStringTokenizer tokenizer_;
    
    /**
     * Empty constructor needed for demarshalling
     */
    public TokenizedList() {}
    
    /**
     * Create a new TokenizedList from a string,
     * previously generated by write().  If nReadNumTokens is
     * TOKEN_READ_ALL, then all tokens are read.  Otherwise, up to
     * nReadTokens is read.
     */
    public TokenizedList(MsgState state, String sData, int nReadNumTokens)
    {
        tokenizer_ = new EscapeStringTokenizer(sData, TOKEN_DELIM);
        read(state, tokenizer_, nReadNumTokens);
    }

        
    /**
     * Finish parsing any remaining tokens from the data stream created 
     * by the above constructor
     */
    public void finishParsing(MsgState state)
    {
        if (tokenizer_ == null) return;
        _read(state, tokenizer_, TOKEN_READ_ALL);
        tokenizer_ = null;
    }
    
    /**
     * get next token
     */
    private Object nextToken(Class<?> cExpected)
    {
        ApplicationError.assertTrue(tokens_.size() > 0, "No tokens left");
        Object o = tokens_.remove(0);
        if (o == null) return null;
        ApplicationError.assertTrue(cExpected.isAssignableFrom(o.getClass()), "Next token wrong type", o.getClass().getName());
        return o;
    }
    
    /**
     * Peek at next token
     */
    public Object peekToken()
    {
        ApplicationError.assertTrue(tokens_.size() > 0, "No tokens left");
        return tokens_.get(0);
    }
    
    /**
     * got tokens?
     */
    public boolean hasMoreTokens()
    {
        return(tokens_.size() > 0);
    }
    
    /**
     * Add null token
     */
    public void addTokenNull()
    {
        tokens_.add(null);
    }
        
    /**
     * Add String token
     */
    public void addToken(String sToken)
    {
        if (sToken == null) { addTokenNull(); return; }
        addToken(new DataMarshaller.DMString(sToken));
    }
    
    /**
     * Remove string token
     */
    public String removeStringToken()
    {
        Object o = nextToken(DataMarshaller.DMString.class);
        if (o == null) return null;
        return (String) ((DataMarshaller.DMWrapper) o).value();
    }
    
    /**
     * Add integer token (new Integer created)
     */
    public void addToken(int nToken)
    {
        addToken(new DataMarshaller.DMInteger(nToken));
    }
    
    /**
     * Remove int token
     */
    public int removeIntToken()
    {
        Object o = nextToken(DataMarshaller.DMInteger.class);
        ApplicationError.assertNotNull(o, "No integer found");
        return (Integer) ((DataMarshaller.DMWrapper) o).value();
    }
    
    /**
     * Add Integer token
     */
    public void addToken(Integer nToken)
    {
        if (nToken == null) { addTokenNull(); return; }
        addToken(new DataMarshaller.DMInteger(nToken));
    }
    
    /**
     * Remove Integer token
     */
    public Integer removeIntegerToken()
    {
        Object o = nextToken(DataMarshaller.DMInteger.class);
        if (o == null) return null;
        return (Integer) ((DataMarshaller.DMWrapper) o).value();
    }
    
    /**
     * Add long token (new Long created)
     */
    public void addToken(long nToken)
    {
        addToken(new DataMarshaller.DMLong(nToken));
    }
    
    /**
     * Remove long token
     */
    public long removeLongToken()
    {
        Object o = nextToken(DataMarshaller.DMLong.class);
        ApplicationError.assertNotNull(o, "No long found");
        return (Long) ((DataMarshaller.DMWrapper) o).value();
    }
    
    /**
     * Add Long token
     */
    public void addToken(Long nToken)
    {
        if (nToken == null) { addTokenNull(); return; }
        addToken(new DataMarshaller.DMLong(nToken));
    }

    /**
     * Add double token (new Double created)
     */
    public void addToken(double dToken)
    {
        addToken(new DataMarshaller.DMDouble(dToken));
    }
    
    /**
     * Remove double token
     */
    public double removeDoubleToken()
    {
        Object o = nextToken(DataMarshaller.DMDouble.class);
        ApplicationError.assertNotNull(o, "No double found");
        return (Double) ((DataMarshaller.DMWrapper) o).value();
    }
    
    /**
     * Add boolean token
     */
    public void addToken(boolean bToken)
    {
        addToken(new DataMarshaller.DMBoolean(bToken));
    }
    
    /**
     * Remove boolean token
     */
    public boolean removeBooleanToken()
    {
        Object o = nextToken(DataMarshaller.DMBoolean.class);
        ApplicationError.assertNotNull(o, "No boolean found");
        return (Boolean) ((DataMarshaller.DMWrapper) o).value();
    }
    
    /**
     * Store name/value pair where value is an Integer
     */
    public void addNameValueToken(String sName, Integer iValue)
    {
        addToken(new NameValueToken(sName, iValue));
    }

    /**
     * Store name/value pair where value is an Long
     */
    public void addNameValueToken(String sName, Long lValue)
    {
        addToken(new NameValueToken(sName, lValue));
    }
       
    /**
     * Store name/value pair where value is a string
     */
    public void addNameValueToken(String sName, String sValue)
    {
        addToken(new NameValueToken(sName, sValue));
    }
    
    /**
     * Store name/value pair where value is a Boolean
     */
    public void addNameValueToken(String sName, Boolean bValue)
    {
        addToken(new NameValueToken(sName, bValue));
    }
    
    /**
     * Store name/value pair where value is a Double
     */
    public void addNameValueToken(String sName, Double dValue)
    {
        addToken(new NameValueToken(sName, dValue));
    }
       
    /**
     * Store name/value pair where value is marshallable
     */
    public void addNameValueToken(String sName, DataMarshal dValue)
    {
        addToken(new NameValueToken(sName, dValue));
    }
    
    /**
     * Remove name value token token
     */
    public NameValueToken removeNameValueToken()
    {
        Object o = nextToken(NameValueToken.class);
        if (o == null) return null;
        return (NameValueToken) o;
    }
    
    /**
     * Add marshallable token
     */
    public void addToken(DataMarshal dmToken)
    {
        if (dmToken == null)
        {
            addTokenNull();
        }
        else
        {
            tokens_.add(dmToken);
        }
    }
    
    /**
     * Remove marshallable token
     */
    public DataMarshal removeToken()
    {
        Object o = nextToken(DataMarshal.class);
        if (o == null) return null;
        return (DataMarshal) o;
    }
    
    /**
     * Class to escape special chars in given value.
     * Escapes special values used by TokenizedList,
     * NameValueToken and DataMarshaller
     */
    static String escape(String sValue)
    {
        if (sValue == null) return null;
        
        char c;
        int length = sValue.length();
        // lazily create StringBuilder only if we are escaping something
        StringBuilder sbEscape = null;
        for (int i = 0; i < length; i++)
        {
            c = sValue.charAt(i);
            switch(c)
            {
                case EscapeStringTokenizer.ESCAPE:
                case EscapeStringTokenizer.DOUBLE_QUOTE:
                case TOKEN_NULL:
                case TOKEN_DELIM:
                case NameValueToken.TOKEN_NVT_SEP:
                    if (sbEscape == null)
                    {
                        sbEscape = new StringBuilder(length+1);
                        sbEscape.append(sValue.substring(0, i));
                    }
                    sbEscape.append(EscapeStringTokenizer.ESCAPE);
                    break;
                    
                // escape returns since many uses of tokenized list
                // assume one per line
                case EscapeStringTokenizer.ACTUAL_RETURN:
                    if (sbEscape == null)
                    {
                        sbEscape = new StringBuilder(length+2);
                        sbEscape.append(sValue.substring(0, i));
                    }
                    sbEscape.append(EscapeStringTokenizer.ESCAPE);
                    sbEscape.append(EscapeStringTokenizer.ESCAPED_RETURN);
                    continue;
                    
                default:
                    break;
            }
            if (sbEscape != null) sbEscape.append(c);
        }
        return sbEscape == null ? sValue : sbEscape.toString();
    }
    
    /**
     * Write this entry out to the given writer
     */
    public void write(MsgState state, Writer writer) throws IOException
    {
        Object token;
        for (int i = 0; i < tokens_.size(); i++)
        {
            token = tokens_.get(i);
            if (i > 0)
            {
                writer.write(TOKEN_DELIM);
            }
            
            if (token == null)
            {
                writer.write(TOKEN_NULL);
            }
            else if (token instanceof DataMarshal)
            {
                writer.write(escape(DataMarshaller.marshal(state, (DataMarshal)token)));
            }
            else
            {
                ApplicationError.assertTrue(false, "Unsupported token", token);
            }
        }
    }
    
    /**
     * Init this list from a string (opposite of write)
     */
    public void read(MsgState state, EscapeStringTokenizer tokenizer, int nReadNumTokens)
    {
        _read(state, tokenizer, nReadNumTokens);
    }
    
    /**
     * read logic - private so can't be overridden
     */
    public void _read(MsgState state, EscapeStringTokenizer tokenizer, int nReadNumTokens)
    {
        int nCnt = 0;
        String token;
        while (tokenizer.hasMoreTokens() && nCnt < nReadNumTokens)
        {
            token = tokenizer.nextToken();
            if (token.length() == 1 && token.charAt(0) == TOKEN_NULL)
            {
                addTokenNull();
            }
            else
            {
                addToken(DataMarshaller.demarshal(state, token));
            }
            nCnt++;
        }
    }    

    /**
     * Don't allow toString() calls
     */
    public String toString()
    {
        logger.debug("Don't call toString() - use marshal() instead");
        return this.getClass().getName();
    }
    
    /**
     * Recreate list from string
     */
    public void demarshal(MsgState state, String sData) {
        EscapeStringTokenizer st = new EscapeStringTokenizer(sData, TOKEN_DELIM);
        read(state, st, TOKEN_READ_ALL);
    }
    
    /**
     * Store list as a string
     */
    public String marshal(MsgState state) {
        StringWriter sw = new StringWriter();
        try {
            write(state, sw);
        } catch (IOException ioe)
        {
            throw new ApplicationError(ioe);
        }
        return sw.toString();
    }
}
