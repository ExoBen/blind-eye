package org.toby.blindeye.bugs;

import KinectPV2.KinectPV2;
import org.toby.blindeye.bugs.instances.*;
import org.toby.blindeye.features.instances.Invert;
import org.toby.blindeye.interfaces.LoadersInterface;
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
  private DownloadedStatic downloadedStatic;

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
    downloadedStatic = new DownloadedStatic(p);
  }

  public PImage execute(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    PImage outputVideo;

    if (currentBug != null) {
      outputVideo = executeBug(liveVideo, body, savedBackground, kinect);
      if (System.currentTimeMillis() > bugStartTime + currentBugLength) {
        currentBug = null;
        currentlyBugging = false;
      }
    } else {
      int dice = rand.nextInt(8);
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
        case 6:
          currentBug = bWStaticOnSavedBackground;
          break;
        default:
          currentBug = downloadedStatic;
      }
      sounds.playSound();
      currentBugLength = rand.nextInt(200) + 100;
      currentlyBugging = true;
      bugStartTime = System.currentTimeMillis();
      outputVideo = executeBug(liveVideo, body, savedBackground, kinect);
    }
    return outputVideo;
  }

  public PImage executeDownloadedStatic(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    currentBug = downloadedStatic;
    sounds.playSound();
    currentBugLength = rand.nextInt(100) + 200;
    currentlyBugging = true;
    bugStartTime = System.currentTimeMillis();
    return executeBug(liveVideo, body, savedBackground, kinect);
  }

  public boolean isCurrentlyBugging() {
    return currentlyBugging;
  }

  private PImage executeBug(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    return currentBug.executeBug(liveVideo, body, savedBackground, kinect);
  }
}
