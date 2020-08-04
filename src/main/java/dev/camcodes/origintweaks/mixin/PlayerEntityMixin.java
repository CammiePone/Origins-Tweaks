package dev.camcodes.origintweaks.mixin;

import io.github.apace100.origins.power.PowerTypes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity
{
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
	private void handleFallDamage(float fallDistance, float damageMultiplier, CallbackInfoReturnable<Boolean> info)
	{
		if(PowerTypes.PHASING.isActive(this) && this.getBlockState() != Blocks.AIR.getDefaultState()) info.setReturnValue(false);
	}

	@Inject(method = "tick", at = @At("HEAD"))
	private void tick(CallbackInfo info)
	{
		if(PowerTypes.TAILWIND.isActive(this)) flyingSpeed *= 1.5D;
	}
}
