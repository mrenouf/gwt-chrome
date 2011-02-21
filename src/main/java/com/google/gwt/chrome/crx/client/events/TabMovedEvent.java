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
 * Wrapper for event object returned from chrome.tabs.onMove.
 * 
 * Access with Tabs.getOnMoveEvent()
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/tabs-api"
 * >Tabs API</a>
 */
public final class TabMovedEvent extends Event {
  /**
   * Data object passed onTabMoved.
   */
  public static class Data extends JavaScriptObject {
    protected Data() {
    }

    public final native int getFromIndex() /*-{
      return this.fromIndex;
    }-*/;

    public final native int getToIndex() /*-{
      return this.toIndex;
    }-*/;

    public final native int getWindowId() /*-{
      return this.windowId;
    }-*/;
  }

  /**
   * Listener interface for TabMovedEvents.
   */
  public interface Listener {
    void onTabMoved(int tabId, Data data);
  }

  protected TabMovedEvent() {
  }

  public ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(tabId, data) {
      listener.
          @com.google.gwt.chrome.crx.client.events.TabMovedEvent.Listener::onTabMoved(ILcom/google/gwt/chrome/crx/client/events/TabMovedEvent$Data;)
          (tabId, data);
    }

    this.addListener(handle);
    return handle;
  }-*/;
}