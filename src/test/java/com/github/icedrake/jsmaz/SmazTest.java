/*
   Copyright 2011 icedrake

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.github.icedrake.jsmaz;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author icedrake
 */
public class SmazTest {
    // immutable smaz instance
    private Smaz smaz = new Smaz();


    @Test
    public void simpleTest() {
        String testString = "this is a simple test";
        byte[] compressed = smaz.compress(testString);
        String uncompressedString = smaz.decompress(compressed);
        assertEquals(testString, uncompressedString);
    }

    @Test(expected = IllegalArgumentException.class)
    public void asciiOnlyTest() {
        String testString = "this is a utf-8 string ÿ";
        smaz.compress(testString);
    }

    @Test
    // minus a few that were off by 1 from the c version but match the ruby one
    public void originalSmazCTest() {
        String[] strings = {
                "This is a small string",
                "foobar",
                "the end",
                "not-a-g00d-Exampl333",
                "Smaz is a simple compression library",
                "1000 numbers 2000 will 10 20 30 compress very little",
                "and now a few italian sentences:",
                "Nel mezzo del cammin di nostra vita, mi ritrovai in una selva oscura",
                "Mi illumino di immenso",
                "try it against urls",
                "http://google.com",
                "http://programming.reddit.com",
                "http://github.com/antirez/smaz/tree/master"
        };

        String expectedOutput = "'This is a small string' compressed by 50%\n" +
                "'foobar' compressed by 34%\n" +
                "'the end' compressed by 58%\n" +
                "'not-a-g00d-Exampl333' enlarged by 15%\n" +
                "'Smaz is a simple compression library' compressed by 39%\n" +
                "'1000 numbers 2000 will 10 20 30 compress very little' compressed by 10%\n" +
                "'and now a few italian sentences:' compressed by 41%\n" +
                "'Nel mezzo del cammin di nostra vita, mi ritrovai in una selva oscura' compressed by 33%\n" +
                "'Mi illumino di immenso' compressed by 37%\n" +
                "'try it against urls' compressed by 37%\n" +
                "'http://google.com' compressed by 59%\n" +
                "'http://programming.reddit.com' compressed by 52%\n" +
                "'http://github.com/antirez/smaz/tree/master' compressed by 46%\n";

        StringBuilder output = new StringBuilder();
        for (String str : strings) {
            byte[] origBytes = str.getBytes();
            System.out.println(str);
            byte[] compressedBytes = smaz.compress(str);
            int comprlevel = 100 - ((100 * compressedBytes.length) / origBytes.length);
            if (comprlevel < 0) {
                output.append(String.format("'%s' enlarged by %d%%\n", str, -comprlevel));
            } else {
                output.append(String.format("'%s' compressed by %d%%\n", str, comprlevel));
            }
        }

        assertEquals(expectedOutput, output.toString());
    }
}
