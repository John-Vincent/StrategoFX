package stratego.mode.singleplayer;

import stratego.mode.ModeWorker;

/**
 * The class that lets the SinglePlayerUI communicate with the network.
 */
public class SingleplayerWorker extends ModeWorker {

	/**
	 * Constructor sets the tasklist that communicates tasks between the UI and
	 * the worker.
	 * 
	 * @param q
	 *            The queue that SinglePlayerUI uses to pass request to the
	 *            SinglePlayerWorker.
	 */
	public SingleplayerWorker() {
		super();
		queueTask(new StartMusic());
	}

	@Override
	public boolean addTask(String name) {
		switch (name) {
		case "back":
			queueTask(new MenuOptions());
			return true;
		case "loose":
			queueTask(new LossMusic());
			return true;
		case "win":
			queueTask(new WinMusic());
			return true;
		default:
			return false;
		}
	}

	private class MenuOptions implements Runnable {

		@Override
		public void run() {
			setRunning(false);
		}

	}

	private class StartMusic implements Runnable {

		@Override
		public void run() {
			stratego.components.MusicPlayer.changeMusic(stratego.components.MusicPlayer.getGameMusic(),
					stratego.components.MusicPlayer.getCurrentVolume());
		}
	}

	private class LossMusic implements Runnable {

		@Override
		public void run() {
			stratego.components.MusicPlayer.changeMusic(stratego.components.MusicPlayer.getLossMusic(),
					stratego.components.MusicPlayer.getCurrentVolume());

		}

	}

	private class WinMusic implements Runnable {

		@Override
		public void run() {
			stratego.components.MusicPlayer.changeMusic(stratego.components.MusicPlayer.getWinMusic(),
					stratego.components.MusicPlayer.getCurrentVolume());
		}

	}
}
