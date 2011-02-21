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

import com.google.gwt.chrome.crx.client.events.BrowserActionEvent;
import com.google.gwt.chrome.crx.client.events.BrowserActionEvent.Listener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A BrowserAction which inserts a button in the toolbar.
 * 
 * API for chrome.browserAction.
 * 
 * The API is described in JSON form in the Chrome source:
 * src/chrome/common/extensions/api/extension_api.json
 * 
 * TODO(jaimeyap): Implement the badging API when we need it.
 * 
 */
public abstract class BrowserAction implements Component {
  /**
   * Annotation for defining the properties of a PageAction.
   */
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface ManifestInfo {
    String defaultIcon();
    String name();
  }

  protected BrowserAction() {
  }

  public final void addListener(Listener listener) {
    getBrowserActionEvent().addListener(listener);
  }

  public abstract String getName();

  public final void setIcon(int tabId, Icon icon) {
    setIconImpl(tabId, icon.getPath());
  }

  public final native void setTitle(int tabId, String title) /*-{
    chrome.browserAction.setTitle({tabId: tabId, title: title});
  }-*/;

  private native BrowserActionEvent getBrowserActionEvent() /*-{
    return chrome.browserAction.onClicked;
  }-*/;

  private native void setIconImpl(int tabId, String iconPath) /*-{
    chrome.browserAction.setIcon({tabId: tabId, path: iconPath});
  }-*/;
}