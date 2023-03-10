package com.xxmicloxx.NoteBlockAPI;

public class Note {

	private byte instrument;
	private byte key;
	private byte velocity;
	private int panning;
	private short pitch;

	public Note(byte instrument, byte key) {
		this(instrument, key, (byte) 100, (byte) 100, (short) 0);
	}

	public Note(byte instrument, byte key, byte velocity, int panning, short pitch) {
		this.instrument = instrument;
		this.key = key;
		this.velocity = velocity;
		this.panning = panning;
		this.pitch = pitch;
	}

	/**
	 * Gets instrument number
	 */
	public byte getInstrument() {
		return instrument;
	}

	/**
	 * Sets instrument number
	 */
	public void setInstrument(byte instrument) {
		this.instrument = instrument;
	}

	public byte getKey() {
		return key;
	}

	public void setKey(byte key) {
		this.key = key;
	}

	/**
	 * Returns note pitch.
	 * 100 = 1 key
	 * 1200 = 1 octave
 	 * @return
	 */
	public short getPitch() {
		return pitch;
	}

	public float getRealPitch() {
		return  NoteUtils.getPitchInOctave(this);
	}

	/**
	 * Sets note pitch.
	 * 100 = 1 key
	 * 1200 = 1 octave
	 * @param pitch note pitch
	 */
	public void setPitch(short pitch) {
		this.pitch = pitch;
	}

	/**
	 * Returns note velocity (volume)
	 * @return
	 */
	public byte getVelocity() {
		return velocity;
	}

	/**
	 * Sets note velocity (volume)
	 * @param velocity number from 0 - 100
	 */
	public void setVelocity(byte velocity) {
		if (velocity < 0) velocity = 0;
		if (velocity > 100) velocity = 100;

		this.velocity = velocity;
	}

	public int getPanning() {
		return panning;
	}

	public void setPanning(int panning) {
		this.panning = panning;
	}
}
