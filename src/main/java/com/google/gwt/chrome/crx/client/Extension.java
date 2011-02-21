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

import com.google.gwt.core.client.EntryPoint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The main EntryPoint for your extension.
 */
public abstract class Extension implements EntryPoint {
  /**
   * Annotation for the Specification meta data for the entry point. This data
   * is used for generating the extension manifest.
   */
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface ManifestInfo {
    String description();

    String[] icons() default {};

    String name();

    String[] permissions();

    String updateUrl() default NO_UPDATE_URL;

    String version();

    String publicKey();
  }

  public static final String NO_UPDATE_URL = "";

  public abstract String getVersion();

  /**
   * Implement this entry point in your extension subclass.
   */
  public abstract void onBackgroundPageLoad();

  public void onModuleLoad() {
    // TODO(jaimeyap): Figure out how to have this kick off potentially useful
    // debugging UI in hosted mode.
    onBackgroundPageLoad();
  }
}
