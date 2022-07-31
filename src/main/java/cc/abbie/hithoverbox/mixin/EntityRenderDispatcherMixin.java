package cc.abbie.hithoverbox.mixin;

import cc.abbie.hithoverbox.client.HitHoverBoxClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    private static boolean playerIsLookingAt(Entity entity) {
        return entity.equals(Minecraft.getInstance().crosshairPickEntity);
    }
    @Inject(method = "renderHitbox", at = @At("HEAD"), cancellable = true)
    private static void onlyRenderOnHoveredEntity(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, float f, CallbackInfo ci) {
        if (!playerIsLookingAt(entity) && HitHoverBoxClient.enabled) {
            ci.cancel();
        }
    }
}
