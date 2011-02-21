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
package com.google.gwt.chrome.crx.client;

import com.google.gwt.chrome.crx.client.Tabs.Tab;
import com.google.gwt.chrome.crx.client.events.MessageEvent;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * Extension messaging port for communication between ContentScripts and the
 * BackgroundPage.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/content-scripts"
 * >Content Script Messaging</a>
 */
public class Port extends JavaScriptObject {
  /**
   * Overlay type for messages sent over a Port.
   */
  public static class Message extends JavaScriptObject {
    /**
     * Subclasses should use this to obtain a JSO and then extend it with custom
     * fields.
     */
    public static native Message create(int type) /*-{
      return {type: type};
    }-*/;

    protected Message() {
    }

    public final native int getType() /*-{
      return this.type;
    }-*/;

    public final native Message setProperty(String field, boolean value) /*-{
      this[field] = value;
      return this;
    }-*/;

    public final native Message setProperty(String field, int value) /*-{
      this[field] = value;
      return this;
    }-*/;

    public final native Message setProperty(String field, JavaScriptObject value) /*-{
      this[field] = value;
      return this;
    }-*/;

    public final native Message setProperty(String field, String value) /*-{
      this[field] = value;
      return this;
    }-*/;
  }

  protected Port() {
  }

  public final native String getName() /*-{
    return this.name;
  }-*/;

  public final native MessageEvent getOnMessageEvent() /*-{
    return this.onMessage;
  }-*/;

  public final native Tab getTab() /*-{
    return this.tab;
  }-*/;

  public final native void postMessage(JavaScriptObject msg) /*-{
    this.postMessage(msg);
  }-*/;
}
