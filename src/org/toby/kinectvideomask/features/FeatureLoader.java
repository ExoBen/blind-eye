package org.toby.kinectvideomask.features;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.features.instances.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Random;

public class FeatureLoader {

  private FeatureSounds sounds;
  private Random rand;
  private AbstractFeature currentFeature;

  private DepthImage depthImage;
  private GreyStatic greyStatic;
  private BlackAndWhiteStatic blackAndWhiteStatic;
  private ShowThePast showThePast;

  private boolean currentlyFeaturing;
  private long featureStartTime;


  public FeatureLoader(PApplet p) {
    sounds = new FeatureSounds(p);
    rand = new Random();
    depthImage = new DepthImage();
    greyStatic = new GreyStatic();
    showThePast = new ShowThePast();
    blackAndWhiteStatic = new BlackAndWhiteStatic();
  }

  public PImage executeFeature (PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    PImage outputVideo;

    if (currentFeature != null) {
      outputVideo = execute(liveVideo, body, staticBackground, kinect);
      if (System.currentTimeMillis() > featureStartTime + 2000) {
        currentFeature = null;
        currentlyFeaturing = false;
      }
    } else {
      int dice = rand.nextInt(5);
      switch (dice) {
        case 0:
          currentFeature = depthImage;
          break;
        case 1:
          currentFeature = blackAndWhiteStatic;
          break;
        case 2:
          currentFeature = greyStatic;
          break;
        default:
          currentFeature = showThePast;
      }
      sounds.playFeatureSound();
      currentlyFeaturing = true;
      featureStartTime = System.currentTimeMillis();
      outputVideo = execute(liveVideo, body, staticBackground, kinect);
    }
    return outputVideo;
  }

  public boolean isCurrentlyFeaturing() {
    return currentlyFeaturing;
  }

  private PImage execute(PImage liveVideo, PImage body, PImage staticBackground, KinectPV2 kinect) {
    return currentFeature.executeFeature(liveVideo, body, staticBackground, kinect);
  }
}

