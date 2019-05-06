package org.toby.blindeye.features;

import org.toby.blindeye.interfaces.SoundsInterface;
import processing.core.PApplet;
import processing.sound.SoundFile;

class FeatureSounds implements SoundsInterface {

  private PApplet parent;
  private SoundFile feature1;


  FeatureSounds(PApplet p) {
    parent = p;
    loadSounds();
  }

  private void loadSounds() {
    String clickBuzzSound = "C:/Users/toby5/OneDrive - University of Dundee/Year 4/blind-eye/resources/audio/feature1.wav";
    feature1 = new SoundFile(parent, clickBuzzSound);
    feature1.amp(0.3f);
  }

  public void playSound() {
    feature1.play();
  }

}