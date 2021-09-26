package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class CoinMan extends ApplicationAdapter {
	SpriteBatch batch;
	Texture bg;
	Texture[] man;
	int manState=0;
	int pause=0;
	float gravity=0.5f;
	float velocity=0;
	int manY=0;
	int score=0;
	Texture over;
	ArrayList<Integer> coinX=new ArrayList<Integer>();
	ArrayList<Integer> coinY=new ArrayList<Integer>();
	ArrayList<Integer> bombX=new ArrayList<Integer>();
	ArrayList<Integer> bombY=new ArrayList<Integer>();
	ArrayList<Rectangle> coinRectangle=new ArrayList<Rectangle>();
	ArrayList<Rectangle> bombRectangle=new ArrayList<Rectangle>();
	Rectangle manRectangle;
	BitmapFont font;
	Texture coin;
	int gameState=0;
	Texture bomb;
	int coinCount=0;
	int bombCount=0;
	Random rand;

	public void makeCoin(){
		float height=rand.nextFloat()*Gdx.graphics.getHeight();
		coinY.add((int) height);
		coinX.add(Gdx.graphics.getWidth());
	}
	public void makeBomb(){
		float height=rand.nextFloat()*Gdx.graphics.getHeight();
		bombY.add((int) height);
		bombX.add(Gdx.graphics.getWidth());
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		bg=new Texture("bg2.png");
		man=new Texture[4];
		rand=new Random();
		coin=new Texture("coin.png");
		bomb=new Texture("bomb.png");
		font=new BitmapFont();
		over=new Texture("dizzy-1.png");
		man[0]=new Texture("frame-1.png");
		man[1]=new Texture("frame-2.png");
		man[2]=new Texture("frame-3.png");
		man[3]=new Texture("frame-4.png");
		manY=Gdx.graphics.getHeight()/2-man[manState].getHeight()/2;
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(bg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(gameState==1){
			if(coinCount<100){
				coinCount++;
			}else{
				coinCount=0;
				makeCoin();
			}
			if(bombCount<250){
				bombCount++;
			}else{
				bombCount=0;
				makeBomb();
			}
			coinRectangle.clear();
			for(int i=0;i<coinX.size();i++){
				batch.draw(coin,coinX.get(i),coinY.get(i));
				coinX.set(i,coinX.get(i)-8);
				coinRectangle.add(new Rectangle(coinX.get(i),coinY.get(i),coin.getWidth(),coin.getHeight()));
			}
			bombRectangle.clear();
			for(int i=0;i<bombX.size();i++){
				batch.draw(bomb,bombX.get(i),bombY.get(i));
				bombX.set(i,bombX.get(i)-16);
				bombRectangle.add(new Rectangle(bombX.get(i),bombY.get(i),bomb.getWidth(),bomb.getHeight()));
			}

			if(Gdx.input.justTouched()){
				velocity=10;
			}
			if(pause<8){
				pause++;
			}else {
				pause=0;
				if (manState < 3) {
					manState++;
				} else {
					manState = 0;
				}
			}
			velocity-=gravity;
			manY+=velocity;
			if(manY<=0){
				manY=0;
			}
			if(manY>=Gdx.graphics.getHeight()-200){
				manY=Gdx.graphics.getHeight()-198;
			}

		}else if (gameState==2){
			if(Gdx.input.justTouched()){
				gameState=1;
				manY=Gdx.graphics.getHeight()/2;
				score=0;
				velocity=0;
				coinX.clear();
				coinY.clear();
				coinRectangle.clear();
				coinCount=0;
				bombX.clear();
				bombY.clear();
				bombRectangle.clear();
				bombCount=0;
			}
		}else if (gameState==0){
			if (Gdx.input.justTouched()){
				gameState=1;
			}
		}



		if(gameState==2) {
			batch.draw(over,Gdx.graphics.getWidth() / 2 - 2 *over.getWidth(),manY,man[manState].getWidth() / 2, man[manState].getHeight() / 2);
		}else{
			batch.draw(man[manState], Gdx.graphics.getWidth() / 2 - 2 * man[manState].getWidth(), manY, man[manState].getWidth() / 2, man[manState].getHeight() / 2);
		}
		manRectangle=new Rectangle(Gdx.graphics.getWidth()/2-2*man[manState].getWidth(),manY,man[manState].getWidth()/2,man[manState].getHeight()/2);
		for(int i=0;i<coinRectangle.size();i++){
			if(Intersector.overlaps(manRectangle,coinRectangle.get(i))){
				score++;
				coinRectangle.remove(i);
				coinX.remove(i);
				coinY.remove(i);
				Gdx.app.log("score",String.valueOf(score));
				break;
			}
		}
		for(int i=0;i<bombRectangle.size();i++){
			if(Intersector.overlaps(manRectangle,bombRectangle.get(i))){
				bombRectangle.remove(i);
				bombX.remove(i);
				bombY.remove(i);
				gameState=2;
				break;
			}
		}
		font.setColor(Color.BLACK);
		font.getData().setScale(9);
		font.draw(batch,String.valueOf(score),80,250);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
