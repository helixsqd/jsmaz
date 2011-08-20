jsmaz
======
jsmaz is a Java port of the Smaz short string compression algorithm by Salvatore Sanfilippo and released as a C
library at: https://github.com/antirez/smaz

You can also find a Ruby port by Peter Cooper at: https://github.com/peterc/rsmaz

This port currently only supports ASCII characters and will throw an exception when trying to compress non-ascii characters.

Usage
=====

    Smaz smaz = new Smaz();
    String testString = "this is a simple test";
    byte[] compressed = smaz.compress(testString);
    String uncompressedString = smaz.decompress(compressed);
    assertEquals(testString, uncompressedString);


License
=======
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