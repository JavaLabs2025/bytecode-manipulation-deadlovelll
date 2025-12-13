package org.example.abc_visitor;

import org.example.structs.MethodInfo;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class AbcVisitor extends MethodVisitor {

    private final MethodInfo methodInfo;

    public AbcVisitor(int api, MethodInfo methodInfo) {
        super(api);
        this.methodInfo = methodInfo;
    }

    /// ///// a-метрика
    @Override
    public void visitVarInsn(int opcode, int var) {
        if (opcode >= Opcodes.ISTORE && opcode <= Opcodes.ASTORE) {
            methodInfo.assignments++;
        }
        super.visitVarInsn(opcode, var);
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        methodInfo.assignments++;
        super.visitIincInsn(var, increment);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (opcode == Opcodes.PUTFIELD || opcode == Opcodes.PUTSTATIC) {
            methodInfo.assignments++;
        }
        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitInsn(int opcode) {
        switch (opcode) {
            case Opcodes.IASTORE:
            case Opcodes.LASTORE:
            case Opcodes.FASTORE:
            case Opcodes.DASTORE:
            case Opcodes.AASTORE:
            case Opcodes.BASTORE:
            case Opcodes.CASTORE:
            case Opcodes.SASTORE:
                methodInfo.assignments++;
                break;
        }
        super.visitInsn(opcode);
    }

    /// //////// b-метрика
    @Override
    public void visitMethodInsn(
            int opcode,
            String owner,
            String name,
            String descriptor,
            boolean isInterface
    ) {
        methodInfo.branches++;
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitInvokeDynamicInsn(
            String name,
            String descriptor,
            Handle bootstrapMethodHandle,
            Object... bootstrapMethodArguments
    ) {
        methodInfo.branches++;
        super.visitInvokeDynamicInsn(
                name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments
        );
    }

    /// ///////// c-метрика
    @Override
    public void visitJumpInsn(int opcode, Label label) {
        if (opcode != Opcodes.GOTO) {
            methodInfo.conditions++;
        }
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitTableSwitchInsn(
            int min,
            int max,
            Label dflt,
            Label... labels
    ) {
        methodInfo.conditions++;
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(
            Label dflt,
            int[] keys,
            Label[] labels
    ) {
        methodInfo.conditions++;
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }
}
