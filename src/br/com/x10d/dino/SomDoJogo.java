package br.com.x10d.dino;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SomDoJogo {

	public static void main(String[] args) throws Exception {
		new SomDoJogo().tocaAudio("/sons/pulo.wav");
		//new Player().tocaAudio("/sons/Passaro.wav");
		//new Player().tocaAudio("/sons/colisao.wav");
	}

	public Clip tocaAudio(String nomeDoAudio) {

		URL url = getClass().getResource(nomeDoAudio);
		Clip clip = null;
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			     
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * SwingUtilities.invokeLater(new Runnable() { public void run() {
		 * JOptionPane.showMessageDialog(null, "Clique pra fechar!"); } });
		 */
		return clip;
	}
}
