package org.toby.kinectvideomask.glitches;

import KinectPV2.KinectPV2;
import org.toby.kinectvideomask.glitches.instances.BlackAndWhiteMask;
import org.toby.kinectvideomask.glitches.instances.BlackAndWhiteVideo;
import org.toby.kinectvideomask.glitches.instances.DarkLightGreyMask;
import org.toby.kinectvideomask.glitches.instances.LightDarkGreyMask;
import processing.core.PImage;

import java.util.Random;

public class GlitchLoader {

  private Random rand;
  private AbstractGlitch currentGlitch;

  private BlackAndWhiteMask blackAndWhiteMask;
  private DarkLightGreyMask darkLightGreyMask;
  private LightDarkGreyMask lightDarkGreyMask;
  private BlackAndWhiteVideo blackAndWhiteVideo;

  private boolean currentlyGlitching;
  private long glitchStartTime;

  public GlitchLoader() {
    rand = new Random();
    blackAndWhiteMask = new BlackAndWhiteMask();
    darkLightGreyMask = new DarkLightGreyMask();
    lightDarkGreyMask = new LightDarkGreyMask();
    blackAndWhiteVideo = new BlackAndWhiteVideo();
  }

  public PImage executeGlitch (PImage liveVideo, PImage body, KinectPV2 kinect) {
    PImage outputVideo;

    if (currentGlitch != null) {
      outputVideo = executeAndCheck(liveVideo, body, kinect);
      if (System.currentTimeMillis() > glitchStartTime + 300) {
        currentGlitch = null;
        currentlyGlitching = false;
      }
    } else {
      int dice = rand.nextInt(4);
      switch (dice) {
        case 0:
          currentGlitch = darkLightGreyMask;
          break;
        case 1:
          currentGlitch = lightDarkGreyMask;
          break;
        case 2:
          currentGlitch = blackAndWhiteMask;
          break;
        default:
          currentGlitch = blackAndWhiteVideo;
      }
      System.out.println(currentGlitch.toString());
      currentlyGlitching = true;
      glitchStartTime = System.currentTimeMillis();
      outputVideo = executeAndCheck(liveVideo, body, kinect);
    }
    return outputVideo;
  }

  public boolean isCurrentlyGlitching() {
    return currentlyGlitching;
  }

  private PImage executeAndCheck(PImage liveVideo, PImage body, KinectPV2 kinect) {
    PImage outputVideo = currentGlitch.executeGlitch(liveVideo, body, kinect);
    return outputVideo;
  }
}
