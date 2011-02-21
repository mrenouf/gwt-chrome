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
 * Artifact for {@link com.google.gwt.chrome.crx.client.ToolStrip}s.
 */
public class ToolStripArtifact extends Artifact<ToolStripArtifact> {
  private static final long serialVersionUID = -486331903367200384L;

  private final String path;

  public ToolStripArtifact(String path) {
    super(ExtensionLinker.class);
    this.path = path;
  }

  public String getPath() {
    return path;
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }

  @Override
  protected int compareToComparableArtifact(ToolStripArtifact o) {
    assert o != null;
    return path.compareTo(o.path);
  }

  @Override
  protected Class<ToolStripArtifact> getComparableArtifactType() {
    return ToolStripArtifact.class;
  }
}
