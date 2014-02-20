package alternativemods.awl.coremod.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.Iterator;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 16:49
 */
public class WorldTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(name.equals("net.minecraft.world.World")) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);

            Iterator<MethodNode> methods = classNode.methods.iterator();
            while(methods.hasNext())
            {
                MethodNode m = methods.next();

                if (m.name.equals("getBlockPowerInput") && m.desc.equals("(III)I")) {
                    System.out.println(m.desc);
                    System.out.println("********* Inside target method 1!");

                    Iterator iter = m.instructions.iterator();

                    while (iter.hasNext()) {
                        AbstractInsnNode node = (AbstractInsnNode) iter.next();
                        if(node.getOpcode() == Opcodes.ICONST_0) {
                            System.out.println("I'M IN!");
                            InsnList list = new InsnList();

                            list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));

                            list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 2));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 3));

                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/World", "provider", "Lnet/minecraft/world/WorldProvider;"));
                            list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/WorldProvider", "dimensionId", "I"));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "isBlockPoweredByLogic", "(IIII)Z"));

                            Label l1 = new Label();
                            list.add(new JumpInsnNode(Opcodes.IFEQ, new LabelNode(l1)));

                            list.add(new LdcInsnNode(15));
                            list.add(new InsnNode(Opcodes.IRETURN));
                            list.add(new LabelNode(l1));
                            m.instructions.insertBefore(node, list);
                            break;
                        }
                    }

                    System.out.println("Patching Complete!");
                }
                if(m.name.equals("isBlockIndirectlyGettingPowered") && m.desc.equals("(III)Z")) {
                    System.out.println(m.desc);
                    System.out.println("********* Inside target method 2!");

                    Iterator iter = m.instructions.iterator();

                    while (iter.hasNext()) {
                        AbstractInsnNode node = (AbstractInsnNode) iter.next();
                        if(node.getOpcode() == Opcodes.ALOAD) {
                            System.out.println("I'M IN!");
                            InsnList list = new InsnList();

                            list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));

                            list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 2));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 3));

                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/World", "provider", "Lnet/minecraft/world/WorldProvider;"));
                            list.add(new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/world/WorldProvider", "dimensionId", "I"));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "isBlockPoweredByLogic", "(IIII)Z"));

                            Label l1 = new Label();
                            list.add(new JumpInsnNode(Opcodes.IFEQ, new LabelNode(l1)));

                            list.add(new InsnNode(Opcodes.ICONST_1));
                            list.add(new InsnNode(Opcodes.IRETURN));
                            list.add(new LabelNode(l1));
                            m.instructions.insertBefore(node, list);
                            break;
                        }
                    }

                    System.out.println("Patching Complete!");
                }
                if(m.name.equals("notifyBlockChange") && m.desc.equals("(IIILnet/minecraft/block/Block;)V")) {
                    System.out.println(m.desc);
                    System.out.println("********* Inside target method 3!");

                    Iterator iter = m.instructions.iterator();

                    while (iter.hasNext()) {
                        AbstractInsnNode node = (AbstractInsnNode) iter.next();
                        if(node.getOpcode() == Opcodes.ALOAD) {
                            System.out.println("I'M IN!");
                            InsnList list = new InsnList();

                            list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new TypeInsnNode(Opcodes.NEW, "alternativemods/awl/util/Point"));
                            list.add(new InsnNode(Opcodes.DUP));

                            list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 2));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 3));
                            list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "alternativemods/awl/util/Point", "<init>", "(III)V"));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "isWireStartingAt", "(Lnet/minecraft/world/World;Lalternativemods/awl/util/Point;)Z"));

                            Label l1 = new Label();
                            list.add(new JumpInsnNode(Opcodes.IFEQ, new LabelNode(l1)));

                            list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            list.add(new TypeInsnNode(Opcodes.NEW, "alternativemods/awl/util/Point"));
                            list.add(new InsnNode(Opcodes.DUP));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 2));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 3));
                            list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "alternativemods/awl/util/Point", "<init>", "(III)V"));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "notifyWireEnds", "(Lnet/minecraft/world/World;Lalternativemods/awl/util/Point;)V"));

                            list.add(new LabelNode(l1));
                            m.instructions.insertBefore(node, list);
                            break;
                        }
                    }

                    System.out.println("Patching Complete!");
                }
            }
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(cw);
            return cw.toByteArray();
        }

        return basicClass;
    }
}