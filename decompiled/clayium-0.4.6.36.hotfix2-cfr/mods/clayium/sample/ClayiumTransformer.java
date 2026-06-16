/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.FMLLaunchHandler
 *  cpw.mods.fml.relauncher.Side
 *  net.minecraft.launchwrapper.IClassTransformer
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.Opcodes
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.InsnList
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package mods.clayium.sample;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import mods.clayium.sample.ClayiumTransformerLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ClayiumTransformer
implements IClassTransformer,
Opcodes {
    private static final String TARGET_CLASS_NAME = "net.minecraft.src.TargetClass";

    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (FMLLaunchHandler.side() != Side.CLIENT || !name.equals(TARGET_CLASS_NAME)) {
            // empty if block
        }
        try {
            if (name.equals("net.minecraft.client.renderer.WorldRenderer")) {
                return this.replaceClass(bytes, "net\\minecraft\\client\\renderer\\WorldRenderer.class");
            }
            if (name.equals("net.minecraft.client.renderer.EntityRenderer")) {
                return this.replaceClass(bytes, "net\\minecraft\\client\\renderer\\EntityRenderer.class");
            }
            if (name.equals("net.minecraft.client.renderer.RenderGlobal")) {
                return this.replaceClass(bytes, "net\\minecraft\\client\\renderer\\RenderGlobal.class");
            }
            return bytes;
        }
        catch (Exception e) {
            throw new RuntimeException("failed : TutorialTransformer loading", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private byte[] replaceClass(byte[] bytes, String targetClass) throws IOException {
        ZipFile zf = null;
        InputStream zi = null;
        try {
            zf = new ZipFile(ClayiumTransformerLoadingPlugin.location);
            ZipEntry ze = zf.getEntry(targetClass);
            if (ze != null) {
                int ret;
                zi = zf.getInputStream(ze);
                int len = (int)ze.getSize();
                bytes = new byte[len];
                int MAX_READ = 1024;
                for (int readed = 0; readed < len; readed += ret) {
                    int readsize = MAX_READ;
                    if (len - readed < MAX_READ) {
                        readsize = len - readed;
                    }
                    if ((ret = zi.read(bytes, readed, readsize)) == -1) break;
                }
            }
            byte[] len = bytes;
            return len;
        }
        catch (Exception e) {
            zi = new FileInputStream(ClayiumTransformerLoadingPlugin.location + "\\" + targetClass);
            int len = zi.available();
            if (zi != null) {
                int ret;
                bytes = new byte[len];
                int MAX_READ = 1024;
                for (int readed = 0; readed < len; readed += ret) {
                    int readsize = MAX_READ;
                    if (len - readed < MAX_READ) {
                        readsize = len - readed;
                    }
                    if ((ret = zi.read(bytes, readed, readsize)) == -1) break;
                }
                zi.close();
                byte[] byArray = bytes;
                return byArray;
            }
        }
        finally {
            if (zi != null) {
                zi.close();
            }
            if (zf != null) {
                zf.close();
            }
        }
        return bytes;
    }

    private byte[] hookDoRenderLivingMethod(byte[] bytes) {
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept((ClassVisitor)cnode, 0);
        String targetMethodName = "doRenderLiving";
        String targetMethoddesc = "(Lnet/minecraft/entity/EntityLiving;DDDFF)V";
        MethodNode mnode = null;
        for (MethodNode curMnode : cnode.methods) {
            if (!targetMethodName.equals(curMnode.name) || !targetMethoddesc.equals(curMnode.desc)) continue;
            mnode = curMnode;
            break;
        }
        if (mnode != null) {
            InsnList overrideList = new InsnList();
            overrideList.add((AbstractInsnNode)new VarInsnNode(25, 1));
            overrideList.add((AbstractInsnNode)new VarInsnNode(24, 2));
            overrideList.add((AbstractInsnNode)new VarInsnNode(24, 4));
            overrideList.add((AbstractInsnNode)new VarInsnNode(24, 6));
            overrideList.add((AbstractInsnNode)new MethodInsnNode(184, "tutorial/test", "passTestRender", "(LEntityLiving;DDD)V"));
            mnode.instructions.insert(mnode.instructions.get(1), overrideList);
            ClassWriter cw = new ClassWriter(3);
            cnode.accept((ClassVisitor)cw);
            bytes = cw.toByteArray();
        }
        return bytes;
    }
}

