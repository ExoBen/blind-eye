package org.toby.kinectvideomask.features;

import processing.core.PImage;

import java.util.Random;

public class FeatureLoader {

  private Random rand;
  private AbstractFeature currentFeature;
  private Suspirium suspirium;
  private BitmapFuzz bitmapFuzz;

  private boolean currentlyFeaturing;
  private long featureStartTime;

  public FeatureLoader() {
    rand = new Random();
    suspirium = new Suspirium();
    bitmapFuzz = new BitmapFuzz();
  }

  public PImage executeFeature (PImage liveVideo, PImage body, PImage staticBackground) {
    PImage outputVideo;

    if (currentFeature != null) {
      outputVideo = execute(liveVideo, body, staticBackground);
      if (System.currentTimeMillis() > featureStartTime + 2000) {
        currentFeature = null;
        currentlyFeaturing = false;
      }
    } else {
      int dice = rand.nextInt(2);
      switch (dice) {
        case 0:
          currentFeature = bitmapFuzz;
          break;
        default:
          currentFeature = suspirium;
      }
      currentlyFeaturing = true;
      featureStartTime = System.currentTimeMillis();
      outputVideo = execute(liveVideo, body, staticBackground);
    }
    return outputVideo;
  }

  public boolean isCurrentlyFeaturing() {
    return currentlyFeaturing;
  }

  private PImage execute(PImage liveVideo, PImage body, PImage staticBackground) {
    return currentFeature.executeFeature(liveVideo, body, staticBackground);
  }
}

