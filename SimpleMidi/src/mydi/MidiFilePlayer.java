package mydi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

public class MidiFilePlayer {
	
	Sequencer sequencer;
	
	public MidiFilePlayer() {
		
		
		try {

			
			System.out.println("midi started");

			sequencer = MidiSystem.getSequencer();


			System.out.println("finished");

		} catch (MidiUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		} 
		
	}
	
	public void play(String filepath) {
		
		try {
		
		// Opens the device, indicating that it should now acquire any
					// system resources it requires and become operational.
					sequencer.open();

					// create a stream from a file
					
					//"C:\\Users\\mendrika\\Downloads\\Oldies_Simon__Garfunkel__The_sound_of_silence.mid"
					
					InputStream is = new BufferedInputStream(new FileInputStream(new File(filepath)));

					// Sets the current sequence on which the sequencer operates.
					// The stream must point to MIDI file data.
					sequencer.setSequence(is);

					// Starts playback of the MIDI data in the currently loaded sequence.
					sequencer.start();
					
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}

}
