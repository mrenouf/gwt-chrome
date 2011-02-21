/*
 * Copyright 2009 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.chrome.crx.linker;

/**
 * A set of static utility methods that come in handy when writing a
 * {@link com.google.gwt.core.ext.Generator}.
 */
public class GeneratorUtils {

  /**
   * Escapes a string into a java source string literal that can safely be
   * emitted as source code using {@link java.io.PrintWriter}.
   * 
   * @param text unescaped text
   * @return an escaped string literal
   */
  public static String toJavaLiteral(String text) {
    // Replace all quotes with \\".
    text = text.replaceAll("\"", "\\\\\"");
    // Replace all EOL with "\\n".
    text = text.replaceAll("\n", "\\\\n");
    return text;
  }
}
