package com.company;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game
        implements KeyListener{
    private Snake player;
    private Food food;
    private Graphics graphics;

    private JFrame window;

    public static final int width = 30;
    public static final int height = 30;
    public static final int dimension = 20;

    public Game() {
        window = new JFrame();

        player = new Snake();

        food = new Food(player);

        graphics = new Graphics(this);

        window.add(graphics);

        window.setTitle("Elliot Snake Game");
        window.setSize(width * dimension + 2, height * dimension + dimension + 4);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {
        graphics.state = "RUNNING";
    }

    public void update() {
        if(graphics.state == "RUNNING") {
            if(check_food_collision()) {
                player.grow();
                food.random_spawn(player);
            }
            else if(check_wall_collision() || check_self_collision()) {
                graphics.state = "END";
            }
            else {
                player.move();
            }
        }
    }

    private boolean check_wall_collision() {
        if(player.getX() < 0 || player.getX() >= width * dimension
                || player.getY() < 0|| player.getY() >= height * dimension) {
            return true;
        }
        return false;
    }

    private boolean check_food_collision() {
        if(player.getX() == food.getX() * dimension && player.getY() == food.getY() * dimension) {
            return true;
        }
        return false;
    }

    private boolean check_self_collision() {
        for(int i = 1; i < player.getBody().size(); i++) {
            if(player.getX() == player.getBody().get(i).x &&
                    player.getY() == player.getBody().get(i).y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {	}

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(graphics.state == "RUNNING") {
            if(keyCode == KeyEvent.VK_W && player.getMove() != "DOWN") {
                player.up();
            }

            if(keyCode == KeyEvent.VK_S && player.getMove() != "UP") {
                player.down();
            }

            if(keyCode == KeyEvent.VK_A && player.getMove() != "RIGHT") {
                player.left();
            }

            if(keyCode == KeyEvent.VK_D && player.getMove() != "LEFT") {
                player.right();
            }
        }
        else {
            this.start();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {	}

    public Snake getPlayer() {
        return player;
    }

    public void setPlayer(Snake player) {
        this.player = player;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }

}
