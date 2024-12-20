/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEGTH = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_HEGTH * SCREEN_WIDTH) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int applex;
    int appley;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random ramdom;

    public GamePanel() {
        // TODO Auto-generated constructor stub
        ramdom = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEGTH));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        newapple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
//			for (int i = 0; i < SCREEN_HEGTH / UNIT_SIZE; i++) {
//				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEGTH);
//				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//			}

            g.setColor(Color.red);
            g.fillOval(applex, appley, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
//					g.setColor(new Color(ramdom.nextInt(255), ramdom.nextInt(255), ramdom.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                } else {
                    g.setColor(new Color(45, 100, 0));
                    g.setColor(new Color(ramdom.nextInt(255), ramdom.nextInt(255), ramdom.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("JetBrans Mono", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Diem: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Diem: " + applesEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void startScreen(Graphics g) {
        g.setColor(Color.green);
        g.setFont(new Font("JetBrains Mono", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Snake Game", (SCREEN_WIDTH - metrics.stringWidth("Snake Game")) / 2, SCREEN_HEGTH / 3);

        g.setFont(new Font("JetBrains Mono", Font.BOLD, 40));
        g.drawString("Press SPACE to Start", (SCREEN_WIDTH - metrics.stringWidth("Press SPACE to Start")) / 2, SCREEN_HEGTH / 2);
    }

    public void newapple() {
        applex = ramdom.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appley = ramdom.nextInt((int) (SCREEN_HEGTH / UNIT_SIZE)) * UNIT_SIZE;

    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }

    public void checkApple() {
        if ((x[0] == applex) && (y[0] == appley)) {
            bodyParts++;
            applesEaten++;
            newapple();

        }
    }

    public void checkCollisions() {
        // Kiem tra nieu dau va cham voi phan than
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && (y[0] == y[i])) {
                running = false;
            }
        }
        // Kiem tra dau co cham ben trai duong vien hay ko
        if (x[0] < 0) {
            running = false;
        }
        // Kiem tra dau co cham ben phai duong vien hay ko
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        // Kiem tra dau co cham ben tren duong vien hay ko
        if (y[0] < 0) {
            running = false;
        }
        // Kiem tra dau co cham ben duoi duong vien hay ko
        if (y[0] > SCREEN_HEGTH) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // hien thi diem
        g.setColor(Color.red);
        g.setFont(new Font("JetBrans Mono", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Diem: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Diem: " + applesEaten)) / 2,
                g.getFont().getSize());
        // thong bao sua khi ket thuc game
        g.setColor(Color.red);
        g.setFont(new Font("JetBrans Mono", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("You Die")) / 2, SCREEN_HEGTH / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (running) {
            move();
            checkApple();
            checkCollisions();

        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
