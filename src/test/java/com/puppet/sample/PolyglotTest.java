package com.puppet.sample;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PolyglotTest
{
    @Test
    public void testEnMsg() {
        assertEquals("Well Come To My Tomcat Server!", new Polyglot().enMsg());
    }
    
}
