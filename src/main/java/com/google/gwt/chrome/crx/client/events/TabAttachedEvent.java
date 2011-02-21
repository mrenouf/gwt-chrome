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
 * Wraps event from chrome.tabs.onAttached.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/tabs-api"
 * >Tabs API</a>
 */
public final class TabAttachedEvent extends Event {
  /**
   * The data object that gets passed back onTabAttached.
   */
  public static class Data extends JavaScriptObject {
    protected Data() {
    }

    public final native int getNewPosition() /*-{
      return this.newPosition;
    }-*/;

    public final native int getNewWindowId() /*-{
      return this.newWindowId;
    }-*/;
  }

  /**
   * Listener interface for receiving TabAttachedEvents.
   */
  public interface Listener {
    void onTabAttached(int tabId, Data data);
  }

  protected TabAttachedEvent() {
  }

  public ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(tabId, data) {
      listener.
          @com.google.gwt.chrome.crx.client.events.TabAttachedEvent.Listener::onTabAttached(ILcom/google/gwt/chrome/crx/client/events/TabAttachedEvent$Data;)
          (tabId, data);
    }

    this.addListener(handle);
    return handle;
  }-*/;
}