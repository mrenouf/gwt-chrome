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
package com.google.gwt.chrome.crx.linker;

import com.google.gwt.chrome.crx.client.Extension;
import com.google.gwt.chrome.crx.linker.ExtensionArtifact.IconInfo;
import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.Artifact;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.CompilationResult;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.SelectionProperty;
import com.google.gwt.core.ext.linker.LinkerOrder.Order;
import com.google.json.serialization.JsonArray;
import com.google.json.serialization.JsonObject;
import com.google.json.serialization.JsonValue;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Linker for chrome Extensions.
 */
@LinkerOrder(Order.PRIMARY)
public class ExtensionLinker extends AbstractLinker {

  private static ArtifactSet addArtifacts(ArtifactSet artifacts,
      Artifact<?>... newArtifacts) {
    final ArtifactSet newSet = new ArtifactSet(artifacts);
    for (Artifact<?> artifact : newArtifacts) {
      newSet.add(artifact);
    }
    return newSet;
  }

  private static void checkForDisallowedJs(String js, String patternString,
      int allowedOccurences, TreeLogger logger)
      throws UnableToCompleteException {
    Pattern pattern = Pattern.compile(patternString);
    Matcher matcher = pattern.matcher(js);
    int count = 0;
    while (matcher.find()) {
      count++;
    }
    if (count > allowedOccurences) {
      logger.log(TreeLogger.ERROR, "You may have a reference to "
          + patternString
          + ". This is not allowed when using the ExtensionLinker! (Got "
          + count + " references, expected " + allowedOccurences + ")");
      throw new UnableToCompleteException();
    }
  }

  private static JsonValue createContentScriptsArray(
      SortedSet<ContentScriptArtifact> contentScripts) {
    final JsonArray array = JsonArray.create();
    for (ContentScriptArtifact contentScript : contentScripts) {
      JsonObject entry = JsonObject.create();
      JsonArray whiteList = JsonArray.create();
      String[] patterns = contentScript.getWhiteList();
      for (String pattern : patterns) {
        whiteList.add(pattern);
      }
      entry.put("matches", whiteList);
      JsonArray path = JsonArray.create();
      path.add(contentScript.getPath());
      entry.put("js", path);
      entry.put("run_at", contentScript.getRunAt());
      array.add(entry);
    }
    return array;
  }

  private static JsonValue createPageActionsArray(
      SortedSet<PageActionArtifact> pageActions) {
    final JsonArray pageActionArray = JsonArray.create();
    for (PageActionArtifact pageAction : pageActions) {
      final JsonObject pageActionObject = JsonObject.create();
      pageActionObject.put("id", pageAction.getId());
      pageActionObject.put("name", pageAction.getName());
      final JsonArray iconsArray = JsonArray.create();
      String[] icons = pageAction.getIcons();
      for (String icon : icons) {
        iconsArray.add(icon);
      }
      pageActionObject.put("icons", iconsArray);
      pageActionArray.add(pageActionObject);
    }
    return pageActionArray;
  }

  private static JsonValue createPluginsArray(SortedSet<PluginArtifact> plugins) {
    JsonArray pluginsArray = JsonArray.create();
    for (PluginArtifact plugin : plugins) {
      JsonObject pluginObject = JsonObject.create();
      pluginObject.put("path", plugin.getPath());
      pluginObject.put("public", plugin.isPublic());
      pluginsArray.add(pluginObject);
    }
    return pluginsArray;
  }

  private static JsonArray createToolStripsArray(
      Set<ToolStripArtifact> toolstrips) {
    final JsonArray array = JsonArray.create();
    for (ToolStripArtifact toolstrip : toolstrips) {
      array.add(toolstrip.getPath());
    }
    return array;
  }

  private static CompilationResult findCompilation(TreeLogger logger,
      ArtifactSet artifacts) throws UnableToCompleteException {
    final SortedSet<CompilationResult> compilations = artifacts.find(CompilationResult.class);
    if (compilations.size() != 1) {
      logger.log(TreeLogger.ERROR,
          "Found " + compilations.size() + " permutations compiled in "
              + ExtensionLinker.class.getSimpleName()
              + ".  Use only a single permutation per module with this linker.");
      logPermutations(logger, compilations);
      throw new UnableToCompleteException();
    }
    return compilations.first();
  }

  private static ExtensionArtifact findExtensionArtifact(TreeLogger logger,
      ArtifactSet artifacts) throws UnableToCompleteException {
    final SortedSet<ExtensionArtifact> extensions = artifacts.find(ExtensionArtifact.class);
    if (extensions.size() != 1) {
      // TODO(knorton): Improve error message.
      logger.log(
          TreeLogger.ERROR,
          ExtensionLinker.class.getSimpleName()
              + ": got " + extensions.size() + " entry points, but there must be one per module.");
      throw new UnableToCompleteException();
    }

    return extensions.first();
  }

  private static String generateHtmlContents(TreeLogger logger,
      CompilationResult js) throws UnableToCompleteException {
    // Thou shalt not reference $doc or $wnd !!!
    String compiledJs = js.getJavaScript()[0];

    // Work around for GWT 2.0, there are 2 harmless $wnd references that creep
    // in
    checkForDisallowedJs(compiledJs, "\\$wnd", 2, logger);
    checkForDisallowedJs(compiledJs, "\\$doc", 0, logger);

    final StringBuffer buffer = new StringBuffer();
    buffer.append("<html>");
    buffer.append("<head></head>");
    buffer.append("<body>");
    buffer.append("<script>var $stats;\n" + compiledJs
        + "gwtOnLoad();\n</script>");
    buffer.append("</body>");
    buffer.append("</html>");
    return buffer.toString();
  }

  private static String getHtmlFilename(LinkerContext context) {
    return context.getModuleName() + ".html";
  }

  private static void logPermutations(TreeLogger logger,
      Collection<CompilationResult> compilations) {
    int count = 0;
    for (CompilationResult compilationResult : compilations) {
      SortedSet<SortedMap<SelectionProperty, String>> propertyMap = compilationResult.getPropertyMap();
      StringBuilder builder = new StringBuilder();
      for (SortedMap<SelectionProperty, String> propertySubMap : propertyMap) {
        builder.append("{");
        for (Entry<SelectionProperty, String> entry : propertySubMap.entrySet()) {
          
          SelectionProperty selectionProperty = entry.getKey();
          if (!selectionProperty.isDerived()) {
            builder.append(selectionProperty.getName() + ":" + entry.getValue() + " ");
          }
        }
        builder.append("}");        
      }
      logger.log(TreeLogger.ERROR, "Permutation " + count + ": "
          + builder.toString());
      count++;
    }
  }

  @Override
  public String getDescription() {
    return "Chrome Extension Linker";
  }

  @Override
  public ArtifactSet link(TreeLogger logger, LinkerContext context,
      ArtifactSet artifacts) throws UnableToCompleteException {
    // Retrieve the spec for the extension.
    final ExtensionArtifact extension = findExtensionArtifact(logger, artifacts);

    // Retrieve the PageActions.
    final SortedSet<PageActionArtifact> pageActions = artifacts.find(PageActionArtifact.class);

    // Retrieve the BrowserAction (should be a collection of size one).
    final SortedSet<BrowserActionArtifact> browserActions = artifacts.find(BrowserActionArtifact.class);

    // Retrieve the ToolStrips.
    final SortedSet<ToolStripArtifact> toolStrips = artifacts.find(ToolStripArtifact.class);

    // Retrieve the ContentScripts
    final SortedSet<ContentScriptArtifact> contentScripts = artifacts.find(ContentScriptArtifact.class);

    // Retrieve the plugins.
    final SortedSet<PluginArtifact> plugins = artifacts.find(PluginArtifact.class);

    // Retrieve the compilation.
    final CompilationResult compilation = findCompilation(logger, artifacts);

    String backgroundPageFileName = getHtmlFilename(context);
    return addArtifacts(artifacts, emitString(logger, generateHtmlContents(
        logger, compilation), backgroundPageFileName), emitString(logger,
        generateManifestContents(logger, backgroundPageFileName, extension,
            pageActions, browserActions, toolStrips, contentScripts, plugins),
        "manifest.json"));
  }

  private JsonObject createBrowserAction(
      SortedSet<BrowserActionArtifact> browserActions) {
    int size = browserActions.size();
    if (size == 0) {
      return null;
    }
    // TODO(jaimeyap): This should really be a user error and not an assertion
    // failure.
    assert (size == 1) : "Only 1 BrowserAction per extension allowed.";
    BrowserActionArtifact browserAction = browserActions.first();
    final JsonObject browserActionObject = JsonObject.create();
    browserActionObject.put("name", browserAction.getName());
    final JsonArray iconsArray = JsonArray.create();
    String[] icons = browserAction.getIcons();
    for (String icon : icons) {
      iconsArray.add(icon);
    }
    browserActionObject.put("icons", iconsArray);
    browserActionObject.put("default_icon", browserAction.getDefaultIcon());

    return browserActionObject;
  }

  private String generateManifestContents(TreeLogger logger,
      String backgroundPageFileName, ExtensionArtifact extension,
      SortedSet<PageActionArtifact> pageActions,
      SortedSet<BrowserActionArtifact> browserActions,
      SortedSet<ToolStripArtifact> toolStrips,
      SortedSet<ContentScriptArtifact> contentScripts,
      SortedSet<PluginArtifact> plugins) throws UnableToCompleteException {
    final JsonObject config = JsonObject.create();
    config.put("name", extension.getName());
    config.put("version", extension.getVersion());

    if (!extension.getUpdateUrl().equals(Extension.NO_UPDATE_URL)) {
      config.put("update_url", extension.getUpdateUrl());
    }

    // All other fields in the manifest are optional
    if (extension.getDescription().length() > 0) {
      config.put("description", extension.getDescription());
    }
    if (backgroundPageFileName.length() > 0) {
      config.put("background_page", backgroundPageFileName);
    }
    if (toolStrips.size() > 0) {
      config.put("toolstrips", createToolStripsArray(toolStrips));
    }
    if (contentScripts.size() > 0) {
      config.put("content_scripts", createContentScriptsArray(contentScripts));
    }
    if (pageActions.size() > 0) {
      config.put("page_actions", createPageActionsArray(pageActions));
    }

    if (browserActions.size() > 0) {
      JsonValue browserAction = createBrowserAction(browserActions);
      if (browserAction != null) {
        config.put("browser_action", browserAction);
      }
    }

    if (plugins.size() > 0) {
      config.put("plugins", createPluginsArray(plugins));
    }

    if (extension.getPermissions().length > 0) {
      final JsonArray perms = JsonArray.create();
      for (String perm : extension.getPermissions()) {
        perms.add(perm);
      }
      config.put("permissions", perms);
    }

    if (extension.getIcons().length > 0) {
      JsonObject icons = JsonObject.create();
      for (IconInfo info : extension.getIcons()) {
        icons.put(Integer.toString(info.getSize()), info.getFilename());
      }
      config.put("icons", icons);
    }
    
    if(extension.getPublicKey().length() > 0) {
      config.put("key", extension.getPublicKey());
    }

    final StringWriter writer = new StringWriter();
    try {
      config.write(writer);
    } catch (IOException e) {
      logger.log(TreeLogger.ERROR, "Unexpected error.", e);
      throw new UnableToCompleteException();
    }
    return writer.toString();
  }
}
