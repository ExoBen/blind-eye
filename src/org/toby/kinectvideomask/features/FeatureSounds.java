package org.toby.kinectvideomask.features;

import processing.core.PApplet;
import processing.sound.SoundFile;

class FeatureSounds {

  private PApplet parent;
  private SoundFile feature1;


  FeatureSounds(PApplet p) {
    parent = p;
    loadSounds();
  }

  private void loadSounds() {
    String clickBuzzSound = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/feature1.wav";
    feature1 = new SoundFile(parent, clickBuzzSound);
    feature1.amp(0.3f);
  }

  void playFeatureSound() {
    feature1.play();
  }

}
