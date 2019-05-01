package org.toby.kinectvideomask.glitches;

import processing.core.PImage;

class ShowThePast extends AbstractGlitch {

  private boolean currentlyGlitching;
  private PImage[] savedFrame = new PImage[40];

  PImage executeGlitch(PImage liveVideo, PImage body, int counter) {
    currentlyGlitching = true;
    PImage outputVideo;
    if (savedFrame[counter] != null) {
      outputVideo = savedFrame[counter];
    } else {
      outputVideo = liveVideo;
    }
    savedFrame[counter] = liveVideo;
    counter++;
    if (counter >= 40) {
      currentlyGlitching = false;
    }
    return outputVideo;
  }

  boolean stillGlitching() {
    return currentlyGlitching;
  }
}
