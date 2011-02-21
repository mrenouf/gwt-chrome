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
 * Wraps event returned when a port has a message.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/content-scripts"
 * >Content Script Messaging</a>
 */
public class MessageEvent extends Event {
  /**
   * Subclass Message when you want to define a message type to send on a Port.
   */
  public static class Message extends Event {
    protected Message() {
    }
  }

  /**
   * Messaging handler that gets called each time you send a message on a Port.
   */
  public interface Listener {
    void onMessage(Message message);
  }

  protected MessageEvent() {
  }

  public final ListenerHandle addListener(Listener listener) {
    return new ListenerHandle(this, addListenerImpl(listener));
  }

  private native JavaScriptObject addListenerImpl(Listener listener) /*-{
    var handle = function(data) {
    listener.
        @com.google.gwt.chrome.crx.client.events.MessageEvent.Listener::onMessage(Lcom/google/gwt/chrome/crx/client/events/MessageEvent$Message;)
        (data);
    };

    this.addListener(handle);
    return handle;
  }-*/;
}