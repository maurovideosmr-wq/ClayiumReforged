/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.launchwrapper.IClassTransformer
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.objectweb.asm.ClassReader
 *  org.objectweb.asm.ClassVisitor
 *  org.objectweb.asm.ClassWriter
 *  org.objectweb.asm.Opcodes
 *  org.objectweb.asm.tree.AbstractInsnNode
 *  org.objectweb.asm.tree.ClassNode
 *  org.objectweb.asm.tree.FieldInsnNode
 *  org.objectweb.asm.tree.InsnNode
 *  org.objectweb.asm.tree.LdcInsnNode
 *  org.objectweb.asm.tree.MethodInsnNode
 *  org.objectweb.asm.tree.MethodNode
 *  org.objectweb.asm.tree.VarInsnNode
 */
package mods.clayium.asm;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import mods.clayium.asm.ClayiumTransformerLoadingPlugin;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ClayiumTransformer
implements IClassTransformer,
Opcodes {
    private static List<String> targets = new ArrayList<String>();
    private static List<MethodKey> methodList;
    private AbstractInsnNode[] test;
    private AbstractInsnNode[] test2;
    private Logger logger = LogManager.getLogger((String)"ClayiumTransformer");

    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (!targets.contains(name) && !targets.contains(transformedName)) {
            return bytes;
        }
        try {
            return this.hookMethod(bytes);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("failed : ClayiumTransformer loading  name=" + name + " transformedName=" + transformedName + "", e);
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

    private MethodNode getMethodNode(ClassNode classNode, String className, String methodName, String methodDesc) {
        if (classNode == null || classNode.name == null || !classNode.name.equals(className)) {
            return null;
        }
        MethodKey key = new MethodKey(className, methodName, methodDesc);
        if (methodList.contains(key)) {
            return null;
        }
        for (MethodNode curMnode : classNode.methods) {
            if (!methodName.equals(curMnode.name) || !methodDesc.equals(curMnode.desc)) continue;
            methodList.add(key);
            this.logger.info("Get MethodNode (" + className + ", " + methodName + ", " + methodDesc + ")");
            return curMnode;
        }
        return null;
    }

    private AbstractInsnNode convertInsnD2F(AbstractInsnNode insn) {
        if (insn instanceof LdcInsnNode && ((LdcInsnNode)insn).cst instanceof Double) {
            double d = (Double)((LdcInsnNode)insn).cst;
            if (d == 2.0) {
                return new InsnNode(13);
            }
            return new LdcInsnNode((Object)Float.valueOf((float)d));
        }
        if (insn.getOpcode() == 14) {
            return new InsnNode(11);
        }
        if (insn.getOpcode() == 15) {
            return new InsnNode(12);
        }
        return null;
    }

    private byte[] hookMethod(byte[] bytes) {
        AbstractInsnNode insn0;
        ArrayList<AbstractInsnNode> targetInsnList;
        boolean flag = false;
        boolean deobf = false;
        ClassNode cnode = new ClassNode();
        ClassReader reader = new ClassReader(bytes);
        reader.accept((ClassVisitor)cnode, 0);
        MethodNode mnode = this.getMethodNode(cnode, "net/minecraft/client/multiplayer/PlayerControllerMP", "getBlockReachDistance", "()F");
        if (mnode == null) {
            mnode = this.getMethodNode(cnode, "net/minecraft/client/multiplayer/PlayerControllerMP", "func_78757_d", "()F");
            deobf = false;
        } else {
            deobf = true;
        }
        if (mnode != null) {
            flag = true;
            targetInsnList = new ArrayList<AbstractInsnNode>();
            for (AbstractInsnNode inst : mnode.instructions.toArray()) {
                if (inst.getNext() == null || inst.getNext().getOpcode() != 174) continue;
                targetInsnList.add(inst);
            }
            for (AbstractInsnNode targetInsn : targetInsnList) {
                mnode.instructions.insert(targetInsn, (AbstractInsnNode)new MethodInsnNode(184, "mods/clayium/item/gadget/GadgetLongArm", "hookBlockReachDistance", "(F)F", false));
            }
        }
        if ((mnode = this.getMethodNode(cnode, "net/minecraft/client/renderer/EntityRenderer", "getMouseOver", "(F)V")) == null) {
            mnode = this.getMethodNode(cnode, "net/minecraft/client/renderer/EntityRenderer", "func_78473_a", "(F)V");
            deobf = false;
        } else {
            deobf = true;
        }
        if (mnode != null) {
            flag = true;
            targetInsnList = new ArrayList();
            int d0var = -1;
            int d1var = -1;
            for (AbstractInsnNode insn : mnode.instructions.toArray()) {
                if (d0var == -1 && insn.getOpcode() == 182 && insn instanceof MethodInsnNode && ((MethodInsnNode)insn).desc.equals("()F") && ((MethodInsnNode)insn).name.equals(deobf ? "getBlockReachDistance" : "func_78757_d") && ((MethodInsnNode)insn).owner.equals("net/minecraft/client/multiplayer/PlayerControllerMP") && (insn0 = insn.getNext()) != null && insn0.getOpcode() == 141 && (insn0 = insn0.getNext()) != null && insn0.getOpcode() == 57 && insn0 instanceof VarInsnNode) {
                    d0var = ((VarInsnNode)insn0).var;
                }
                if (d1var == -1 && insn.getOpcode() == 24 && insn instanceof VarInsnNode && ((VarInsnNode)insn).var == d0var && (insn0 = insn.getNext()) != null && insn0.getOpcode() == 57 && insn0 instanceof VarInsnNode) {
                    d1var = ((VarInsnNode)insn0).var;
                }
                if (insn.getOpcode() == 182 && insn instanceof MethodInsnNode && ((MethodInsnNode)insn).desc.equals("(DDD)Lnet/minecraft/util/Vec3;") && ((MethodInsnNode)insn).name.equals(deobf ? "addVector" : "func_72441_c") && ((MethodInsnNode)insn).owner.equals("net/minecraft/util/Vec3")) {
                    d0var = -2;
                    d1var = -2;
                }
                if ((insn0 = insn.getNext()) != null && insn0.getOpcode() == 57 && insn0 instanceof VarInsnNode && d0var == ((VarInsnNode)insn0).var && this.convertInsnD2F(insn) != null) {
                    targetInsnList.add(insn);
                }
                if ((insn0 = insn.getNext()) != null && insn0.getOpcode() == 57 && insn0 instanceof VarInsnNode && d1var == ((VarInsnNode)insn0).var && this.convertInsnD2F(insn) != null) {
                    targetInsnList.add(insn);
                }
                if ((insn0 = insn.getPrevious()) == null || insn0.getOpcode() != 24 || !(insn0 instanceof VarInsnNode) || d0var != ((VarInsnNode)insn0).var || (insn0 = insn.getNext()) == null || insn0.getOpcode() != 151 || this.convertInsnD2F(insn) == null) continue;
                targetInsnList.add(insn);
            }
            for (AbstractInsnNode targetInsn : targetInsnList) {
                AbstractInsnNode insn02 = this.convertInsnD2F(targetInsn);
                mnode.instructions.set(targetInsn, insn02);
                mnode.instructions.insert(insn02, (AbstractInsnNode)new MethodInsnNode(184, "mods/clayium/item/gadget/GadgetLongArm", "hookBlockReachDistance", "(F)F", false));
                mnode.instructions.insert(insn02.getNext(), (AbstractInsnNode)new InsnNode(141));
            }
            AbstractInsnNode[] array = mnode.instructions.toArray();
            this.logger.info("Transformed " + mnode.name);
        }
        if ((mnode = this.getMethodNode(cnode, "net/minecraft/network/NetHandlerPlayServer", "processUseEntity", "(Lnet/minecraft/network/play/client/C02PacketUseEntity;)V")) == null) {
            mnode = this.getMethodNode(cnode, "net/minecraft/network/NetHandlerPlayServer", "func_147340_a", "(Lnet/minecraft/network/play/client/C02PacketUseEntity;)V");
            deobf = false;
        } else {
            deobf = true;
        }
        if (mnode != null) {
            flag = true;
            targetInsnList = new ArrayList();
            int flagvar = -1;
            int d0var = -1;
            for (AbstractInsnNode insn : mnode.instructions.toArray()) {
                if (flagvar == -1 && d0var == -1 && insn.getOpcode() == 182 && insn instanceof MethodInsnNode && ((MethodInsnNode)insn).desc.equals("(Lnet/minecraft/entity/Entity;)Z") && ((MethodInsnNode)insn).name.equals(deobf ? "canEntityBeSeen" : "func_70685_l") && ((MethodInsnNode)insn).owner.equals("net/minecraft/entity/player/EntityPlayerMP") && (insn0 = insn.getNext()) != null && insn0.getOpcode() == 54 && insn0 instanceof VarInsnNode) {
                    flagvar = ((VarInsnNode)insn0).var;
                }
                insn0 = insn.getNext();
                if (flagvar >= 0 && d0var == -1 && insn0 != null && insn0.getOpcode() == 57 && insn0 instanceof VarInsnNode) {
                    if (this.convertInsnD2F(insn) == null) continue;
                    d0var = ((VarInsnNode)insn0).var;
                    targetInsnList.add(insn);
                    continue;
                }
                if (insn0 == null || insn0.getOpcode() != 57 || !(insn0 instanceof VarInsnNode) || d0var != ((VarInsnNode)insn0).var || this.convertInsnD2F(insn) == null) continue;
                targetInsnList.add(insn);
            }
            for (AbstractInsnNode targetInsn : targetInsnList) {
                AbstractInsnNode insn03 = this.convertInsnD2F(targetInsn);
                mnode.instructions.set(targetInsn, insn03);
                mnode.instructions.insert(insn03, (AbstractInsnNode)new VarInsnNode(25, 0));
                insn03 = insn03.getNext();
                mnode.instructions.insert(insn03, (AbstractInsnNode)new FieldInsnNode(180, "net/minecraft/network/NetHandlerPlayServer", deobf ? "playerEntity" : "field_147369_b", "Lnet/minecraft/entity/player/EntityPlayerMP;"));
                insn03 = insn03.getNext();
                mnode.instructions.insert(insn03, (AbstractInsnNode)new MethodInsnNode(184, "mods/clayium/item/gadget/GadgetLongArm", "hookBlockReachDistanceSq", "(FLnet/minecraft/entity/player/EntityPlayer;)F", false));
                insn03 = insn03.getNext();
                mnode.instructions.insert(insn03, (AbstractInsnNode)new InsnNode(141));
            }
            AbstractInsnNode[] array = mnode.instructions.toArray();
            this.logger.info("Transformed " + mnode.name);
        }
        if (flag) {
            ClassWriter cw = new ClassWriter(3);
            cnode.accept((ClassVisitor)cw);
            bytes = cw.toByteArray();
        }
        return bytes;
    }

    static {
        targets.add("net.minecraft.client.multiplayer.PlayerControllerMP");
        targets.add("net.minecraft.client.renderer.EntityRenderer");
        targets.add("net.minecraft.network.NetHandlerPlayServer");
        methodList = new ArrayList<MethodKey>();
    }

    private static class MethodKey {
        String className;
        String methodName;
        String methodDesc;

        public MethodKey(String className, String methodName, String methodDesc) {
            this.className = className;
            this.methodName = methodName;
            this.methodDesc = methodDesc;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + (this.className == null ? 0 : this.className.hashCode());
            result = 31 * result + (this.methodDesc == null ? 0 : this.methodDesc.hashCode());
            result = 31 * result + (this.methodName == null ? 0 : this.methodName.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (this.getClass() != obj.getClass()) {
                return false;
            }
            MethodKey other = (MethodKey)obj;
            if (this.className == null ? other.className != null : !this.className.equals(other.className)) {
                return false;
            }
            if (this.methodDesc == null ? other.methodDesc != null : !this.methodDesc.equals(other.methodDesc)) {
                return false;
            }
            return !(this.methodName == null ? other.methodName != null : !this.methodName.equals(other.methodName));
        }
    }
}

