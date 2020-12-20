package mydi;

import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;

public class MidiKeyboardPlayer implements Receiver {

	private int instru = 0;

	Synthesizer midiSynth;

	boolean ignoreVelocity ;

	MidiDevice device;

	Instrument[] instr;

	MidiChannel[] mChannels;
	
	public ArrayList<InstrumentItem> instrus = new ArrayList<MidiKeyboardPlayer.InstrumentItem>() ;

	public MidiDevice.Info[] deviceInfos;
	
	public String name;

	public int channel = 0;

	public MidiKeyboardPlayer() {
		this(false) ;
	}
	
	public MidiKeyboardPlayer(boolean ignoreVelocity) {

		this.ignoreVelocity = ignoreVelocity ;
		
		deviceInfos = MidiSystem.getMidiDeviceInfo();

	}

	public int getInstru() {
		return instru;
	}


	public void setInstru(int instru) {
		this.instru = instru;
		mChannels[channel].programChange(instr[instru].getPatch().getProgram());
	}


	public void openKeaboard(String name) {
	
		this.name = name;
	
		for (int i = 0; i < deviceInfos.length; i++) {
	
			try {
	
				device = MidiSystem.getMidiDevice(deviceInfos[i]);
	
				Transmitter trans = device.getTransmitter();
				trans.setReceiver(this);
	
				if (device.getDeviceInfo().getName().toLowerCase().contains(name.toLowerCase())) {
					// open each device
					device.open();
					// if code gets this far without throwing an exception
					// print a success message
					System.out.println(device.getDeviceInfo() + " Was Opened");
	
					break;
				}
	
			} catch (MidiUnavailableException e) {
	
			}
	
			try {
	
				midiSynth = MidiSystem.getSynthesizer();
				midiSynth.open();
	
				instr = midiSynth.getDefaultSoundbank().getInstruments();
				mChannels = midiSynth.getChannels();
	
				for (int j = 0; j < instr.length; j++) {
	
					Instrument inst = instr[j];
	
					instrus.add(new InstrumentItem(j, inst.getName())) ;
	
				}
	
				// midiSynth.loadInstrument(instr[132]);// load an instrument
	
				mChannels[channel].programChange(instr[instru].getPatch().getProgram());
	
			} catch (MidiUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	public void close() {
	
		midiSynth.close();
	
	}

	public void send(MidiMessage msg, long timeStamp) {

		if (msg.getLength() == 3) {

			new Thread() {
				public void run() {

					byte velocity = msg.getMessage()[2] ;
					
					if(ignoreVelocity) {
						velocity = 100 ; 
					}
					
					if (msg.getMessage()[0] == (byte) ShortMessage.NOTE_ON) {
						
						mChannels[channel].noteOn(msg.getMessage()[1], velocity);
						
					} else if (msg.getMessage()[0] == (byte) ShortMessage.NOTE_OFF) {
						
						mChannels[channel].noteOff(msg.getMessage()[1]);// turn of the note
						
					} else if (msg.getMessage()[0] == (byte) ShortMessage.PITCH_BEND) {
						
						mChannels[channel].setPitchBend(msg.getMessage()[2]);

					} else {

					}

				}
			}.start();

			//

		} else if (msg.getLength() > 3) {

			System.out.println(msg.getLength());

		} else if (msg.getLength() == 2) {

			System.out.println(msg.getLength());

		}
	}
	
	public class InstrumentItem {
		public int id ;
		public String name ;
		
		public InstrumentItem(int number, String label) {
			id = number ;
			name = label ;
		}
		
		public String toString() {
			return id+" - "+name ;
		}
	}

}
