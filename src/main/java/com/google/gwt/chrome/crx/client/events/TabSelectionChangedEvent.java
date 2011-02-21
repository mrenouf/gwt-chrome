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

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wraps event from chrome.tabs.onSelectionChanged.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/tabs-api"
 * >Tabs API</a>
 */
public final class TabSelectionChangedEvent extends Event {
  /**
   * DataObject for TabSelection changes.
   */
  public static class Data extends JavaScriptObject {
    protected Data() {
    }

    public final native int getWindowId() /*-{
      return this.windowId;
    }-*/;
  }

  /**
   * Listener interface.
   */
  public interface Listener {
    void onTabSelectionChanged(int tabId, Data changedProps);
  }

  protected TabSelectionChangedEvent() {
  }

  public ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(tabId, data) {
      listener.
          @com.google.gwt.chrome.crx.client.events.TabSelectionChangedEvent.Listener::onTabSelectionChanged(ILcom/google/gwt/chrome/crx/client/events/TabSelectionChangedEvent$Data;)
          (tabId, data);
    }

    this.addListener(handle);
    return handle;
  }-*/;
}
