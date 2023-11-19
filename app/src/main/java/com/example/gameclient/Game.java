package com.example.gameclient;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;


public class Game extends View {

    List cac_list;
    List shot_list;
    Context context;
    Double factor_x;
    Double factor_y;
    //String name;
    //String direction;
    //int x;
    //int y;
    Bitmap bitmap;
    int pic;
    public boolean draw;
    public boolean cont;
    public String amount;
    public String score;
    public Game(Context context)
    {
        super(context);
        this.context = context;
        this.cont = true;
        this.draw = false;
        cac_list = new ArrayList();
        shot_list = new ArrayList();







    }
    /*public void SetSprite(String sprite)
    {
        boolean added = false;
        if (!sprite.split("_")[1].equals("FIRE")) {
            for (int i = 0; i < cac.size() && !added; i++) {
                if (sprite_list.get(i).toString().split("_")[5].equals(sprite.split("_")[5])) {
                    sprite_list.set(i, sprite);
                    added = true;
                }
                //if the sprite is fire remove it!!!!!!
            }
        }
        if (!added)
            sprite_list.add(sprite);
        this.draw = true;

    }*/

    public void SetFactors(Double x, Double y)
    {
        factor_x = x;
        factor_y = y;
    }
    public void SetCharacter(String sprite)
    {
        boolean added = false;
        for (int i = 0; i < cac_list.size() && !added; i++) {
            if (cac_list.get(i).toString().split("_")[6].equals(sprite.split("_")[6])) {
                cac_list.set(i, sprite);
                added = true;
            }

        }
        if (!added)
            cac_list.add(sprite);
        this.draw = true;

    }

    public void SetShot(String sprite)
    {

        shot_list.add(sprite);

    }

    public void RemoveSprite(String number)
    {
        boolean removed = false;
        for (int i = 0; i < cac_list.size() && !removed; i++) {
            if (cac_list.get(i).toString().split("_")[6].equals(number)) {
                cac_list.remove(i);
                removed = true;
            }
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.draw)
        {
            Paint paint = new Paint();
            paint.setTextSize((int)(50*factor_y));
            paint.setColor(Color.BLACK);
            canvas.drawText("Remaining Players: " + amount, 0, 50, paint);
            canvas.drawText("Score: " + score, 0, 100, paint);

            for(int i=0; i<shot_list.size(); i++) {
                String sprite = shot_list.get(i).toString();
                String name = sprite.split("_")[1];
                String direction = sprite.split("_")[2];
                int x = Integer.parseInt(sprite.split("_")[3]);
                int y = Integer.parseInt(sprite.split("_")[4]);
                if (name.equals("FIRE")) {
                    if (direction.equals("RIGHT"))
                        pic = R.drawable.fire_right;
                    else if (direction.equals("LEFT"))
                        pic = R.drawable.fire_left;
                    else if (direction.equals("UP"))
                        pic = R.drawable.fire_up;
                    else if (direction.equals("DOWN"))
                        pic = R.drawable.fire_down;
                }
                else if (name.equals("SKULL")) {
                    if (direction.equals("RIGHT"))
                        pic = R.drawable.skull_right;
                    else if (direction.equals("LEFT"))
                        pic = R.drawable.skull_left;
                    else if (direction.equals("UP"))
                        pic = R.drawable.skull_up;
                    else if (direction.equals("DOWN"))
                        pic = R.drawable.skull_down;
                }
                else if (name.equals("BLUE-FIRE"))
                    pic = R.drawable.blue_fire;
                else if (name.equals("POKEBALL"))
                    pic = R.drawable.pokeball;
                else if (name.equals("MASTERBALL"))
                    pic = R.drawable.masterball;
                else if (name.equals("BONE"))
                    pic = R.drawable.bone;
                bitmap = BitmapFactory.decodeResource(getResources(), pic);
                Rect source = new Rect (0,0,bitmap.getWidth(),bitmap.getHeight());
                Rect target = new Rect (x,y,(int)(x+bitmap.getWidth()*factor_x),(int)(y+bitmap.getHeight()*factor_y));
                canvas.drawBitmap(bitmap, source, target, null);
            }
            shot_list.clear();

            for(int i=0; i<cac_list.size(); i++) {

                String sprite = cac_list.get(i).toString();
                String name = sprite.split("_")[1];
                String direction = sprite.split("_")[2];
                int x = Integer.parseInt(sprite.split("_")[3]);
                int y = Integer.parseInt(sprite.split("_")[4]);
                int hp = Integer.parseInt(sprite.split("_")[5]);
                String user = sprite.split("_")[7];
                boolean moving = false;
                if (sprite.split("_")[8].equals("True"))
                    moving = true;
                int move_number = Integer.parseInt(sprite.split("_")[9]);
                if (name.equals("BLUE"))
                {
                    if (hp ==0)
                        pic = R.drawable.blue_melt;
                    else if (direction.equals("RIGHT"))
                        pic = R.drawable.blue_right;
                    else if (direction.equals("LEFT"))
                        pic = R.drawable.blue_left;
                    else if (direction.equals("UP"))
                        pic = R.drawable.blue_up;
                    else if (direction.equals("DOWN"))
                        pic = R.drawable.blue;
                }
                else if (name.equals("KID"))
                {
                    if (direction.equals("RIGHT"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.kid_right_step1;
                            else if (move_number == 2)
                                pic = R.drawable.kid_right_step2;
                            if (move_number == 3)
                                pic = R.drawable.kid_right_step3;
                            if (move_number == 4)
                                pic = R.drawable.kid_right_step4;
                        }
                        else
                            pic = R.drawable.kid_right;
                    }
                    else if (direction.equals("LEFT"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.kid_left_step1;
                            else if (move_number == 2)
                                pic = R.drawable.kid_left_step2;
                            if (move_number == 3)
                                pic = R.drawable.kid_left_step3;
                            if (move_number == 4)
                                pic = R.drawable.kid_left_step4;
                        }
                        else
                            pic = R.drawable.kid_left;
                    }
                    else if (direction.equals("UP"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.kid_up_step1;
                            else if (move_number == 2)
                                pic = R.drawable.kid_up_step2;
                            if (move_number == 3)
                                pic = R.drawable.kid_up_step3;
                            if (move_number == 4)
                                pic = R.drawable.kid_up_step4;
                        }
                        else
                            pic = R.drawable.kid_up;
                    }
                    else if (direction.equals("DOWN"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.kid_step1;
                            else if (move_number == 2)
                                pic = R.drawable.kid_step2;
                            if (move_number == 3)
                                pic = R.drawable.kid_step3;
                            if (move_number == 4)
                                pic = R.drawable.kid_step4;
                        }
                        else
                            pic = R.drawable.kid;
                    }

                }
                else if (name.equals("SKELETON"))
                {
                    if (direction.equals("RIGHT"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.skeleton_right_step1;
                            else if (move_number == 2)
                                pic = R.drawable.skeleton_right_step2;
                            if (move_number == 3)
                                pic = R.drawable.skeleton_right_step3;
                            if (move_number == 4)
                                pic = R.drawable.skeleton_right_step4;
                        }
                        else
                            pic = R.drawable.skeleton_right;
                    }
                    else if (direction.equals("LEFT"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.skeleton_left_step1;
                            else if (move_number == 2)
                                pic = R.drawable.skeleton_left_step2;
                            if (move_number == 3)
                                pic = R.drawable.skeleton_left_step3;
                            if (move_number == 4)
                                pic = R.drawable.skeleton_left_step4;
                        }
                        else
                            pic = R.drawable.skeleton_left;
                    }
                    else if (direction.equals("UP"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.skeleton_up_step1;
                            else if (move_number == 2)
                                pic = R.drawable.skeleton_up_step2;
                            if (move_number == 3)
                                pic = R.drawable.skeleton_up_step3;
                            if (move_number == 4)
                                pic = R.drawable.skeleton_up_step4;
                        }
                        else
                            pic = R.drawable.skeleton_up;
                    }
                    else if (direction.equals("DOWN"))
                    {
                        if (moving)
                        {
                            if (move_number == 1)
                                pic = R.drawable.skeleton_step1;
                            else if (move_number == 2)
                                pic = R.drawable.skeleton_step2;
                            if (move_number == 3)
                                pic = R.drawable.skeleton_step3;
                            if (move_number == 4)
                                pic = R.drawable.skeleton_step4;
                        }
                        else
                            pic = R.drawable.skeleton;
                    }

                }
                bitmap = BitmapFactory.decodeResource(getResources(), pic);
                Rect source = new Rect (0,0,bitmap.getWidth(),bitmap.getHeight());
                Rect target = new Rect (x,y,(int)(x+bitmap.getWidth()*factor_x),(int)(y+bitmap.getHeight()*factor_y));
                canvas.drawBitmap(bitmap, source, target, null);
                paint.setTextSize((int)(50*factor_y));
                paint.setColor(Color.BLACK);
                canvas.drawText("HP: " + Integer.toString(hp), x, (int)(y-5*factor_y), paint);
                canvas.drawText(user, x, (int)(y+(bitmap.getHeight()+10)*factor_y), paint);



            }

        }
        if (cont)
            invalidate();
    }

}
