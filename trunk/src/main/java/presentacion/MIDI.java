package presentacion;

import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class MIDI implements Runnable {
	Sequencer sequencer = null; // reproduce MIDI
	
	ResourceLoader resourceLoader = new ResourceLoader();

	public MIDI(){
		
		// se decidio implementar con thread porque la carga del midi duraba mucho
        Thread t = new Thread(this);
        t.start();
	}

	public void run() {
		try {
	        // From file
			URL url = resourceLoader.load("/midi/Smells_like_teen_spirit.mid");
			Sequence sequence = MidiSystem.getSequence(url.openStream());

	        // Create a sequencer for the sequence
	        sequencer = MidiSystem.getSequencer();
	        sequencer.open();
	        sequencer.setSequence(sequence);
	    
	        // Start playing
	        sequencer.start();

	    } catch (IOException e) {
	    } catch (MidiUnavailableException e) {
	    } catch (InvalidMidiDataException e) {
	    }
	}
	
	public void stop(){
		sequencer.stop();
	}
}
