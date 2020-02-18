package domain;

import java.util.ArrayList;
import java.util.Arrays;

import domain.elements.ThrowDice;
import domain.square.MoneyGiver;
import domain.square.RealEstateSquare;
import domain.square.Square;

public class MovementHandler {

	MultiPolyController controller;
	private ThrowDice dices;
	private ArrayList<Integer> diceValues;
	private int total;

	public MovementHandler(MultiPolyController controller) {
		this.controller = controller;
		dices = new ThrowDice();
		total = 0;

	}

	public ArrayList<Integer> getDiceValues() {
		return diceValues;
	}

	public void startBusIconProcess() {
		ArrayList<String> properties = new ArrayList<String>();
		int id = 0;
		String name = "";
		for (int i = 0; i < controller.getBoard().getSquares().size(); i++) {
			try {
				id = controller.getBoard().getSquares().get(i).getId();
			} catch (Exception e) {
				System.out.println("There is sth worng witd id of " + i + " . square");
			}
			try {
				name = controller.getBoard().getSquares().get(i).getName();
			} catch (Exception e) {
				System.out.println("There is sth worng witd name of " + i + " . square");
			}

			properties.add(id + " " + name);

		}
		controller.notifyObservers("StartBusIconProcess", properties, null);
	}

	public ThrowDice getDices() {
		return dices;
	}

	public void setDices(ThrowDice dices) {
		this.dices = dices;
	}

	private ThrowDice roll() {
		dices.forgetDie();
		dices.throwAndGet();

		return dices;
	}

	public void jailCase() {
		if (dices.isDouble()) {
			dices.incNumDub();
			controller.getCurrentPlayer().setInJail(false);

			controller.getGameFlow().add(controller.getCurrentPlayer().getName() + " is free!");
			controller.setCautionMoneyPayed(false);
			regularMove(dices.getRegularValues());
			controller.notifyObservers("RepresentJailDie", null,null);
			controller.notifyObservers("EnableThrowDiceButton", null, null);
			controller.notifyObservers("DisableEndTurnButton", null, null);

		} else {
			controller.getGameFlow().add(controller.getCurrentPlayer().getName() + " did not throw double and will be in jail.");
			controller.notifyObservers("RepresentJailDie", null,null);
			controller.getCurrentPlayerLoc().actForSquare(controller);  
		}
		controller.notifyObservers("DisableThrowDiceButton", null, null);
		controller.notifyObservers("EnableEndTurnButton", null, null);

	}

	public void normalCase() {

		if (dices.isSpecialDie()) {
			if (dices.getDie().get(2).getValue() == 0) {
				total = 0;
				for (int i = 0; i < dices.getDie().size() - 1; i++) {
					total += dices.getDie().get(i).getValue();
				}
				regularMove(total);
				startBusIconProcess();

				// moveWithBusIcon();
			} else {
				total = 0;
				for (int i = 0; i < dices.getDie().size() - 1; i++) {
					total += dices.getDie().get(i).getValue();
				}
				regularMove(total);
				controller.notifyObservers("EnableMrMonopolyButton", null, null);
				// moveWithMrMonopoly();
			}
		} else {
			total = 0;
			for (int i = 0; i < dices.getDie().size(); i++) {
				total += dices.getDie().get(i).getValue();
			}
			regularMove(total);
		}

	}

	public void extremeCase() {

		if (dices.isDouble()) {
			dices.incNumDub();
			if (dices.getNumDub() == 3) {
				controller.getCurrentPlayer().setInJail(true);
				controller.getCurrentPlayer().getPiece().setLocation(controller.getBoard().getSquares().get(86));
				controller.getGameFlow().add(
						controller.getCurrentPlayer().getName() + " arrested! The trial will begin in the next turn.");
				controller.setCautionMoneyPayed(false);
				dices.resetNumDub();
				controller.notifyObservers("GoToJail", 14, 86);
				controller.notifyObservers("JustRepresentDice", diceValues, null);

			} else {
				normalCase();
				controller.notifyObservers("EnableThrowDiceButton", null, null);
			}
		} else if (dices.isTriple()) {
			dices.resetNumDub();
			startBusIconProcess();
			controller.notifyObservers("moveWithTriple", null, null); 
		}

	}

	public void throwAndMove() {

		ThrowDice thrw = roll();
		
		diceValues = new ArrayList<Integer>(Arrays.asList(dices.getDie().get(0).getValue(),
				dices.getDie().get(1).getValue(), dices.getDie().get(2).getValue()));
		if (controller.getCurrentPlayer().isInJail()) {
			jailCase();
		} else if (dices.isDouble() || dices.isTriple()) {
			extremeCase();
		} else {
			normalCase();
		}
		//reverseDirHandler
		if(!(controller.getCurrentPlayerLocID() == 106)) 
			controller.getCurrentPlayer().resetDirection(); 


	}

	public void regularMove(int total) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		int currentIndex = controller.getCurrentPlayerLocID();
		int startIndex = controller.getCurrentPlayerLocID();
		
		path.add(currentIndex);

		int remaining = total;
		boolean isEven = false;

		if (total % 2 == 0) {
			isEven = true;
		}
		boolean isReverse = controller.getCurrentPlayer().isReverse();

		while (remaining != 0) {
			Square sq = controller.getBoard().getSquareByID(currentIndex); 
			Square sqNot = controller.getBoard().getSquareByID(startIndex); 
			if(sq instanceof MoneyGiver && !(sqNot.equals(sq))) { 
				((MoneyGiver) sq).receiveMoney(controller); 
			} 

			
			
			if (!isReverse) {
				if (isEven) {
					switch (currentIndex) {
					case 7:
						currentIndex = 61;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 61:
						currentIndex = 7;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 71:
						currentIndex = 105;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 105:
						currentIndex = 71;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 35:
						currentIndex = 81;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 81:
						currentIndex = 35;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 91:
						currentIndex = 117;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 117:
						currentIndex = 91;
						path.add(currentIndex);
						currentIndex++;
						break;

					// Track change
					case 55:
						currentIndex = 0;
						break;
					case 95:
						currentIndex = 56;
						break;
					case 119:
						currentIndex = 96;
						break;

					default:
						currentIndex++;
						break;
					}
					remaining--;
				} else {
					switch (currentIndex) {
					case 55:
						currentIndex = 0;
						break;
					case 95:
						currentIndex = 56;
						break;
					case 119:
						currentIndex = 96;
						break;

					default:
						currentIndex++;
						break;
					}
					remaining--;
				}

				// Reversed case
			} else {
				// Transit station
				if (isEven) {
					switch (currentIndex) {
					case 7:
						currentIndex = 61;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 61:
						currentIndex = 7;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 71:
						currentIndex = 105;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 105:
						currentIndex = 71;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 35:
						currentIndex = 81;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 81:
						currentIndex = 35;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 91:
						currentIndex = 117;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 117:
						currentIndex = 91;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;

					// Reversed track change
					case 0:
						currentIndex = 55;
						break;
					case 56:
						currentIndex = 95;
						break;
					case 96:
						currentIndex = 119;
						break;
					default:
						currentIndex--;
						break;
					}
					remaining--;
				} else {

					switch (currentIndex) {
					case 0:
						currentIndex = 55;
						break;
					case 56:
						currentIndex = 95;
						break;
					case 96:
						currentIndex = 119;
						break;
					default:
						currentIndex--;
						break;
					}
					remaining--;
				}
			}
			path.add(currentIndex);// Path added (for further usage)
		}
		controller.setCurrentPlayerLoc(controller.getBoard().getSquares().get(path.get(path.size() - 1))); // current
		// player
		// set
		controller.getCurrentPlayerLoc().actForSquare(controller); // Act for square got the controller info

		// update game flow
		controller.getGameFlow().add(
				"" + controller.getCurrentPlayer().getName() + " came " + controller.getCurrentPlayerLoc().getName());
		// update observers
		controller.notifyObservers("RegularMove", controller.getPlayers().indexOf(controller.getCurrentPlayer()), path);

	}

	public void moveWithMrMonopoly() {
		ArrayList<Integer> path = new ArrayList<Integer>();

		int currentIndex = controller.getCurrentPlayerLocID();
		int PermanentIndex = currentIndex;

		boolean isOwned = true;
		path.add(currentIndex);

		int remaining = total;
		// TODO: use this variable according to design

		boolean isEven = false;

		if (total % 2 == 0) {
			isEven = true;
		}
		boolean isReverse = controller.getCurrentPlayer().isReverse();
		boolean isStart = true;

		while (isStart || (isOwned && currentIndex != PermanentIndex)) {
			isStart = false;
			if (!isReverse) {
				// transit station
				if (isEven) {
					switch (currentIndex) {
					case 7:
						currentIndex = 61;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 61:
						currentIndex = 7;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 71:
						currentIndex = 105;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 105:
						currentIndex = 71;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 35:
						currentIndex = 81;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 81:
						currentIndex = 35;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 91:
						currentIndex = 117;
						path.add(currentIndex);
						currentIndex++;
						break;
					case 117:
						currentIndex = 91;
						path.add(currentIndex);
						currentIndex++;
						break;
					default:
						// track end
						if (currentIndex == 55) {
							currentIndex = 0;
						} else if (currentIndex == 95) {
							currentIndex = 56;
						} else if (currentIndex == 119) {
							currentIndex = 96;
						} else {
							currentIndex++;
						}
						break;
					}
					remaining--;
				} else {
					if (currentIndex == 55) {
						currentIndex = 0;
						remaining--;
					} else if (currentIndex == 95) {
						currentIndex = 56;
						remaining--;
					} else if (currentIndex == 119) {
						currentIndex = 96;
						remaining--;
					} else {
						currentIndex++;
						remaining--;
					}
				}
				// moneyComingSquare will come here
				path.add(currentIndex);
				// reverse coming
			} else {
				// transit station
				if (isEven) {
					switch (currentIndex) {
					case 7:
						currentIndex = 61;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 61:
						currentIndex = 7;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 71:
						currentIndex = 105;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 105:
						currentIndex = 71;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 35:
						currentIndex = 81;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 81:
						currentIndex = 35;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 91:
						currentIndex = 117;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					case 117:
						currentIndex = 91;
						path.add(currentIndex);
						currentIndex++;
						isReverse = false;
						break;
					default:
						// track end
						if (currentIndex == 0) {
							currentIndex = 55;
						} else if (currentIndex == 56) {
							currentIndex = 95;
						} else if (currentIndex == 96) {
							currentIndex = 119;
						} else {
							currentIndex--;
						}
						break;
					}
					remaining--;
				} else {
					if (currentIndex == 0) {
						currentIndex = 55;
					} else if (currentIndex == 56) {
						currentIndex = 95;
					} else if (currentIndex == 96) {
						currentIndex = 119;
					} else {
						currentIndex--;
					}
					remaining--;
				}
				// TODO: Money Coming Square
				path.add(currentIndex);
			}
			if (controller.getBoard().getSquares().get(currentIndex) instanceof RealEstateSquare) {
				if (((RealEstateSquare) controller.getBoard().getSquares().get(currentIndex)).getOwner() == null) {
					isOwned = false;
					break;
				}
			}
		}
		if (isOwned == true) {
			ArrayList<Integer> pathTwo = new ArrayList<Integer>();

			for (int i = 0; i < path.size(); i++) {
				Square sq = controller.getBoard().getSquares().get(path.get(i));
				if (sq instanceof RealEstateSquare) {
					if (((RealEstateSquare) sq).getOwner() != controller.getCurrentPlayer()) {

						pathTwo.add(path.get(i));
						break;
					}
				}
				pathTwo.add(path.get(i));

			}
			controller.setCurrentPlayerLoc(controller.getBoard().getSquares().get(pathTwo.get(pathTwo.size() - 1)));
			controller.getCurrentPlayerLoc().actForSquare(controller);
			controller.getGameFlow()
					.add("" + controller.getCurrentPlayer().getName() + " could not find any unowned square");

			controller.notifyObservers("MrMonopolyMove", controller.getPlayers().indexOf(controller.getCurrentPlayer()),
					pathTwo);

		} else {
			controller.setCurrentPlayerLoc(controller.getBoard().getSquares().get(path.get(path.size() - 1)));
			controller.getCurrentPlayerLoc().actForSquare(controller);
			controller.getGameFlow().add("" + controller.getCurrentPlayer().getName() + " came "
					+ controller.getCurrentPlayerLoc().getName() + " with Mr.Monopoly");

			controller.notifyObservers("MrMonopolyMove", controller.getPlayers().indexOf(controller.getCurrentPlayer()),
					path);

		}
	}

	// Bus extension
	public void moveWithBusIcon() {
		// TODO: Implement this function

		// Attention: Below method will give a null, since sqid never initialized or altered in controller
		int idNumber = controller.getSquareIdFromPlayer();
		Square selectedSq = controller.getBoard().getSquares().get(idNumber);
		controller.getCurrentPlayer().getPiece().setLocation(selectedSq);
		controller.getCurrentPlayer().getPiece().getLocation().actForSquare(controller);
		controller.notifyObservers("BusIconMove", controller.getPlayers().indexOf(controller.getCurrentPlayer()), idNumber);
		controller.getGameFlow().add(controller.getCurrentPlayer().getName()+" travelled with Bus Icon");

	}
}
