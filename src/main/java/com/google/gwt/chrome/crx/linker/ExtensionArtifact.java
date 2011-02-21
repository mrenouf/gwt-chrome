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

class ExtensionArtifact extends Artifact<ExtensionArtifact> {
  private static final long serialVersionUID = 8088249825810527401L;

  static class IconInfo {
    private final String filename;
    private final int size;

    IconInfo(String filename, int size) {
      this.filename = filename;
      this.size = size;
    }

    String getFilename() {
      return filename;
    }

    int getSize() {
      return size;
    }
  }

  private final String description;
  private final String name;
  private final String[] permissions;
  private final String version;
  private final String updateUrl;
  private final IconInfo[] icons;
  private final String publicKey;

  public ExtensionArtifact(String name, String description, String version,
      String[] permissions, String updateUrl, IconInfo[] icons, String publicKey) {
    super(ExtensionLinker.class);
    this.name = name;
    this.description = description;
    this.version = version;
    this.permissions = permissions;
    this.updateUrl = updateUrl;
    this.icons = icons;
    this.publicKey = publicKey;
  }

  public String getDescription() {
    return description;
  }

  public IconInfo[] getIcons() {
    return icons;
  }

  public String getName() {
    return name;
  }

  public String[] getPermissions() {
    return this.permissions;
  }

  public String getUpdateUrl() {
    return updateUrl;
  }

  public String getVersion() {
    return version;
  }
  
  public String getPublicKey() {
    return publicKey;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  protected int compareToComparableArtifact(ExtensionArtifact o) {
    assert o != null;
    return name.compareTo(o.name);
  }

  @Override
  protected Class<ExtensionArtifact> getComparableArtifactType() {
    return ExtensionArtifact.class;
  }
}
