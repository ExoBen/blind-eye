package org.toby.kinectvideomask.glitches;

import org.toby.kinectvideomask.UtilitiesAndConstants;
import processing.core.PImage;

import static org.toby.kinectvideomask.UtilitiesAndConstants.BLACK;
import static org.toby.kinectvideomask.UtilitiesAndConstants.WHITE;

class BlackAndWhite extends AbstractGlitch {

  private boolean currentlyGlitching;

  BlackAndWhite() {}

  PImage executeGlitch(PImage liveVideo, PImage body, int counter) {
    currentlyGlitching = counter <= 10;
    return UtilitiesAndConstants.twoTone(body, WHITE, BLACK);
  }

  boolean stillGlitching() {
    return currentlyGlitching;
  }

}
