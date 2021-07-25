package twilightforest.entity.passive;

import net.minecraft.world.level.material.Material;
import net.minecraft.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;

// TODO: See Bunny
import net.minecraft.world.entity.AgableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class SquirrelEntity extends Animal {

	protected static final Ingredient SEEDS = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);

	public SquirrelEntity(EntityType<? extends SquirrelEntity> type, Level world) {
		super(type, world);

		// maybe this will help them move cuter?
		this.maxUpStep = 1;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.38F));
		this.goalSelector.addGoal(2, new TemptGoal(this, 1.0F, true, SEEDS));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class, 2.0F, 0.8F, 1.4F));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0F));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.25F));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	public static AttributeSupplier.Builder registerAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 1.0D)
				.add(Attributes.MOVEMENT_SPEED, 0.3D);
	}

	@Override
	public boolean causeFallDamage(float distance, float multiplier) {
		return false;
	}

	@Override
	public float getEyeHeight(Pose pose) {
		return this.getBbHeight() * 0.7F;
	}

	@Override
	public float getWalkTargetValue(BlockPos pos) {
		// prefer standing on leaves
		Material underMaterial = this.level.getBlockState(pos.below()).getMaterial();
		if (underMaterial == Material.LEAVES) {
			return 12.0F;
		}
		if (underMaterial == Material.WOOD) {
			return 15.0F;
		}
		if (underMaterial == Material.GRASS) {
			return 10.0F;
		}
		// default to just prefering lighter areas
		return this.level.getMaxLocalRawBrightness(pos) - 0.5F;
	}

	@Nullable
	@Override
	public AgableMob getBreedOffspring(ServerLevel world, AgableMob ageableEntity) {
		return null;
	}
}