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
 * Wraps event from chrome.tabs.onUpdated.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/tabs-api"
 * >Tabs API</a>
 */
public final class TabUpdatedEvent extends Event {
  /**
   * Data object passed onTabUpdated.
   */
  public static class ChangeInfo extends JavaScriptObject {
    public static final String STATUS_LOADING = "loading";
    public static final String STATUS_COMPLETED = "completed";
    
    protected ChangeInfo() {
    }

    public final native String getStatus() /*-{
      return this.status;
    }-*/;

    public final native String getUrl() /*-{
      return this.url;
    }-*/;
  }

  /**
   * Listener interface for tab updates.
   */
  public interface Listener {
    void onTabUpdated(int tabId, ChangeInfo changeInfo, Tab tab);
  }

  protected TabUpdatedEvent() {
  }

  public ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(tabId, changeInfo, tab) {
      listener.@com.google.gwt.chrome.crx.client.events.TabUpdatedEvent.Listener::onTabUpdated(ILcom/google/gwt/chrome/crx/client/events/TabUpdatedEvent$ChangeInfo;Lcom/google/gwt/chrome/crx/client/Tabs$Tab;)(tabId, changeInfo, tab);
    }

    this.addListener(handle);
    return handle;
  }-*/;
}