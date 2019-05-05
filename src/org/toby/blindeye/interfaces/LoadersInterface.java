package org.toby.blindeye.interfaces;

import KinectPV2.KinectPV2;
import processing.core.PImage;

public interface LoadersInterface {
  PImage execute(PImage liveVideo, PImage body, PImage savedBackground, KinectPV2 kinect);
}
