package com.example.demoEL;

import com.example.demoEL.conf_copel.MapaDeDatos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ognl.OgnlException;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.time.LocalTime;
import java.util.*;

@SpringBootApplication
@EnableAutoConfiguration
//@ImportResource("classpath:productConfig.xml")
public class DemoElApplication {


    public static void main(String[] args) throws OgnlException {
        SpringApplication app = new SpringApplication(DemoElApplication.class);
        app.setAdditionalProfiles("Product");
        app.run(DemoElApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctxt) {
        return args -> {
            LocalTime init = LocalTime.now();
/*            System.setProperty("spring.profiles.active", "Product");
            ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"classpath:productConfig.xml"}, ctxt);

            StandardEvaluationContext context = new StandardEvaluationContext();
            MyBean myBean = ctxt.getBean(MyBean.class);
            Map<String, Object> beansMap = new HashMap();
            Arrays.stream(ctxt.getBeanDefinitionNames())
                    .filter(name -> !name.contains("."))
                    .forEach(name ->
               beansMap.put(name, ctxt.getBean(name))
            );
            Arrays.stream(ac.getBeanDefinitionNames())
                    .filter(name -> !name.contains("."))
                    .forEach(name ->
                            beansMap.put(name, ac.getBean(name))
                    );
            context.setRootObject(beansMap);
            ExpressionParser expressionParser = new SpelExpressionParser();
            Expression expression = expressionParser.parseExpression("[myBean].getValue('Isra') + ' Hello World'");
            System.out.println(expression.getValue(context));

            SyncConf syncConf = (SyncConf)ac.getBean("confProducto");
            List<Record> records = new ArrayList<>();
            for(int i = 0 ; i < 5 ; i++) {
                records.add(new Record("Data"+i, "Data2"+i, "Data3"+i));
            }
            List<Map<String, Object>> result = syncConf.execCalculationRule1(beansMap, records);
            ObjectMapper mapper = new ObjectMapper();
            //Converting the Object to JSONString

            result.stream().forEach(record -> {
                String jsonString = null;
                try {
                    Map<String, Object> inner = new HashMap<>();
                    inner.put("Inner", record);
                    jsonString = mapper.writeValueAsString(inner);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                System.out.println(jsonString);
            });
  */
            System.out.println("POC ------------------------------------------------------------------------>" );
            ObjectMapper mapper = new ObjectMapper();

            ClassPathXmlApplicationContext poc = new ClassPathXmlApplicationContext(new String[]{"classpath:ProducConfigPoC.xml"}, ctxt);
            Map<String, Object>beansMapPOC = new HashMap<>();
            Map<String, Object>entradaPOC = new HashMap<>();
            entradaPOC.put("entrada1", "Hello");
            entradaPOC.put("entrada2", 25);
            entradaPOC.put("entrada3", "Some Data ");

            Arrays.stream(poc.getBeanDefinitionNames())
                    .filter(name -> !name.contains("."))
                    .forEach(name ->
                            beansMapPOC.put(name, poc.getBean(name))
                    );

            Arrays.stream(ctxt.getBeanDefinitionNames())
                    .filter(name -> !name.contains("."))
                    .forEach(name ->
                            beansMapPOC.put(name, ctxt.getBean(name))
                    );
            beansMapPOC.put("registroOrigen", entradaPOC);

            System.out.println("\n\n");
            System.out.println("Inicia POC --------------DataMapProductoSimple---------------------------------------------------------->" );

            MapaDeDatos mapaDeDatos = poc.getBean("DataMapProductoSimple", MapaDeDatos.class);
            String jsoMapa= mapper.writeValueAsString(mapaDeDatos);
            MapaDeDatos mapaDeDatosRead = mapper.readValue(jsoMapa,  MapaDeDatos.class);
            //mapper  - Object mapper para generar el Json de salida
            //beansMapPOC  - Mapa que contiene los beans disponibles en el contexto
            //mapaDeDatos  - Bean que contiene la configuración del mapa a ser utilizado por el proceso
            processAndPrintResult(mapper, beansMapPOC, mapaDeDatosRead);
            System.out.print("\n");
            System.out.println("End First Process-  DataMapProductoSimple ----------------------------------------------------------------------->" );
            System.out.println("\n\n");
            System.out.println("Inicia POC --------------DataMapProductoComplejo1---------------------------------------------------------->" );
            MapaDeDatos mapaDeDatos1 = poc.getBean("DataMapProductoComplejo1", MapaDeDatos.class);
            processAndPrintResult(mapper, beansMapPOC, mapaDeDatos1);
            System.out.print("\n");
            System.out.println("End Second Process-----DataMapProductoComplejo1------------------------------------------------------------------->" );

            System.out.println("\n\n");
            System.out.println("Inicia POC --------------DataMapProductoMultiple---------------------------------------------------------->" );

            MapaDeDatos mapaDeDatos2 = poc.getBean("DataMapProductoMultiple", MapaDeDatos.class);
            processAndPrintResult(mapper, beansMapPOC, mapaDeDatos2);
            System.out.print("\n");
            System.out.println("End POC --------------DataMapProductoMultiple---------------------------------------------------------->" );
            System.out.println("Init --->" + init);
            System.out.println("End --->" + LocalTime.now());
        };
    }

    private void processAndPrintResult(ObjectMapper mapper, Map<String, Object> beansMapPOC, MapaDeDatos mapaDeDatos) {
        //Se crea contexto a ser utilizado por Expression Language de Spring
        StandardEvaluationContext contextoDeMapeo = new StandardEvaluationContext(beansMapPOC);

        //Realiza el procedimiento de mapeo de dato conforma a la configuracion dada en XML
        List<Map<String, Object>> respond =  mapaDeDatos.aplicarMapaDeDatos(contextoDeMapeo);
        respond.stream().forEach(record -> {
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(record);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            JSONObject jsonObj = new JSONObject(jsonString);
            System.out.println(jsonObj.toString(2));
        });
    }

/*
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctxt) {
		return args -> {
			ExpressionParser expressionParser = new SpelExpressionParser();
			Expression expression = expressionParser.parseExpression("'Any string'");
			String result = (String) expression.getValue();
			System.out.println(result);
			Map<String, Object> model = new HashMap<>();
			model.put("data1", new Data1());
			MyBean myBean = ctxt.getBean(MyBean.class);
			model.put("myBean",myBean);
			EvaluationContext context = new StandardEvaluationContext(model);

			Object expr = Ognl.parseExpression("data1.myDate");
			OgnlContext ctx = new OgnlContext();
			Date value = (Date) Ognl.getValue(expr, ctx, model);
			System.out.println(value);

			StandardEvaluationContext context1 = new StandardEvaluationContext(model);
			ExpressionParser expressionParser1 = new SpelExpressionParser();
			Object value1 = expressionParser1.parseExpression("[data1].myDate").getValue(context1);
			System.out.println(value1);
			ExpressionParser expressionParser2 = new SpelExpressionParser();
			Object value2 = expressionParser2.parseExpression("[myBean].getNameBean()").getValue(context1);
			System.out.println(value2);


			Object value3 = expressionParser2.parseExpression("3 + [myBean].getDataInt()").getValue(context1);
			System.out.println(value3);
			//model.put("tiendas1", expressionParser2.parseExpression("{1,2,3,4}").getValue());
			context1.setVariable("tiendas1",expressionParser2.parseExpression("{1,2,3,4}").getValue() );

			Object value4 = expressionParser2.parseExpression("#tiendas1.?[#this>2].contains(4)").getValue(context1);
			System.out.println(value4);

		};
	}

			static class Data1{
		String name = "example";
		Date myDate = new Date();

		public Date getMyDate(){
			return myDate;
		}

		public String getHelloName(String greeting){
			return name + greeting;
		}
	}

 */
}
