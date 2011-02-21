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
import com.google.gwt.chrome.crx.client.Extension.ManifestInfo;
import com.google.gwt.chrome.crx.linker.ExtensionArtifact.IconInfo;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.dev.util.Util;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

/**
 * Generator responsible for extracting the Specification annotations from
 * Extension subclasses.
 */
public class ExtensionGenerator extends Generator {

  private static IconInfo[] createIconResources(TreeLogger logger,
      GeneratorContext context, JClassType userType, String[] icons)
      throws UnableToCompleteException {
    final IconInfo[] result = new IconInfo[icons.length];
    for (int i = 0, n = icons.length; i < n; ++i) {
      final String icon = icons[i];

      // Open a stream for the icon resource.
      InputStream iconStream = ExtensionGenerator.class.getClassLoader().getResourceAsStream(
          getResourcePath(userType, icon));
      if (iconStream == null) {
        logger.log(TreeLogger.ERROR, "Resource not found on classpath: " + icon);
        throw new UnableToCompleteException();
      }

      try {
        // Read the icon's byte data and decode it to determine the size.
        final byte[] iconData = getBytesFromStream(iconStream);
        assert iconData != null;
        final String strongname = Util.computeStrongName(iconData)
            + getIconExtension(icon);
        result[i] = new IconInfo(strongname, getImageSize(iconData));

        // Write the icon's bytes into GWT resource.
        try {
          final OutputStream resStream = context.tryCreateResource(logger,
              strongname);
          if (resStream != null) {
            resStream.write(iconData);
            context.commitResource(logger, resStream);
          }
        } catch (IOException e) {
          logger.log(TreeLogger.ERROR, "Unable to write resource: " + icon, e);
          throw new UnableToCompleteException();
        }

      } catch (IOException e) {
        logger.log(TreeLogger.ERROR, "Unable to read image: " + icon, e);
        throw new UnableToCompleteException();
      }
    }

    return result;
  }

  private static String generateExtensionType(TreeLogger logger,
      GeneratorContext context, JClassType userType, ExtensionArtifact spec) {
    final String subclassName = userType.getSimpleSourceName().replace('.', '_')
        + "_generated";
    final String packageName = userType.getPackage().getName();
    final ClassSourceFileComposerFactory f = new ClassSourceFileComposerFactory(
        packageName, subclassName);
    f.setSuperclass(userType.getQualifiedSourceName());
    final PrintWriter pw = context.tryCreate(logger, packageName, subclassName);
    if (pw != null) {
      final SourceWriter sw = f.createSourceWriter(context, pw);

      final String version = (spec.getVersion() != null) ? spec.getVersion()
          : "";
      sw.println("@Override public String getVersion() {");
      sw.println("  return \"" + GeneratorUtils.toJavaLiteral(version) + "\";");
      sw.println("}");

      sw.commit(logger);
    }
    return f.getCreatedClassName();
  }

  private static byte[] getBytesFromStream(InputStream stream)
      throws IOException {
    final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    int n;
    final byte[] buffer = new byte[2048];
    while ((n = stream.read(buffer)) >= 0) {
      byteStream.write(buffer, 0, n);
    }
    return byteStream.toByteArray();
  }

  private static String getIconExtension(String icon) {
    final int index = icon.lastIndexOf('.');
    return (index < 0) ? "" : icon.substring(index);
  }

  private static int getImageSize(byte[] data) throws IOException {
    final InputStream stream = new ByteArrayInputStream(data);
    final BufferedImage image = ImageIO.read(stream);
    return Math.max(image.getWidth(), image.getHeight());
  }

  private static String getResourcePath(JClassType userType, String name) {
    final String base = userType.getPackage().getName().replace('.', '/');
    return base + "/" + name;
  }

  private static ExtensionArtifact getSpecification(TreeLogger logger,
      GeneratorContext context, JClassType userType)
      throws UnableToCompleteException {
    final ManifestInfo spec = userType.getAnnotation(Extension.ManifestInfo.class);
    if (spec != null) {
      return new ExtensionArtifact(spec.name(), spec.description(),
          spec.version(), spec.permissions(), spec.updateUrl(),
          createIconResources(logger, context, userType, spec.icons()),
          spec.publicKey());
    }

    logger.log(TreeLogger.ERROR,
        "You need a @Extension.Specification annotation on "
            + userType.getQualifiedSourceName()
            + " or else I'll have to make up a silly name for it.");
    throw new UnableToCompleteException();
  }

  @Override
  public String generate(TreeLogger logger, GeneratorContext context,
      String typeName) throws UnableToCompleteException {
    try {
      final JClassType userType = context.getTypeOracle().getType(typeName);
      final ExtensionArtifact spec = getSpecification(logger, context, userType);
      context.commitArtifact(logger, spec);
      return generateExtensionType(logger, context, userType, spec);
    } catch (NotFoundException e) {
      // TODO(knorton): Better error message.
      logger.log(TreeLogger.ERROR, "Unknown Type: " + typeName);
      throw new UnableToCompleteException();
    }
  }
}
