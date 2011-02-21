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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Wraps event from chrome.onRequestExternal.
 *
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/content-scripts"
 * >Content Script Messaging</a>
 *
 */
public class RequestExternalEvent extends Event {
  /**
   * The extension that sent the request.
   */
  public static class Sender extends JavaScriptObject {
    protected Sender() {
    }

    public final native String getId() /*-{
      return this.id;
    }-*/;
  }

  /**
   * Function object that can be used to send response JSON payloads back to the
   * sender.
   */
  public static class SendResponse extends JavaScriptObject {
    protected SendResponse() {
    }

    public final native void invoke(JavaScriptObject response) /*-{
      this(response);
    }-*/;
  }

  /**
   * Called when a ContentScript opens a port.
   */
  public interface Listener {
    void onRequestExternal(JavaScriptObject request, Sender sender,
        SendResponse sendResponse);
  }

  /**
   * Takes care of reporting exceptions to the console in hosted mode.
   *
   * @param listener the listener object to call back.
   * @param request the payload of the data that was sent with this request
   * @param sender the sender information for the extension that sent the
   *          request
   * @param sendResponse the function object that can be used to send a response
   *          to the sender
   */
  private static void onRequestExternalImpl(Listener listener,
      JavaScriptObject request, Sender sender, SendResponse sendResponse) {
    UncaughtExceptionHandler ueh = GWT.getUncaughtExceptionHandler();
    if (ueh != null) {
      try {
        listener.onRequestExternal(request, sender, sendResponse);
      } catch (Exception ex) {
        ueh.onUncaughtException(ex);
      }
    } else {
      listener.onRequestExternal(request, sender, sendResponse);
    }
  }

  protected RequestExternalEvent() {
  }

  public final ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(request, sender, sendResponse) {
      @com.google.gwt.chrome.crx.client.events.RequestExternalEvent::onRequestExternalImpl(Lcom/google/gwt/chrome/crx/client/events/RequestExternalEvent$Listener;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/chrome/crx/client/events/RequestExternalEvent$Sender;Lcom/google/gwt/chrome/crx/client/events/RequestExternalEvent$SendResponse;)
      (listener, request, sender, sendResponse);
    }

    this.addListener(handle);
    return handle;
  }-*/;
}
