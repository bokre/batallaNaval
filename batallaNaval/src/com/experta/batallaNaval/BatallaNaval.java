package com.experta.batallaNaval;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class BatallaNaval extends JFrame {

	/****************************************************************************/

	// variables globales customizables
	static String title = "BATALLA NAVAL";
	static int squares = 10;
	static int height = 500;
	static int width = 1000;
	static ImageIcon iPortaaviones = new ImageIcon("WebContent/images/portaaviones.gif");
	static ImageIcon iDestructor = new ImageIcon("WebContent/images/destructor.gif");
	static ImageIcon iFragata = new ImageIcon("WebContent/images/fragata.gif");

	static ImageIcon iBarco = new ImageIcon("WebContent/images/barco.gif");
	static ImageIcon iAgua = new ImageIcon("WebContent/images/agua.gif");
	static ImageIcon iTocado = new ImageIcon("WebContent/images/tocado.gif");
	static ImageIcon iHundido = new ImageIcon("WebContent/images/hundido.gif");

	// paneles y botones
	static JLayeredPane lPane;
	static JPanelRotate p1;
	static JPanelRotate p2;
	static JPanelRotate p3;
	static JPanelRotate p4;
	static JPanelRotate p5;
	static JLabel l1;
	static JLabel l2;
	static JLabel l3;
	static JLabel l4;
	static JLabel l5;
	JButton ship1;
	JButton ship2;
	JButton ship3;
	JButton ship4;
	JButton ship5;
	int shipSelected = 0;
	static JPanel pShips;
	static JPanel ownArea;
	static JPanel enemyArea;
	static JButton start;
	static JPanel actions;

	// matrix: ship, long, coordenadas de ubicacion y hits
	static String[][] shipsOwn = new String[5][7];
	static String[][] shipsAI = new String[5][7];

	static int turnOfPlay = 0;

	/****************************************************************************/

	public BatallaNaval() {

		// generales
		this.setTitle(title);

		// panel principal
		lPane = new JLayeredPane();
		lPane.setPreferredSize(new Dimension(width, height));
		getContentPane().add(lPane);

		/****************************************************************************/

		// barcos propios para agregar al tablero
		ship1 = new JButton("Portaaviones", iPortaaviones);
		ship1.addActionListener(e -> bClicked(e, 1));
		ship2 = new JButton("Destructor 1", iDestructor);
		ship2.addActionListener(e -> bClicked(e, 2));
		ship3 = new JButton("Destructor 2", iDestructor);
		ship3.addActionListener(e -> bClicked(e, 3));
		ship4 = new JButton("Fragata 1", iFragata);
		ship4.addActionListener(e -> bClicked(e, 4));
		ship5 = new JButton("Fragata 2", iFragata);
		ship5.addActionListener(e -> bClicked(e, 5));

		pShips = new JPanel();
		TitledBorder borderShips = new TitledBorder("Ubicar mis barcos");
		pShips.setBorder(borderShips);
		pShips.setLayout(new GridLayout(5, 1));
		pShips.setLocation(0, 0);
		pShips.add(ship1);
		pShips.add(ship2);
		pShips.add(ship3);
		pShips.add(ship4);
		pShips.add(ship5);
		pShips.setSize(width / 2, height / 2);
		pShips.setVisible(true);

		lPane.add(pShips);

		/****************************************************************************/

		// Acciones
		start = new JButton("Comenzar");
		start.addActionListener(e -> bClickedStart());

		actions = new JPanel();
		TitledBorder borderActions = new TitledBorder("Acciones");
		actions.setBorder(borderActions);
		actions.setLayout(new GridLayout(1, 1));
		actions.setLocation(0, height / 2);
		actions.add(start);
		actions.setSize(width / 2, height / 2);
		actions.setVisible(true);

		lPane.add(actions);

		/****************************************************************************/

		// tablero barcos propios
		ownArea = new JPanel();
		TitledBorder borderOwnArea = new TitledBorder("Mapa de mi territorio");
		ownArea.setBorder(borderOwnArea);
		ownArea.setLayout(new GridLayout(squares, squares));
		ownArea.setLocation(width / 2, 0);

		for (int i = 0; i < squares; i++) {
			for (int j = 0; j < squares; j++) {
				JButton b = new JButton();
				b.setName(String.valueOf(i) + String.valueOf(j));
				b.setRolloverEnabled(false);
				b.addActionListener(e -> bGridClicked(e));
				ownArea.add(b);
			}
		}
		ownArea.setSize(width / 2, height);
		ownArea.setVisible(true);

		lPane.add(ownArea);

		// tablero barcos enemigos
		enemyArea = new JPanel();
		TitledBorder borderEnemyArea = new TitledBorder("Mapa de territorio enemigo");
		enemyArea.setBorder(borderEnemyArea);
		enemyArea.setLayout(new GridLayout(squares, squares));
		enemyArea.setLocation(0, 0);

		for (int i = 0; i < squares; i++) {
			for (int j = 0; j < squares; j++) {
				JButton b = new JButton();
				b.setName(String.valueOf(i) + String.valueOf(j));
				b.setRolloverEnabled(false);
				b.addActionListener(e -> bGridEnemyClicked(e));
				enemyArea.add(b);
			}
		}
		enemyArea.setSize(width / 2, height);

		lPane.add(enemyArea);

		// paneles invisibles
		l1 = new JLabel(iPortaaviones);
		l1.setSize(iPortaaviones.getIconWidth(), iPortaaviones.getIconHeight() + 1);
		p1 = new JPanelRotate();
		p1.setSize(iPortaaviones.getIconWidth(), iPortaaviones.getIconHeight() + 1);
		p1.add(l1);

		l2 = new JLabel(iDestructor);
		l2.setSize(iDestructor.getIconWidth(), iDestructor.getIconHeight());
		p2 = new JPanelRotate();
		p2.setSize(iDestructor.getIconWidth(), iDestructor.getIconHeight());
		p2.add(l2);

		l3 = new JLabel(iDestructor);
		l3.setSize(iDestructor.getIconWidth(), iDestructor.getIconHeight());
		p3 = new JPanelRotate();
		p3.setSize(iDestructor.getIconWidth(), iDestructor.getIconHeight());
		p3.add(l3);

		l4 = new JLabel(iFragata);
		l4.setSize(iFragata.getIconWidth(), iFragata.getIconHeight());
		p4 = new JPanelRotate();
		p4.setSize(iFragata.getIconWidth(), iFragata.getIconHeight());
		p4.add(l4);

		l5 = new JLabel(iFragata);
		l5.setSize(iFragata.getIconWidth(), iFragata.getIconHeight());
		p5 = new JPanelRotate();
		p5.setSize(iFragata.getIconWidth(), iFragata.getIconHeight());
		p5.add(l5);

		/****************************************************************************/

		// setup de barcos propios y enemigos
		shipsOwn[0][0] = "1";
		shipsOwn[0][1] = "4";
		shipsOwn[0][6] = "0";
		shipsOwn[1][0] = "2";
		shipsOwn[1][1] = "3";
		shipsOwn[1][6] = "0";
		shipsOwn[2][0] = "3";
		shipsOwn[2][1] = "3";
		shipsOwn[2][6] = "0";
		shipsOwn[3][0] = "4";
		shipsOwn[3][1] = "2";
		shipsOwn[3][6] = "0";
		shipsOwn[4][0] = "5";
		shipsOwn[4][1] = "2";
		shipsOwn[4][6] = "0";

		shipsAI[0][0] = "1";
		shipsAI[0][1] = "4";
		shipsAI[0][6] = "0";
		shipsAI[1][0] = "2";
		shipsAI[1][1] = "3";
		shipsAI[1][6] = "0";
		shipsAI[2][0] = "3";
		shipsAI[2][1] = "3";
		shipsAI[2][6] = "0";
		shipsAI[3][0] = "4";
		shipsAI[3][1] = "2";
		shipsAI[3][6] = "0";
		shipsAI[4][0] = "5";
		shipsAI[4][1] = "2";
		shipsAI[4][6] = "0";
	}

	public void bClicked(ActionEvent e, int ship) {
		JButton b = (JButton) e.getSource();
		b.getModel().setPressed(true);
		shipSelected = ship;

		// quitar barco seleccionado
		removeShip(shipsOwn, shipSelected);

		switch (ship) {
		case 1:
			lPane.remove(p1);
			lPane.revalidate();
			lPane.repaint();
			break;
		case 2:
			lPane.remove(p2);
			lPane.revalidate();
			lPane.repaint();
			break;
		case 3:
			lPane.remove(p3);
			lPane.revalidate();
			lPane.repaint();
			break;
		case 4:
			lPane.remove(p4);
			lPane.revalidate();
			lPane.repaint();
			break;
		case 5:
			lPane.remove(p5);
			lPane.revalidate();
			lPane.repaint();
			break;
		default:
			break;
		}

		lPane.revalidate();
		lPane.repaint();
	}

	public void bGridClicked(ActionEvent e) {

		if (shipSelected > 0) {

			JButton b = (JButton) e.getSource();

			int orientation = 0; // horizontal

			switch (shipSelected) {
			case 1:
				if (!verifyLegalPosShip(shipsOwn, shipSelected, b.getName(), orientation))
					break;
				lPane.remove(p1);
				lPane.revalidate();
				lPane.repaint();
				p1.setLocation(b.getX() + width / 2, b.getY());
				lPane.add(p1, new Integer(105));
				break;
			case 2:
				if (!verifyLegalPosShip(shipsOwn, shipSelected, b.getName(), orientation))
					break;
				lPane.remove(p2);
				lPane.revalidate();
				lPane.repaint();
				p2.setLocation(b.getX() + width / 2, b.getY());
				lPane.add(p2, new Integer(104));
				break;
			case 3:
				if (!verifyLegalPosShip(shipsOwn, shipSelected, b.getName(), orientation))
					break;
				lPane.remove(p3);
				lPane.revalidate();
				lPane.repaint();
				p3.setLocation(b.getX() + width / 2, b.getY());
				lPane.add(p3, new Integer(103));
				break;
			case 4:
				if (!verifyLegalPosShip(shipsOwn, shipSelected, b.getName(), orientation))
					break;
				lPane.remove(p4);
				lPane.revalidate();
				lPane.repaint();
				p4.setLocation(b.getX() + width / 2, b.getY());
				lPane.add(p4, new Integer(102));
				break;
			case 5:
				if (!verifyLegalPosShip(shipsOwn, shipSelected, b.getName(), orientation))
					break;
				lPane.remove(p5);
				lPane.revalidate();
				lPane.repaint();
				p5.setLocation(b.getX() + width / 2, b.getY());
				lPane.add(p5, new Integer(101));
				break;
			default:
				break;
			}
			shipSelected = 0;
		}
	}

	public static boolean verifyLegalPosShip(String[][] matrix, int shipSelected, String name, int orientation) {

		// setup de variables basicas
		int row = Integer.parseInt(name.substring(0, 1));
		int col = Integer.parseInt(name.substring(1, 2));
		int large = Integer.parseInt(matrix[shipSelected - 1][1]);

		// construccion de coordenadas del barco que se desea agregar
		List<String> listNewShip = new ArrayList<String>();
		listNewShip.add(name);

		if (orientation == 0) {
			listNewShip.add(String.valueOf(row) + String.valueOf(col + 1));
			if (large > 2) {
				listNewShip.add(String.valueOf(row) + String.valueOf(col + 2));
				if (large > 3) {
					listNewShip.add(String.valueOf(row) + String.valueOf(col + 3));
				}
			}
		} else {
			listNewShip.add(String.valueOf(row + 1) + String.valueOf(col));
			if (large > 2) {
				listNewShip.add(String.valueOf(row + 2) + String.valueOf(col));
				if (large > 3) {
					listNewShip.add(String.valueOf(row + 3) + String.valueOf(col));
				}
			}
		}

		// se compara la matriz de barcos existentes con el nuevo
		if (isCoordOccupied(matrix, listNewShip)) {
			return false;
		}

		////////////////////////////////

		// verificacion de posicion legal en el tablero
		if (orientation == 0) { // horizontal
			if (large + col > 10) {
				return false;
			}
		} else { // vertical
			if (large + row > 10) {
				return false;
			}
		}

		// si todo está ok, se procede a grabar el nuevo barco en la matriz
		addShip(matrix, shipSelected, name, orientation);

		return true;
	}

	private static void addShip(String[][] ships, int shipSelected, String name, int orientation) {

		int row = Integer.parseInt(name.substring(0, 1));
		int col = Integer.parseInt(name.substring(1, 2));
		int large = Integer.parseInt(ships[shipSelected - 1][1]);

		ships[shipSelected - 1][2] = name;
		if (orientation == 0) { // horizontal
			ships[shipSelected - 1][3] = String.valueOf(row) + String.valueOf(col + 1);

			if (large > 2)
				ships[shipSelected - 1][4] = String.valueOf(row) + String.valueOf(col + 2);
			else
				ships[shipSelected - 1][4] = null;

			if (large > 3)
				ships[shipSelected - 1][5] = String.valueOf(row) + String.valueOf(col + 3);
			else
				ships[shipSelected - 1][5] = null;

		} else { // vertical
			ships[shipSelected - 1][3] = String.valueOf(row + 1) + String.valueOf(col);

			if (large > 2)
				ships[shipSelected - 1][4] = String.valueOf(row + 2) + String.valueOf(col);
			else
				ships[shipSelected - 1][4] = null;

			if (large > 3)
				ships[shipSelected - 1][5] = String.valueOf(row + 3) + String.valueOf(col);
			else
				ships[shipSelected - 1][5] = null;

		}
	}

	private static void removeShip(String[][] ships, int shipSelected) {
		ships[shipSelected - 1][2] = null;
		ships[shipSelected - 1][3] = null;
		ships[shipSelected - 1][4] = null;
		ships[shipSelected - 1][5] = null;
	}

	// metodo que compara una o mas posiciones de un array de string con la matriz
	private static boolean isCoordOccupied(String[][] ships, List<String> values) {
		Stream<String> matrix = Arrays.stream(ships).flatMap(x -> Arrays.stream(x));
		if (matrix.anyMatch(x -> values.contains(x))) {
			return true;
		}
		return false;
	}

	// metodo que posiciona los barcos enemigos
	private static void bClickedStart() {

		boolean allShipsLocated = true;

		// validacion que estan todos los barcos propios ubicados
		for (int i = 0; i < 5; i++) {
			if (shipsOwn[i][2] == null) {
				allShipsLocated = false;
			}
		}

		if (allShipsLocated) {
			// shipsAI
			// random de ubicacion de barcos enemigos
			boolean generateAgain = true;
			for (int i = 1; i <= 5; i++) {
				int orientation = Helper.getRandom(0, 1);
				generateAgain = true;
				while (generateAgain) {

					// generar coordenadas random
					int row = Helper.getRandom(0, 9);
					int col = Helper.getRandom(0, 9);

					if (!verifyLegalPosShip(shipsAI, i, String.valueOf(row) + String.valueOf(col), orientation)) {
						generateAgain = true;
					} else {
						generateAgain = false;
					}
				}
			}

			// Test print
			/*
			 * Arrays.stream(shipsAI).flatMap(x ->
			 * Arrays.stream(x)).forEach(System.out::println);
			 */

			startGame();

		} else {
			// System.out.println("LOCATE SHIPS");
		}
	}

	private static void startGame() {

		pShips.setVisible(false);
		actions.setVisible(false);

		Helper.resizeProportional(ownArea);
		lPane.remove(p1);
		lPane.remove(p2);
		lPane.remove(p3);
		lPane.remove(p4);
		lPane.remove(p5);
		lPane.revalidate();
		lPane.repaint();

		for (int i = 0; i < 5; i++) {
			if (shipsOwn[i][2] != null) {
				((JButton) ownArea.getComponent(Integer.parseInt(shipsOwn[i][2]))).setIcon(iBarco);
			}
			if (shipsOwn[i][3] != null) {
				((JButton) ownArea.getComponent(Integer.parseInt(shipsOwn[i][3]))).setIcon(iBarco);
			}
			if (shipsOwn[i][4] != null) {
				((JButton) ownArea.getComponent(Integer.parseInt(shipsOwn[i][4]))).setIcon(iBarco);
			}
			if (shipsOwn[i][5] != null) {
				((JButton) ownArea.getComponent(Integer.parseInt(shipsOwn[i][5]))).setIcon(iBarco);
			}
		}

		enemyArea.setVisible(true);
	}

	public void bGridEnemyClicked(ActionEvent e) {

		if (turnOfPlay == 0) {
			JButton b = (JButton) e.getSource();
			List<String> listNewShip = new ArrayList<String>();
			listNewShip.add(b.getName());
			if (isCoordOccupied(shipsAI, listNewShip)) {
				int shooted = shipShooted(shipsAI, listNewShip);
				if (isSunk(shipsAI, shooted)) {
					for (int i = 2; i < 6; i++) {
						if (shipsAI[shooted - 1][i] != null) {
							((JButton) enemyArea.getComponent(Integer.parseInt(shipsAI[shooted - 1][i])))
									.setIcon(iHundido);
							Helper.removeActionListener(
									((JButton) enemyArea.getComponent(Integer.parseInt(shipsAI[shooted - 1][i]))));
						}
					}
					if (checkEndGame()) {
						// System.out.println("FIN DEL JUEGO: HUMANO GANA!");
						turnOfPlay = 1;
					}
				} else {
					b.setIcon(iTocado);
					Helper.removeActionListener(b);
				}
			} else {
				b.setIcon(iAgua);
				Helper.removeActionListener(b);

				turnOfPlay = 1;

				playAI();
			}
		}
	}

	private static void playAI() {

		// generar shoot random
		int row = Helper.getRandom(0, 9);
		int col = Helper.getRandom(0, 9);

		// chequeo de no repeticion de disparo enemigo
		if (((JButton) ownArea.getComponent(Integer.parseInt(String.valueOf(row) + String.valueOf(col))))
				.getActionListeners().length > 0) {

			List<String> listNewShip = new ArrayList<String>();
			listNewShip.add(String.valueOf(row) + String.valueOf(col));
			if (isCoordOccupied(shipsOwn, listNewShip)) {
				int shooted = shipShooted(shipsOwn, listNewShip);
				if (isSunk(shipsOwn, shooted)) {
					for (int i = 2; i < 6; i++) {
						if (shipsOwn[shooted - 1][i] != null) {
							((JButton) ownArea.getComponent(Integer.parseInt(shipsOwn[shooted - 1][i])))
									.setIcon(iHundido);
							Helper.removeActionListener(
									((JButton) ownArea.getComponent(Integer.parseInt(shipsOwn[shooted - 1][i]))));
						}
					}
					if (checkEndGame()) {
						//System.out.println("FIN DEL JUEGO: AI GANA!");
					} else {
						playAI();
					}
				} else {
					((JButton) ownArea.getComponent(Integer.parseInt(String.valueOf(row) + String.valueOf(col))))
							.setIcon(iTocado);
					Helper.removeActionListener(((JButton) ownArea
							.getComponent(Integer.parseInt(String.valueOf(row) + String.valueOf(col)))));
					playAI();
				}
			} else {
				((JButton) ownArea.getComponent(Integer.parseInt(String.valueOf(row) + String.valueOf(col))))
						.setIcon(iAgua);
				Helper.removeActionListener(
						((JButton) ownArea.getComponent(Integer.parseInt(String.valueOf(row) + String.valueOf(col)))));

				turnOfPlay = 0;
			}
		} else {
			playAI();
		}
	}

	private static int shipShooted(String[][] ships, List<String> values) {

		for (int i = 0; i < 5; i++) {

			if (Objects.equals(ships[i][2], String.join("", values))
					|| Objects.equals(ships[i][3], String.join("", values))
					|| Objects.equals(ships[i][4], String.join("", values))
					|| Objects.equals(ships[i][5], String.join("", values))) {
				ships[i][6] = String.valueOf(Integer.parseInt(ships[i][6]) + 1);
				return Integer.parseInt(ships[i][0]);
			}
		}
		return 0;
	}

	private static boolean isSunk(String[][] ships, int shipSelected) {
		if (Objects.equals(ships[shipSelected - 1][1], ships[shipSelected - 1][6])) {
			return true;
		}
		return false;
	}

	private static boolean checkEndGame() {

		if (turnOfPlay == 0) {
			for (int i = 0; i < 5; i++) {
				if (!shipsAI[i][1].equals(shipsAI[i][6])) {
					return false;
				}
			}
		} else {
			for (int i = 0; i < 5; i++) {
				if (!shipsOwn[i][1].equals(shipsOwn[i][6])) {
					return false;
				}
			}
		}

		JLabel lFinal = new JLabel();
		if (turnOfPlay == 0) {
			lFinal.setText("HAS GANADO EL JUEGO! VICTORIA HUMANA!");
		} else {
			lFinal.setText("HAS PERDIDO EL JUEGO! VICTORIA AI!");
		}

		lFinal.setLocation(width / 2, height / 2);
		lFinal.setSize(width / 2, height / 2);
		lFinal.setVisible(true);
		lPane.add(lFinal);

		return true;
	}

	public static void main(String[] args) {
		JFrame frame = new BatallaNaval();
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}