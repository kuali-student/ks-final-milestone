package org.kuali.student.commons.ui.mvc.rebind;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import org.kuali.student.commons.ui.mvc.client.HasMetadata;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Called by the GWT compiler to generate an implementation for any class implementing HasMetadata
 */
public class MetadataGenerator extends Generator {

    // inherited generator method
    /**
     * Generates the implementation class, which extends the class implementing HasMetadata
     */
    @Override
    public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
        logger.log(Type.DEBUG, "Generating " + typeName);

        String className = null;
        String packageName = null;
        JClassType classType = null;

        TypeOracle typeOracle = context.getTypeOracle();

        try {

            // get classType and save instance variables
            classType = typeOracle.getType(typeName);
            packageName = classType.getPackage().getName();
            className = classType.getName() + "MetadataImpl";
            className = className.replaceAll("\\.", "\\_");

            // Generate class source code
            PrintWriter pw = null;
            pw = context.tryCreate(logger, packageName, className);
            if (pw == null) {
                logger.log(TreeLogger.DEBUG, "Type already generated, ignoring.");
            } else {
                // init composer, set class properties, create source writer
                ClassSourceFileComposerFactory composer = null;
                composer = new ClassSourceFileComposerFactory(packageName, className);

                composer.addImport("java.util.Set");
                composer.addImport("java.util.HashSet");
                composer.addImport(HasMetadata.class.getName());
                if (classType.isInterface() != null) {
                    composer.addImplementedInterface(typeName);
                } else {
                    composer.setSuperclass(typeName);
                }
                SourceWriter writer = composer.createSourceWriter(context, pw);

                // create type hierarchy list
                writer.println("private final Set<String> typeHierarchy = new HashSet<String>();");
                writer.println("private final String definitionClassname = \"" + formatTypeName(typeName) + "\";");

                // start constructor source generation
                writer.println("private " + className + "() { ");
                writer.indent();
                writer.println("super();");

                // build type hierarchy
                Set<String> interfaces = new HashSet<String>();
                getAllInterfaces(classType, interfaces);
                for (String s : interfaces) {
                    writer.println("typeHierarchy.add(\"" + s + "\");");
                }

                // end constructor source generation
                writer.outdent();
                writer.println("}");

                // add default impl methods
                writer.println("public Set<String> getTypeHierarchy() {");
                writer.indent();
                writer.println("return typeHierarchy;");
                writer.outdent();
                writer.println("}");

                writer.println("public Class<? extends HasMetadata> getDefinitionClass() {");
                writer.indent();
                writer.println("return " + typeName + ".class;");
                writer.outdent();
                writer.println("}");

                writer.println("public boolean isAssignableFrom(HasMetadata o) {");
                writer.indent();
                writer.println("return o.getTypeHierarchy().contains(definitionClassname);");
                writer.outdent();
                writer.println("}");

                writer.println("public boolean isAssignableTo(HasMetadata o) {");
                writer.indent();
                writer.println("return getTypeHierarchy().contains(o.getFormattedDefinitionClassname());");
                writer.outdent();
                writer.println("}");

                writer.println("public HasMetadata newInstance() {");
                writer.indent();
                writer.println("return (HasMetadata) new " + className + "();");
                writer.outdent();
                writer.println("}");

                writer.println("public String getFormattedDefinitionClassname() {");
                writer.indent();
                writer.println("return definitionClassname;");
                writer.outdent();
                writer.println("}");

                // end class definition
                writer.outdent();
                writer.println("}");

                // commit generated class
                context.commit(logger, pw);

            }

        } catch (Exception e) {

            // record to logger that Map generation threw an exception
            logger.log(TreeLogger.ERROR, "Error generating MVCEvent wrapper", e);

        }

        // return the fully qualified name of the class generated
        return packageName + "." + className;

    }

    private void getAllInterfaces(JClassType type, Set<String> set) {
        if (type != null) {
            String s = type.getPackage().getName() + "." + type.getName();
            if (!set.contains(s)) {
                set.add(s);
                JClassType[] interfaces = type.getImplementedInterfaces();
                for (JClassType tmp : interfaces) {
                    getAllInterfaces(tmp, set);
                }
                getAllInterfaces(type.getSuperclass(), set);
            }
        }
    }

    private String formatTypeName(String typeName) {
        return typeName.replaceAll("\\$", "\\.");
    }

}