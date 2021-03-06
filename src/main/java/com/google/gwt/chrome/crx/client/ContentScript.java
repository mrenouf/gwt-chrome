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
package com.google.gwt.chrome.crx.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.gwt.core.client.EntryPoint;

/**
 * ContentScripts are themselves EntryPoints and should be the main entry point
 * in a separate stand alone module. You may annotate a ContentScript with a
 * ContentScript.ScriptName annotation to specify what the output js file looks
 * like. If no annotation is present the file name defaults to the name of the
 * subclass.
 * 
 * <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/content-scripts"
 * >Content Scripts</a>
 */
public abstract class ContentScript implements Component, EntryPoint {

  public enum RunAt {
    DOCUMENT_START,
    DOCUMENT_END,
    DOCUMENT_IDLE;
  }

  /**
   * ContentScript Specification annotation for defining the fields that go in
   * the manifest.
   */
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface ManifestInfo {
    String[] matches();

    String path();

    RunAt runAt() default RunAt.DOCUMENT_IDLE;
  }
}
