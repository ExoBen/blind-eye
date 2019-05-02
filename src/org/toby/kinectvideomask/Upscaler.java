package org.toby.kinectvideomask;

import processing.core.PImage;

import static processing.core.PConstants.THRESHOLD;

public class Upscaler {

  Upscaler() {}

  public static PImage upscaler(PImage cropBody, int cropBodySize) {
    PImage bodyUpscaled = new PImage(1302, 1080);
    bodyUpscaled.loadPixels();
    for (int i = 0; i < cropBodySize; i++) {
      bodyUpscaled.pixels[i*3] = cropBody.pixels[i];
      bodyUpscaled.pixels[i*3+1] = cropBody.pixels[i];
      bodyUpscaled.pixels[i*3+2] = cropBody.pixels[i];
    }
    for (int i = cropBody.height-1; i >=0; i--) {
      PImage line = bodyUpscaled.get(0, i, 1302, 1);
      bodyUpscaled.set(0, i*3, line);
      bodyUpscaled.set(0, i*3+1, line);
      bodyUpscaled.set(0, i*3+2, line);
    }
    bodyUpscaled.filter(THRESHOLD);
    return bodyUpscaled;
  }
}
