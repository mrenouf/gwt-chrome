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

import com.google.gwt.core.ext.linker.Artifact;

class BrowserActionArtifact extends Artifact<BrowserActionArtifact> {
  private static final long serialVersionUID = -544245597242157263L;
  private final String[] icons;
  private final String name;
  private final String defaultIcon;

  public BrowserActionArtifact(String name, String[] icons, String defaultIcon) {
    super(ExtensionLinker.class);
    this.name = name;
    this.icons = icons;
    this.defaultIcon = defaultIcon;
  }

  public String getDefaultIcon() {
    return this.defaultIcon;
  }
  
  public String[] getIcons() {
    return icons;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return icons.hashCode();
  }

  @Override
  protected int compareToComparableArtifact(BrowserActionArtifact o) {
    assert o != null;
    
    // Ordering doesn't really have meaning for us.
    return 0;
  }

  @Override
  protected Class<BrowserActionArtifact> getComparableArtifactType() {
    return BrowserActionArtifact.class;
  }
}
