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
 * Wraps several events from chrome.windows.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/windows-api"
 * >Windows API</a>
 */
public final class WindowEvent extends Event {
  /**
   * Callback for events that return a single window id in the first argument.
   */
  public interface Listener {
    void onWindow(int windowId);
  }

  protected WindowEvent() {
  }

  public ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(windowId) {
      listener.
          @com.google.gwt.chrome.crx.client.events.WindowEvent.Listener::onWindow(I)
          (windowId);
    }

    this.addListener(handle);
    return handle;
  }-*/;
}