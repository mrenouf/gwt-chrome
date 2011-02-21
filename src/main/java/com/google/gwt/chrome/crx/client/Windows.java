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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * Wraps the chrome.windows API.
 * 
 * The windows API allows you to manipulate the browser windows in Chromium.
 * 
 * See documentation at: <a href=
 * "http://dev.chromium.org/developers/design-documents/extensions/windows-api"
 * >Windows API</a>
 * 
 * The API is described in JSON form in the Chrome source:
 * src/chrome/common/extensions/api/extension_api.json
 */
public class Windows {
  /**
   * Window description that gets passed around in Chrome Extension land.
   */
  public static class Window extends JavaScriptObject {
    protected Window() {
    }

    public final native String getFavIconUrl() /*-{
      return this.favIconUrl;
    }-*/;

    public final native int getId() /*-{
      return this.id;
    }-*/;

    public final native int getIndex() /*-{
      return this.index;
    }-*/;

    public final native String getTitle() /*-{
      return this.title;
    }-*/;

    public final native String getUrl() /*-{
      return this.url;
    }-*/;

    public final native int getWindowId() /*-{
      return this.windowId;
    }-*/;

    public final native boolean isSelected() /*-{
      return this.selected;
    }-*/;
  }

  /**
   * Callback that returns an array of windows.
   */
  public interface OnWindowsCallback {
    void onWindow(JsArray<Window> windows);
  }

  /**
   * Simple callback interface for getting information about a Window.
   */
  public interface OnWindowCallback {
    void onWindow(Window window);
  }

  /**
   * Simple callback interface that returns no args.
   */
  public interface OnRemovedCallback {
    void onRemove();
  }

  /**
   * Creates a new window. Note that even "popup" windows, which don't contain
   * any visible tabs are still considered browsers, they just have a single
   * invisible tab.
   * 
   * @param url the initial url of the first tab in the window
   * @param left the distance from the left of the screen in pixels of the top
   *          left corner of the new window
   * @param top the distance from the top of the screen in pixels of the top
   *          left corner of the new window
   * @param width the width of the window
   * @param height the height of the window
   * @param callback the {@link OnWindowCallback} invoked asynchronously when
   *          the window is created.
   */
  public static native void create(String url, int left, int top, int width,
      int height, OnWindowCallback callback) /*-{
    chrome.windows.create({'url': url, 'left': left, 'top': top, 'width': width, 
                           'height': height}, 
                          function(wnd){
        callback.
        @com.google.gwt.chrome.crx.client.Windows$OnWindowCallback::onWindow(Lcom/google/gwt/chrome/crx/client/Windows$Window;)
        (wnd);
     });
  }-*/;

  /**
   * Overload of
   * {@link Windows#create(String, int, int, int, int, OnWindowCallback)} that
   * leaves off the optional callback.
   * 
   * @param url the initial url of the first tab in the window
   * @param left the distance from the left of the screen in pixels of the top
   *          left corner of the new window
   * @param top the distance from the top of the screen in pixels of the top
   *          left corner of the new window
   * @param width the width of the window
   * @param height the height of the window
   */
  public static native void create(String url, int left, int top, int width,
      int height) /*-{
    chrome.windows.create({'url': url, 'left': left, 'top': top, 'width': width, 
                           'height': height});
  }-*/;

  public static native void get(int windowId, OnWindowCallback callback) /*-{
    chrome.windows.get(windowId, function(wnd){ 
        callback.
        @com.google.gwt.chrome.crx.client.Windows$OnWindowCallback::onWindow(Lcom/google/gwt/chrome/crx/client/Windows$Window;)
        (wnd);
     });
  }-*/;

  public static native void getAll(boolean populateTabs,
      OnWindowsCallback callback) /*-{
    chrome.windows.getAll(populateTabs, function(wnds){ 
        callback.
        @com.google.gwt.chrome.crx.client.Windows$OnWindowsCallback::onWindow(Lcom/google/gwt/core/client/JsArray;)(wnds);
        (wnd);
     });
  }-*/;

  public static native void getCurrent(OnWindowCallback callback) /*-{
    chrome.windows.getCurrent(function(wnd){ 
        callback.
        @com.google.gwt.chrome.crx.client.Windows$OnWindowCallback::onWindow(Lcom/google/gwt/chrome/crx/client/Windows$Window;)
        (wnd);
     });
  }-*/;

  public static native void getLastFocused(OnWindowCallback callback) /*-{
    chrome.windows.getLastFocused(function(wnd){ 
        callback.
        @com.google.gwt.chrome.crx.client.Windows$OnWindowCallback::onWindow(Lcom/google/gwt/chrome/crx/client/Windows$Window;)
        (wnd);
     });
  }-*/;

  public static native void remove(int windowId, OnRemovedCallback callback) /*-{
    chrome.windows.remove(windowId, function(wnd) {
        callback.
        @com.google.gwt.chrome.crx.client.Windows$OnRemovedCallback::onRemove()
        ();
    });
  }-*/;
}
