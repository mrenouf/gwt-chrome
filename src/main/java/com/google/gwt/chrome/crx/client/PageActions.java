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

import com.google.gwt.chrome.crx.client.events.PageActionEvent;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * PageActions API.
 * 
 * See documentation at: <a href="http://dev.chromium.org/developers/design-documents/extensions/page-actions-api"
 * >Page Actions</a>
 * 
 * The API is described in JSON form in the Chrome source:
 * src/chrome/common/extensions/api/extension_api.json
 */
public class PageActions extends JavaScriptObject {

  public static final native void disableForTab(String pageActionId, int tabId,
      String url) /*-{
    chrome.pageActions.disableForTab(pageActionId, {tabId: tabId, url: url});
  }-*/;

  public static final native void enableForTab(String pageActionId, int tabId,
      String url, String title, int iconId) /*-{
    chrome.pageActions.enableForTab(pageActionId,
        {tabId: tabId,
         url: url,
         title: title,
         iconId: iconId});
  }-*/;

  public static native PageActionEvent getPageActionEvent(String pageActionId) /*-{
    return chrome.pageActions[pageActionId];
  }-*/;

  protected PageActions() {
  }
}
