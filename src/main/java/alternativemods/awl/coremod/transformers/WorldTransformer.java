package alternativemods.awl.coremod.transformers;

import alternativemods.awl.coremod.AWLCoreMod;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Author: Lordmau5
 * Date: 20.02.14
 * Time: 16:49
 */
public class WorldTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass){
        if(name.equals("CLASS?!")){
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);

            for(MethodNode m : classNode.methods){
                if(m.name.equals("METHOD?!") && m.desc.equals("DESC?!")){

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
