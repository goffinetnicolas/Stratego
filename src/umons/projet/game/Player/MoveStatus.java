package umons.projet.game.Player;

/**
 * 
 * @author goffinet nicolas
 * Classe enum avec les transitions effectuees 
 *
 */

public enum MoveStatus {
	
    DONE { //apres avoir execute un mouvement legal
    
        @Override
        public boolean isDone() {
    	
    	return true;
        }
    },
    
    ILLEGAL_MOVE { //si le mouvement etait illegal

		@Override
		public boolean isDone() {
			return false;
		}
    	
    };
    
	/**
	 * 
	 * @return true si le MoveStatus est de type DONE
	 * 
	 */
	
   public abstract boolean isDone();
}
