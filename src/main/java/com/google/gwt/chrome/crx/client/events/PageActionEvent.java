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
 * Overlay for event object returned from chrome.pageActions[pageActionId].
 * 
 * See documentation at: <a href="http://dev.chromium.org/developers/design-documents/extensions/page-actions-api"
 * >Page Actions API</a>
 */
public final class PageActionEvent extends Event {
  /**
   * The data object that gets passed to a {@link Listener} when a page action
   * is clicked.
   */
  public static class Info extends JavaScriptObject {
    protected Info() {
    }

    public final native int getButton() /*-{
      return this.button;
    }-*/;

    public final native int getTabId() /*-{
      return this.tabId;
    }-*/;

    public final native String getTabUrl() /*-{
      return this.tabUrl;
    }-*/;
  }

  /**
   * The listener interface for handling PageAction Events.
   */
  public interface Listener {
    void onPageAction(int pageActionId, Info info);
  }

  protected PageActionEvent() {
  }

  public ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(pageActionId, info) {
        listener.
          @com.google.gwt.chrome.crx.client.events.PageActionEvent.Listener::onPageAction(ILcom/google/gwt/chrome/crx/client/events/PageActionEvent$Info;)
          (pageActionId, info);
    }
    this.addListener(handle);
    return handle;
  }-*/;
}