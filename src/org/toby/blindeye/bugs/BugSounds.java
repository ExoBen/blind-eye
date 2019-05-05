package org.toby.blindeye.bugs;

import org.toby.blindeye.interfaces.SoundsInterface;
import processing.core.PApplet;
import processing.sound.SoundFile;

import java.util.ArrayList;
import java.util.Random;

public class BugSounds implements SoundsInterface {

  private Random rand;
  private PApplet parent;
  private ArrayList<SoundFile> sounds;
  private long lastSoundTime;

  BugSounds(PApplet p) {
    parent = p;
    rand = new Random();
    loadSounds();
  }

  private void loadSounds() {
    String bug1Sound = "F:/OneDrive - University of Dundee/Year 4/blind-eye/resources/audio/bug1.wav";
    SoundFile bug1 = new SoundFile(parent, bug1Sound);
    bug1.amp(0.5f);
    String bug2Sound = "F:/OneDrive - University of Dundee/Year 4/blind-eye/resources/audio/bug2.wav";
    SoundFile bug2 = new SoundFile(parent, bug2Sound);
    bug2.amp(0.5f);

    sounds = new ArrayList<>();
    sounds.add(bug1);
    sounds.add(bug2);
  }

  public void playSound() {
    if (System.currentTimeMillis() - lastSoundTime > 1000) {
      SoundFile currentSound = sounds.get(rand.nextInt(sounds.size()));
      currentSound.play();
    }
    lastSoundTime = System.currentTimeMillis();
  }

}
