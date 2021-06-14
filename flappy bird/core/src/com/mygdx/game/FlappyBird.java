package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.ArrayList;
import java.util.Random;

import sun.rmi.runtime.Log;

public class FlappyBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture bg;
	Texture[] bird;
	float gravity;
	float velocity=0;
	int birdState=0;
	int pause=0;
	int birdY;
	Texture topTube;
	int gap=500;
	Texture bottomTube;
	Random rand;
	float[] tubeOff=new float[3];
	float maxOff;
	float[] tubeX= new float[3];
	float distance;
	int score=0;
	Circle birdCircle;
	int gameState=2;
	Texture gameOver;
	Stage stage;
	TextButton button;
	TextButton.TextButtonStyle textButtonStyle;

	Skin skin;
	TextureAtlas buttonAtlas;



	BitmapFont font;
	BitmapFont font2;
	ArrayList<Rectangle> tubeTopRectangle;
	ArrayList<Rectangle> tubeBottomRectangle;
	public void againScreen()
	{
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		font2 = new BitmapFont();
		skin = new Skin();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		font = new BitmapFont();
		skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
		skin.addRegions(buttonAtlas);
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.font = font;
		textButtonStyle.up = skin.getDrawable("up-button");
		textButtonStyle.down = skin.getDrawable("down-button");
		textButtonStyle.checked = skin.getDrawable("checked-button");
		button = new TextButton("Button1", textButtonStyle);
		stage.addActor(button);
		score=0;
		gameState=1;
		velocity=0;
		Play();
	}


	@Override
	public void create () {

		batch = new SpriteBatch();
		distance=Gdx.graphics.getWidth()*2/3;
		font=new BitmapFont();
		gameOver=new Texture("gameOver.png");
		font.setColor(Color.WHITE);
		font.getData().setScale(7,7);
		bg=new Texture("bg.png");
		bird=new Texture[2];
		bird[0]=new Texture("bird.png");
		bird[1]=new Texture("bird2.png");
		topTube=new Texture("toptube.png");
		bottomTube=new Texture("bottomtube.png");
		rand=new Random();
		maxOff=Gdx.graphics.getHeight()/2 - gap/2 -100;
		Play();


	}
	public void Play(){
		birdY=Gdx.graphics.getHeight()/2;
		for(int i=0;i<3;i++){
			tubeOff[i]=(rand.nextFloat()-0.5f) * (Gdx.graphics.getHeight()-gap-200);
			tubeX[i]=Gdx.graphics.getWidth()+i*distance;
			tubeTopRectangle=new ArrayList<Rectangle>();
			tubeBottomRectangle=new ArrayList<Rectangle>();
			gravity=2;
		}
	}
	@Override
	public void render () {
		batch.begin();
		batch.draw(bg,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState==1){
			if(pause<8){
				pause++;
			}else{
				pause=0;
				if(birdState<1){
					birdState++;
				}else{
					birdState=0;
				}
			}
			if(Gdx.input.justTouched()){
				velocity=30;
			}

			velocity=velocity-gravity;
			birdY+=velocity;
			if(birdY<=0){
				birdY=0;
			}else if(birdY>=Gdx.graphics.getHeight()-bird[0].getHeight()){
				birdY=Gdx.graphics.getHeight()-bird[0].getHeight();
			}

			for(int i=0;i<3;i++){
				if(tubeX[i]<=0-topTube.getWidth()){
					tubeX[i]+=3*distance;
				}else {
					tubeX[i] -= 4;
					if(tubeX[i]==Gdx.graphics.getWidth()/2-(bird[birdState].getWidth()/2)-200){
						score++;
						Gdx.app.log("score",String.valueOf(score));
					}
				}
				batch.draw(topTube,tubeX[i],Gdx.graphics.getHeight()/2+gap/2+tubeOff[i],topTube.getWidth(),Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/2+gap/2+tubeOff[i]));
				batch.draw(bottomTube, tubeX[i],0,bottomTube.getWidth(),Gdx.graphics.getHeight()/2+gap/2+tubeOff[i]-gap);
				tubeTopRectangle.add(new Rectangle(tubeX[i],Gdx.graphics.getHeight()/2+gap/2+tubeOff[i],topTube.getWidth(),Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()/2+gap/2+tubeOff[i])));
				tubeBottomRectangle.add(new Rectangle(tubeX[i],0,topTube.getWidth(),Gdx.graphics.getHeight()/2+gap/2+tubeOff[i]-gap));

				if (tubeX[i]<=Gdx.graphics.getWidth()/2-(bird[birdState].getWidth()/2)-200-topTube.getWidth()){
					tubeTopRectangle.clear();
					tubeBottomRectangle.clear();
				}

			}



		}else if(gameState==0){
			birdY=0;
			batch.draw(gameOver,Gdx.graphics.getWidth()/2-gameOver.getWidth()/2-200,Gdx.graphics.getHeight()/2+350,gameOver.getWidth()+400,gameOver.getHeight()+200);
			againScreen();
		}else if (gameState==2){
			if(Gdx.input.justTouched())
			gameState=1;

		}
		batch.draw(bird[birdState],Gdx.graphics.getWidth()/2-(bird[birdState].getWidth()/2)-200,birdY);
		font.draw(batch,String.valueOf(score),100,200);
		birdCircle=new Circle(Gdx.graphics.getWidth()/2-200,birdY+bird[birdState].getHeight()/2,bird[birdState].getWidth()/2	);

		batch.end();
		for(int j=0;j<tubeTopRectangle.size();j++)
			if(Intersector.overlaps(birdCircle,tubeTopRectangle.get(j)) || Intersector.overlaps(birdCircle,tubeBottomRectangle.get(j))){
				gameState=0;

			}



	}

	@Override
	public void dispose () {
	}
}