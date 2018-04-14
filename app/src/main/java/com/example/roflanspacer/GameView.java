package com.example.roflanspacer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import static com.example.roflanspacer.MainActivity.textView;

/**
 * Created by Никита on 10.04.2018.
 */

public class GameView extends SurfaceView implements Runnable {
    Context context;
    public  boolean f=true;

    public static int maxX = 20;
    public static int maxY = 28;
    public static float unitW = 0;
    public static float unitH = 0;
    private boolean firstTime = true;
    private boolean gameRunning = true;
    private Ship ship;
    private Asteroid asteroid;

    private Thread gameThread = null;
    private Paint paint;
  //  private Bullet bullet;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    public int count=0;
    private final Handler handler;
    Random random = new Random();
    private Bitmap bitmap;
    Resources resources;
    Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.image);
    Paint p;




    public GameView(Context context) {
        super(context);


        surfaceHolder = getHolder();
        paint = new Paint();
        gameThread = new Thread(this);
        gameThread.start();
        handler = new Handler(context.getMainLooper());
            this.context=context;

    }
    private void Start(){
        runOnUiThread(new Runnable() {

        @Override
        public void run() {
            textView.setText("Монеты : "+count+"           "+"Жизни : 100");
        }
    });
    }


        @Override
        public void run() throws NullPointerException {
        Start();


            while (gameRunning) {
                update();
                try {
                    draw();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                checkCollision();
                try {
                    checkIfNewAsteroid();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                control();
                try {
                    checkIfNewMoney();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                checkCollisionMoney();
                try {
                    checkIfNewBoom();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                checkCollisionBoom();
               // checkCollisionBullet();
               // checkIfNewBullet();




            }
            while (!gameRunning){
                upDEAD();
              // context.stopService(new Intent(context,Music.class));
                try {
                    draw();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (life==0){
                    Bitmap b1=BitmapFactory.decodeResource(getResources(), R.drawable.ship2);
                    ship.bitmap=Bitmap.createScaledBitmap(
                            b1, (int)(3 * GameView.unitW), (int)(3 * GameView.unitH), false);
                    ship.drow(paint,canvas);

                }
            }
        }





    private void update() {
        if(!firstTime) {
            ship.update();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
            for (Asteroid spaceBody : asteroids) {
                spaceBody.update();
            }
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
            for (Money money: moneys){
                money.update();
          }
                }
            });
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
            for(Bboom bboom : booms){
              bboom.update();
            }
                }
            });
       //  runOnUiThread(new Runnable() {
       //      @Override
       //      public void run() {
       //          for (Bullet bullet: bullets) {
       //              bullet.update();}

       //  }
       //;


        }
    }
    private void upDEAD(){
        if(!firstTime){
            for (Asteroid asteroid : asteroids) {
                asteroid.update();
            }
            for (Money money: moneys){
                money.update();
         }
            for(Bboom bboom : booms){
                bboom.update();
            }


        }


    }

    private void draw() throws FileNotFoundException {
        if (surfaceHolder.getSurface().isValid()) {

            if(firstTime){
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX;
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

                ship = new Ship(getContext());
            }

            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(b, 0, 0, p);
            ship.drow(paint, canvas);


          //  bullet.drow(paint, canvas);
            for(SpaceBody spaceBody : asteroids){
                spaceBody.drow(paint, canvas);
            }
            for (Money money: moneys){
                money.drow(paint,canvas);
            }
            for(Bboom bboom : booms){
                bboom.drow(paint,canvas);
            }
         //  for (Bullet bullet: bullets) {
         //      bullet.drow(paint,canvas);}


            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }


    private void control() {
        try {
            gameThread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private int currentTime=0;
    private int life=100;
    private final int ASTEROID_INTERVAL = 30;
    private void checkCollision() {
        for (final Asteroid asteroid : asteroids) {
            if (asteroid.isCollision(ship.x, ship.y, ship.size)) {
                life=life-20+random.nextInt(20);{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Монеты : " + count + "           " + "Жизни : " + life);
                            asteroids.remove(asteroid);
                        }
                    });
                }
                if(life==0 || life<0){
                    if (count>15){
                    life=50;
                        count-=15;}
                        if (count<15){
                        life=0;
                    gameRunning=false;
                    }}}


            }
        }

    private void checkIfNewAsteroid() throws FileNotFoundException {
        if(currentTime >= ASTEROID_INTERVAL){
            Asteroid asteroid = new Asteroid(getContext());
            asteroids.add(asteroid);
            currentTime = 0;
        }else{
            currentTime ++;
            currentTime++;
        }
    }


    private ArrayList<Money> moneys= new ArrayList<>();

    private final int Money_interval=60;
    private int currentTime1 = 0;
    private void checkCollisionMoney(){
        for (final Money money : moneys) {
            if(money.isCollisionMoney(ship.x, ship.y, ship.size)){
                count++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moneys.remove(money);
                        textView.setText("Монеты : "+count+"           "+"Жизни : "+life);
                    }
                });
            }
            }
        }

    private void checkIfNewMoney() throws FileNotFoundException {
        if(currentTime1 >=  Money_interval){
            Money money = new Money(getContext());
            moneys.add(money);
            currentTime1 = 0;
        }else{
            currentTime1 ++;
        }
    }

    private ArrayList< Bboom> booms= new ArrayList<>();

    private final int boom_interval=300;
    private int currentTime2 = 0;
    private void checkCollisionBoom(){

        for (final Bboom boom : booms) {
            if(boom.isCollisionBboom(ship.x, ship.y, ship.size)){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        booms.remove(boom);
                        asteroids.clear();


                    }
                });
            }
        }
    }

    private void checkIfNewBoom() throws FileNotFoundException {
        if(currentTime2 >= boom_interval){
            Bboom boom = new Bboom(getContext());
            booms.add(boom);
            currentTime2 = 0;
        }else{
            currentTime2 ++;
        }
    }
  //  private ArrayList<Bullet> bullets= new ArrayList<>();
//
  //  private final int bulletInterval=1;
  //  private int currentTime3 = 0;
  //  private int piy=0;
  // private void checkCollisionBullet() {

  //     for (final Bullet bullet : bullets) {
  //         if (bullet.isCollisionBullet(asteroid.x, asteroid.y, asteroid.size)) {
  //             piy++;
  //             if (piy > 5) {
  //                 runOnUiThread(new Runnable() {
  //                     @Override
  //                     public void run() {
  //                         asteroids.remove(asteroid);
  //                         piy=0;


  //                     }
  //                 });
  //             }
  //         }
  //     }
  //// }
  //  private void checkIfNewBullet(){
  //      if(currentTime3 >= bulletInterval){
  //          Bullet bullet = new Bullet(getContext());
  //          bullets.add(bullet);
  //          currentTime3 = 0;
  //      }else{
  //          currentTime3 ++;
  //      }
  //  }


    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }


}
