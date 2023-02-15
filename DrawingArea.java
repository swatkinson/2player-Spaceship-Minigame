
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.AWTEvent;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger; //initializing libraries

/**
 * @author Spencer Watkinson ICS3U - Slings and Arrows Game 2 player game where
 * you shoot the 2 types of projectiles at your opponent until you destroy their
 * ship.
 */
public class DrawingArea extends javax.swing.JPanel {

    //importing all used images
    Image leftShip = Toolkit.getDefaultToolkit().getImage("LeftShip.png");
    Image rightShip = Toolkit.getDefaultToolkit().getImage("RightShip.png");
    Image bg = Toolkit.getDefaultToolkit().getImage("Background.jpg");
    Image ionBeam = Toolkit.getDefaultToolkit().getImage("Beam.png");
    Image missile = Toolkit.getDefaultToolkit().getImage("Missile.png");

    Timer t1;
    boolean[] Button = new boolean[9];
    int curKey;
    int leftShipY = 330;
    int rightShipY = 330;
    boolean leftRockFlying = false;
    boolean rightRockFlying = false;
    boolean leftArrowFlying = false;
    boolean rightArrowFlying = false;
    int missileLX = 50;
    int missileLY;
    int missileRX = 950;
    int missileRY;
    int ionBeamLX = 210;
    int ionBeamLY;
    int ionBeamRX = 790;
    int ionBeamRY;
    int missileBaseL;
    int missileBaseR;
    int leftShipDir = -1;
    int rightShipDir = -1;
    int leftMissileDir;
    int rightMissileDir;
    int leftScore;
    int rightScore;
    boolean robotMode = false;

    String Miss = "Miss.wav"; //sound input
    String Hit = "Missilehit.wav";
    SoundPlayer theSound = new SoundPlayer(); //sound player startup

    public DrawingArea() {
        initComponents();
        setFocusable(true);

        addKeyListener(new DrawingArea.AL());
        t1 = new Timer(10, new DrawingArea.TimerListener()); //add a new timer
        t1.start();
        this.enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_EVENT_MASK | AWTEvent.KEY_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK | AWTEvent.COMPONENT_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);
    }

    public class AL extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) { //set positions in boolean array to true based on key inputs
            curKey = e.getKeyCode();
            if (curKey == e.VK_A) {
                Button[0] = true;
            } else {
                Button[0] = false;
            }
            if (curKey == e.VK_D) {
                Button[1] = true;
            } else {
                Button[1] = false;
            }
            if (curKey == e.VK_W) {
                Button[2] = true;
                leftShipDir = -1;
            } else {
                Button[2] = false;
            }
            if (curKey == e.VK_S) {
                Button[3] = true;
                leftShipDir = 1;
            } else {
                Button[3] = false;
            }
            if (curKey == e.VK_LEFT) {
                Button[4] = true;
            } else {
                Button[4] = false;
            }
            if (curKey == e.VK_RIGHT) {
                Button[5] = true;
            } else {
                Button[5] = false;
            }
            if (curKey == e.VK_UP) {
                Button[6] = true;
                rightShipDir = -1;
            } else {
                Button[6] = false;
            }
            if (curKey == e.VK_DOWN) {
                Button[7] = true;
                rightShipDir = 1;
            } else {
                Button[7] = false;
            }
            if (curKey == e.VK_F) {
                Button[8] = true;
            } else {
                Button[8] = false;
            }
        }

        public void keyReleased(KeyEvent e) { //set positions in boolean array to false based on key inputs
            if (curKey == e.VK_A) {
                Button[0] = false;
            }
            if (curKey == e.VK_D) {
                Button[1] = false;
            }
            if (curKey == e.VK_W) {
                Button[2] = false;
            }
            if (curKey == e.VK_S) {
                Button[3] = false;
            }
            if (curKey == e.VK_LEFT) {
                Button[4] = false;
            }
            if (curKey == e.VK_RIGHT) {
                Button[5] = false;
            }
            if (curKey == e.VK_UP) {
                Button[6] = false;
            }
            if (curKey == e.VK_DOWN) {
                Button[7] = false;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bg, 0, -200, 1000, 1000, this); //draw in the background with new scale
        g.drawImage(leftShip, 20, leftShipY, 200, 70, this); //draw in left ship
        g.drawImage(rightShip, 770, rightShipY, 200, 70, this); //draw in right ship

        g.setColor(Color.white);
        g.fillRect(350, 0, 300, 80); //creating the scoreboard rectangle
        g.setColor(Color.black);
        Font f = new Font("Tenorite", Font.PLAIN, 30); // setting the font
        g.setFont(f);
        g.drawString("scoreboard:", 420, 30);
        g.setColor(Color.red);
        g.drawString("" + leftScore, 380, 70); //displaying left ships score
        g.setColor(Color.blue);
        g.drawString("" + rightScore, 600, 70); //displaying right ships score
        g.setColor(Color.white);
        g.drawString("Press F to enable/", 700, 50);
        g.drawString("disable robot mode", 700, 80);

        if (rightScore >= 5) { //if right won
            g.fillRect(200, 200, 600, 400); //displays right players win box
            g.setColor(Color.blue);
            g.drawString("Right side wins!!", 400, 400);
            try {
                TimeUnit.SECONDS.sleep(1); //pauses the game for 1 second so it doesnt seem jumpy
            } catch (InterruptedException ex) {
                Logger.getLogger(DrawingArea.class.getName()).log(Level.SEVERE, null, ex);
            }
            jDialog2.setVisible(true); //displays the play again dialog box
        }
        if (leftScore >= 5) { //if left won
            g.fillRect(200, 200, 600, 400); //displays left players win box
            g.setColor(Color.red);
            g.drawString("Left side wins!!", 400, 400);
            try {
                TimeUnit.SECONDS.sleep(1); //pauses game for 1 second
            } catch (InterruptedException ex) {
                Logger.getLogger(DrawingArea.class.getName()).log(Level.SEVERE, null, ex);
            }
            jDialog2.setVisible(true); //displays the play again dialog box
        }

        if(Button[8] == true && robotMode == false){ //Toggle robot mode ON if its not already
            robotMode = true;
            Button[8] = false; //turn off the F key input
        }else if(Button[8] == true){ //Toggle robot mode OFF if its on already
           robotMode = false;
            Button[8] = false;//turn off the F key input
        }
        
        if (robotMode == true) { //if robot mode is turned on
            Button[4] = true; //constantly fire rock
            Button[5] = true; //constantly fire arrow
            if (leftRockFlying == false && leftArrowFlying == false) { //if the player hasnt shot a projectile
                if (leftShipY > rightShipY) { //move down if the player is lower than the robot
                    rightShipY++; //movement speed set to 1 so it trails behind the player
                    rightShipDir = 1; //change the missile direction
                }
                if (leftShipY < rightShipY) { //move up if the playe is above you
                    rightShipY--;
                    rightShipDir = -1; //change missile direction
                }
            } else if (rightShipY > 0 && rightShipY < 690) { //if the player has shot, and the robotship isnt outside of the boundaries
                rightShipY = rightShipY + 2 * rightShipDir; //move in the last direction moved at double speed to try to avoid shots
            }

        }

        // ================ MOVING THE SHIPS ==================
        if (Button[2] == true && leftShipY > 0) { //if up arrow is pressed
            leftShipY = leftShipY - 4;// move ship by 4
        }
        if (Button[3] == true && leftShipY < 690) { //moves left ship down (and not off screen)
            leftShipY = leftShipY + 4;
        }
        if (Button[6] == true && rightShipY > 0) { //moves right ship up (and not off screen)
            rightShipY = rightShipY - 4;
        }
        if (Button[7] == true && rightShipY < 690) { //moves right ship down (and not off screen)
            rightShipY = rightShipY + 4;
        }

        // ========= DRAWING AND MOVING THE PROJECTILES =================
        //Rocks:
        //shooting of left beam
        if (leftRockFlying == true) {
            g.drawImage(ionBeam, ionBeamLX, ionBeamLY, 20, 20, this); //draw the ion beam
            ionBeamLX = ionBeamLX + 8; //add 8 to the ion beam's x coordinate
        }
        //miss detection
        if (ionBeamLX > 1000) { //if ion beam is passed the screen
            leftRockFlying = false; //remove projectile from screen
            theSound.play(Miss); //play miss sound
            ionBeamLX = 210; //resets the ion beam's x position for next shot
        }
        //hit detection
        if (ionBeamLX > 770 && ionBeamLY > rightShipY && ionBeamLY < rightShipY + 70) { //if ion beam is inside of the other ship
            leftScore++; //add score
            theSound.play(Hit); //play hit sound
            ionBeamLX = 210; //reset position of beam
            leftRockFlying = false; //remove beam from screen
        }
        //shooting of right beam
        if (rightRockFlying == true) {
            g.drawImage(ionBeam, ionBeamRX, ionBeamRY, -20, 20, this); //draws ion beam but backwards (-20 x scale)
            ionBeamRX = ionBeamRX - 8; //moves ion beam left by 8
        }
        //miss detection
        if (ionBeamRX < 0) { //if ion beam is passed the screen
            rightRockFlying = false;
            theSound.play(Miss); //play miss sound
            ionBeamRX = 790; //resets the ion beam's x position for next shot
        }
        //hit detection
        if (ionBeamRX < 220 && ionBeamRY > leftShipY && ionBeamRY < leftShipY + 70) { //if ion beam is inside of the other ship
            rightScore++; //add score
            theSound.play(Hit); //play hit sound
            ionBeamRX = 790; //reset beam position
            rightRockFlying = false; //remove beam from screen
        }

        //Arrows:
        //shooting the left missile
        if (leftArrowFlying == true) {
            g.drawImage(missile, missileLX, missileLY, 60, 30, this); //draw the missile
            missileLY = missileLY + 3 * leftMissileDir; //move the missile in the last direction 3 per tick
            missileBaseL = missileBaseL + 1; //increase the x value in the y= ax^2 by 1 per tick
            missileLX = (int) (missileLX + 0.005 * Math.pow(missileBaseL, 2)); //calculate the missiles X coordinate based on y = ax^2
        }
        //miss detection
        if (missileLX > 1000) { //if the missle has missed
            leftArrowFlying = false;
            theSound.play(Miss); //play miss sound
            missileLX = 50; //reset missile back to original x value
        }
        //hit detection
        if (missileLX > 770 && missileLY > rightShipY && missileLY < rightShipY + 70) { //if missile is inside of the other ship
            leftScore++; //add score
            theSound.play(Hit); //play hit sound
            missileLX = 50; //return missile to original position
            leftArrowFlying = false; //remove missle from screen
        }
        //shooting of the right missile
        if (rightArrowFlying == true) {
            g.drawImage(missile, missileRX, missileRY, -60, 30, this); //draw the missile
            missileRY = missileRY + 3 * rightMissileDir;//move the missile in the last direction 3 per tick
            missileBaseR = missileBaseR + 1;//increase the x value in the y= ax^2 by 1 per tick
            missileRX = (int) (missileRX + 0.005 * -Math.pow(missileBaseR, 2));//calculate the missiles X coordinate based on y = ax^2
        }
        //miss detection
        if (missileRX < 0) { //if the missle has missed
            rightArrowFlying = false; //remove missile from screen
            theSound.play(Miss); //play miss sound
            missileRX = 950; //reset missile back to original x value
        }
        //hit detection
        if (missileRX < 220 && missileRY > leftShipY && missileRY < leftShipY + 70) { //if ion beam is inside of the other ship
            rightScore++; //increases score
            theSound.play(Hit); //plays hit sound
            missileRX = 950; //reset missile to original position
            rightArrowFlying = false; //remove missile from screen
        }
    }

    private class TimerListener implements ActionListener {

        @Override

        public void actionPerformed(ActionEvent ae) {
            //=======Projectile Shot Detection =======
            //Rocks:
            if (Button[1] == true && leftRockFlying == false) { //makes sure button is pressed and shot isnt already happening
                leftRockFlying = true; //starts action
                ionBeamLY = leftShipY + 30; //sets the y value of the rock to the y value of the ship (and a little bit down)
            }
            if (Button[4] == true && rightRockFlying == false) {
                rightRockFlying = true; //starts action
                ionBeamRY = rightShipY + 30; //sets the y value of the rock to the y value of the ship (and a little bit down)
            }
            //Arrows:
            if (Button[0] == true && leftArrowFlying == false) {
                leftArrowFlying = true; //starts the action
                missileLY = leftShipY + 30; //sets the y value of the missile to the y value of the ship
                missileBaseL = 0; //sets the x value to 0 in the y=ax^2 equation, so it can be scaled up
                leftMissileDir = leftShipDir; //sets the direction that the ship last travelled, up or down
            }
            if (Button[5] == true && rightArrowFlying == false) {
                rightArrowFlying = true; //starts the action
                missileRY = rightShipY + 30; //sets y value of ship to y value of missile
                missileBaseR = 0; //sets new proxy missile x value for the quadratic equation
                rightMissileDir = rightShipDir; //sets the last ship direction
            }
            repaint(); //refreshes the screen
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog2 = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jDialog2.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog2.setMinimumSize(new java.awt.Dimension(400, 300));
        jDialog2.setModal(true);
        jDialog2.setPreferredSize(new java.awt.Dimension(800, 400));

        jButton1.setText("Exit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Play again");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Unispace", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(200, 0, 0));
        jLabel1.setText("Good job!");

        jLabel2.setFont(new java.awt.Font("Unispace", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(200, 0, 0));
        jLabel2.setText("Would you like to play again?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(28, 28, 28))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(101, 101, 101)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(442, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //===== play again ====
        rightScore = 0; //resets the game scores
        leftScore = 0;
        missileLY = -20; //moves all the projectiles off screen to clear old projectiles from previous game
        missileRY = -20;
        ionBeamLY = -20;
        ionBeamRY = -20;

        jDialog2.setVisible(false); //removes the end dialog box
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.exit(0); //end game exit button
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
