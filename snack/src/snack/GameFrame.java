/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snack;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	GameFrame() {
		// TODO Auto-generated constructor stub
		this.add(new GamePanel());
		this.setTitle("Ran san moi");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}