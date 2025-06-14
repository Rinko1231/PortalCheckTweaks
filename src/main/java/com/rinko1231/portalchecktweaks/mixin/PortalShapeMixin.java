package com.rinko1231.portalchecktweaks.mixin;

import com.rinko1231.portalchecktweaks.config.PortalCheckTweaksConfig;
import net.minecraft.BlockUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = PortalShape.class)
public abstract class PortalShapeMixin {

    @Inject(method = "createPortalInfo", at = @At("HEAD"), cancellable = true)
    private static void createPortalInfo2(ServerLevel serverLevel, BlockUtil.FoundRectangle p_259931_, Direction.Axis p_259901_, Vec3 p_259630_, Entity entity, Vec3 p_260043_, float p_259853_, float p_259667_, CallbackInfoReturnable<PortalInfo> cir) {
        BlockPos blockpos = p_259931_.minCorner;
        BlockState blockstate = serverLevel.getBlockState(blockpos);
        Direction.Axis direction$axis = blockstate.getOptionalValue(BlockStateProperties.HORIZONTAL_AXIS).orElse(Direction.Axis.X);
        double d0 = (double) p_259931_.axis1Size;
        double d1 = (double) p_259931_.axis2Size;
        EntityDimensions entitydimensions = entity.getDimensions(entity.getPose());
        int i = p_259901_ == direction$axis ? 0 : 90;
        Vec3 vec3 = p_259901_ == direction$axis ? p_260043_ : new Vec3(p_260043_.z, p_260043_.y, -p_260043_.x);
        double d2 = (double) entitydimensions.width / 2.0D + (d0 - (double) entitydimensions.width) * p_259630_.x();
        double d3 = (d1 - (double) entitydimensions.height) * p_259630_.y();
        double d4 = 0.5D + p_259630_.z();
        boolean flag = direction$axis == Direction.Axis.X;
        Vec3 vec31 = new Vec3((double) blockpos.getX() + (flag ? d2 : d4), (double) blockpos.getY() + d3, (double) blockpos.getZ() + (flag ? d4 : d2));
        //如果设定完全不检测，就跳过检测
        if (PortalCheckTweaksConfig.disableCollisionCheck.get())
            cir.setReturnValue(new PortalInfo(vec31, vec3, p_259853_ + (float) i, p_259667_));
        else if (PortalCheckTweaksConfig.onlyForPlayer.get()) {
            //如果只为玩家开启检测
            if (entity instanceof Player) {
                //且确实是玩家
                Vec3 newPos = Portal$findCollisionFreePosition(vec31, serverLevel, entity, entitydimensions);
                cir.setReturnValue(new PortalInfo(newPos, vec3, p_259853_ + (float) i, p_259667_));
            } else
                //不是玩家就不检测
                cir.setReturnValue(new PortalInfo(vec31, vec3, p_259853_ + (float) i, p_259667_));
        } else if (entity instanceof LivingEntity) {
            //非生物没必要检测
            Vec3 newPos = Portal$findCollisionFreePosition(vec31, serverLevel, entity, entitydimensions);
            cir.setReturnValue(new PortalInfo(newPos, vec3, p_259853_ + (float) i, p_259667_));
        } else
            cir.setReturnValue(new PortalInfo(vec31, vec3, p_259853_ + (float) i, p_259667_));
    }

    @Unique
    private static Vec3 Portal$findCollisionFreePosition(Vec3 position, ServerLevel serverLevel, Entity entity, EntityDimensions entityDimensions) {
        if (!(entityDimensions.width > 4.0F) && !(entityDimensions.height > 4.0F)) {
            double halfHeight = (double) entityDimensions.height / 2.0D;
            Vec3 posCenter = position.add(0.0D, halfHeight, 0.0D);
            VoxelShape voxelshape = Shapes.create(AABB.ofSize(posCenter, (double) entityDimensions.width, 0.0D, (double) entityDimensions.width).expandTowards(0.0D, 1.0D, 0.0D).inflate(1.0E-6D));
            Optional<Vec3> optional = serverLevel.findFreePosition(entity, voxelshape, posCenter, (double) entityDimensions.width, (double) entityDimensions.height, (double) entityDimensions.width);
            Optional<Vec3> optional1 = optional.map((p_259019_) -> {
                return p_259019_.subtract(0.0D, halfHeight, 0.0D);
            });
            return optional1.orElse(position);
        } else {
            return position;
        }
    }

}
