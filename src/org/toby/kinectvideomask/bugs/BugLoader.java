package org.toby.kinectvideomask.bugs;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.bugs.instances.*;
import org.toby.kinectvideomask.features.instances.Invert;
import org.toby.kinectvideomask.interfaces.LoadersInterface;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Random;

public class BugLoader implements LoadersInterface {

  private BugSounds sounds;
  private Random rand;
  private AbstractBug currentBug;

  private BlackAndWhiteMask blackAndWhiteMask;
  private DarkLightGreyMask darkLightGreyMask;
  private LightDarkGreyMask lightDarkGreyMask;
  private BlackAndWhiteVideo blackAndWhiteVideo;
  private Invert invert;
  private SavedBackgroundOnBlack savedBackgroundOnBlack;
  private BWStaticOnSavedBackground bWStaticOnSavedBackground;

  private boolean currentlyBugging;
  private long bugStartTime;
  private int currentBugLength;

  public BugLoader(PApplet p) {
    sounds = new BugSounds(p);
    rand = new Random();
    blackAndWhiteMask = new BlackAndWhiteMask();
    darkLightGreyMask = new DarkLightGreyMask();
    lightDarkGreyMask = new LightDarkGreyMask();
    blackAndWhiteVideo = new BlackAndWhiteVideo();
    invert = new Invert();
    savedBackgroundOnBlack = new SavedBackgroundOnBlack();
    bWStaticOnSavedBackground = new BWStaticOnSavedBackground();
  }

  public PImage execute(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    PImage outputVideo;

    if (currentBug != null) {
      outputVideo = executeBug(liveVideo, body, savedBackground, kinect);
      if (System.currentTimeMillis() > bugStartTime + currentBugLength) {
        currentBug = null;
        currentlyBugging = false;
//        sounds.stopFeatureSound();
      }
    } else {
      int dice = rand.nextInt(7);
      switch (dice) {
        case 0:
          currentBug = darkLightGreyMask;
          break;
        case 1:
          currentBug = lightDarkGreyMask;
          break;
        case 2:
          currentBug = blackAndWhiteMask;
          break;
        case 3:
          currentBug = blackAndWhiteVideo;
          break;
        case 4:
          currentBug = invert;
          break;
        case 5:
          currentBug = savedBackgroundOnBlack;
          break;
        default:
          currentBug = bWStaticOnSavedBackground;
      }
      sounds.playFeatureSound();
      currentBugLength = rand.nextInt(300);
      currentlyBugging = true;
      bugStartTime = System.currentTimeMillis();
      outputVideo = executeBug(liveVideo, body, savedBackground, kinect);
    }
    return outputVideo;
  }

  public boolean isCurrentlyBugging() {
    return currentlyBugging;
  }

  private PImage executeBug(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    return currentBug.executeBug(liveVideo, body, savedBackground, kinect);
  }
}
