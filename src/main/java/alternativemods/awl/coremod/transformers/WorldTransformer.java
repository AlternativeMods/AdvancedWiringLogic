package alternativemods.awl.coremod.transformers;

import alternativemods.awl.coremod.AWLCoreMod;
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
    public byte[] transform(String name, String transformedName, byte[] basicClass){
        if(name.equals("net.minecraft.block.Block")){
        	ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);

            for(MethodNode m : classNode.methods){
            	if(m.name.equals("canProvidePower") && m.name.equals("()Z")){
            		Iterator iter = m.instructions.iterator();
                	
                	while(iter.hasNext()){
                		AbstractInsnNode node = (AbstractInsnNode) iter.next();
                		if(node.getOpcode() == Opcodes.ICONST_0){
                			m.instructions.remove(node);
                			m.instructions.insert(new InsnNode(Opcodes.ICONST_1));
                			break;
                		}
                	}
            	}
                if(m.name.equals("onNeighborBlockChange") && m.desc.equals("(Lnet/minecraft/world/World;IIILnet/minecraft/block/Block;)V")){
                    Iterator iter = m.instructions.iterator();

                    while(iter.hasNext()){
                        AbstractInsnNode node = (AbstractInsnNode) iter.next();
                        if(node.getOpcode() == Opcodes.RETURN){
                            InsnList list = new InsnList();

                            Label lbl1_lbl = new Label();
                            LabelNode lbl = new LabelNode(lbl1_lbl);

                            list.add(new TypeInsnNode(Opcodes.NEW, "alternativemods/awl/util/Point"));
                            list.add(new InsnNode(Opcodes.DUP));

                            list.add(new VarInsnNode(Opcodes.ILOAD, 2));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 3));
                            list.add(new VarInsnNode(Opcodes.ILOAD, 4));
                            list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "alternativemods/awl/util/Point", "<init>", "(III)V"));
                            list.add(new VarInsnNode(Opcodes.ASTORE, 6));

                            list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));
                            list.add(new JumpInsnNode(Opcodes.IFNULL, lbl));

                            list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 6));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "isWireStartingAt", "(Lnet/minecraft/world/World;Lalternativemods/awl/api/util/AbstractPoint;)Z"));
                            list.add(new JumpInsnNode(Opcodes.IFEQ, lbl));

                            list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            list.add(new VarInsnNode(Opcodes.ALOAD, 6));
                            list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "work", "(Lnet/minecraft/world/World;Lalternativemods/awl/api/util/AbstractPoint;)V"));

                            list.add(lbl);
                            m.instructions.insertBefore(node, list);
                            break;
                        }
                    }
                }
            }

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(cw);
            AWLCoreMod.logger.debug("Successfully patched class {}", name);
            return cw.toByteArray();
        }
    	if(name.equals("net.minecraft.world.World")){
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);

            for(MethodNode m : classNode.methods){
                if(m.name.equals("isBlockIndirectlyGettingPowered") && m.desc.equals("(III)Z")){
            		Iterator iter = m.instructions.iterator();
                	
                	while(iter.hasNext()){
                		AbstractInsnNode node = (AbstractInsnNode) iter.next();
                		if(node.getOpcode() == Opcodes.ALOAD){
                			InsnList list = new InsnList();

                            Label lbl1_lbl = new Label();
                            LabelNode lbl = new LabelNode(lbl1_lbl);

                			list.add(new TypeInsnNode(Opcodes.NEW, "alternativemods/awl/util/Point"));
                			list.add(new InsnNode(Opcodes.DUP));
                			list.add(new VarInsnNode(Opcodes.ILOAD, 1));
                			list.add(new VarInsnNode(Opcodes.ILOAD, 2));
                			list.add(new VarInsnNode(Opcodes.ILOAD, 3));
                			list.add(new MethodInsnNode(Opcodes.INVOKESPECIAL, "alternativemods/awl/util/Point", "<init>", "(III)V"));
                			list.add(new VarInsnNode(Opcodes.ASTORE, 4));

                			list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));

                			list.add(new JumpInsnNode(Opcodes.IFNULL, lbl));

                			list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));
                			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                			list.add(new VarInsnNode(Opcodes.ALOAD, 4));
                			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "isWireEndingAt", "(Lnet/minecraft/world/World;Lalternativemods/awl/api/util/AbstractPoint;)Z"));
                			list.add(new JumpInsnNode(Opcodes.IFEQ, lbl));
                			
                			list.add(new FieldInsnNode(Opcodes.GETSTATIC, "alternativemods/awl/Main", "wiresContainer", "Lalternativemods/awl/manager/WiresContainer;"));
                			list.add(new VarInsnNode(Opcodes.ALOAD, 0));
                			list.add(new VarInsnNode(Opcodes.ALOAD, 4));
                			list.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "alternativemods/awl/manager/WiresContainer", "isBlockPowered", "(Lnet/minecraft/world/World;Lalternativemods/awl/api/util/AbstractPoint;)Z"));
                			list.add(new JumpInsnNode(Opcodes.IFEQ, lbl));
                			
                			list.add(new InsnNode(Opcodes.ICONST_1));
                			list.add(new InsnNode(Opcodes.IRETURN));

                			list.add(lbl);
                			m.instructions.insertBefore(node, list);
                			break;
                		}
                	}
                }
            }

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(cw);
            AWLCoreMod.logger.debug("Successfully patched class {}", name);
            return cw.toByteArray();
        }

        return basicClass;
    }
}
