package org.toby.blindeye.base;

import KinectPV2.KinectPV2;
import processing.core.PImage;

public class BaseLoader {

  private Ghosting ghost;

  public BaseLoader() {
    ghost = new Ghosting();
  }

  public PImage executeBase(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect) {
    return ghost.execute(liveVideo, body, savedBackground, kinect);
  }

}

