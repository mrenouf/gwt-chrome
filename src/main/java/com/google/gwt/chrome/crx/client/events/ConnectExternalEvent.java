/*
 * Copyright 2010 Google Inc.
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

import com.google.gwt.chrome.crx.client.Port;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wraps event from chrome.onConnectExternal.
 *
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/content-scripts"
 * >Content Script Messaging</a>
 *
 */
public class ConnectExternalEvent extends Event {
  /**
   * Called when a ContentScript opens a port.
   */
  public interface Listener {
    void onConnectExternal(Port port);
  }

  /**
   * Takes care of reporting exceptions to the console in hosted mode.
   *
   * @param listener the listener object to call back.
   * @param port argument from the callback.
   */
  private static void onConnectImpl(Listener listener, Port port) {
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (ueh != null) {
      try {
        listener.onConnectExternal(port);
      } catch (Exception ex) {
        ueh.onUncaughtException(ex);
      }
    } else {
      listener.onConnectExternal(port);
    }
  }

  protected ConnectExternalEvent() {
  }

  public final ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(port) {
      @com.google.gwt.chrome.crx.client.events.ConnectExternalEvent::onConnectImpl(Lcom/google/gwt/chrome/crx/client/events/ConnectExternalEvent$Listener;Lcom/google/gwt/chrome/crx/client/Port;)
      (listener, port);
    }

    this.addListener(handle);
    return handle;
  }-*/;
}
