package org.toby.kinectvideomask.features;

import processing.core.PApplet;
import processing.sound.SoundFile;

public class FeatureSounds {

  private PApplet parent;
  private SoundFile clickBuzz;

  private SoundFile current;

  FeatureSounds(PApplet p) {
    parent = p;
    loadSounds();

  }

  private void loadSounds() {
    String clickBuzzSound = "F:/OneDrive - University of Dundee/Year 4/Kinect Video Mask/kinect-video-mask/resources/clickBuzz.wav";
    clickBuzz = new SoundFile(parent, clickBuzzSound);
    clickBuzz.amp(0.4f);
  }



  public void playFeatureSound() {
    // random .play
    //current = random

  }



  public void stopFeatureSound() {
    current.stop();
  }

}
