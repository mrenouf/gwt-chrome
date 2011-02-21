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
package com.google.gwt.chrome.crx.client.events;

import com.google.gwt.chrome.crx.client.Tabs.Tab;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Overlay for event object returned from chrome.browserAction.
 * 
 * See documentation at: <a href="http://dev.chromium.org/developers/design-documents/extensions/page-actions-api"
 * >Page Actions API</a>
 */
public final class BrowserActionEvent extends Event {
  /**
   * The listener interface for handling BrowserAction Events.
   */
  public interface Listener {
    void onClicked(Tab tab);
  }

  protected BrowserActionEvent() {
  }

  public ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(tab) {
        listener.
          @com.google.gwt.chrome.crx.client.events.BrowserActionEvent.Listener::onClicked(Lcom/google/gwt/chrome/crx/client/Tabs$Tab;)
          (tab);
    }
    this.addListener(handle);
    return handle;
  }-*/;
}