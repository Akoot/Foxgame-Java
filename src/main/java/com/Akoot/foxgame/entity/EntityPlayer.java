package com.Akoot.foxgame.entity;

import com.Akoot.foxgame.Foxgame;
import com.Akoot.foxgame.input.KeyboardHandler;
import com.Akoot.foxgame.item.clothes.*;
import com.Akoot.foxgame.item.hat.*;
import com.Akoot.foxgame.util.Color;
import com.Akoot.foxgame.util.ResourceLocation;
import com.Akoot.foxgame.util.Texture;

public class EntityPlayer extends EntityLiving
{
	private double hunger;
	private double maxHunger;

	public EntityPlayer(Foxgame game, String username)
	{
		super(game, username);
		this.texture = new Texture(new ResourceLocation("assets/textures/entity/player/base/idle.png"));
		this.height = 100;
		this.width = 50;
		this.speed = 8;
		this.jumpHeight = 8;
		this.color = Color.getColor(0xffffff);
		this.maxHunger = 50.0;
		this.hunger = 45.0;
		this.friction = 1;
		this.setClothing();
		System.out.println(this.x + ":" + this.y);
	}

	public void setClothing()
	{
		Shirt shirt = new Shirt();
		Pants pants = new Pants();
		Boots boots = new Boots();
		Hair hair = new Hair("2");
		
		if(1 < 2) //if user does not have custom clothes
		{
			shirt.setColor(Color.getRandomColor());
			pants.setColor(Color.getColor(0x343434));
			boots.setColor(Color.getRandomColor());
			hair.setColor(Color.getColor(0x1a1a1a));
		}
		
		this.clothing.add(shirt);
		this.clothing.add(pants);
		this.clothing.add(boots);
		this.clothing.add(hair);
		
		if(1 < 2) //if user has hat
		{
			Hat hat = new BaseballCap();
			hat.setColor(Color.getRandomColor());
			this.setHat(hat);
		}
	}

	@Override
	public void init()
	{
		startX = 200;
		startY = 200;
		reset();
	}

	public void reset()
	{
		this.x = startX;
		this.y = startY;
		health = 100;
		speedl = 0;
		speedr = 0;
		grav = 0;
	}

	@Override
	public void tick()
	{
		//System.out.println("tick");
		//System.out.println("is ther a stage? " + (stage != null ? "yes!" : "no..."));
		
		
		
		double speed = KeyboardHandler.isKeyDown(32) ? 5 : 1.25;
		if(KeyboardHandler.isKeyDown(68) || KeyboardHandler.isKeyDown(262)) {x += (speed * friction); scaleX = defScale; inertia += speed;}
		if(KeyboardHandler.isKeyDown(65) || KeyboardHandler.isKeyDown(263)) {x -= (speed * friction); scaleX = -(defScale); inertia -= speed;}
		if(KeyboardHandler.isKeyDown(83) || KeyboardHandler.isKeyDown(264)) y += speed;
		if(KeyboardHandler.isKeyDown(87) || KeyboardHandler.isKeyDown(265)) y -= speed;
		
		if(inertia > 10) inertia = 10;
		else if(inertia < -10) inertia = -10;
		
		if(inertia < 0) inertia ++;
		else if(inertia > 0) inertia --;
		
		x += inertia;
		

		//		for(EntityLiving entity: game.currentLevel.getLivingEntities())
		//		{
		//			if(this.hitTest(entity) && entity != this)
		//			{
		//				health--;
		//				System.out.println("OW");
		//			}
		//		}
	}

	@Override
	public void renderBody()
	{
		//stage.drawTexture(x, y, width * scale, height * scale, texture, color);
		stage.drawTexture(x - (scaleX < 0 ? width * scaleX : 0), y, width * scaleX, height * scaleY, texture);
		
		//Draw hitbox
		stage.drawRect(x, y, width, height, Color.getColor(0x00ff00, 0.5));
		
		for(Clothes clothes: clothing)
		{
			clothes.x = x;
			clothes.y = y;
			clothes.scaleX = scaleX;
			clothes.scaleY = scaleY;
		}
	}

	public double getMaxHunger()
	{
		return maxHunger;
	}

	public double getHunger()
	{
		return hunger;
	}
}
