package com.apigcc.example;

import com.apigcc.common.URI;
import com.apigcc.parser.VisitorParser;
import com.apigcc.render.AsciidocHtmlRender;
import com.apigcc.render.AsciidocRender;
import com.apigcc.schema.Project;
import com.apigcc.spring.SpringParserStrategy;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.javaparser.utils.SourceRoot;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @title Apigcc示例文档
 * @description 通过javadoc设置文档描述信息
 * 优先级大于通过Environment.description()设置的值
 * @readme 所有接口均使用Https调用
 * /app路径下的接口为app专用
 * /mini路径下的接口为小程序专用
 */
public class ApigccTest {

    @Test
    public void test() throws IOException {

        Project project = new Project();
        project.setId("test");
        project.setName("测试项目");

        VisitorParser visitorParser = new VisitorParser();
        visitorParser.setParserStrategy(new SpringParserStrategy());

        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        typeSolver.add(new JavaParserTypeSolver("D:/apigcc/apigcc-demo-spring/src/main/java"));
        typeSolver.add(new ReflectionTypeSolver(false));

        ParserConfiguration parserConfiguration = new ParserConfiguration()
                .setSymbolResolver(new JavaSymbolSolver(typeSolver));

        SourceRoot root = new SourceRoot(Paths.get("D:/apigcc/apigcc-demo-spring/src/main/java"), parserConfiguration);
        for (ParseResult<CompilationUnit> result : root.tryToParse()) {
            if(result.isSuccessful() && result.getResult().isPresent()){
                result.getResult().get().accept(visitorParser, project);
            }
        }

//        project.getBooks().forEach((key,value)->{
//            value.getChapters().forEach(chapter -> {
//                chapter.getSections().forEach(section -> {
//                    System.out.println(section.getId()+" "+section.getUri());
//                    System.out.println(ObjectMappers.pretty(section.getParameter()));
//                });
//            });
//        });

//        System.out.println(ObjectMappers.pretty(project));

        new AsciidocRender().render(project);

        new AsciidocHtmlRender().render(project);

    }

    @Test
    public void testApigcc() {
//        Options options = new Options()
//                .source(Paths.get("src", "test", "java"))
//                .ignore("ResponseEntity")
//                .jar(Paths.get("src/test/resources/lib/apigcc-model-1.0-SNAPSHOT.jar"))
//                .id("apigcc")
//                .title("示例接口文档")
//                .description("示例接口文档，使用默认模板");
//        Apigcc apigcc = new Apigcc(options);
//        apigcc.lookup().build();
//
//        Path buildAdoc = options.getOutPath().resolve(options.getId() + ".adoc");
//        Path template = options.getOutPath().resolve("../../src/test/resources/template.adoc");
//        Path templateHtml = options.getOutPath().resolve("../../src/test/resources/template.html");
//        Path resultHtml = options.getOutPath().resolve("diff.html");
//
//        FileMatcher fileMatcher = new FileMatcher();
//        int changed = fileMatcher.compare(template, buildAdoc);
//        if(changed>0){
//            fileMatcher.rederHtml(templateHtml, resultHtml);
//        }
//
//        System.out.println("BUILD SUCCESS");
    }

}