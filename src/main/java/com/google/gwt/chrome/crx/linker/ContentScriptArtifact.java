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

/**
 * Artifacts for ContentScripts.
 */
public class ContentScriptArtifact extends Artifact<ContentScriptArtifact> {
  private static final long serialVersionUID = -1216255540374320761L;

  private final String path;
  private final String runAt;
  private final String[] whiteList;
  
  public ContentScriptArtifact(String path, String[] whiteList, String runAt) {
    super(ExtensionLinker.class);
    this.path = path;
    this.whiteList = whiteList;
    this.runAt = runAt;
  }

  public String getPath() {
    return path;
  }
  
  public String getRunAt() {
    return this.runAt;
  }
  
  public String[] getWhiteList() {
    return this.whiteList;
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }

  @Override
  protected int compareToComparableArtifact(ContentScriptArtifact o) {
    assert o != null;
    return path.compareTo(o.path);
  }

  @Override
  protected Class<ContentScriptArtifact> getComparableArtifactType() {
    return ContentScriptArtifact.class;
  }
}
