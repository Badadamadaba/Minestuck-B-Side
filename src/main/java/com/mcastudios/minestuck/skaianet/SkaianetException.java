package com.mcastudios.minestuck.skaianet;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class SkaianetException extends Exception
{
	private final Component textComponent;
	
	public SkaianetException(String translationKey, Object... args)
	{
		this(new TranslatableComponent(translationKey, args));
	}
	
	public SkaianetException(Component textComponent)
	{
		this.textComponent = textComponent;
	}
	
	public SkaianetException(Component textComponent, String message)
	{
		super(message);
		this.textComponent = textComponent;
	}
	
	public Component getTextComponent()
	{
		return textComponent;
	}
	
	@FunctionalInterface
	public interface SkaianetConsumer<T>
	{
		void consume(T t) throws SkaianetException;
	}
}