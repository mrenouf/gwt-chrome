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
 * Chrome Extensions Event API.
 * 
 * This class provides a Java Wrapper for a Chrome Event.
 * 
 * Each subclass should provide an 'addListener()' method to register a callback
 * to be called when this event is dispatched that returns the closure used to
 * add the event as a ListenerHandle object.
 * 
 * See the documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/events"
 * >Events</a>
 */
public abstract class Event extends JavaScriptObject {

  /**
   * Opaque wrapper for the callback closure used to remove the listener if
   * needed.
   */
  public static class ListenerHandle {
    private final Event event;

    private final JavaScriptObject functionHandle;

    public ListenerHandle(Event event, JavaScriptObject functionHandle) {
      this.functionHandle = functionHandle;
      this.event = event;
    }

    public final boolean isAttached() {
      return event.hasListener(functionHandle);
    }

    public final void removeListener() {
      event.removeListener(functionHandle);
    }
  }

  protected Event() {
  }

  /**
   * Test if the given callback is registered for this event.
   * 
   * @param functionHandle handle returned from addListener.
   * @return true if the callback is registered.
   */
  private native boolean hasListener(JavaScriptObject functionHandle) /*-{
    return !!this.hasListener(functionHandle);
  }-*/;

  /**
   * Unregisters a callback.
   * 
   * @param functionHandle handle returned from addListener.
   */
  private native void removeListener(JavaScriptObject functionHandle) /*-{
    this.removeListener(functionHandle);
  }-*/;
}
