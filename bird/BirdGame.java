package com.hcs.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;
import java.util.zip.Adler32;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy;
	float enemyx[] = new float[4];
	float enemyy1[] = new float[4];
	float enemyy2[] = new float[4];
	float enemyy3[] = new float[4];
	float enemyy4[] = new float[4];
	float birdy;
	float birdx;
	int gamestate = 0;
	float velocity = 0.1f;
	float distance;
	Random random;
	Circle birdCircle;
	Circle[] enemyCircle1;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;
	Circle[] enemyCircle4;
	ShapeRenderer shapeRenderer;
	int score = 0;
	int scorenemy = 0;
	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy = new Texture("enemy.png");
		random = new Random();
		birdx = Gdx.graphics.getWidth() / 3;
		birdy = Gdx.graphics.getHeight() / 2;
		distance = Gdx.graphics.getWidth() / 4;
		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		enemyCircle1 = new Circle[4];
		enemyCircle2 = new Circle[4];
		enemyCircle3 = new Circle[4];
		enemyCircle4 = new Circle[4];
		font = new BitmapFont();
		font.setColor(Color.BLUE);
		font.getData().setScale(5);
		for (int i = 0; i < 4; i++){
			enemyx[i] = Gdx.graphics.getWidth() + distance * i;

			enemyy1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
			enemyy2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
			enemyy3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
			enemyy4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);

			enemyCircle1[i] = new Circle();
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();
			enemyCircle4[i] = new Circle();
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(bird, birdx , birdy, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
		birdCircle.set((birdx + (Gdx.graphics.getWidth() / 30)), (birdy + (Gdx.graphics.getHeight() / 20)), Gdx.graphics.getWidth() / 30);

		if (gamestate == 0){
			if (Gdx.input.isTouched())
				gamestate = 1;
		}//gamestate == 0 end

		else if(gamestate == 1){
			//set set geldikleri için, setlere sıra ile bakabilirim, aynı anda bakmama gerek yok
			if (enemyx[scorenemy] < birdx){
				score++;
				//3 deme nedenim benim 4 tane set kullanmam
				if (scorenemy == 3)
					scorenemy = 0;
				else
					scorenemy++;
			}
			//score yazdırmak
			font.draw(batch, String.valueOf(score), 100, 1000);

			//dokundukça yükselmesi için
			if (Gdx.input.isTouched()){
				velocity = -7;
				float total = hesap(velocity);
				if (birdy + total >= Gdx.graphics.getHeight() - (Gdx.graphics.getHeight() / 20)) {
					velocity = 0;
				}
			}

			for (int i = 0; i < 4; i++) {
				if (enemyx[i] < 0) {
					enemyx[i] = Gdx.graphics.getWidth();
					enemyy1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
					enemyy2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
					enemyy3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
					enemyy4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
				}
				batch.draw(enemy, enemyx[i], Gdx.graphics.getHeight() / 2 + enemyy1[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(enemy, enemyx[i], Gdx.graphics.getHeight() / 2 + enemyy2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(enemy, enemyx[i], Gdx.graphics.getHeight() / 2 + enemyy3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(enemy, enemyx[i], Gdx.graphics.getHeight() / 2 + enemyy4[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);

				enemyCircle1[i].set((enemyx[i] + (Gdx.graphics.getWidth() / 30)), (enemyy1[i] + Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 30 );
				enemyCircle2[i].set((enemyx[i] + (Gdx.graphics.getWidth() / 30)), (enemyy2[i] + Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 30 );
				enemyCircle3[i].set((enemyx[i] + (Gdx.graphics.getWidth() / 30)), (enemyy3[i] + Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 30 );
				enemyCircle4[i].set((enemyx[i] + (Gdx.graphics.getWidth() / 30)), (enemyy4[i] + Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() / 20), Gdx.graphics.getWidth() / 30 );

				enemyx[i] -= 4f;
			}
			if (birdy > 0){
				//daha hızlı düşmesi için velocity artıyor
				velocity += 0.15f;
				//birdy azalıyor
				birdy -= velocity;
			}
			//birdy>0 değilse yere çarpmıştır oyunu bitir.
			else
				gamestate = 2;
		}//gamestate == 1 end

		else if (gamestate == 2) {
			font.getData().setScale(10);
			font.draw(batch, "Game Over", 500, 1000);
			//yeniden başlatmak için alttakilerde, ilk tanımlamalar
			if (Gdx.input.isTouched())
				gamestate = 1;

			birdy = Gdx.graphics.getHeight() / 2;
			velocity = 0;
			scorenemy = 0;
			score = 0;
			font.getData().setScale(5);
			for (int i = 0; i < 4; i++){
				enemyx[i] = Gdx.graphics.getWidth() + distance * i;

				enemyy1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
				enemyy2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
				enemyy3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);
				enemyy4[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 100);

				enemyCircle1[i] = new Circle();
				enemyCircle2[i] = new Circle();
				enemyCircle3[i] = new Circle();
				enemyCircle4[i] = new Circle();
			}
		}//gamestate == 2 end

		batch.end();
		/* //birdcircle'ın doğru konumlandığını kontrol etmek için
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		shapeRenderer.end();
		*/
		//birdcircle ile enemycircle'lar arasında çarpışma kontrolü
		for(int i = 0; i < 4; i++){
			if (Intersector.overlaps(birdCircle, enemyCircle1[i])
			 || Intersector.overlaps(birdCircle, enemyCircle2[i])
			 || Intersector.overlaps(birdCircle, enemyCircle3[i])
			 || Intersector.overlaps(birdCircle, enemyCircle4[i])){
				gamestate = 2;
			}

			/* //enemycircle'ların doğru konumlandığını kontrol etmek için
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(Color.BLACK);
			shapeRenderer.circle(enemyCircle1[i].x, enemyCircle1[i].y, enemyCircle1[i].radius);
			shapeRenderer.circle(enemyCircle2[i].x, enemyCircle2[i].y, enemyCircle2[i].radius);
			shapeRenderer.circle(enemyCircle3[i].x, enemyCircle3[i].y, enemyCircle3[i].radius);
			shapeRenderer.circle(enemyCircle4[i].x, enemyCircle4[i].y, enemyCircle4[i].radius);
			shapeRenderer.end();*/
		}
	}
	
	@Override
	public void dispose () {

	}
	//velocity ile ne kadar yukarı çıkacağını hesaplamak için
	public float hesap(float a){
		float total = 0;
		for (float i = 0; i <= a; i += 0.2f){
			total += i;
		}
		return total;
	}
}
