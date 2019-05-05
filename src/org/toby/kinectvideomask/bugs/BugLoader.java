package org.toby.kinectvideomask.bugs;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.features.instances.Invert;
import org.toby.kinectvideomask.bugs.instances.BlackAndWhiteMask;
import org.toby.kinectvideomask.bugs.instances.BlackAndWhiteVideo;
import org.toby.kinectvideomask.bugs.instances.DarkLightGreyMask;
import org.toby.kinectvideomask.bugs.instances.LightDarkGreyMask;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Random;

public class BugLoader {

  private BugSounds sounds;
  private Random rand;
  private AbstractBug currentBug;

  private BlackAndWhiteMask blackAndWhiteMask;
  private DarkLightGreyMask darkLightGreyMask;
  private LightDarkGreyMask lightDarkGreyMask;
  private BlackAndWhiteVideo blackAndWhiteVideo;
  private Invert invert;

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
  }

  public PImage executeBug(PImage liveVideo, PImage body, KinectPV2 kinect) {
    PImage outputVideo;

    if (currentBug != null) {
      outputVideo = executeAndCheck(liveVideo, body, kinect);
      if (System.currentTimeMillis() > bugStartTime + currentBugLength) {
        currentBug = null;
        currentlyBugging = false;
//        sounds.stopFeatureSound();
      }
    } else {
      int dice = rand.nextInt(5);
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
        default:
          currentBug = invert;
      }
      sounds.playFeatureSound();
      currentBugLength = rand.nextInt(300);
      System.out.println(currentBug.toString());
      currentlyBugging = true;
      bugStartTime = System.currentTimeMillis();
      outputVideo = executeAndCheck(liveVideo, body, kinect);
    }
    return outputVideo;
  }

  public boolean isCurrentlyBugging() {
    return currentlyBugging;
  }

  private PImage executeAndCheck(PImage liveVideo, PImage body, KinectPV2 kinect) {
    PImage outputVideo = currentBug.executeBug(liveVideo, body, kinect);
    return outputVideo;
  }
}
