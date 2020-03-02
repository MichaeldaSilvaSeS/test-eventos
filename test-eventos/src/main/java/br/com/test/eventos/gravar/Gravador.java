package br.com.test.eventos.gravar;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.NativeInputEvent;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

public abstract class Gravador implements NativeKeyListener, NativeMouseListener, NativeMouseWheelListener, NativeMouseMotionListener {

	protected abstract void adicionarEvento(String evento, NativeInputEvent nativeEvent);

	public void nativeKeyTyped(NativeKeyEvent nativeEvent) {
	}

	public void nativeKeyReleased(NativeKeyEvent nativeEvent) {
		adicionarEvento("liberado", nativeEvent);
	}

	public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
		adicionarEvento("pressionado", nativeEvent);
	}
	
	public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
		adicionarEvento("move", nativeEvent);
	}

	public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
	}
	
	public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
		adicionarEvento("click", nativeEvent);
	}

	public void nativeMousePressed(NativeMouseEvent nativeEvent) {
	}

	public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
	}

	public void nativeMouseWheelMoved(NativeMouseWheelEvent nativeEvent) {
	}
	
	public void iniciar()
			throws NativeHookException {
		Logger.getLogger(GlobalScreen.class.getPackage().getName()).setLevel(Level.OFF);
		
		GlobalScreen.registerNativeHook();
		GlobalScreen.addNativeKeyListener(this);
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseWheelListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
	}
	
	protected void finalizar() throws NativeHookException {
		GlobalScreen.unregisterNativeHook();
		GlobalScreen.removeNativeKeyListener(this);
		GlobalScreen.removeNativeMouseListener(this);
		GlobalScreen.removeNativeMouseWheelListener(this);
		GlobalScreen.removeNativeMouseMotionListener(this);
	}
}
