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
 * Artifact for {@link com.google.gwt.chrome.crx.client.Plugin}s.
 */
public class PluginArtifact extends Artifact<PluginArtifact> {
  private static final long serialVersionUID = -2913839495155156112L;

  private final boolean isPublic;
  private final String path;

  public PluginArtifact(String path, boolean isPublic) {
    super(ExtensionLinker.class);
    this.path = path;
    this.isPublic = isPublic;
  }

  public String getPath() {
    return path;
  }

  @Override
  public int hashCode() {
    return path.hashCode();
  }

  public boolean isPublic() {
    return isPublic;
  }

  @Override
  protected int compareToComparableArtifact(PluginArtifact o) {
    assert o != null;
    return path.compareTo(o.path);
  }

  @Override
  protected Class<PluginArtifact> getComparableArtifactType() {
    return PluginArtifact.class;
  }
}
