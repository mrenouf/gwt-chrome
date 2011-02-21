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

import com.google.gwt.chrome.crx.client.events.PageActionEvent.Listener;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A PageAction which inserts buttons into the omnibox.
 * 
 * API for chrome.pageActions.
 * 
 * See documentation at: <a href="http://dev.chromium.org/developers/design-documents/extensions/page-actions-api"
 * >Page Actions</a>
 * 
 * The API is described in JSON form in the Chrome source:
 * src/chrome/common/extensions/api/extension_api.json
 * 
 */
public abstract class PageAction implements Component {
  /**
   * Annotation for defining the properties of a PageAction.
   */
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  public @interface ManifestInfo {
    String name();

    String pageActionId();
  }

  protected PageAction() {
  }

  public final void addListener(Listener listener) {
    PageActions.getPageActionEvent(getId()).addListener(listener);
  }

  public final void disableForTab(int tabId, String url) {
    PageActions.disableForTab(getId(), tabId, url);
  }

  public final void enableForTab(int tabId, String url, String title,
      Icon icon) {
    PageActions.enableForTab(getId(), tabId, url, title, icon.getId());
  }

  public abstract String getId();
  
  public abstract String getName();
  
  public native void hide(int tabId) /*-{
    chrome.pageAction.hide(tabId);
  }-*/;
  
  public final void setIcon(int tabId, Icon icon) {
    setIconImpl(tabId, icon.getId());
  }

  public final native void setTitle(int tabId, String title) /*-{
    chrome.pageAction.setTitle({tabId: tabId, title: title});
  }-*/;
  
  public final native void show(int tabId) /*-{
    chrome.pageAction.show(tabId);
  }-*/;

  private native void setIconImpl(int tabId, int iconId) /*-{
    chrome.pageAction.setIcon({tabId: tabId, iconIndex: iconId});
  }-*/;
}