

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class DumbPhone extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 500;
	private JTextArea screen;
	private JButton topMiddleButton;
	private JButton topLeftButton;
	private JButton topRightButton;
	private JButton[] numberButtons = new JButton[15];
	private JButton starButton;
	private JButton poundButton;
	private static final String CALL_BUTTON_TEXT = "Call";
	private static final String TEXT_BUTTON_TEXT = "Text";
	private static final String DELETE_BUTTON_TEXT = "Delete";
	
	
	public DumbPhone() {
		setTitle("Dumb Phone");
	    setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    createContents();
	    setVisible(true);
	}
	
	private void createContents() {
		Listener listener = new Listener();
		
		//create JPanel, and JTextArea display
		JPanel northPanel = new JPanel();
		JPanel centerPanel = new JPanel(new GridLayout(5,3, 5, 5));
		
		// Set screen settings
		screen = new JTextArea(1,1);
		screen.setLineWrap(true);
		screen.setFont(new Font("Helvetica", Font.PLAIN, 32));
		screen.setPreferredSize(new Dimension(280,80));
		screen.setEditable(false);
		northPanel.add(screen);
		
		
        
        //create JButtons
		JButton topLeftButton = new JButton(DELETE_BUTTON_TEXT);
		JButton topMiddleButton = new JButton(CALL_BUTTON_TEXT);
		JButton topRightButton = new JButton(TEXT_BUTTON_TEXT);
		JButton b1 = new JButton("<html><center>1</center></html>");
		JButton b2 = new JButton("<html><center>2<br>ABC</center></html>");
		JButton b3 = new JButton("<html><center>3<br>DEF</center></html>");
		JButton b4 = new JButton("<html><center>4<br>GHI</center></html>");
		JButton b5 = new JButton("<html><center>5<br>JKL</center></html>");
		JButton b6 = new JButton("<html><center>6<br>MNO</center></html>");
		JButton b7 = new JButton("<html><center>7<br>PQRS</center></html>");
		JButton b8 = new JButton("<html><center>8<br>TUV</center></html>");
		JButton b9 = new JButton("<html><center>9<br>WXYZ</center></html>");
		JButton starButton = new JButton("*");
		JButton b0 = new JButton("<html><center>0<br>space</center></html>");
		JButton poundButton = new JButton("#");
        
        //add JButtons to buttons JPanel
       centerPanel.add(topLeftButton);
       centerPanel.add(topMiddleButton);
       centerPanel.add(topRightButton);
       centerPanel.add(b1);
       centerPanel.add(b2);
       centerPanel.add(b3);
       centerPanel.add(b4);
       centerPanel.add(b5);
       centerPanel.add(b6);
       centerPanel.add(b7);
       centerPanel.add(b8);
       centerPanel.add(b9);
       centerPanel.add(starButton);
       centerPanel.add(b0);
       centerPanel.add(poundButton);
       
       topLeftButton.setEnabled(false);
       
       // Add numerical buttons to array
	   numberButtons[0] = b0;
	   numberButtons[1] = b1;
	   numberButtons[2] = b2;
	   numberButtons[3] = b3;
	   numberButtons[4] = b4;
	   numberButtons[5] = b5;
	   numberButtons[6] = b6;
	   numberButtons[7] = b7;
	   numberButtons[8] = b8;
	   numberButtons[9] = b9;
	   numberButtons[10] = topLeftButton;
	   numberButtons[11] = topMiddleButton;
	   numberButtons[12] = topRightButton;
	   numberButtons[13] = starButton;
	   numberButtons[14] = poundButton;
       
        
        //add Listener instance (inner class) to buttons
	   	topLeftButton.addActionListener(listener);
	   	topMiddleButton.addActionListener(listener);
	   	topRightButton.addActionListener(listener);
	   	starButton.addActionListener(listener);
	   	poundButton.addActionListener(listener);
   		for(int i=0; i<10; i++) {
   			numberButtons[i].addActionListener(listener);
   		}
        
        //add display and buttons to JFrame
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		

	}
	
	private class Listener implements ActionListener {
		
		private boolean isNumberMode = true;
		private JButton lastPressed;
		private int lastCharacterIndex;
		private long lastNanoTime;
		
		public void actionPerformed(ActionEvent e) {
			
			
			if(e.getActionCommand() == DELETE_BUTTON_TEXT) {
				screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
				if(screen.getText().length() == 0) {
					numberButtons[10].setEnabled(false);
				}
				
			}
			
			
			if(screen.getText().equals(null) || screen.getText().equals("Enter a number...")) {
				numberButtons[10].setEnabled(false);
			}
				
			
				if(screen.getText().equals("") && e.getActionCommand() == CALL_BUTTON_TEXT || screen.getText().equals("") && e.getActionCommand() == TEXT_BUTTON_TEXT) {
					screen.setText("Enter a number...");
				} else {
					
					
					if(e.getActionCommand() == TEXT_BUTTON_TEXT && !screen.getText().equals("Enter a number...")) {
						isNumberMode = false;
						screen.setText("Enter Text...");
						numberButtons[10].setText(DELETE_BUTTON_TEXT);
						numberButtons[10].setEnabled(false);
						numberButtons[11].setText("Cancel");
						numberButtons[12].setText("Send");
						numberButtons[12].setEnabled(false);
						numberButtons[1].setEnabled(false);
						numberButtons[13].setEnabled(false);
						numberButtons[14].setEnabled(false);
					}
					

					if(isNumberMode == false) {
						
						if(!screen.getText().equals("") && !screen.getText().equals("Enter Text...")) {
							
							if(e.getSource() == numberButtons[12]) {
								screen.setText("");
								isNumberMode = true;
								numberButtons[10].setText(DELETE_BUTTON_TEXT);
								numberButtons[10].setEnabled(false);
								numberButtons[11].setText("Call");
								numberButtons[12].setText("Text");
								numberButtons[12].setEnabled(true);
								numberButtons[1].setEnabled(true);
								numberButtons[13].setEnabled(true);
								numberButtons[14].setEnabled(true);
							}
						}
						
						
						if(e.getSource() == numberButtons[11]) {
							screen.setText("");
							isNumberMode = true;
							numberButtons[10].setText(DELETE_BUTTON_TEXT);
							numberButtons[10].setEnabled(false);
							numberButtons[11].setText("Call");
							numberButtons[12].setText("Text");
							numberButtons[12].setEnabled(true);
							numberButtons[1].setEnabled(true);
							numberButtons[13].setEnabled(true);
							numberButtons[14].setEnabled(true);
						}
						
						if(lastPressed == e.getSource() && (System.nanoTime() / 1000000 - lastNanoTime / 1000000) < 1000) {
							lastCharacterIndex++;
						} else {
							lastCharacterIndex = 0;
						}
						  
						if(e.getSource() == numberButtons[2]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[2];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 3 == 0) {
								
								if(lastCharacterIndex > 2) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "A");
							} else if(lastCharacterIndex % 3 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "B");
							} else if(lastCharacterIndex % 3 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "C");
							}
							
						}
						
						if(e.getSource() == numberButtons[3]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[3];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 3 == 0) {
								
								if(lastCharacterIndex > 2) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "D");
							} else if(lastCharacterIndex % 3 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "E");
							} else if(lastCharacterIndex % 3 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "F");
							}
							
						}
						
						if(e.getSource() == numberButtons[4]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[4];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 3 == 0) {
								
								if(lastCharacterIndex > 2) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "G");
							} else if(lastCharacterIndex % 3 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "H");
							} else if(lastCharacterIndex % 3 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "I");
							}
							
						}
						
						if(e.getSource() == numberButtons[5]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[5];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 3 == 0) {
								
								if(lastCharacterIndex > 2) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "J");
							} else if(lastCharacterIndex % 3 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "K");
							} else if(lastCharacterIndex % 3 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "L");
							}
							
						}
						
						if(e.getSource() == numberButtons[6]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[6];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 3 == 0) {
								
								if(lastCharacterIndex > 2) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "M");
							} else if(lastCharacterIndex % 3 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "N");
							} else if(lastCharacterIndex % 3 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "O");
							}
							
						}
						
						if(e.getSource() == numberButtons[7]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[7];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 4 == 0) {
								
								if(lastCharacterIndex > 3) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "P");
							} else if(lastCharacterIndex % 4 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "Q");
							} else if(lastCharacterIndex % 4 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "R");
							} else if(lastCharacterIndex % 4 == 3) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "S");
							}
							
						}
						
						if(e.getSource() == numberButtons[8]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[8];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 3 == 0) {
								
								if(lastCharacterIndex > 2) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "T");
							} else if(lastCharacterIndex % 3 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "U");
							} else if(lastCharacterIndex % 3 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "V");
							}
							
						}
						
						if(e.getSource() == numberButtons[9]) {
							lastNanoTime = System.nanoTime();
							lastPressed = numberButtons[9];
							numberButtons[10].setEnabled(true);
							numberButtons[12].setEnabled(true);
							
							if(lastCharacterIndex % 4 == 0) {
								
								if(lastCharacterIndex > 3) {
									screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								}
								
								if(screen.getText().equals("Enter Text...")) {
									screen.setText("");
								}
								
								screen.setText(screen.getText() + "W");
							} else if(lastCharacterIndex % 4 == 1) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "X");
							} else if(lastCharacterIndex % 4 == 2) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "Y");
							} else if(lastCharacterIndex % 4 == 3) {
								screen.setText(screen.getText().substring(0, screen.getText().length() - 1));
								screen.setText(screen.getText() + "Z");
							}
							
						}
						
						if(e.getSource() == numberButtons[0]) {
							
							if(screen.getText().equals("Enter Text...")) {
								screen.setText("");
							}
							
							screen.setText(screen.getText() + " ");
						}
						
					}
				
					if(isNumberMode == true) {
						
						if(e.getActionCommand() == CALL_BUTTON_TEXT && !screen.getText().equals("Enter a number...")) {
							screen.setText("Calling...");
							numberButtons[10].setEnabled(false);
							numberButtons[10].setText("");
							numberButtons[12].setEnabled(false);
							numberButtons[12].setText("");
							numberButtons[11].setText("End");
						}

						if(e.getActionCommand().equals("*")) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "*");
						} else if(e.getActionCommand().equals("#")) {
							
							if(screen.getText().equals("Enter a number...")  || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "#");
						} else if(e.getSource() == numberButtons[0]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "0");
						}  else if(e.getSource() == numberButtons[1]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "1");
						}  else if(e.getSource() == numberButtons[2]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "2");
						}  else if(e.getSource() == numberButtons[3]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "3");
						}  else if(e.getSource() == numberButtons[4]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "4");
						} else if(e.getSource() == numberButtons[5]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "5");
						} else if(e.getSource() == numberButtons[6]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "6");
						} else if(e.getSource() == numberButtons[7]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "7");
						} else if(e.getSource() == numberButtons[8]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "8");
						} else if(e.getSource() == numberButtons[9]) {
							
							if(screen.getText().equals("Enter a number...") || screen.getText().equals("Calling...")) {
								screen.setText("");
							}
							
							numberButtons[10].setEnabled(true);
							screen.setText(screen.getText() + "9");
						} else if(e.getSource() == numberButtons[11] && e.getActionCommand() == "End") {
							screen.setText("");
							numberButtons[12].setEnabled(true);
							numberButtons[12].setText(TEXT_BUTTON_TEXT);
							numberButtons[10].setText(DELETE_BUTTON_TEXT);
							numberButtons[11].setText(CALL_BUTTON_TEXT);
						}
						
						
					}
				
				}
				
			
	    }
	}
	
	public static void main(String[] args) {
		new DumbPhone();
	}
}