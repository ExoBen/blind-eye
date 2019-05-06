package org.toby.blindeye.features;

import KinectPV2.KinectPV2;
import org.toby.blindeye.features.instances.*;
import org.toby.blindeye.interfaces.LoadersInterface;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.toby.blindeye.UtilitiesAndConstants.loadStatics;

public class FeatureLoader implements LoadersInterface {

  private PApplet parent;
  private FeatureSounds sounds;
  private Random rand;
  private AbstractFeature currentFeature;

  private DepthImage depthImage;
  private GreyStatic greyStatic;
  private BlackAndWhiteStatic blackAndWhiteStatic;
  private ShowThePast showThePast;

  private boolean currentlyFeaturing;
  private long featureStartTime;
  private int frame;
  private ArrayList<PImage> statics;
  private Integer[] startingPoints = new Integer[] {0,3,6,9};
  private Integer startingPoint;


  public FeatureLoader(PApplet p) {
    parent = p;
    sounds = new FeatureSounds(p);
    rand = new Random();
    depthImage = new DepthImage();
    greyStatic = new GreyStatic();
    showThePast = new ShowThePast();
    blackAndWhiteStatic = new BlackAndWhiteStatic();
    statics = loadStatics(p);
  }

  public PImage execute(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    PImage outputVideo;
    if (currentFeature != null) {
      if (frame < 6) {
        outputVideo = statics.get(startingPoint + frame/2);
      } else {
        outputVideo = executeFeature(liveVideo, body, savedBackground, kinect);
        if (System.currentTimeMillis() > featureStartTime + 2000) {
          currentFeature = null;
          currentlyFeaturing = false;
        }
      }
      frame++;
    } else {
      int dice = rand.nextInt(4);
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
      sounds.playSound();
      currentlyFeaturing = true;
      featureStartTime = System.currentTimeMillis();
      frame = 0;
      startingPoint = startingPoints[rand.nextInt(4)];
      outputVideo = executeFeature(liveVideo, body, savedBackground, kinect);
    }
    return outputVideo;
  }

  public boolean isCurrentlyFeaturing() {
    return currentlyFeaturing;
  }

  private PImage executeFeature(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    return currentFeature.executeFeature(liveVideo, body, savedBackground, kinect);
  }
}

