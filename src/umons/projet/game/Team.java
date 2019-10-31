package umons.projet.game;

import umons.projet.game.Player.BluePlayer;
import umons.projet.game.Player.Player;
import umons.projet.game.Player.RedPlayer;

// Classe representant les differentes equipes

public enum Team {
    
	RED {
		
		@Override
		public boolean isNull() {
			
			return false;
		}
		
		@Override
		public boolean isRed() {
			
			return true;
		}
		
		@Override
		public boolean isBlue() {
			
			return false;
		}

		@Override
		public Player choosePlayer(final RedPlayer redPlayer, final BluePlayer bluePlayer) {
			
			return redPlayer;
		}
	},
	BLUE {
		
		@Override
		public boolean isNull() {
			
			return false;
		}
		
		@Override
		public boolean isRed() {
			
			return false;
		}
		
		@Override
		public boolean isBlue() {
			
			return true;
		}

		@Override
		public Player choosePlayer(final RedPlayer redPlayer, final BluePlayer bluePlayer) {
			
			return bluePlayer;
		}
	},
	
    NULL {
		
		@Override
		public boolean isNull() {
			
			return true;
		}
		
		@Override
		public boolean isRed() {
			
			return false;
		}
		
		@Override
		public boolean isBlue() {
			
			return false;
		}

		@Override
		public Player choosePlayer(final RedPlayer redPlayer, final BluePlayer bluePlayer) {
			
			return null;
		}
	};
	
	public abstract boolean isNull();
	public abstract boolean isRed();
	public abstract boolean isBlue();
	public abstract Player choosePlayer(RedPlayer redPlayer, BluePlayer bluePlayer);
}
