package org.toby.kinectvideomask.glitches;

import processing.core.PImage;

import java.util.Random;

public class GlitchLoader {

  private Random rand;
  private AbstractGlitch currentGlitch;
  private BlackAndWhite blackAndWhite;
  private ShowThePast showThePast;
  private boolean currentlyGlitching;
  private int glitchedFrameCount;

  public GlitchLoader() {
    rand = new Random();
    blackAndWhite = new BlackAndWhite();
    showThePast = new ShowThePast();
  }

  public PImage executeGlitch (PImage liveVideo, PImage body) {
    PImage outputVideo;

    if (currentGlitch != null) {
      outputVideo = executeAndCheck(liveVideo, body);
      glitchedFrameCount++;
    } else {
      int dice = rand.nextInt(6);
      switch (dice) {
        case 0:
          currentGlitch = showThePast;
          break;
        default:
          currentGlitch = blackAndWhite;
      }
      outputVideo = executeAndCheck(liveVideo, body);
    }
    if (!currentlyGlitching) {
      currentGlitch = null;
      glitchedFrameCount = 0;
    }
    return outputVideo;
  }

  public boolean isCurrentlyGlitching() {
    return currentlyGlitching;
  }

  private PImage executeAndCheck(PImage liveVideo, PImage body) {
    PImage outputVideo = currentGlitch.executeGlitch(liveVideo, body, glitchedFrameCount);
    currentlyGlitching = currentGlitch.stillGlitching();
    return outputVideo;
  }
}
