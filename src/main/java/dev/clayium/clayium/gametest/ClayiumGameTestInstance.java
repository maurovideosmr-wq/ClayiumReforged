package dev.clayium.clayium.gametest;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

final class ClayiumGameTestInstance extends GameTestInstance {
    static final MapCodec<ClayiumGameTestInstance> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Identifier.CODEC.fieldOf("function").forGetter(ClayiumGameTestInstance::functionId),
            TestData.CODEC.forGetter(ClayiumGameTestInstance::infoForCodec)
    ).apply(instance, ClayiumGameTestInstance::new));

    private final Identifier functionId;

    ClayiumGameTestInstance(Identifier functionId, TestData<Holder<TestEnvironmentDefinition<?>>> info) {
        super(info);
        this.functionId = functionId;
    }

    @Override
    public void run(GameTestHelper helper) {
        ClayiumGameTests.run(this.functionId, helper);
    }

    @Override
    public MapCodec<ClayiumGameTestInstance> codec() {
        return CODEC;
    }

    @Override
    protected MutableComponent typeDescription() {
        return Component.literal("Clayium direct function");
    }

    @Override
    public Component describe() {
        return this.describeType()
                .append(this.descriptionRow("test_instance.description.function", this.functionId.toString()))
                .append(this.describeInfo());
    }

    private Identifier functionId() {
        return this.functionId;
    }

    private TestData<Holder<TestEnvironmentDefinition<?>>> infoForCodec() {
        return this.info();
    }
}
